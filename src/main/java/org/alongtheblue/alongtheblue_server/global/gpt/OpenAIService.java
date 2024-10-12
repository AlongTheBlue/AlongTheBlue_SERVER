package org.alongtheblue.alongtheblue_server.global.gpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public List<String> getHashtags(String description) {
        // OpenAI API 호출 및 Mono<List<String>>를 동기적으로 List<String>으로 변환
        return extractHashtags(description).block();
    }

    private Mono<List<String>> extractHashtags(String description) {
        // OpenAI API 호출
        return generateHashtags(description) // Mono<String> 응답
                .flatMap(response -> {
                    try {
                        // JSON 파싱
                        JsonNode root = objectMapper.readTree(response);

                        // 해시태그가 있는 content 필드 추출
                        String hashtagsContent = root.path("choices")
                                .get(0)
                                .path("message")
                                .path("content")
                                .asText();

                        // 해시태그 문자열을 공백 기준으로 나누어 리스트로 변환
                        List<String> hashtags = Arrays.asList(hashtagsContent.split(" "));

                        // Mono<List<String>> 형태로 반환
                        return Mono.just(hashtags);
                    } catch (Exception e) {
                        // 에러 발생 시 Mono 에러 반환
                        return Mono.error(new RuntimeException("해시태그 추출 중 오류 발생", e));
                    }
                });
    }

    private Mono<String> generateHashtags(String description) {
        // 입력된 설명에서 첫 두 문장만 추출
        String[] sentences = description.split("(?<=\\.)"); // 문장을 '.' 기준으로 분리
        String shortenedDescription = sentences.length >= 2 ? sentences[0] + sentences[1] : sentences[0];

        // 한글로 해시태그를 요청하는 프롬프트 작성
        String prompt = "다음 글을 보고 5개의 해시태그 만들어줘 \n" + shortenedDescription;

        // 요청 바디 구성
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo-0125");
        body.put("messages", new Object[] {
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", prompt);
                }}
        });

        // WebClient를 이용한 API 호출
        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json; charset=UTF-8")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class);
    }
}

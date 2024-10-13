package org.alongtheblue.alongtheblue_server.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.alongtheblue.alongtheblue_server.global.data.global.CustomPage;
import org.alongtheblue.alongtheblue_server.global.data.global.CustomPageMixIn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // MixIn 등록
        mapper.addMixIn(CustomPage.class, CustomPageMixIn.class);
        return mapper;
    }
}

package org.alongtheblue.alongtheblue_server.global.data.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cafe")
public class CafeController {

    private final CafeService cafeService;

    @GetMapping("/fetch")
    public Mono<Void> fetchAndSavecafe() {
        return cafeService.fetchAndSaveData();
    }

    // 1단계 저장 100개만
    @GetMapping("/save")
    public void saveCafes() {
        cafeService.saveCafes();
    }
    // 2단계 저장
    @GetMapping("/save/introduction")
    public void saveCafesIntroduction() {
        cafeService.processSaveIntroduction();
    }
    // 3단계 저장
    @GetMapping("/save/info")
    public void saveCafesInfo() {
        cafeService.processSaveInfo();
    }
    // 4단계 저장
    @GetMapping("/save/image")
    public void saveCafesImages() {
        cafeService.processSaveImage();
    }


    @GetMapping("/detail/all")
    public List<CafeDTO> getAll() {
        return cafeService.getAll();
    }


    @GetMapping("/home")
    public List<CafeDTO> homecafe() {
        return cafeService.homeCafe();
    }


    @GetMapping("/detail/{id}")
    public CafeDTO getCafe(@PathVariable Long id) {
        return cafeService.getCafe(id);
    }
}
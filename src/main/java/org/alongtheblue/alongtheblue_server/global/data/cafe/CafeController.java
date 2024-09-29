package org.alongtheblue.alongtheblue_server.global.data.cafe;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/detail/all")
    public List<CafeDTO> getcafe(){
        return cafeService.getCafe();
    }


    @GetMapping("/home")
    public List<CafeDTO> homecafe(){
        return cafeService.homeCafe();
    }
}

package org.alongtheblue.alongtheblue_server.global.data.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jejuDivision")
public class JejuDivisionController {

    private final JejuDivisionService jejuDivisionService;

    @GetMapping("/save")
    private void saveJejuDivision(){
        jejuDivisionService.saveJejuDivision();
    }
}

package org.alongtheblue.alongtheblue_server.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
    public String greeting() {
        return "Hello World!";
    }
}

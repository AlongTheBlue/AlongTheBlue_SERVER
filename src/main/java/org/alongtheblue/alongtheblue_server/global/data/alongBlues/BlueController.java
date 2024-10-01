package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blue")
public class BlueController {

    @Autowired
    private BlueService blueService;

    @GetMapping("/list")
    public ApiResponse<List<BlueResponseDto>> getAllBlues(){
        return blueService.getBlues();
    }
}

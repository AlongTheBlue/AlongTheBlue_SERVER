package org.alongtheblue.alongtheblue_server.global.data.blue;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blue")
@RequiredArgsConstructor
public class BlueController {

    private final BlueService blueService;

    @GetMapping("/list")
    public ApiResponse<List<BlueResponseDto>> getAllBlues(){
        return blueService.getBlues();
    }

    @GetMapping("/{id}")
    public ApiResponse<BlueResponseDto> getBlueById2(@PathVariable Long id){
        return blueService.getBlueById(id);
    }

}

package com.robot.friday.api;

import com.robot.friday.service.Mouth;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/api")
public class MusicController {

    private final Mouth mouth;

    @Autowired
    public MusicController(Mouth mouth) {
        this.mouth = mouth;
    }

    @GetMapping(value = "/song")
    @ApiOperation(value = "Music player interface")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "search and play"),
    })
    public String sing(@RequestParam("song") String song, String singer) {
        mouth.sing(song, singer);
        return "success";
    }

    @GetMapping(value = "/voice")
    @ApiOperation(value = "Reading interface")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "read"),
    })
    public void read(@RequestParam("lines") String lines) {
        mouth.speak(lines);
    }
}

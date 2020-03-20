package com.robot.friday.integration;

import com.robot.friday.integration.response.KugouSearchResponse;
import com.robot.friday.integration.response.KugouSongResponse;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;


@FeignClient(name = "kugouClient")
public interface KugouClient {

    @GetMapping()
    KugouSearchResponse searchSong(URI uri);

    @GetMapping()
    KugouSongResponse getPlayUrl(URI uri);

    @GetMapping()
    Response play(URI uri);

}

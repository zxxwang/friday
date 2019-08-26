package com.robot.friday.integration;

import com.robot.friday.integration.response.KugouSearchResponse;
import com.robot.friday.integration.response.KugouSongResponse;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;

import java.net.URI;

@FeignClient(name = "kugouClient")
public interface KugouClient {

    @RequestLine("GET")
    KugouSearchResponse searchSong(URI uri);

    @RequestLine("GET")
    KugouSongResponse getPlayUrl(URI uri);

    @RequestLine("GET")
    Response play(URI uri);

}

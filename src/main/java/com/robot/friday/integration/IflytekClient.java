package com.robot.friday.integration;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "iflytekClient", url = "${integration.iflytek.tts}")
public interface IflytekClient {

    @PostMapping(path = "/v1/tts", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    Response text2Sound(@RequestHeader("X-Param") String xParam, @RequestHeader("X-CurTime") String xCurTime,
                        @RequestHeader("X-CheckSum") String xCheckSum, @RequestHeader("X-Appid") String xAppid,
                        @RequestBody String body);
}

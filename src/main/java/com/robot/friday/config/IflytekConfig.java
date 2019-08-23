package com.robot.friday.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.ImmutableMap;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "iflytek")
public class IflytekConfig {

    private String appId;
    private String apiKey;
    private String aue;
    private String auf;
    private String speed;
    private String volume;
    private String pitch;
    private String voiceName;
    private String engineType;
    private String textType;

    public String genXParam() {
        ImmutableMap<String, String> param = new ImmutableMap.Builder<String, String>()
                .put("auf", auf)
                .put("aue", aue)
                .put("voice_name", voiceName)
                .put("speed", speed)
                .put("volume", volume)
                .put("pitch", pitch)
                .put("engine_type", engineType)
                .put("text_type", textType)
                .build();
        return JSONObject.toJSONString(param);
    }
}

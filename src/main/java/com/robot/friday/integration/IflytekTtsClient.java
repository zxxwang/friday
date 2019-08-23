package com.robot.friday.integration;

import com.robot.friday.config.IflytekConfig;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class IflytekTtsClient {

    private IflytekClient iflytekClient;
    private IflytekConfig iflytekConfig;

    @Autowired
    public IflytekTtsClient(IflytekClient iflytekClient, IflytekConfig iflytekConfig) {
        this.iflytekClient = iflytekClient;
        this.iflytekConfig = iflytekConfig;
    }

    public InputStream read(String words) {
        try {
            Response result = iflytekClient.text2Sound(getParam(), getCurTime(), getCheckSum(),
                    getAppId(), "text=" + URLEncoder.encode(words, "utf-8"));
            return new BufferedInputStream(result.body().asInputStream());
        } catch (Exception e) {
            log.error("讯飞文字转音频接口调用失败, {}", e);
        }
        return null;
    }

    private String getParam() {
        return new String(Base64.encodeBase64(iflytekConfig.genXParam()
                .getBytes(StandardCharsets.UTF_8)));
    }

    private String getCurTime() {
        return System.currentTimeMillis() / 1000L + "";
    }

    private String getCheckSum() {
        return DigestUtils.md5Hex(iflytekConfig.getApiKey() + getCurTime() + getParam());
    }

    private String getAppId() {
        return iflytekConfig.getAppId();
    }

}

package com.robot.friday;

import java.util.Map;
import java.util.stream.Collectors;

public class StringUtils {

    public static String urlBuilder(String url, Map<String, String> params) {
        String paramsStr = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        return url + "?" + paramsStr;
    }
}

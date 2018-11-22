package com.itmuch.thor.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author limu.zl
 */
@Slf4j
public class UrlUtil {
    private static String getQueryStringByMap(Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return "";
        }
        List<String> paramPairs = map.keySet()
                .stream()
                .map(key -> String.format("%s=%s", key, map.get(key)))
                .collect(Collectors.toList());

        return String.join("&", paramPairs);
    }

    @SuppressWarnings("unchecked")
    public static String getTargetUrl(String baseUrl, @Nullable Object params) {
        // 不带参数
        if (params == null) {
            return baseUrl;
        }

        // 带参数
        if (!(params instanceof Map)) {
            throw new IllegalArgumentException("参数非法，参数应该是个json");
        }
        Map map = (Map) params;
        String queryString = getQueryStringByMap(map);
        String targetUrl = baseUrl + "?" + queryString;
        log.info("targetUrl = {}, queryString = {}", targetUrl, queryString);
        return targetUrl;
    }
}

package com.itmuch.thor.controller;

import com.itmuch.thor.entity.Request;
import com.itmuch.thor.httpclient.WebClientUtil;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author limu.zl
 */
@RestController
public class ForwardController {
    @PostMapping("/forward")
    public Mono<Object> forward(@RequestBody @Valid Request request) {
        return forward(request.getBaseUrl(), request.getMethod(), request.getParams());
    }

    private static Mono<Object> forward(String baseUrl, String method, Object params) {
        if (HttpMethod.GET.name().equalsIgnoreCase(method)) {
            return WebClientUtil.get(baseUrl, params, Object.class);
        }
        if (HttpMethod.POST.name().equalsIgnoreCase(method)) {
            return WebClientUtil.postJSON(baseUrl, params, Object.class);
        }
        throw new IllegalArgumentException("fuck");
    }
}

package com.itmuch.thor.httpclient;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author limu.zl
 * ref：https://segmentfault.com/a/1190000012916413
 */
public class WebClientUtil {

    private static WebClient buildWebClient() {
        return WebClient.create();
    }

    public static <T> Mono<T> get(String baseUrl, Object params, Class<T> returnType) {

        return WebClientUtil.buildWebClient()
                .get()
                .uri(UrlUtil.getTargetUrl(baseUrl, params))
                .retrieve()
                .bodyToMono(returnType);
    }

    public static <T> Mono<T> get(String targetUrl, Object[] uriVariables, Class<T> returnType) {
        return WebClientUtil.buildWebClient()
                .get()
                .uri(targetUrl, uriVariables)
                .retrieve()
                .bodyToMono(returnType);
    }

    public static <T> Mono<T> postJSON(String baseUrl, Object request, Class<T> returnType) {
        Mono<Object> reqMono = Mono.just(request);
        return WebClientUtil.buildWebClient()
                .post()
                .uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(reqMono, Object.class)
                .retrieve()
                .bodyToMono(returnType);
    }

    public static <T> Mono<T> postForm(String baseUrl, MultiValueMap<String, String> formData, Class<T> returnType) {
        return WebClientUtil.buildWebClient()
                .post()
                .uri(baseUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(returnType);
    }


}

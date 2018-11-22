package com.itmuch.thor.httpclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class WebClientUtilTest {
    @Test
    public void testGet() {
        Mono<String> mono = WebClientUtil.get("http://localhost:8080/get?a={a}&b={b}",
                new Object[]{"a", "b"},
                String.class);
        System.out.println(mono.block());
    }

    @Test
    public void testPostForm() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.putIfAbsent("a", Collections.singletonList("aa"));
        formData.put("b", Collections.singletonList("bb"));

        Mono<String> json = WebClientUtil.postForm(
                "http://localhost:8080/postForm",
                formData,
                String.class);
        System.out.println(json.block());
    }

    @Test
    public void testPostJSON() {
        Req req = Req.builder().a("a")
                .b("b")
                .build();
        Mono<String> json = WebClientUtil.postJSON(
                "http://localhost:8080/postJSON",
                req,
                String.class);
        System.out.println(json.block());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Req {
    private String a;
    private String b;
}

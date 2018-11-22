package com.itmuch.thor.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

/**
 * @author limu.zl
 */
@Controller
public class TestController {
    @GetMapping("/get")
    @ResponseBody
    public Mono<Req> get(String a, String b) {
        return Mono.just(
                Req.builder()
                        .a(a)
                        .b(b)
                        .build()
        );
    }

    @PostMapping("postJSON")
    @ResponseBody
    public Mono<Req> post(@RequestBody Req req) {
        return Mono.just(req);
    }

    @PostMapping("postForm")
    @ResponseBody
    public Mono<Req> postForm(Req req) {
        return Mono.just(
                req
        );
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

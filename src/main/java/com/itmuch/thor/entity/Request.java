package com.itmuch.thor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Request {
    /**
     * 请求url
     */
    @URL
    public String baseUrl;

    /**
     * 请求方法
     */
    @Pattern(regexp = "GET|POST|DELETE|PUT")
    public String method;

    /**
     * 参数
     * - get请求：url参数，形如{"key":"value"}
     * - post请求：body
     */
    private Object params;
}

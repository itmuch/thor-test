package com.itmuch.thor.js;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;

@Slf4j
public class JavascriptEngineTest {
    @Data
    @AllArgsConstructor
    @Builder
    static class Request {
        private String temperatureUploadFrequency;
        private String humidityUploadFrequency;
        private Metadata metadata;

        @Data
        @AllArgsConstructor
        @Builder
        static class Metadata {
            private String ss_serialNumber;
        }
    }

    public static void main(String[] args) throws Exception {
        // 支持ES6语法：https://stackoverflow.com/questions/48911937/can-i-run-ecmascript-6-from-java-9-nashorn-engine
        System.setProperty("nashorn.args", "--language=es6 --no-deprecation-warning");

        ScriptEngine engine = new ScriptEngineManager()
                .getEngineByName("ECMAScript");

        if (!(engine instanceof Invocable)) {
            throw new IllegalArgumentException("参数非法！！");
        }

        // 加载JS
        FileReader reader = new FileReader("/Users/reno/develop/code_mine/thor/src/main/java/com/itmuch/thor/js/test2.js");
        engine.eval(reader);

        // 处理参数
        Request request = Request.builder()
                .temperatureUploadFrequency("aaaasdfa")
                .humidityUploadFrequency("bbbbb")
                .metadata(
                        Request.Metadata.builder()
                                .ss_serialNumber("编号")
                                .build()
                )
                .build();
        Object json = toJSON(engine, request);

        // 执行代码：用函数执行
        Invocable invoke = (Invocable) engine;
        Object test = invoke.invokeFunction("test", json);
        // 可以存储了
        log.info(new ObjectMapper().writeValueAsString(test));

        // 执行代码：直接执行，不打算使用
        engine.eval("var json = " + new ObjectMapper().writeValueAsString(request));
        Object eval = engine.eval("json.temperatureUploadFrequency + json.humidityUploadFrequency");
        System.out.println("=====" + eval);
    }

    /**
     * 将对象转换成JavaScript JSON对象
     *
     * @param engine engine
     * @param object 对象
     * @return JSON对象
     * @throws JsonProcessingException e
     * @throws ScriptException         e
     * @throws NoSuchMethodException   e
     */
    private static Object toJSON(ScriptEngine engine, Object object) throws JsonProcessingException, ScriptException, NoSuchMethodException {
        String jsonString = new ObjectMapper().writeValueAsString(object);
        Object json = engine.eval("JSON");
        Invocable invoke = (Invocable) engine;
        return invoke.invokeMethod(json, "parse", jsonString);
    }
}

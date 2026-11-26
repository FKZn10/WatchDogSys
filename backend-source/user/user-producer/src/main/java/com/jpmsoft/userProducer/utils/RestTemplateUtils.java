package com.jpmsoft.userProducer.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Deprecated
public class RestTemplateUtils {

    private static Object restTemplateToPost(String url, String request) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Object response = restTemplate.postForObject(url, new HttpEntity<>(request, headers), Object.class);
        return response;
    }
}

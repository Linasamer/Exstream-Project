package com.example.exstream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ThirdPartyService {

    @Value("${thirdparty.auth-url}")
    private String authUrl;

    @Value("${thirdparty.input-url}")
    private String inputUrl;

    private String authToken;

    public String authenticate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = new HashMap<>();
        request.put("username", "tenantadmin");
        request.put("password", "tddemo");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, entity, Map.class);

        // Assuming token is in `accessToken` field of the response
        authToken = (String) response.getBody().get("accessToken");
        return authToken;
    }

    public String sendInput(String xsrfToken, String accessToken, String xmlInput) {
//        if (authToken == null) {
//            authenticate();
//        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set("X-Xsrf-Token", xsrfToken);
        headers.set("Cookie", "XSRF-TOKEN=" + xsrfToken);

        HttpEntity<String> entity = new HttpEntity<>(xmlInput, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(inputUrl, entity, String.class);

        return response.getBody();
    }
}

package com.example.exstream.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ThirdPartyService {

    @Value("${thirdparty.auth-url}")
    private String authUrl;

    @Value("${thirdparty.input-url}")
    private String inputUrl;

    @Value("${thirdparty.xsrf-url}")
    private String xsrfUrl;

    @Value("${thirdparty.basic-auth}")
    private String basicAuth;

    private String authToken;
    private String xsrfToken;

    private String getXsrfToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(xsrfUrl, HttpMethod.GET, entity, Void.class);
            String setCookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
            if (setCookie != null && setCookie.contains("XSRF-TOKEN=")) {
                xsrfToken = setCookie.split("XSRF-TOKEN=")[1].split(";")[0];
            } else {
                throw new RuntimeException("Failed to retrieve XSRF token");
            }
            return xsrfToken;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve XSRF token: " + ex.getMessage(), ex);
        }
    }

    // Get Access Token
    public String authenticate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + basicAuth);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(authUrl, HttpMethod.POST, entity, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                authToken = (String) response.getBody().get("access_token");
                return authToken;
            } else {
                throw new RuntimeException("Failed to authenticate: Invalid response from server");
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new RuntimeException("Failed to authenticate: " + ex.getResponseBodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred during authentication", ex);
        }
    }

    // Send Input
    public String sendInput(String xmlInput) {
        if (authToken == null) {
            authToken = authenticate(); // Step 2: Authenticate using XSRF Token
            xsrfToken = getXsrfToken(); // Step 1: Get XSRF Token
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set("Authorization", "Bearer " + authToken);
        headers.set("X-Xsrf-Token", xsrfToken);
        headers.set("Cookie", "XSRF-TOKEN=" + xsrfToken);

        HttpEntity<String> entity = new HttpEntity<>(xmlInput, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(inputUrl, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new RuntimeException("Failed to send input: " + ex.getResponseBodyAsString(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred while sending input", ex);
        }
    }
}

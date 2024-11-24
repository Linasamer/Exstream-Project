package com.example.exstream.controller;

import com.example.exstream.service.ThirdPartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class ApiController {

    private final ThirdPartyService thirdPartyService;

    public ApiController(ThirdPartyService thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }

    @GetMapping("/auth")
    public ResponseEntity<String> getAuthToken() {
        String token = thirdPartyService.authenticate();
        return ResponseEntity.ok(token);
    }

    @PostMapping("/input")
    public ResponseEntity<String> sendInput(@RequestHeader(value = "X-Xsrf-Token") String xsrfToken,
                                            @RequestHeader(value = "Authorization") String authorization,
                                            @RequestBody String xmlInput) {
        String response = thirdPartyService.sendInput(xsrfToken, authorization, xmlInput);
        return ResponseEntity.ok(response);
    }
}

package com.example.exstream.controller;

import com.example.exstream.service.ThirdPartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<String> sendInput(@RequestBody String xmlInput, @RequestHeader(value = "Type") Integer type) {
        String response = thirdPartyService.sendInput( xmlInput, type);
        return ResponseEntity.ok(response);
    }


}

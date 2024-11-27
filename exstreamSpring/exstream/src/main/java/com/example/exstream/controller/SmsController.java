package com.example.exstream.controller;

import com.example.exstream.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
@CrossOrigin(origins = "*")
    public class SmsController {
    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(
            @RequestParam String to,
            @RequestParam String message) {
        String sid = smsService.sendSms(to, message);
        return ResponseEntity.ok("SMS sent successfully! SID: " + sid);
    }
}

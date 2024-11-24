package com.example.exstream.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final TwilioConfig twilioConfig;

    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public String sendSms(String to, String body) {
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+2" + to),
                new com.twilio.type.PhoneNumber(twilioConfig.getPhoneNumber()),
                body
        ).create();
        return message.getSid(); // Returns the SID of the message
    }
}

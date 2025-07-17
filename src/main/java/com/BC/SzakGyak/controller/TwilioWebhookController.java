package com.BC.SzakGyak.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class TwilioWebhookController {

    @PostMapping("/webhook/twilio")
    public ResponseEntity<String> receiveMessage(@RequestParam("From") String from,
                                                 @RequestParam("Body") String body) {

        System.out.println("Üzenet érkezett: " + body + " - Feladótól: " + from);
        String responseMessage = "<Response><Message>Üzeneted megkaptuk!</Message></Response>";


        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(responseMessage);
    }
}

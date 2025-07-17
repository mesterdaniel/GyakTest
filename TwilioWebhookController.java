package com.BC.SzakGyak.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class TwilioWebhookController {

    @PostMapping("/twilio/webhook")
    public ResponseEntity<String> receiveMessage(@RequestBody String payload) {
        // Logolják a payload adatokat, hogy lásd mi érkezik be
        System.out.println("Received payload: " + payload);

        // Ha minden rendben, 200 OK válasz küldése
        return ResponseEntity.ok("Received");
    }
}

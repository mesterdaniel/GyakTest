package com.BC.SzakGyak.controller;
import com.BC.SzakGyak.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class TwilioWebhookController {
   /* @Autowired
    private TwilioService twilioService;
    @PostMapping("/webhook/twilio")
    public ResponseEntity<String> receiveMessage(@RequestParam("From") String from,
                                                 @RequestParam("Body") String body) {

        System.out.println("Üzenet érkezett: " + body + " - Feladótól: " + from);
    String lowerCaseBody = body.trim().toLowerCase();
    String responseMessage;
    if (lowerCaseBody.equals("menu")) {
        responseMessage = twilioService.menu();
    } else if (lowerCaseBody.equals("1") || lowerCaseBody.equals("2")) {
        responseMessage = twilioService.menuReply(lowerCaseBody);
    } else {
        responseMessage = "<Response><Message>Üzeneted megkaptuk! Írj 'menu'-t a lehetőségekhez.</Message></Response>";
    }



        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(responseMessage);
    }*/
    @Autowired
    private TwilioService twilioService;
    private String buildTwimlResponse(String message) {
        return "<Response><Message>" + message + "</Message></Response>";
    }
    @PostMapping("/webhook/twilio")
    public ResponseEntity<String> receiveSms(@RequestParam("From") String from, @RequestParam("Body") String body) {
        String responseText = twilioService.handleIncomingMessage(from, body);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(buildTwimlResponse(responseText));
       
    }

    
}

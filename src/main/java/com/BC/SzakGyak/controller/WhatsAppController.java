package com.BC.SzakGyak.controller;


import com.BC.SzakGyak.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WhatsAppController {

    @Autowired
    private TwilioService twilioService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String phoneNumber, @RequestParam String message, Model model) {
        if (phoneNumber == null || phoneNumber.isEmpty() || (!phoneNumber.startsWith("+36") && phoneNumber.length() != 11)) {
            model.addAttribute("errorMessage", "Hibás telefonszám formátum! A számnak +36-tal kell kezdődnie és 11 karakter hosszúnak kell lennie.");
        }else{

            twilioService.sendMessage(phoneNumber, message);
            model.addAttribute("status", "Message sent to " + phoneNumber);

        }
        return "index"; 

    }

}

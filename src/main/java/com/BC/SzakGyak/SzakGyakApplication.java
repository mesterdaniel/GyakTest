package com.BC.SzakGyak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.BC.SzakGyak.WhatsAppSender.sendSticker;

@SpringBootApplication
public class SzakGyakApplication {

	public static void main(String[] args) {
		SpringApplication.run(SzakGyakApplication.class, args);

	}
}

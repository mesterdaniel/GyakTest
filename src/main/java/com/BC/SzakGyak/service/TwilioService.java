package com.BC.SzakGyak.service;

import com.twilio.Twilio;
import com.twilio.http.NetworkHttpClient;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromNumber;
    @Value("${https.proxyHost}")
    private String proxyHost;
    @Value("${https.proxyPort}")
    private int proxyPort;

    public void sendMessage(String to, String text) {
        //Twilio.init(accountSid, authToken);
        // Proxy beállítása
        HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
        HttpClientBuilder clientBuilder = HttpClientBuilder.create().setProxy(proxy);
        NetworkHttpClient httpClient = new NetworkHttpClient(clientBuilder);

        // Twilio kliens létrehozása proxyval
        TwilioRestClient client = new TwilioRestClient.Builder(accountSid, authToken)
                .httpClient(httpClient)
                .build();

        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber(fromNumber),
                text
        ).create(client);


        System.out.println("Message SID: " + message.getSid());
    }
}


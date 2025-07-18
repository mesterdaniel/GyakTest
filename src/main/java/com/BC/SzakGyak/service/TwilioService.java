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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TwilioService {

    private enum MenuState {
        MAIN_MENU,
        WAITING_FOR_SELECTION,
        HELP_MENU,
        SETTINGS_MENU
    }

    private class UserState {
        private MenuState currentState = MenuState.MAIN_MENU;

        public MenuState getCurrentState() {
            return currentState;
        }

        public void setCurrentState(MenuState s) {
            currentState = s;
        }
    }

//--------------------------------------------------------------------------------
    
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromNumber;
    /*
    @Value("${https.proxyHost}")
    private String proxyHost;
    @Value("${https.proxyPort}")
    private int proxyPort;
*/
    public void sendMessage(String to, String text) {
        Twilio.init(accountSid, authToken);
        // Proxy beállítása
       // HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
        //HttpClientBuilder clientBuilder = HttpClientBuilder.create().setProxy(proxy);
        //NetworkHttpClient httpClient = new NetworkHttpClient(clientBuilder);
/*
        // Twilio kliens létrehozása proxyval
        TwilioRestClient client = new TwilioRestClient.Builder(accountSid, authToken)
                .httpClient(httpClient)
                .build();
*/
        TwilioRestClient client=new TwilioRestClient.Builder(accountSid, authToken).build();
        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber(fromNumber),
                text
        ).create(client);


        System.out.println("Message SID: " + message.getSid());
    }
   /*  public String menu() {
        return "<Response><Message>Menü:" + "\n"+
                "1:Info" +"\n"+
                "2:Elérhetőség" +
                "</Message></Response>";
    }

    public String menuReply(String body) {

        String response;
        switch (body) {
            case "1":
                response = "Info: Nincs info";
                break;
            case "2":
                response = "Elérhetőség: XYZ";
                break;
            default:
                response = "Jelenleg nincs ilyen opció :(";
                break;
        }


        return "<Response><Message>" + response + "</Message></Response>";
    }*/
    private final Map<String, UserState> userStates = new ConcurrentHashMap<>();

    public String handleIncomingMessage(String from, String body) {
        String msg = body == null ? "" : body.trim().toLowerCase();

        UserState userState = userStates.computeIfAbsent(from, f -> new UserState());

        switch (userState.getCurrentState()) {
            case MAIN_MENU:
                if ("menu".equals(msg)) {
                    userState.setCurrentState(MenuState.WAITING_FOR_SELECTION);
                    return buildMenu();
                }
                return "Írd be: menu a lehetőségekhez.";

            case WAITING_FOR_SELECTION:
                return handleMenuSelection(msg, userState);

            case HELP_MENU:
            case SETTINGS_MENU:
                if ("back".equals(msg)) {
                    userState.setCurrentState(MenuState.MAIN_MENU);
                    return "Visszatértél a főmenübe. Írd be: menu";
                }
                return "Ebben a menüben vagy. Írj 'back'-et a visszalépéshez.";

            default:
                userState.setCurrentState(MenuState.MAIN_MENU);
                return "Hiba történt, vissza a főmenübe. Írd be: menu";
        }
    }

    private String buildMenu() {
        return "1 - Help\n2 - Beállítások\n0 - Kilépés";
    }

    private String handleMenuSelection(String msg, UserState userState) {
        switch (msg) {
            case "1":
                userState.setCurrentState(MenuState.HELP_MENU);
                return "Help menü. Írj 'back'-et a visszalépéshez.";
            case "2":
                userState.setCurrentState(MenuState.SETTINGS_MENU);
                return "Beállítások menü. Írj 'back'-et a visszalépéshez.";
            case "0":
                userState.setCurrentState(MenuState.MAIN_MENU);
                return "Kiléptél a menüből.";
            default:
                return "Kérlek válassz: 1, 2 vagy 0.";
        }

    }
}


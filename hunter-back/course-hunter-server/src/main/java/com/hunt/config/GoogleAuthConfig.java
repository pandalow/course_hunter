package com.hunt.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;


@Configuration
public class GoogleAuthConfig {

//    @Value("${}")
    private String googleClientId = "234209469090-fjp91clg376k25enntk48kpaqscinutn.apps.googleusercontent.com";

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier(){
        // Create the verifier instance/Bean, Avoiding Creation Continuously;
        NetHttpTransport transport = new NetHttpTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        System.out.println("Initializing Google Verifier with Client ID: [" + googleClientId + "]");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.
                Builder(transport, jsonFactory).setAudience(
                Collections.singletonList(googleClientId)
        ).build();

        return verifier;
    }
}

package com.hunt.service;

public interface EmailService {
    void send(String to, String email, String from, String subject);
    String emailBuilder(String emailTemplate, String... strings);
}

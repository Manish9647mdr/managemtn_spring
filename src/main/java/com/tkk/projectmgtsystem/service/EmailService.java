package com.tkk.projectmgtsystem.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sentEmailWithToken(String userEmail, String link) throws MessagingException;
}

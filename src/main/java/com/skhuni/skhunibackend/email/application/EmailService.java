package com.skhuni.skhunibackend.email.application;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendEmail(String email) throws UnsupportedEncodingException, MessagingException;

}

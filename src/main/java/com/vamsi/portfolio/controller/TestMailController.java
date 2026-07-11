package com.vamsi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vamsi.portfolio.service.MailService;

@RestController
public class TestMailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/send-test-mail")
    public String sendMail() {

        mailService.sendMail(
                "vamsikannemadugu1334@gmail.com",
                "MavisPortfolio",
                "Congratulations! Your email service is working successfully.");

        return "Mail Sent Successfully";
    }
}
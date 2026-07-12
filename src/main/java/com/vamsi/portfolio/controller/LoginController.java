package com.vamsi.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vamsi.portfolio.service.AdminService;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email,
                                 Model model) {

        try {

            adminService.forgotPassword(email);

            model.addAttribute("message",
                    "Reset password link has been sent to your email.");

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute("message",
                    e.getMessage());
        }

        return "login";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam("token") String token,
                                    Model model) {

        model.addAttribute("token", token);

        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password,
                                Model model) {

        try {

            adminService.resetPassword(
                    token,
                    password,
                    passwordEncoder);

            model.addAttribute("message",
                    "Password changed successfully.");

            return "login";

        } catch (Exception e) {

            model.addAttribute("message",
                    "Invalid or expired reset link.");

            model.addAttribute("token", token);

            return "reset-password";
        }
    }
}
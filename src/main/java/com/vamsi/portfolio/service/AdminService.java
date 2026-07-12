package com.vamsi.portfolio.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vamsi.portfolio.model.Admin;
import com.vamsi.portfolio.repository.AdminRepository;

@Service
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("========== LOGIN ==========");
        System.out.println("Username entered : " + username);

        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> {

                    System.out.println("ADMIN NOT FOUND");

                    return new UsernameNotFoundException("Admin not found");
                });

        System.out.println("Admin Found : " + admin.getUsername());

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }

    public void forgotPassword(String email) {

        System.out.println("========== FORGOT PASSWORD ==========");
        System.out.println("Searching Email : " + email);

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> {

                    System.out.println("EMAIL NOT FOUND");

                    return new RuntimeException("Email not found");
                });

        System.out.println("Email Found : " + admin.getEmail());

        String token = UUID.randomUUID().toString();

        admin.setResetToken(token);
        admin.setTokenExpiry(LocalDateTime.now().plusMinutes(15));

        adminRepository.save(admin);

        String link =
                "https://mavisvisuals.onrender.com/reset-password?token=" + token;

        System.out.println("Reset Link : " + link);

        mailService.sendMail(
                admin.getEmail(),
                "Reset Password",
                "Click the link below to reset your password:\n\n" + link);

        System.out.println("Reset Mail Sent Successfully");
    }

    public void resetPassword(String token,
                              String newPassword,
                              PasswordEncoder passwordEncoder) {

        System.out.println("========== RESET PASSWORD ==========");

        Admin admin = adminRepository.findByResetToken(token)
                .orElseThrow(() -> {

                    System.out.println("INVALID TOKEN");

                    return new RuntimeException("Invalid token");
                });

        if (admin.getTokenExpiry().isBefore(LocalDateTime.now())) {

            System.out.println("TOKEN EXPIRED");

            throw new RuntimeException("Token expired");
        }

        admin.setPassword(passwordEncoder.encode(newPassword));

        admin.setResetToken(null);
        admin.setTokenExpiry(null);

        adminRepository.save(admin);

        System.out.println("Password Updated Successfully");
    }
}
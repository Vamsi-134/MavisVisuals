package com.vamsi.portfolio.service;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Admin not found"));

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }
    public void forgotPassword(String email) {

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        String token = UUID.randomUUID().toString();
        
        admin.setResetToken(token);
        admin.setTokenExpiry(LocalDateTime.now().plusMinutes(15));

        adminRepository.save(admin);

        String link =
                "http://localhost:8080/reset-password?token=" + token;

        mailService.sendMail(
                admin.getEmail(),
                "Reset Password",
                "Click the link below to reset your password:\n\n" + link);
    }
    public void resetPassword(String token,
            String newPassword,
            PasswordEncoder passwordEncoder) {

        Admin admin = adminRepository.findByResetToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid token"));

        if (admin.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        admin.setPassword(passwordEncoder.encode(newPassword));

        admin.setResetToken(null);
        admin.setTokenExpiry(null);

        adminRepository.save(admin);
    }
}
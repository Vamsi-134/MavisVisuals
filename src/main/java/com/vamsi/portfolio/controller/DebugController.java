package com.vamsi.portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vamsi.portfolio.model.Admin;
import com.vamsi.portfolio.repository.AdminRepository;

@RestController
public class DebugController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/debug-admin")
    public List<Admin> debugAdmin() {
        return adminRepository.findAll();
    }
}
package com.vamsi.portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vamsi.portfolio.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Optional<Admin> findByEmail(String email);

	Optional<Admin> findByResetToken(String resetToken);

    Optional<Admin> findByUsername(String username);

}
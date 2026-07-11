package com.vamsi.portfolio.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vamsi.portfolio.model.Media;

public interface MediaRepository extends JpaRepository<Media, Integer> {

    List<Media> findByCategory(String category);
    Optional<Media> findById(Integer id);
    long countByType(String type);
    Page<Media> findByCategory(String category, Pageable pageable);

    List<Media> findByCoverImageTrue();

    long countByCategory(String category);

    Media findFirstByCategory(String category);

}
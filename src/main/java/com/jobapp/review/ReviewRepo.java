package com.jobapp.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, String> {
    List<Review> findByCompanyCompanyId(String companyId);
}

package com.jobapp.jobapplication.review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews(String companyId);
    ReviewDTO createReview(String companyId, ReviewDTO review);
    ReviewDTO getReviewById(String companyId, String reviewId);
    ReviewDTO updateReview(String companyId, String reviewId, ReviewDTO review);
    void deleteReview(String companyId, String reviewId);
}

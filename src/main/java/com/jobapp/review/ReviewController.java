package com.jobapp.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Get all reviews
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(@PathVariable String companyId) {
        List<ReviewDTO> reviews = reviewService.getAllReviews(companyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // Create a new review
    @PostMapping("/reviews")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable String companyId ,
                                                  @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(companyId, reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }


    // Get a specific review by ID
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable String companyId,
                                                   @PathVariable String reviewId) {
        ReviewDTO review = reviewService.getReviewById(companyId, reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    // Update a review
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable String companyId,
                                                  @PathVariable String reviewId,
                                               @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(companyId, reviewId, reviewDTO);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // Delete a review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable String companyId, @PathVariable String reviewId) {
        reviewService.deleteReview(companyId, reviewId);
        return new ResponseEntity<>("Review deleted successfully!",
                HttpStatus.OK);
    }
}
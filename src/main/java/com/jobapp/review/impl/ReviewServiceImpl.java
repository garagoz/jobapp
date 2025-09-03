package com.jobapp.review.impl;

import com.jobapp.company.CompanyDTO;
import com.jobapp.company.CompanyService;
import com.jobapp.shared.exceptions.ResourceNotFoundException;
import com.jobapp.review.Review;
import com.jobapp.review.ReviewDTO;
import com.jobapp.review.ReviewRepo;
import com.jobapp.review.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepository;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;

    @Autowired
    public ReviewServiceImpl (ReviewRepo reviewRepository,
                              ModelMapper modelMapper,
                              CompanyService companyService){
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.companyService = companyService;
    }

    @Override
    public List<ReviewDTO> getAllReviews(String companyId) {
        CompanyDTO company = companyService.findCompanyById(companyId);
        List<Review> allReviews = reviewRepository.findByCompanyCompanyId(companyId);
        if(allReviews.isEmpty()) return List.of();

        return allReviews.stream().map((review) ->
                this.modelMapper.map(review, ReviewDTO.class)).toList();
    }

    @Override
    public ReviewDTO createReview(String companyId, ReviewDTO reviewDTO) {
        CompanyDTO company = companyService.findCompanyById(companyId);
        reviewDTO.setReviewId(UUID.randomUUID().toString());
        reviewDTO.setCompany(company);
        return this.modelMapper.map(reviewRepository.save(this.modelMapper
                .map(reviewDTO,Review.class)), ReviewDTO.class);
    }

    @Override
    public ReviewDTO getReviewById(String companyId, String reviewId) {
        CompanyDTO company = companyService.findCompanyById(companyId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with id " + reviewId + " not found!"
                ));
        return this.modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(String companyId, String reviewId,
                                  ReviewDTO reviewDTO) {
        CompanyDTO company = companyService.findCompanyById(companyId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with id " + reviewId + " not found!"
                ));

        review.setTitle(reviewDTO.getTitle());
        review.setDescription(reviewDTO.getDescription());
        review.setRating(reviewDTO.getRating());

        return this.modelMapper.map(reviewRepository.save(review), ReviewDTO.class);
    }

    @Override
    public void deleteReview(String companyId, String reviewId) {
        CompanyDTO company = companyService.findCompanyById(companyId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with id " + reviewId + " not found!"
                ));
        reviewRepository.delete(review);
    }
}

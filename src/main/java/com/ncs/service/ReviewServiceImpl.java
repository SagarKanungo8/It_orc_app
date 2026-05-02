package com.ncs.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.exception.ProductException;
import com.ncs.model.Product;
import com.ncs.model.Review;
import com.ncs.model.User;
import com.ncs.repository.ReviewRepo;
import com.ncs.request.ReviewRequest;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewRepo reviewRepo;
	@Autowired
	private ProductService productService;
	
	

	@Override
	public Review createReview(ReviewRequest reviewRequest, User user) throws ProductException{
		
		Product product = productService.findProductById(reviewRequest.getProductId());
		
		Review review = new Review();
		
		review.setProduct(product);
		review.setUser(user);
		review.setReview(reviewRequest.getDescription());
		review.setCreatedAt(LocalDateTime.now());
		
		
		return reviewRepo.save(review);
	}

	@Override
	public List<Review> getProductReview(Long productId) {
		
		return reviewRepo.getAllPRoductReview(productId);
	}
	

}

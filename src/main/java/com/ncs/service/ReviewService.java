package com.ncs.service;

import java.util.List;

import com.ncs.exception.ProductException;
import com.ncs.model.Review;
import com.ncs.model.User;
import com.ncs.request.ReviewRequest;

public interface ReviewService {
	
	public Review createReview(ReviewRequest reviewRequest,User user) throws ProductException;
	
	public List<Review> getProductReview(Long productId);

}

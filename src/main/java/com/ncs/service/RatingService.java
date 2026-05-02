package com.ncs.service;

import java.util.List;

import com.ncs.exception.ProductException;
import com.ncs.model.Rating;
import com.ncs.model.User;
import com.ncs.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest ratingRequest,User user) throws ProductException;
	
	public List<Rating> getProductRating(Long productId);
		
		
}

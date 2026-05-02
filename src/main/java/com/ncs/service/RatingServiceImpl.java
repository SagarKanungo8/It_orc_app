package com.ncs.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.exception.ProductException;
import com.ncs.model.Product;
import com.ncs.model.Rating;
import com.ncs.model.User;
import com.ncs.repository.RatingRepo;
import com.ncs.request.RatingRequest;

@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	private RatingRepo ratingRepo;
	@Autowired
	private ProductService productService;
	
	
	

	@Override
	public Rating createRating(RatingRequest ratingRequest, User user) throws ProductException {
		
		Product product = productService.findProductById(ratingRequest.getProductId());
		
		Rating rating = new Rating();
		
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(ratingRequest.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepo.save(rating);
		
	}

	@Override
	public List<Rating> getProductRating(Long productId) {
		return ratingRepo.getAllProductRating(productId);
	}
	

}

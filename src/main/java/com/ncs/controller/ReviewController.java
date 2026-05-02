package com.ncs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncs.exception.ProductException;
import com.ncs.exception.UserException;
import com.ncs.model.Review;
import com.ncs.model.User;
import com.ncs.request.ReviewRequest;
import com.ncs.service.ReviewService;
import com.ncs.service.UserService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ReviewService reviewService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Review>createReviewHandler(@RequestBody ReviewRequest req,@RequestHeader("Authorization")String jwt) throws UserException, ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Review review = reviewService.createReview(req, user);
		
		
		return new ResponseEntity<>(review,HttpStatus.CREATED);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>>getProductReviews(@PathVariable Long productId,@RequestHeader("Authorization")String jwt) throws UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Review> review = reviewService.getProductReview(productId);
		
		return new ResponseEntity<>(review,HttpStatus.OK);
	}
	
	
	
	
	
}

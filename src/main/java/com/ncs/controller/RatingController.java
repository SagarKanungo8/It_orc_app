package com.ncs.controller;

import java.util.List;

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
import com.ncs.model.Rating;
import com.ncs.model.User;
import com.ncs.request.RatingRequest;
import com.ncs.service.RatingService;
import com.ncs.service.UserService;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
	
	private UserService userService;
	
	private RatingService ratingService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Rating>createRatingHandler(@RequestBody RatingRequest req,@RequestHeader("Authorization")String jwt) throws UserException, ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Rating rating = ratingService.createRating(req, user);

		return new ResponseEntity<>(rating,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>>getAllRating(@PathVariable Long productId,@RequestHeader("Authorization")String jwt) throws UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Rating> rating = ratingService.getProductRating(productId);
		
		
		return new ResponseEntity<>(rating,HttpStatus.OK);
	}

}

package com.ncs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncs.exception.ProductException;
import com.ncs.exception.UserException;
import com.ncs.model.Cart;
import com.ncs.model.User;
import com.ncs.request.AddItemRequest;
import com.ncs.response.ApiResponse;
import com.ncs.service.CartService;
import com.ncs.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization")String jwt) throws UserException{
		
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		

		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}

	@PutMapping("/add")
	public ResponseEntity<ApiResponse>itemAddedToCart(@RequestBody AddItemRequest req,@RequestHeader("Authorization")String jwt) throws UserException, ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		cartService.addCartItem(user.getId(),req);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Item Added To Cart Successfully....!!");
		res.setStatus(true);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	
	
}

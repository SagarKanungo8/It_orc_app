package com.ncs.service;

import com.ncs.exception.ProductException;
import com.ncs.exception.UserException;
import com.ncs.model.Cart;
import com.ncs.model.User;
import com.ncs.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req) throws UserException,ProductException;
	
	public Cart findUserCart(Long userId);
	

}

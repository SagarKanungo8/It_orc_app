package com.ncs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.exception.CartItemException;
import com.ncs.exception.UserException;
import com.ncs.model.Cart;
import com.ncs.model.CartItem;
import com.ncs.model.Product;
import com.ncs.model.User;
import com.ncs.repository.CartItemRepo;
import com.ncs.repository.CartRepo;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepo cartItemRepo;

	@Autowired
	private UserService userService;

	@Override
	public CartItem createCartItem(CartItem cartItem) {

		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

		CartItem createdCartItem = cartItemRepo.save(cartItem);

		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

		CartItem item = findCartItemById(id);
		User user = userService.findUserById(item.getUserId());

		if (user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
			item.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
		}

		return cartItemRepo.save(item);

	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

		CartItem cartItem = cartItemRepo.isCartItemExist(cart, product, size, userId);

		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

		CartItem cartItem = findCartItemById(cartItemId);

		User user = userService.findUserById(cartItem.getUserId());

		User reqUser = userService.findUserById(userId);

		if (user.getId().equals(reqUser.getId())) {
			cartItemRepo.deleteById(cartItemId);
		} else {
			throw new UserException("You Cant remove Another User Item");
		}
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {

		Optional<CartItem> opt = cartItemRepo.findById(cartItemId);

		if (opt.isPresent()) {
			return opt.get();
		}

		throw new CartItemException("CartItem Not Found.....!!!");
	}

}

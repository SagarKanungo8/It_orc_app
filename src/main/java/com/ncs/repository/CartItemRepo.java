package com.ncs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ncs.model.Cart;
import com.ncs.model.CartItem;
import com.ncs.model.Product;

public interface CartItemRepo extends JpaRepository<CartItem, Long>{
	
	@Query("SELECT ci FROM CartItem ci WHERE ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart,@Param("product") Product product,
			                        @Param("size") String size,@Param("userId")Long userId);

}

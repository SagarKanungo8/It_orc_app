package com.ncs.service;

import java.util.List;

import com.ncs.exception.OrderException;
import com.ncs.model.Address;
import com.ncs.model.Order;
import com.ncs.model.User;

public interface OrderService {

	public Order createOrder(User user, Address shippingAddress);

	public Order findOrderById(Long orderId) throws OrderException;

	public List<Order> userOrderHistory(Long userId);

	public Order placedOrder(Long orderId) throws OrderException;

	public Order confirmedOrder(Long orderId) throws OrderException;

	public Order shippedOrder(Long orderId) throws OrderException;

	public Order deliveredOrder(Long orderId) throws OrderException;

	public Order cancelledOrder(Long orderId) throws OrderException;
	
	public List<Order> getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;
	
	
}

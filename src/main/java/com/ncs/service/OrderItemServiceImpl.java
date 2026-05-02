package com.ncs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.model.OrderItem;
import com.ncs.repository.OrderItemRepo;

@Service
public class OrderItemServiceImpl implements OrderItemService{
	
	@Autowired
	private OrderItemRepo orderItemRepo;

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepo.save(orderItem);
	}




}

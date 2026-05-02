package com.ncs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ncs.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}

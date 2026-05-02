package com.ncs.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.exception.OrderException;
import com.ncs.model.Address;
import com.ncs.model.Cart;
import com.ncs.model.CartItem;
import com.ncs.model.Order;
import com.ncs.model.OrderItem;
import com.ncs.model.User;
import com.ncs.repository.AddressRepo;
import com.ncs.repository.CartRepo;
import com.ncs.repository.OrderItemRepo;
import com.ncs.repository.OrderRepo;
import com.ncs.repository.UserRepo;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CartRepo cartRepo;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderItemRepo orderItemRepo;
	

	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
		Address address = addressRepo.save(shippingAddress);
		user.getAddress().add(address);
		userRepo.save(user);
		
		Cart cart = cartService.findUserCart(user.getId());
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem item:cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			
		    orderItem.setPrice(item.getPrice());
		    orderItem.setProduct(item.getProduct());
		    orderItem.setQuantity(item.getQuantity());
		    orderItem.setSize(item.getSize());
		    orderItem.setUserId(item.getUserId());
		    orderItem.setDiscountedPrice(item.getDiscountedPrice());
		    
		    OrderItem createdOrderItem = orderItemRepo.save(orderItem);
		    
		    orderItems.add(createdOrderItem);
		    
		}
		    
		    Order createdOrder = new Order();
		    
		    createdOrder.setUser(user);
		    createdOrder.setOrderItems(orderItems);
		    createdOrder.setTotalPrice(cart.getTotalPrice());
		    createdOrder.setDiscount(cart.getDiscount());
		    createdOrder.setTotalItem(cart.getTotalItem());
		    
		    createdOrder.setShippingAddress(address);
		    createdOrder.setOrderDate(LocalDateTime.now());
		    	createdOrder.setOrderStatus("PENDING");
		    	createdOrder.setCreatedAt(LocalDateTime.now());
		    	
		    	Order savedOrder = orderRepo.save(createdOrder);
		    	
		    	for(OrderItem items :orderItems) {
		    		items.setOrder(savedOrder);
		    		orderItemRepo.save(items);
		    	}

		return savedOrder;
		}
	
	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		
		
		Optional<Order> opt = orderRepo.findById(orderId);
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new OrderException("Order Not Found...!!");
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		
		Order order = findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");

		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
	Order order = findOrderById(orderId);
	order.setOrderStatus("CONFIREMED");
	
		return orderRepo.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepo.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		
		return orderRepo.save(order);
	}

	@Override
	public Order cancelledOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepo.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepo.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		
		Order order = findOrderById(orderId);
		
		orderRepo.deleteById(orderId);
		
	}
	
	

}

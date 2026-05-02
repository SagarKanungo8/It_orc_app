package com.ncs.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ncs.exception.ProductException;
import com.ncs.model.Product;
import com.ncs.request.CreateProductRequest;

public interface ProductService {
	
	
	 public Product createProduct(CreateProductRequest req);
	 
	 public String deleteProduct(Long productId) throws ProductException;
	 
	 public Product updateProduct(Long productId,Product req) throws ProductException;
	 
	 public List<Product> findAllProduct();

	 public Product findProductById(Long id) throws ProductException;
	 
	 public List<Product> findProductByCategory(String category);
	 
	 public Page<Product> getAllProduct(String category,List<String>color,List<String>size,
			                            Integer minPrice,Integer maxPrice,Integer minDiscount,
			                            String sort,String stock,Integer pageNumber,Integer pageSize);
	 
	 
	 
	 
	 
	 
}

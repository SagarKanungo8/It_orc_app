package com.ncs.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ncs.exception.ProductException;
import com.ncs.model.Category;
import com.ncs.model.Product;
import com.ncs.repository.CategoryRepo;
import com.ncs.repository.ProductRepo;
import com.ncs.request.CreateProductRequest;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserService userService;
	

	@Override
	public Product createProduct(CreateProductRequest req) {
		
		Category topLevel = categoryRepo.findByName(req.getTopLevelCategory());
		
		if(topLevel==null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			
			topLevel = categoryRepo.save(topLevelCategory);
		}
		
		Category secondLevel = categoryRepo.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
		
		if(secondLevel==null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);
			
			secondLevel = categoryRepo.save(secondLevelCategory);
			
		}
		
		
		Category thirdLevel = categoryRepo.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
		
		  if(thirdLevel==null) {
			  Category thirdLevelCategory = new Category();
			  
			  thirdLevelCategory.setName(req.getThirdLevelCategory());
			  thirdLevelCategory.setParentCategory(secondLevel);
			  thirdLevelCategory.setLevel(3);
			  
			  thirdLevel = categoryRepo.save(thirdLevelCategory);
			  
		  }
		  
		  Product product = new Product();
		  product.setTitle(req.getTitle());
		  product.setColor(req.getColor());
		  product.setDescription(req.getDescription());
		  product.setDiscountPercent(req.getDiscountPercent());
		  product.setDiscountedPrice(req.getDiscountedPrice());
		  product.setImageUrl(req.getImageUrl());
		  product.setBrand(req.getBrand());
		  product.setPrice(req.getPrice());
		  product.setSize(req.getSize());
		  product.setQuantity(req.getQuantity());
		  product.setCategory(thirdLevel);
		  product.setCreatedAt(LocalDateTime.now());
		  
		  Product savedProduct = productRepo.save(product);
		  
		  System.out.println(product);

		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		
		Product product = new Product();
		
		product.getSize().clear();
		
		productRepo.delete(product);

		return "Product Deleted Successfullyyyyy...!!!";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		
		Product product = findProductById(productId);
		
		if(req.getQuantity()!=0) {
			product.setQuantity(req.getQuantity());
		}
		
		return productRepo.save(product);

	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		
		
		Optional<Product> opt = productRepo.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}

		throw new ProductException("Product Not Found....!!!!");
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> color, List<String> size, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		
		List<Product> products = productRepo.filterProduct(category, minPrice, maxPrice, sort);
		
		if(!color.isEmpty()) {
			
			products = products.stream().filter(p->color.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
					                    .collect(Collectors.toList());	
		}
		if(stock!=null) {
			if(stock.equals("in_stock")) {
				products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList()); 
			}
			else if(stock.equals("out_of_stock")){
				products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList()); 
			}
		}
		
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());
		
		List<Product> pageContent = products.subList(startIndex, endIndex);
		
		Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,products.size());
		
		return filteredProducts;
	}

	@Override
	public List<Product> findAllProduct() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

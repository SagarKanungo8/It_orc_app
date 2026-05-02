package com.ncs.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;
	
	@Column(name="description")
	private String description;

	@Column(name="price")
	private Integer price;
	
	@Column(name="discounted_price")
	private Integer discountedPrice;
	
	@Column(name="discount_percent")
	private Integer discountPercent;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="color")
	private String color;
	
	@Column(name="brand")
	private String brand;
	
	@Embedded
	@ElementCollection
	@Column(name="size")
	private Set<Size> size = new HashSet<>();
	
	@Column(name="image_url")
	private String imageUrl;
	
	@OneToMany(mappedBy="product",cascade = CascadeType.ALL)
	private List<Review> review = new ArrayList<>();
	
	@OneToMany(mappedBy="product",cascade = CascadeType.ALL)
	private List<Rating> rating = new ArrayList<>();
	

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	public Product() {

	}

	public Product(Long id, String title, String description, Integer price, Integer discountedPrice,
			Integer discountPercent, Integer quantity, String color, String brand, Set<Size> size, String imageUrl,
			List<Review> review, List<Rating> rating, Category category, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.discountPercent = discountPercent;
		this.quantity = quantity;
		this.color = color;
		this.brand = brand;
		this.size = size;
		this.imageUrl = imageUrl;
		this.review = review;
		this.rating = rating;
		this.category = category;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Integer discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Set<Size> getSize() {
		return size;
	}

	public void setSize(Set<Size> size) {
		this.size = size;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	
	
	
	}
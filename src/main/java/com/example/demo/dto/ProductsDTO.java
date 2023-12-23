package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.ProductReview;

import lombok.Data;

@Data
public class ProductsDTO {
	private int id;
	
    private String name;
	
	private String description;
	
	private double price;
	
	private String imageUrl;
	
	private int quantity;

	private String origin;
	
	private String material;

	private CategoriesDTO category;
	
	private List<ProductReviewDTO> productReviews;
}

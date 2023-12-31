package com.example.demo.service;


import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductReviewDTO;
import com.example.demo.dto.ProductsDTO;
import com.example.demo.entity.Categories;
import com.example.demo.entity.ProductReview;
import com.example.demo.entity.Products;
import com.example.demo.repository.CategoriesRepo;
import com.example.demo.repository.ProductReviewRepo;
import com.example.demo.repository.ProductsRepo;


public interface ProductsService {

	void create(ProductsDTO productsDTO);

	void update(ProductsDTO productsDTO);
	
	List<ProductsDTO> getAll();

	void delete(int id);

	ProductsDTO getById(int id);

}

@Service
class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepo productsRepo;
	
	@Autowired
	private ProductReviewRepo productReviewRepo;
	
	@Autowired
	private CategoriesRepo categoriesRepo;

	@Transactional
	public void create(ProductsDTO productDTO) {
		Categories category = 
				categoriesRepo.findById(productDTO.getCategory().getId()).orElseThrow(NoResultException::new);// java8 lambda
		
		Products product = new ModelMapper().map(productDTO, Products.class);
		product.setCategories(category);
		
		productsRepo.save(product);
		
		//tra ve id sau khi tao
		productDTO.setId(product.getId());
	}

	@Override
	@Transactional
	public void update(ProductsDTO productsDTO) {
	    // Kiểm tra xem Products đã tồn tại trong cơ sở dữ liệu chưa
	    Products products = productsRepo.findById(productsDTO.getId()).orElseThrow(NoResultException::new);

	    // Cập nhật thông tin của Products từ DTO
	    products.setName(productsDTO.getName());
	    products.setQuantity(productsDTO.getQuantity());
	    products.setPrice(productsDTO.getPrice());
	    products.setOrigin(productsDTO.getOrigin());
	    products.setDescription(productsDTO.getDescription());
	    products.setMaterial(productsDTO.getMaterial());
	    // Chuyển đổi List<ProductReviewDTO> sang List<ProductReview>
	    List<ProductReviewDTO> productReviewDTOList = productsDTO.getProductReviews();
	    List<ProductReview> productReviewList = productReviewDTOList.stream()
	            .map(productReviewDTO -> new ModelMapper().map(productReviewDTO, ProductReview.class))
	            .collect(Collectors.toList());

	    // Cập nhật hoặc thêm mới các ProductReview
	    for (ProductReview productReview : productReviewList) {
	        productReview.setProduct(products); // Liên kết với Products
	        // Cập nhật hoặc thêm mới ProductReview
	        productReviewRepo.save(productReview);
	    }

	    // Lưu đối tượng Products
	    productsRepo.save(products);
	}

	
	@Override
	public void delete(int id) {
		  Products product = productsRepo.findById(id).orElseThrow(NoResultException::new);
		    Categories category = product.getCategories();

		    // Xóa liên kết giữa sản phẩm và danh mục
		    category.getProducts().remove(product);
		    categoriesRepo.save(category);

		    // Xóa tất cả các bản ghi liên quan trong bảng OrderItems

		    // Xóa sản phẩm
		    productsRepo.deleteById(id);
		productsRepo.deleteById(id);
	}

	@Override
	public ProductsDTO getById(int id) {
		// Optional
		Products products = productsRepo.findById(id).orElseThrow(NoResultException::new);
	//	List<User> users = department.getUsers();
		if(products != null)
			return convert(products);
		
		return null;
	}
	
	private ProductsDTO convert(Products products) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(products, ProductsDTO.class);
	}

	@Override
	public List<ProductsDTO> getAll() {
		// TODO Auto-generated method stub
		List<Products> productsList = productsRepo.findAll();
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		// java 8
		return productsList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}
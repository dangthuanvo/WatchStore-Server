package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoriesDTO;
import com.example.demo.dto.ShopDTO;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Shop;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoriesRepo;
import com.example.demo.repository.ShopRepo;

public interface ShopService {

	void create(ShopDTO shopDTO);

	void update(ShopDTO shopDTO);
	
	List<ShopDTO> getAll();

	void delete(int id);

	ShopDTO getById(int id);

}

@Service
class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopRepo shopRepo;

	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Override
	@Transactional
	public void create(ShopDTO shopDTO) {
		Shop shop = new ModelMapper().map(shopDTO, Shop.class);
		shopRepo.save(shop);
	}

	@Override
	public void update(ShopDTO shopDTO) {
        // Kiểm tra xem cửa hàng có tồn tại không
        Shop currentShop = shopRepo.findById(shopDTO.getId()).orElseThrow(NoResultException::new);

        // Cập nhật thông tin cửa hàng từ DTO
        currentShop.setName(shopDTO.getName());
        currentShop.setAddress(shopDTO.getAddress());
        currentShop.setPhone(shopDTO.getPhone());
        currentShop.setStar(shopDTO.getStar());
    

        // Thêm các categories từ DTO
        List<CategoriesDTO> categoriesDTOList = shopDTO.getCategories();
        if (categoriesDTOList != null) {
            for (CategoriesDTO categoriesDTO : categoriesDTOList) {
            	Categories categories = categoriesRepo.findById(categoriesDTO.getId()).orElseThrow(NoResultException::new);
                categories.setName(categoriesDTO.getName());
                categories.setCategoryUrl(categoriesDTO.getCategoryUrl());
                // Thêm sản phẩm vào cửa hàng và ngược lại
                currentShop.getCategories().add(categories);
                categories.setShop(currentShop);

                // Lưu Categories vào cơ sở dữ liệu (nếu cần)
                categoriesRepo.save(categories);
            }
        }

        // Lưu cửa hàng vào cơ sở dữ liệu
        shopRepo.save(currentShop);
    }
	
	@Override
	public void delete(int id) {
		shopRepo.deleteById(id);
	}

	@Override
	public ShopDTO getById(int id) {
		// Optional
		Shop shop = shopRepo.findById(id).orElseThrow(NoResultException::new);
	//	List<User> users = department.getUsers();
		if(shop != null)
			return convert(shop);
		
		return null;
	}
	
	private ShopDTO convert(Shop shop) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(shop, ShopDTO.class);
		
	}

	@Override
	public List<ShopDTO> getAll() {
		// TODO Auto-generated method stub
		List<Shop> shopList = shopRepo.findAll();
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		// java 8
		return shopList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}
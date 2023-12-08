package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BannerDTO;
import com.example.demo.dto.PromotionDTO;
import com.example.demo.entity.Banner;
import com.example.demo.entity.Promotion;
import com.example.demo.repository.BannerRepo;
import com.example.demo.repository.PromotionRepo;

public interface BannerService {

	void create(BannerDTO promotionDTO);

	void update(BannerDTO promotionDTO);
	
	List<BannerDTO> getAll();

	void delete(int id);

	BannerDTO getById(int id);

}

@Service
class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerRepo promotionRepo;


	@Override
	@Transactional
	public void create(BannerDTO promotionDTO) {
		Banner shop = new ModelMapper().map(promotionDTO, Banner.class);
		promotionRepo.save(shop);
	}
	@Override
	@Transactional
	public void update(BannerDTO promotionDTO) {
		// check
		Banner promotion = promotionRepo.findById(promotionDTO.getId()).orElseThrow(NoResultException::new);
		//Categories categories = new ModelMapper().map(productsDTO.getCategory(), Categories.class);
		promotion.setName(promotionDTO.getName());
		promotion.setAvatarUrl(promotionDTO.getAvatarUrl());
	
			// save entity
		promotionRepo.save(promotion);
	    

	}
	
	@Override
	public void delete(int id) {
		promotionRepo.deleteById(id);
	}

	@Override
	public BannerDTO getById(int id) {
		// Optional
		Banner promotion = promotionRepo.findById(id).orElseThrow(NoResultException::new);
	//	List<User> users = department.getUsers();
		if(promotion != null)
			return convert(promotion);
		
		return null;
	}
	
	private BannerDTO convert(Banner promotion) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(promotion, BannerDTO.class);
	}

	@Override
	public List<BannerDTO> getAll() {
		// TODO Auto-generated method stub
		List<Banner> promotions = promotionRepo.findAll();
		return promotions.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}
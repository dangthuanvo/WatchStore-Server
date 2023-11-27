package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.PromotionDTO;
import com.example.demo.entity.Promotion;
import com.example.demo.repository.PromotionRepo;


public interface PromotionService {

	void create(PromotionDTO promotionDTO);

	void update(PromotionDTO promotionDTO);
	
	List<PromotionDTO> getAll();

	void delete(int id);

	PromotionDTO getById(int id);

}

@Service
class PromotionServiceImpl implements PromotionService {

	@Autowired
	private PromotionRepo promotionRepo;


	@Override
	@Transactional
	public void create(PromotionDTO promotionDTO) {
		Promotion shop = new ModelMapper().map(promotionDTO, Promotion.class);
		promotionRepo.save(shop);
	}
	@Override
	@Transactional
	public void update(PromotionDTO promotionDTO) {
		// check
		Promotion promotion = promotionRepo.findById(promotionDTO.getId()).orElseThrow(NoResultException::new);
		//Categories categories = new ModelMapper().map(productsDTO.getCategory(), Categories.class);
		promotion.setName(promotionDTO.getName());
		promotion.setStatus(promotionDTO.getStatus());
		promotion.setValue(promotionDTO.getValue());
			// save entity
		promotionRepo.save(promotion);
	    

	}
	
	@Override
	public void delete(int id) {
		promotionRepo.deleteById(id);
	}

	@Override
	public PromotionDTO getById(int id) {
		// Optional
		Promotion promotion = promotionRepo.findById(id).orElseThrow(NoResultException::new);
	//	List<User> users = department.getUsers();
		if(promotion != null)
			return convert(promotion);
		
		return null;
	}
	
	private PromotionDTO convert(Promotion promotion) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(promotion, PromotionDTO.class);
	}

	@Override
	public List<PromotionDTO> getAll() {
		// TODO Auto-generated method stub
		List<Promotion> promotions = promotionRepo.findAll();
		return promotions.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}
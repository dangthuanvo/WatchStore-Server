package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PromotionDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.PromotionService;


@RestController
@RequestMapping("/promotion")
public class PromotionController {
	@Autowired  // DI Dependence Inject
	PromotionService promotionService;
	

	@PostMapping("/new")
	public ResponseDTO<Void> newProducts(@RequestBody PromotionDTO productsDTO) throws IllegalStateException,IOException {
		
		promotionService.create(productsDTO);
		//return "redirect:/user/list";// GET
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	
	@GetMapping("/{id}") // 10
	public ResponseDTO<PromotionDTO> get(@PathVariable("id") int id) {
		PromotionDTO productsDTO = promotionService.getById(id);
		return ResponseDTO.<PromotionDTO>builder().status(200).data(productsDTO).build();
	}
	
	@DeleteMapping("/delete")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		promotionService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("delete").build();
	}
	
	@PostMapping("/edit")
	public ResponseDTO<Void> edit(@RequestBody PromotionDTO productsDTO) throws Exception {
	
		promotionService.update(productsDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/list")
	public ResponseDTO<List<PromotionDTO>> list(Model model) {
		List<PromotionDTO> productsList = promotionService.getAll();
		return ResponseDTO.<List<PromotionDTO>>builder().status(200).data(productsList).build();
	}
}
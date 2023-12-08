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

import com.example.demo.dto.BannerDTO;
import com.example.demo.dto.PromotionDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.service.BannerService;
import com.example.demo.service.PromotionService;


@RestController
@RequestMapping("/banner")
public class BannerController {
	@Autowired  // DI Dependence Inject
	BannerService promotionService;
	

	@PostMapping("/new")
	public ResponseDTO<Void> newProducts(@RequestBody BannerDTO productsDTO) throws IllegalStateException,IOException {
		
		promotionService.create(productsDTO);
		//return "redirect:/user/list";// GET
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	
	@GetMapping("/{id}") // 10
	public ResponseDTO<BannerDTO> get(@PathVariable("id") int id) {
		BannerDTO productsDTO = promotionService.getById(id);
		return ResponseDTO.<BannerDTO>builder().status(200).data(productsDTO).build();
	}
	
	@DeleteMapping("/delete")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		promotionService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("delete").build();
	}
	
	@PostMapping("/edit")
	public ResponseDTO<Void> edit(@RequestBody BannerDTO productsDTO) throws Exception {
	
		promotionService.update(productsDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/list")
	public ResponseDTO<List<BannerDTO>> list(Model model) {
		List<BannerDTO> productsList = promotionService.getAll();
		return ResponseDTO.<List<BannerDTO>>builder().status(200).data(productsList).build();
	}
}
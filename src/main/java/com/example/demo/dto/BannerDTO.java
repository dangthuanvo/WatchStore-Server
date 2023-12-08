package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BannerDTO {
	private int id;
	private String name;
	private String avatarUrl;
	@JsonProperty(required = false)
	private MultipartFile file;
}

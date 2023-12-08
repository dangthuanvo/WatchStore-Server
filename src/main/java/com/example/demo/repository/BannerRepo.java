package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Banner;

public interface BannerRepo extends JpaRepository<Banner, Integer> {

}

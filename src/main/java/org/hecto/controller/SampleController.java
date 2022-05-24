package org.hecto.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.hecto.domain.SampleDTO;
import org.hecto.domain.SampleDTOList;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/sample/*") // sample 경로 
public class SampleController {
	@RequestMapping(value="/basic", method= {RequestMethod.GET}) // 경로: root/sample/basic
	public void basicGet() {
		log.info("basic get......");
	}
	
	@GetMapping("/only") 
	public void onlyGet() {
		log.info("only get......");
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("" + dto);
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name: " + name);
		log.info("age: " + age);
		return "ex02";
	}
	
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids: " + ids);
		return "ex02List";
	}
	
	@GetMapping("/ex02Array")
	public String ex02List(@RequestParam("ids") String[] ids) {
		log.info("array ids: " + Arrays.toString(ids));
		return "ex02List";
	}
	
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos: " + list);
		return "ex02Bean"; 
	} // root/sample/ex02Bean?list%5B0%5D.name=conan&list%5B1%5D.name=rose
	
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) { 
		log.info("dto: " + dto);
		log.info("page: " + page);
		return "/sample/ex04"; 
	} // root/sample/ex04?name=conan&age=10&page=9
	// @ModelAttribute: 강제로 전달받은 파라미터를 모델에 담아서 타입에 관계없이 전달
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("ex06........");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("conan");
		return dto;
	} // root/sample/ex06
	
	@GetMapping("/ex07")
	public ResponseEntity <String> ex07() {
		log.info("ex07log.......");
		String msg = String.format("{\"name\":\"conan\"}");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	// 파일 업로드 처리
	@GetMapping("exUpload")
	public void exUpload() {
		log.info("exUpload.......");
	}
	
	@PostMapping("exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		for (MultipartFile file:files) {
			log.info("name: " + file.getOriginalFilename());
			log.info("size: " + file.getSize());
		}
	}
}

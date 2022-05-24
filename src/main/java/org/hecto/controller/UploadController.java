package org.hecto.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {
	// upload controller
	@GetMapping("/uploadAjax")
	public void uploadAjax() { 
		log.info("upload ajax"); 
	}
	
	/* 중복된 이름의 파일 처리 */
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
	/* uploadAjaxAction 추가 */
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		String uploadFolder = "/Users/heeji/Downloads";
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("Upload Path: " + uploadPath);
		
		// 폴더가 존재하지 않으면 새로 생성 
		if(uploadPath.exists() == false)  
			uploadPath.mkdirs(); // yyyy-mm-dd 폴더 생성
		
		for(MultipartFile multipartFile: uploadFile) {
			log.info("-----------------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			// UUID를 이용하여 고유한 파일명 생성 
			UUID uuid = UUID.randomUUID(); 
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			File saveFile = new File(uploadPath, uploadFileName);
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	
	
}

package org.hecto.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hecto.domain.BoardAttachVO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

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
	
	/* 썸네일 처리 */
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/* uploadAjaxAction 추가 */
	@PostMapping(value="/uploadAjaxAction", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		String uploadFolder = "/Users/heeji/Downloads";
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("Upload Path: " + uploadPath);
		
		List<BoardAttachVO> list = new ArrayList<>();
		
		// 폴더가 존재하지 않으면 새로 생성 
		if(uploadPath.exists() == false)  
			uploadPath.mkdirs(); // yyyy-mm-dd 폴더 생성
		
		for(MultipartFile multipartFile: uploadFile) {
			BoardAttachVO attachVO = new BoardAttachVO();
			
			log.info("-----------------------------------------------");
			log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			log.info("Upload File Size: " + multipartFile.getSize());
			
			// UUID를 이용하여 고유한 파일명 생성 
			UUID uuid = UUID.randomUUID(); 
			String uploadFileName = multipartFile.getOriginalFilename(); // 파일 이름 설정 
			attachVO.setFileName(uploadFileName);
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			File saveFile = new File(uploadPath, uploadFileName);
			try {
				multipartFile.transferTo(saveFile);
				attachVO.setUuid(uuid.toString());
				attachVO.setUploadPath(getFolder());
				
				// 썸네일 생성 및 저장 (원본 이미지, s_썸네일 이미지 총 2개의 이미지 저장) 
				if(checkImageType(saveFile)) {
					attachVO.setFileType(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				list.add(attachVO);
				log.info("attachVO: " + attachVO);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/* 썸네일 이미지 보여주기 */
	// 파일 이름을 전달받아 헤더 이미지 데이터 전송 
	@GetMapping("/display") // http://localhost:9000/display?fileName=2022/05/25/tiger.jpeg
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.info("fileName: " + fileName);
		
		File file = new File("/Users/heeji/Downloads/" + fileName);
		log.info("file: " + file);
		
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			// 파일의 확장자에 따라 적당한 MIME 타입 데이터 지정 후 헤더에 추가 
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/* 첨부파일 다운로드 */
	// 일반 파일의 경우, 클릭 시 다운로드 처리하기 (http://localhost:9000/download?fileName=list.jsp) 
	// 이미지 파일의 경우, 원본 파일 보여주기 (http://localhost:9000/download?fileName=blacktiger.png)
	@GetMapping(value="/download", produces=MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName) {
		log.info("download file: " + fileName);
		
		Resource resource = new FileSystemResource("/Users/heeji/Downloads/" + fileName);
		if(resource.exists() == false)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		log.info("resource: " + resource);
		
		String resourceName = resource.getFilename();
		// 파일 이름에서 uuid 제거 
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		log.info("resourceOriginalName: " + resourceOriginalName);

		HttpHeaders headers = new HttpHeaders();
		try {
			// 다운로드 시 저장되는 이름 
			headers.add("Content-Disposition", "attachment; fileName=" + new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	/* 화면에서 삭제 기능 */
	// 전달되는 파라미터 이름과 종류를 파악해서 처리
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		log.info("deleteFile: " + fileName);

		File file;
		try {
			file = new File("/Users/heeji/Downloads/" + URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("largeFileName: " + largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
}

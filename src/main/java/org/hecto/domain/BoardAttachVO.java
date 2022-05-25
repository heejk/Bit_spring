package org.hecto.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
	/* 업로드 된 파일의 데이터 반환 */
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType; // 이미지 파일 여부  
	private Long bno;
}

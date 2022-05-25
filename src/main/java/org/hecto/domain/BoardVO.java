package org.hecto.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private Date updateDate;
	
	// 여러 개의 첨부 파일을 가지도록
	private List<BoardAttachVO> attachList;
}

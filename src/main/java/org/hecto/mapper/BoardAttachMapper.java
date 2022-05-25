package org.hecto.mapper;

import java.util.List;

import org.hecto.domain.BoardAttachVO;

public interface BoardAttachMapper {
	public void insert(BoardAttachVO vo); // 첨부파일 정보 삽입
	public void delete(String uuid);  // 특정 uuid 갖는 행 삭제
	public List<BoardAttachVO> findByBno(Long bno); // 게시글 번호가 bno인 파일 목록 조회  
}
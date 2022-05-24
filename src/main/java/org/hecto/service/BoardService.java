package org.hecto.service;

import java.util.List;

import org.hecto.domain.BoardVO;
import org.hecto.domain.Criteria;
 
public interface BoardService { 
	public void register(BoardVO board);
	public BoardVO get(Long bno);
	public boolean modify(BoardVO board);
	public boolean remove(Long bno);
	public List<BoardVO> getList();
	
	// 페이지 게시물 리스트 가져오기
	public List<BoardVO> getListWithSearch(Criteria cri);
	
	// 해당 페이지 게시물 개수 가져오기
	public int getTotalCount(Criteria cri);
}

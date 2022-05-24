package org.hecto.mapper;


import java.util.List;

import org.hecto.domain.BoardVO;
import org.hecto.domain.Criteria;

public interface BoardMapper {
	//@Select("SELECT * FROM tbl_board WHERE bno > 0")
	public List<BoardVO> getList();
	
	public void insert(BoardVO board);

	public BoardVO read(long L);

	public int delete(long bno);

	public int update(BoardVO board);

	public void insertSelectKey(BoardVO board);
	
	// 페이지 게시물 리스트 가져오기
	public List<BoardVO> getListWithSearch(Criteria cri);
	
	// 해당 페이지 게시물 개수 가져오기
	public int getTotalCount(Criteria cri);
}

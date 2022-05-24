package org.hecto.mapper;

import java.util.List;

import org.hecto.config.RootConfig;
import org.hecto.domain.BoardVO;
import org.hecto.domain.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class})
@Log4j
public class BoardMapperTest {
	@Setter(onMethod = @__({@Autowired}))
	private BoardMapper mapper;
	
	@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board)); // 람다식  
	}
	
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("user006");
		mapper.insert(board);
		log.info(board);
	}
	
	@Test
	public void testInsertKey() {
		BoardVO board = new BoardVO();
		board.setTitle("selectKey");
		board.setContent("selectKey 내용");
		board.setWriter("user007");
		mapper.insert(board);
		log.info(board);
	}
	
	@Test
	public void testRead() {
		BoardVO board = mapper.read(7L);
		log.info(board);  
	}
	
	@Test
	public void testDelete() {
		log.info("DELETE COUNT: " + mapper.delete(3L));  
	}
	
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		board.setBno(2L);
		board.setTitle("수정한 제목");
		board.setContent("수정한 내용");
		board.setWriter("user002");
		int count = mapper.update(board);
		log.info("UPDATE COUNT: " + count);  
	}
	
	/* 검색 테스트 */
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("Test");
		cri.setType("TC");
		List<BoardVO> list = mapper.getListWithSearch(cri);
		list.forEach(board -> log.info(board));
	}
}

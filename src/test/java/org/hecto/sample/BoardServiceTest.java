package org.hecto.sample;

import static org.junit.Assert.assertNotNull;

import org.hecto.config.RootConfig;
import org.hecto.domain.BoardVO;
import org.hecto.service.BoardService;
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
public class BoardServiceTest {
	@Setter(onMethod_= {@Autowired})
	private BoardService service;
	
	@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service);
	}
	
	@Test
	public void testGetList() {
		service.getList().forEach(board -> log.info(board));
	}
	
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("새 글 from Service");
		board.setContent("새 내용 from Service");
		board.setWriter("user");
		service.register(board);
		log.info("생성된 게시물 번호: " + board.getBno());
	}
	
	@Test
	public void testGet() {
		log.info(service.get(6L).getTitle());
	}
	
	@Test
	public void testDleete() {
		log.info("REMOVE RESULT: " + service.remove(4L));
	}
	
	@Test
	public void testUpdate() {
		BoardVO board = service.get(5L);
		if (board == null) 
			return;
		board.setTitle("제목 수정 from Service");
		log.info("MODIFY RESULT: " + service.modify(board));
	}
}

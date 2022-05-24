package org.hecto.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.hecto.config.RootConfig;
import org.hecto.domain.ReplyVO;
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
public class ReplyMapperTest {
	@Setter(onMethod = @__({@Autowired}))
	private ReplyMapper mapper;
	private Long[] bnoArr = { 1L, 2L, 6L, 7L, 9L };
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i-> {
			ReplyVO vo = new ReplyVO();
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("Replyer" + i);
			mapper.insert(vo);
		});
	}
	
	@Test
	public void testRead() {
		Long tagetRno = 6L;
		ReplyVO vo = mapper.read(tagetRno);
		log.info(vo);
	}
	
	@Test
	public void testDelete() {
		Long tagetRno = 1L;
		mapper.delete(tagetRno);
	}
	
	@Test
	public void testUpdate() {
		Long tagetRno = 10L;
		ReplyVO vo = mapper.read(tagetRno);
		vo.setReply("Update Reply");
		int count = mapper.update(vo);
		log.info("Update Count: " + count);
	}
	
	@Test
	public void testList() {
		List<ReplyVO> replies = mapper.getListByBno(bnoArr[0]); // bnoArr[0]: bno = 1  
		replies.forEach(reply -> log.info(reply));
	}
}

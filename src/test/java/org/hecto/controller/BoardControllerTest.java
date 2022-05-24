package org.hecto.controller;

import org.hecto.config.RootConfig;
import org.hecto.config.ServletConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // WebApplicationContext를 이용하기 위함 
@ContextConfiguration(classes= {RootConfig.class, ServletConfig.class})
@Log4j
public class BoardControllerTest {
	@Setter(onMethod = @__({@Autowired}))
	private WebApplicationContext ctx;
	private MockMvc mockMvc; // 가짜 mvc, 브라우저에서 사용하는 것처럼 Controller 실행 
	
	@Before
	public void setup() { // 모든 테스트 전에 매번 실행
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testList() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
								.andReturn().getModelAndView().getModelMap());
	}
	
	/* BoardController의 등록 처리 테스트 */
	@Test
	public void testResister() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
									.param("title", "test from controller")
									.param("content", "test from controller")
									.param("writer", "user000"))
									.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* BoardController의 조회 테스트 */
	@Test
	public void testGet() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
						.param("bno", "2"))
						.andReturn().getModelAndView().getModelMap());
	}
	
	/* BoardController의 수정 테스트 */
	@Test
	public void testModify() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
									.param("bno", "1")
									.param("title", "modifyTest")
									.param("content", "modifyTest")
									.param("writer", "user000"))
									.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	/* BoardController의 삭제 테스트 */
	@Test
	public void testRemove() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
									.param("bno", "20"))
									.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
}

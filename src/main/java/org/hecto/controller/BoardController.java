package org.hecto.controller;
/* BoardController 목록의 처리 */

import org.hecto.domain.BoardVO;
import org.hecto.domain.Criteria;
import org.hecto.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController { 
	private BoardService service;
	
	// BoardVO의 목록을 Model에 담아서 전달 
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("list");
		model.addAttribute("list", service.getListWithSearch(cri));
	}
	
	/* BoardController의 등록 처리 */
	// POST 방식으로 처리되는 데이터를 BoardVO 타입의 인스턴스로 바인딩해서 메소드에서 활용 
	@PostMapping("/register")	
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register: " + board);
		
		// 데이터 수집 여부 확인
		if(board.getAttachList() != null) 
			board.getAttachList().forEach(attach->log.info(attach));
		
		service.register(board);
		rttr.addFlashAttribute("result" , board.getBno());
		return "redirect:/board/list";  // 'redirect:'를 이용해서 다시 목록으로 이동 
	}
	
	/* BoardController의 조회 */
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
	}
	
	/* BoardController의 수정 */
	@PostMapping("/modify")
	public String get(BoardVO board, RedirectAttributes rttr) {
		log.info("modify: " + board);
		if(service.modify(board))
			rttr.addFlashAttribute("result", "success");
		return "redirect:/board/list";
	}
	
	/* BoardController의 삭제 */
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("remove.........." + bno);
		if(service.remove(bno))
			rttr.addFlashAttribute("result", "success");
		return "redirect:/board/list";
	}
	
	/* 등록 입력 페이지와 등록 */
	@GetMapping("/register")
	public void register() {

	}
	

}

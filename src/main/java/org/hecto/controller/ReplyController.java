package org.hecto.controller;

import java.util.List;

import org.hecto.domain.ReplyVO;
import org.hecto.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
	private ReplyService service;
	
	/*
	 * consumes: 들어오는 데이터 타입 정의 (클라이언트가 서버에게 보내는 데이터 타입 명시)
	 * ex. JSON 타입을 받고싶은 경우, consumes="application/json"
	 * 
	 * produces: 반환하는 데이터 타입 정의 (서버가 클라이언트에게 반환하는 데이터 타입 명시)
	 * ex. 텍스트 타입을 반환하고 싶은 경우, produces= {MediaType.TEXT_PLAIN_VALUE}
	 * 
	 * 메소드 정의에서 쓴 {변수명}을 그대로 @PathVariable("변수명")
	 * "/{rno}" >> @PathVariable("rno")
	 * */
	
	@PostMapping(value="/new", consumes="application/json", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		log.info("ReplyVO: " + vo);
		int insertCount = service.register(vo);
		log.info("Reply INSERT COUNT: " + insertCount);
		
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 모든 댓글 조회 */
	@GetMapping(value="/pages/{bno}/{page}",
				produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno) {
		log.info("getList....");
		return new ResponseEntity<>(service.getListByBno(bno), HttpStatus.OK);
	}
	
	/* 특정 댓글 조회 */
	@GetMapping(value="/{rno}",
				produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
		log.info("get: " + rno);
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	/* 댓글 삭제 */
	@DeleteMapping(value="/{rno}",
				   produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
		log.info("remove: " + rno);
		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/* 댓글 수정 */
	@RequestMapping(value="{rno}",
					method= {RequestMethod.PUT, RequestMethod.PATCH}, 
					consumes="application/json", 
					produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno) {
		vo.setRno(rno);
		log.info("rno: " + rno);
		log.info("modify: " + vo);
		return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

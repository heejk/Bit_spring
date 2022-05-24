package org.hecto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hecto.domain.SampleVO;
import org.hecto.domain.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/rsample")
@Log4j
@AllArgsConstructor
public class RSampleController {
	/* 문자열 반환 */
	// http://localhost:9000/rsample/getText
	@GetMapping(value="/getText", 
				produces="text/plain;charset=UTF-8")
	public String getText() {
		log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE);
		return "안녕하세요";
	} 
	
	/* 객체 반환 */
	// http://localhost:9000/rsample/getSample.json -- 확장자에 따라 다른 타입으로 서비스 
	// http://localhost:9000/rsample/getSample -- XML
	@GetMapping(value="/getSample",
				produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public SampleVO getSample() {
		return new SampleVO(112, "스타", "로드");
	} 

	/* Collection 타입의 객체 반환 List */
	@GetMapping(value="/getList")
	public List<SampleVO> getList() {
		return IntStream.range(1, 10).mapToObj(i->new SampleVO(i, i + "First", i + "Last")).collect(Collectors.toList());
	}
	
	/* Collection 타입의 객체 반환 Map */
	@GetMapping(value="/getMap")
	public Map<String, SampleVO> getMap() {
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		return map;
	}
	
	/* ResponseEntity: HTTP 상태 코드 등 추가적인 데이터 전달 가능*/ 
	// http://localhost:9000/rsample/check.json?height=150&weight=45
	@GetMapping(value="/check", params= {"height", "weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight) {
		// REST 방식으로 호출하는 경우, 요청한 쪽에 상태 코드 등을 전달해야 함
		SampleVO vo = new SampleVO(000, "" + height, "" + weight);
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150) 
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo); // 502
		else 
			result = ResponseEntity.status(HttpStatus.OK).body(vo); // 200
		
		return result;
	}

	/* @PathVariable: URL 경로 중간에 들어간 값을 얻기 위해서 사용 (기본형은 얻을 수 없음) */
	// http://localhost:9000/rsample/product/bags/1234
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath (@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		return new String[] {"category: " + cat, "productId: " + pid };
	}
	
	/* @RequestBody: 전송된 데이터가 JSON이고, 이를 컨트롤러에서는 사용자 정의 타입의 객체로 변환할 때 사용 */
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert........ticket" + ticket);
		return ticket;
	}
}

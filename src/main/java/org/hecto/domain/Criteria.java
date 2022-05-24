package org.hecto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class Criteria {
	private String type; // 검색 항목
	private String keyword; // 검색 키워드 
	
	public String[] getTypeArr() { // 검색 조건을 배열로 만들어 한꺼번에 처리
		return type == null ? new String[] {} : type.split("");
	}

	public Criteria() {
		// TODO Auto-generated constructor stub
	}
}

package org.hecto.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleVO {
	// JSON 혹은 XML로 변환될 데이터 
	private Integer mno;
	private String firstName;
	private String lastName;
}

package org.hecto.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		log.error("Exception........." + ex.getMessage());
		model.addAttribute("exception", ex);
		log.error(model);
		return "errorPage";
	} 
	/* 
	 * root/sample/ex04?name=conan&age=ten&page=9
	 * age=ten >> 일부러 오류 
	 */
}

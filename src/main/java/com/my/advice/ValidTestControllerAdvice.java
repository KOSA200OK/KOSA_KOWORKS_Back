package com.my.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ValidTestControllerAdvice {
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
		String msg = e.getMessage();
		log.error("요청전달데이터 유효성검사 실패 : {}", msg);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<>(msg, headers, HttpStatus.BAD_REQUEST); 
		//실패하면-> 400 Bad Request, 요청전달데이터 유효성검사 실패 : b.no: no값을 반드시 입력하세요
	}
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> bindExceptionHandler(BindException e) {
		BindingResult br = e.getBindingResult(); //default message만 찾아내고 싶을때 .getBindingResult() 활용
		ObjectError error = br.getAllErrors().get(0); //모든 에러의 첫번째 값만 추출
		String msg = error.getDefaultMessage(); //에러에 있는 defaultmessage가 오류 메세지
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<>(msg, headers, HttpStatus.BAD_REQUEST); 
		//실패하면 -> default message에 해당하는 오류메시지만 출력된다
	}

}

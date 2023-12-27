package com.my.exception;

public class DuplicateException extends Exception {
	public DuplicateException() {
		super();
	}
	public DuplicateException(String message) {
		super(message); //예외의 상세메시지
	}
}

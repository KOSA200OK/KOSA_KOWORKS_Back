package com.my.exception;

public class UnavailableException extends Exception {
	public UnavailableException() {
		super();
	}
	public UnavailableException(String message) {
		super(message); //예외의 상세메시지
	}
}

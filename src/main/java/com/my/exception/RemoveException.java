package com.my.exception;

public class RemoveException extends Exception {
	public RemoveException() {
		super();
	}
	public RemoveException(String message) {
		super(message); //예외의 상세메시지
	}
}

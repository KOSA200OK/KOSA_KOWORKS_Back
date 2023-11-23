package com.my.exception;

public class ModifyException extends Exception {
	public ModifyException() {
		super();
	}
	public ModifyException(String message) {
		super(message); //예외의 상세메시지
	}
}

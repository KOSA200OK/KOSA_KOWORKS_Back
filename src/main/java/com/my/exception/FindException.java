package com.my.exception;

public class FindException extends Exception {
	public FindException() {
		super();
	}
	public FindException(String message) {
		super(message); //예외의 상세메시지
	}
}

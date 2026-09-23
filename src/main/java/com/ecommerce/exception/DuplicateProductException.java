package com.ecommerce.exception;

public class DuplicateProductException extends RuntimeException {
	
	private static final long serivalVersionUTD = 1L;
	
	public DuplicateProductException(String message) {
		super(message);
	}

}

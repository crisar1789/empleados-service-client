package com.parameta.service.empleados.exception;

public class SrvException extends RuntimeException {

	private String code;
	private String message;
	
	public SrvException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}	
}

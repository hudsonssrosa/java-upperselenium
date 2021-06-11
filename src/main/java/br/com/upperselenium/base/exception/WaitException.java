package br.com.upperselenium.base.exception;

public class WaitException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public WaitException(String message, Throwable exceptionCause) {
		super(message, exceptionCause);
	}
}

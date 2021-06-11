package br.com.upperselenium.base.exception;

public class DocumentException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DocumentException(String message, Throwable exceptionCause) {
		super(message, exceptionCause);
	}
}

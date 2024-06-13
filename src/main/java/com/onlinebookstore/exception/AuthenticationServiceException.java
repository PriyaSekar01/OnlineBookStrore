package com.onlinebookstore.exception;

public class AuthenticationServiceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationServiceException(String message) {
        super(message);
    }

    public AuthenticationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}


package com.dbs.bgcp.exceptions;

public class BusinessException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2400225632374931067L;

	public BusinessException(String message) {
        super(message);
    }

}

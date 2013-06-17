package org.opencoin.client;

public class RippleWsClientException extends Exception {

	public RippleWsClientException(String error) {
		super(error);
	}

	public RippleWsClientException(Exception exception) {
		super(exception);
	}

	public RippleWsClientException(Throwable exception) {
		super(exception);
	}

	private static final long serialVersionUID = -7791361493455204702L;
}

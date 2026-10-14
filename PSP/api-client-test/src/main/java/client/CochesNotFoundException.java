package client;

public class CochesNotFoundException extends Exception {

	public CochesNotFoundException() {
	}

	public CochesNotFoundException(String message) {
		super(message);
	}

	public CochesNotFoundException(Throwable cause) {
		super(cause);
	}

	public CochesNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CochesNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

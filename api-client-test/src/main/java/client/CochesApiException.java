package client;

public class CochesApiException extends Exception {

	public CochesApiException() {
	}

	public CochesApiException(String message) {
		super(message);
	}

	public CochesApiException(Throwable cause) {
		super(cause);
	}

	public CochesApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public CochesApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

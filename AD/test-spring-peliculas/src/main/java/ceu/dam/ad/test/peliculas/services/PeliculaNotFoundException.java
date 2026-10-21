package ceu.dam.ad.test.peliculas.services;

public class PeliculaNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4635359354433602640L;

	public PeliculaNotFoundException() {
	}

	public PeliculaNotFoundException(String message) {
		super(message);
	}

	public PeliculaNotFoundException(Throwable cause) {
		super(cause);
	}

	public PeliculaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PeliculaNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

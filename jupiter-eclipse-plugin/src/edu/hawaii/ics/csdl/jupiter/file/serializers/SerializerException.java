package edu.hawaii.ics.csdl.jupiter.file.serializers;

public class SerializerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerializerException() {
	}

	public SerializerException(String message) {
		super(message);
	}

	public SerializerException(Throwable cause) {
		super(cause);
	}

	public SerializerException(String message, Throwable cause) {
		super(message, cause);
	}

}

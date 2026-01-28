package service.exception;

public class LoginEccezione extends RuntimeException {
	public LoginEccezione(String message, Throwable cause) {
		super(message, cause);
	}
}

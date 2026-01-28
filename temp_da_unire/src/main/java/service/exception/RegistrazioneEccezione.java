package service.exception;

public class RegistrazioneEccezione extends RuntimeException {
	public RegistrazioneEccezione(String message, Throwable cause) {
		super(message, cause);
	}
}

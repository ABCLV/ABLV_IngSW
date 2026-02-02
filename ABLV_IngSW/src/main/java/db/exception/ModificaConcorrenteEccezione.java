package db.exception;

public class ModificaConcorrenteEccezione extends RuntimeException {
	public ModificaConcorrenteEccezione(String message, Throwable cause) {
		super(message, cause);
	}
}

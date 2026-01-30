package db.exception;

public class AmministratoreEccezione extends RuntimeException {
	public AmministratoreEccezione(String message, Throwable cause) {
		super(message, cause);
	}
	public AmministratoreEccezione(String message) {
        super(message);
    }
}

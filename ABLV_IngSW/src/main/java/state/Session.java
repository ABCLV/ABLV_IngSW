package state;

public final class Session {
	private static String userName; // nome della società / CF / ecc.
	private static String userType; // "Società", "Arbitro", ...
	private static int codiceGara;
	
	private Session() {}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String name) {
		userName = name;
	}

	public static String getUserType() {
		return userType;
	}

	public static void setUserType(String type) {
		userType = type;
	}

	public static int getCodiceGara() {
		return codiceGara;
	}

	public static void setCodiceGara(int codiceGara) {
		Session.codiceGara = codiceGara;
	}

	
	
	
	
	
	
}
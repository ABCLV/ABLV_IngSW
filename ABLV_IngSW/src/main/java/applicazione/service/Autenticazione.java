package applicazione.service;

import database.dao.*;

public class Autenticazione {
	public static boolean verifica(String tipo, String id, String password) {
		try {
			return AuthDAO.checkPassword(tipo, id, password);
		} catch (Exception e) {
			return false;
		}
	}
}
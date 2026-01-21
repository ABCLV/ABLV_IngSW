package applicazione;

import database.Consultazioni;

public class Autenticazione {
    public static boolean verifica(String tipo, String id, String password) {
        try {
            return Consultazioni.checkPassword(tipo, id, password);
        } catch (Exception e) {
            return false;
        }
    }
}
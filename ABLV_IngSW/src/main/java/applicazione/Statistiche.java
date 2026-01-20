package applicazione;

import database.Consultazioni;

public class Statistiche {
    public static long getTotalePescatori() {
        try {
            return Consultazioni.countPescatori();
        } catch (Exception e) {
            return -1;
        }
    }
}
package applicazione.service;

import database.dao.*;;

public class Statistiche {
	public static long getTotalePescatori() {
		try {
			return ConcorrenteDAO.countPescatori();
		} catch (Exception e) {
			return -1;
		}
	}
}
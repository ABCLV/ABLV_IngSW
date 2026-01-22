package applicazione;

import java.sql.SQLException;
import java.time.LocalDate;

import database.Consultazioni;

/**
 * Service MVC: incapsula tutte le operazioni di registrazione. Il controller
 * chiama solo questa classe, mai Consultazioni direttamente.
 */
public final class RegistrazioneService {

	/* ---------- Amministratore ---------- */
	public static void registraAmministratore(String cf, String nome, String cognome, String email, String pwd) {
		try {
			Consultazioni.registraAmministratore(cf, nome, cognome, email, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ---------- Concorrente ---------- */
	public static void registraConcorrente(String cf, String nome, String cognome, String email, LocalDate nascita,
			String societa, String pwd) {
		try {
			Consultazioni.registraConcorrente(cf, nome, cognome, email, nascita, societa, pwd);
		} catch (Exception e) {
			throw new RuntimeException("Registrazione concorrente fallita", e);
		}
	}

	/* ---------- Società ---------- */
	public static void registraSocieta(String nome, String indirizzo, String citta, String cap, String email,
			String pwd) {
		try {
			Consultazioni.registraSocieta(nome, indirizzo, citta, cap, email, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ---------- Arbitro ---------- */
	public static void registraArbitro(String cf, String nome, String cognome, String email, String pwd) {
		try {
			Consultazioni.registraArbitro(cf, nome, cognome, email, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ---------- esistenza ---------- */
	public static boolean esisteArbitro(String cf) throws SQLException {
		return Consultazioni.esisteArbitro(cf);
	}

	public static boolean esisteSocieta(String nome) throws SQLException {
		return Consultazioni.esisteSocieta(nome);
	}

	private RegistrazioneService() {
	}
}
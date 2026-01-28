package service;

import java.sql.SQLException;
import java.time.LocalDate;

import db.exception.AmministratoreEccezione;
import db.exception.ArbitroEccezione;
import db.exception.ConcorrenteEccezione;
import db.exception.SocietaEccezione;
import db.repository.AmministratoreDAO;
import db.repository.ArbitroDAO;
import db.repository.ConcorrenteDAO;
import db.repository.SocietaDAO;
import service.exception.RegistrazioneEccezione;

/**
 * Service MVC: incapsula tutte le operazioni di registrazione. Il controller
 * chiama solo questa classe, mai Consultazioni direttamente.
 */
public class RegistrazioneService {
	
	private final AmministratoreDAO amministratoreDAO;
	private final ConcorrenteDAO concorrenteDAO;
	private final SocietaDAO societaDAO;
	private final ArbitroDAO arbitroDAO;
	
	public RegistrazioneService() {
		this.amministratoreDAO = new AmministratoreDAO();
		this.concorrenteDAO = new ConcorrenteDAO();
		this.societaDAO = new SocietaDAO();
		this.arbitroDAO = new ArbitroDAO();
	}

	/* ---------- Amministratore ---------- */
	public void registraAmministratore(String cf, String nome, String cognome, String email, String pwd)
				throws  RegistrazioneEccezione {
		try {
			this.amministratoreDAO.registraAmministratore(cf, nome, cognome, email, pwd);
		} catch (AmministratoreEccezione e) {
			throw new RegistrazioneEccezione(e.getMessage(), e);
		}
	}

	/* ---------- Concorrente ---------- */
	public void registraConcorrente(String cf, String nome, String cognome, String email, LocalDate nascita,
			String societa, String pwd) throws RegistrazioneEccezione {
		try {
			this.concorrenteDAO.registraConcorrente(cf, nome, cognome, email, nascita, societa, pwd);
		} catch (ConcorrenteEccezione e) {
			throw new RegistrazioneEccezione(e.getMessage(), e);
		}
	}

	/* ---------- Società ---------- */
	public void registraSocieta(String nome, String indirizzo, String citta, String cap, String email,
			String pwd) throws RegistrazioneEccezione {
		try {
			this.societaDAO.registraSocieta(nome, indirizzo, citta, cap, email, pwd);
		} catch (SocietaEccezione e) {
			throw new RegistrazioneEccezione(e.getMessage(), e);
		}
	}

	/* ---------- Arbitro ---------- */
	public void registraArbitro(String cf, String nome, String cognome, String email, String pwd)
				throws RegistrazioneEccezione {
		try {
			this.arbitroDAO.registraArbitro(cf, nome, cognome, email, pwd);
		} catch (ArbitroEccezione e) {
			throw new RegistrazioneEccezione(e.getMessage(), e);
		}
	}

	/* ---------- esistenza ---------- */
	public void esisteArbitro(String cf) throws RegistrazioneEccezione {
		try {
			this.arbitroDAO.esisteArbitro(cf);
		} catch(ArbitroEccezione e) {
			throw new RegistrazioneEccezione(e.getMessage(), e);
		}
	}

	public void esisteSocieta(String nome) throws RegistrazioneEccezione {
		try {
			this.societaDAO.esisteSocieta(nome);
		} catch(SocietaEccezione e) {
			throw new RegistrazioneEccezione(e.getMessage(), e);
		}
	}
}
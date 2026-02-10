package service;

import java.time.LocalDate;

import db.exception.ModificaAmministratoreEccezione;
import db.exception.ModificaArbitroEccezione;
import db.exception.ModificaConcorrenteEccezione;
import db.repository.ModificaDAO;
import service.exception.ModificaEccezione;

public class ModificaService {
	
	private final ModificaDAO modificaDAO;

	public ModificaService() {
		this.modificaDAO = new ModificaDAO();
	}
	
	public void salvaConcorrente(String cf, String nome, String cognome, String email, LocalDate nascita) throws ModificaEccezione{
		try {
			this.modificaDAO.salvaConcorrente(cf, nome, cognome, email, nascita);
		} catch(ModificaConcorrenteEccezione e) {
			throw new ModificaEccezione(e.getMessage(), e);
		}
	}
	
	public void salvaSocieta(String nome, String email, String cap, String citta, String indirizzo) throws ModificaEccezione{
		try {
			this.modificaDAO.salvaSocieta(nome, email, cap, citta, indirizzo);
		} catch(ModificaConcorrenteEccezione e) {
			throw new ModificaEccezione(e.getMessage(), e);
		}
	}
	
	public void salvaArbitro(String cf, String nome, String cognome, String settore) throws ModificaEccezione{
		try {
			this.modificaDAO.salvaArbitro(cf, nome, cognome, settore);
		} catch(ModificaArbitroEccezione e) {
			throw new ModificaEccezione(e.getMessage(), e);
		}
	}
	
	public void salvaAmministratore(String cf, String nome, String cognome) throws ModificaEccezione{
		try {
			this.modificaDAO.salvaAmministratore(cf, nome, cognome);
		} catch(ModificaAmministratoreEccezione e) {
			throw new ModificaEccezione(e.getMessage(), e);
		}
	}

}
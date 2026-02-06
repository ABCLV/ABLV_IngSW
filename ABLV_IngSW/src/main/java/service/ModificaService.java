package service;

import java.time.LocalDate;

import db.exception.ModificaConcorrenteEccezione;
import db.repository.ModificaDAO;
import service.exception.ModificaEccezione;

public class ModificaService {
	
	private final ModificaDAO modificaDAO;

	public ModificaService() {
		this.modificaDAO = new ModificaDAO();
	}
	
	public void salvaConcorrente(String cfVecchio, /*String cfNuovo,*/ String nome, String cognome, String email, LocalDate nascita) throws ModificaEccezione{
		try {
			boolean ret = this.modificaDAO.salvaConcorrente(cfVecchio/*, cfNuovo*/, nome, cognome, email, nascita);
			if(!ret) {
				throw new ModificaEccezione("Nessun concorrente trovato da aggiornare!", new Exception());
			}
		} catch(ModificaConcorrenteEccezione e) {
			throw new ModificaEccezione(e.getMessage(), e);
		}
	}
	
	public void salvaSocieta(String nomeVecchio, /*String nomeNuovo,*/ String email, String cap, String citta, String indirizzo) throws ModificaEccezione{
		try {
			boolean ret = this.modificaDAO.salvaSocieta(nomeVecchio, /*nomeNuovo,*/ email, cap, citta, indirizzo);
			if(!ret) {
				throw new ModificaEccezione("Nessuna società trovata da aggiornare!", new Exception());
			}
		} catch(ModificaConcorrenteEccezione e) {
			throw new ModificaEccezione(e.getMessage(), e);
		}
	}

}

package service;

import java.util.List;

import db.exception.ConcorrenteEccezione;
import db.exception.GaraEccezione;
import db.repository.GaraDAO;
import model.Concorrente;
import model.Gara;
import service.exception.ConcorrenteHomeEccezione;
import service.exception.RicercaEccezione;

public class IscrizioneDAO {

	private final GaraDAO garaDAO;
	
	public IscrizioneDAO() {
		this.garaDAO = new GaraDAO();
	}
	
	public List<Gara> getGareDisponibiliPerIscrizione(){
		List<Gara> ret = null;
		try {
			ret = this.garaDAO.getGareDisponibiliPerIscrizione();
		} catch(GaraEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}
	
	
	

}

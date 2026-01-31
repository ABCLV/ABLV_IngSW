package service;

import java.util.List;

import db.exception.GaraEccezione;
import db.repository.GaraDAO;
import model.Gara;
import service.exception.RicercaEccezione;

public class GaraService {

	private final GaraDAO garaDAO;
	
	public GaraService() {
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

package service;

import java.time.LocalDate;
import java.util.List;

import db.exception.ArbitroEccezione;
import db.exception.GaraEccezione;
import db.repository.ArbitroDAO;
import db.repository.GaraDAO;
import model.Gara;
import model.enums.StatoGara;
import service.exception.AggiornaEccezione;
import service.exception.IscrizioneEccezione;
import service.exception.RicercaEccezione;

public class ArbitroService {

	private final ArbitroDAO arbitroDAO;
	private final GaraDAO garaDAO;
	
	public ArbitroService() {
		this.arbitroDAO = new ArbitroDAO();
		this.garaDAO = new GaraDAO();
	}
	
	public List<Gara> getGareAggiornabiliPerArbitro(String arb) throws RicercaEccezione {
		List<Gara> ret = null;
		try {
			ret = this.arbitroDAO.getGareAggiornabiliPerArbitro(arb);
		} catch(ArbitroEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
		
	}
	
	public List<Gara> getGareDiArbitro(String arb) throws RicercaEccezione {
		List<Gara> ret = null;
		try {
			ret = this.arbitroDAO.getGareDiArbitro(arb);
		} catch(ArbitroEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
		
	}
	
	public List<Gara> getGarePerArbitro() throws RicercaEccezione {
		List<Gara> ret = null;
		try {
			ret = this.arbitroDAO.getGarePerArbitro();
		} catch(ArbitroEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
		
	}
	
	public void aggiornaStatoGara(String codice, StatoGara stato) {
		try {
			this.arbitroDAO.aggiornaStatoGara(codice, stato);
		} catch(ArbitroEccezione e) {
			throw new AggiornaEccezione(e.getMessage(), e);
		}
	}
	
	public void assegnaArbitroAGara(String codiceGara, String arb) throws IscrizioneEccezione {
		try {
			this.arbitroDAO.assegnaArbitroAGara(codiceGara, arb);
		} catch(ArbitroEccezione e) {
			throw new IscrizioneEccezione(e.getMessage(), e);
		}
	}
	
	public void rinvioGaraArbitro(String codiceGara, LocalDate data, String campo) throws AggiornaEccezione {
		try {
			this.garaDAO.controllaDataGara(data, campo);
			this.arbitroDAO.aggiornaDataGara(codiceGara, data);
		} catch(GaraEccezione e) {
			throw new AggiornaEccezione(e.getMessage(), e);
		} catch(ArbitroEccezione e) {
			throw new AggiornaEccezione(e.getMessage(), e);
		}
		
	}
	
	public void rimuoviArbitroDaGara(String codiceGara, String arb) throws AggiornaEccezione {
		try {
			this.arbitroDAO.disiscriviArbitro(codiceGara, arb);
		} catch(ArbitroEccezione e) {
			throw new AggiornaEccezione(e.getMessage(), e);
		}
		
	}
	
}

package service;

import java.time.LocalDate;
import java.util.List;

import db.exception.ArbitroEccezione;
import db.exception.ConcorrenteEccezione;
import db.repository.ArbitroDAO;
import model.Arbitro;
import model.Gara;
import model.enums.StatoGara;
import service.exception.AggiornaEccezione;
import service.exception.ConcorrenteHomeEccezione;
import service.exception.RicercaEccezione;

public class ArbitroService {

	private final ArbitroDAO arbitroDAO;
	
	public ArbitroService() {
		this.arbitroDAO = new ArbitroDAO();
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
	
	public Arbitro getArbitro(String arb) throws RicercaEccezione {
		try {
			return this.arbitroDAO.getArbitro(arb);
		} catch(ArbitroEccezione e) {
			throw new ConcorrenteHomeEccezione(e.getMessage(), e);
		}
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
	
	public void aggiornaStatoGara(int codice, StatoGara stato) {
		try {
			this.arbitroDAO.aggiornaStatoGara(codice, stato);
		} catch(ArbitroEccezione e) {
			throw new AggiornaEccezione(e.getMessage(), e);
		}
	}
	
	public int assegnaArbitroAGara(int codiceGara, String arb) {
		return this.arbitroDAO.assegnaArbitroAGara(codiceGara, arb);
	}
	
	public void rinvioGaraArbitro(int codiceGara, LocalDate data) {
		this.arbitroDAO.aggiornaDataGara(codiceGara, data);
	}
	
	public int rimuoviArbitroDaGara(int codiceGara, String arb) {
		return this.arbitroDAO.disiscriviArbitro(codiceGara, arb);
	}
	
}

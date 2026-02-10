package service;

import java.time.LocalDate;
import java.util.List;

import db.exception.ArbitroEccezione;
import db.exception.ConcorrenteEccezione;
import db.exception.GaraEccezione;
import db.repository.ArbitroDAO;
import db.repository.GaraDAO;
import db.repository.PunteggioDAO;
import model.Arbitro;
import model.Gara;
import model.RisultatoTurno;
import model.enums.StatoGara;
import service.exception.AggiornaEccezione;
import service.exception.ConcorrenteHomeEccezione;
import service.exception.IscrizioneEccezione;
import service.exception.RicercaEccezione;

public class ArbitroService {

	private final ArbitroDAO arbitroDAO;
	private final GaraDAO garaDAO;
	private final PunteggioDAO punteggioDAO;
	
	public ArbitroService() {
		this.arbitroDAO = new ArbitroDAO();
		this.garaDAO = new GaraDAO();
		this.punteggioDAO = new PunteggioDAO();
	}
	
	
	
	
	public int getNumTurni(int codiceGara) {
		return this.garaDAO.getNumTurni(codiceGara);
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
	
	public void assegnaArbitroAGara(int codiceGara, String arb) throws IscrizioneEccezione {
		try {
			this.arbitroDAO.assegnaArbitroAGara(codiceGara, arb);
		} catch(ArbitroEccezione e) {
			throw new IscrizioneEccezione(e.getMessage(), e);
		}
	}
	
	public void rinvioGaraArbitro(int codiceGara, LocalDate data, int campo) throws AggiornaEccezione {
		try {
			this.garaDAO.controllaDataGara(data, campo);
			this.arbitroDAO.aggiornaDataGara(codiceGara, data);
		} catch(GaraEccezione e) {
			throw new AggiornaEccezione(e.getMessage(), e);
		} catch(ArbitroEccezione e) {
			throw new AggiornaEccezione(e.getMessage(), e);
		}
		
	}
	
	public void rimuoviArbitroDaGara(int codiceGara, String arb) throws AggiornaEccezione {
		try {
			this.arbitroDAO.disiscriviArbitro(codiceGara, arb);
		} catch(ArbitroEccezione e) {
			throw new AggiornaEccezione(e.getMessage(), e);
		}
		
	}
	
	public Gara getGara(int codice) {
		return this.garaDAO.getGaraById(codice);
	}
	
	public void salvaTurno(int codiceGara,int turno, List<RisultatoTurno> risultati) {
		try {
			punteggioDAO.salvaTurno(codiceGara, turno, risultati);
		} catch (Exception e) {
			throw new AggiornaEccezione("Errore nel salvataggio del turno", e);
		}
	}
	
	

	
}

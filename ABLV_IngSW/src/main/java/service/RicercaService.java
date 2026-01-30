package service;

import java.util.List;

import db.exception.CampoGaraEccezione;
import db.exception.ConcorrenteEccezione;
import db.exception.GaraEccezione;
import db.exception.SettoreEccezione;
import db.repository.CampoGaraDAO;
import db.repository.ConcorrenteDAO;
import db.repository.GaraDAO;
import db.repository.SettoreDAO;
import model.CampoGara;
import model.Concorrente;
import model.Gara;
import model.Settore;
import service.exception.RicercaEccezione;

public class RicercaService {
	
	private final CampoGaraDAO campoGaraDAO;
	private final SettoreDAO settoreDAO;
	private final GaraDAO garaDAO;
	private final ConcorrenteDAO concorrenteDAO;

	public RicercaService() {
		this.campoGaraDAO = new CampoGaraDAO();
		this.settoreDAO = new SettoreDAO();
		this.garaDAO = new GaraDAO();
		this.concorrenteDAO = new ConcorrenteDAO();
	}
	
	public List<CampoGara> getCampiGara() throws RicercaEccezione {
		List<CampoGara> ret = null;
		try {
			ret =  this.campoGaraDAO.getCampoGara();
		} catch(CampoGaraEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}
	
	public List<Settore> esploraSettori(CampoGara campoGara) throws RicercaEccezione{
		List<Settore> ret = null;
		try {
			ret = this.settoreDAO.esploraSettori(campoGara);
		} catch(SettoreEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}
	
	public List<Gara> esploraGare(CampoGara gara) throws RicercaEccezione{
		List<Gara> ret = null;
		try {
			ret = this.garaDAO.esploraGare(gara);
		} catch(SettoreEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}
	
	public List<Concorrente> getConcorrenti() throws RicercaEccezione{
		List<Concorrente> ret = null;
		try {
			ret = this.concorrenteDAO.getConcorrenti();
		} catch(ConcorrenteEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}
	
	public List<Gara> getGare() throws RicercaEccezione {
		List<Gara> ret = null;
		try {
			ret = this.garaDAO.getGare();
		} catch(GaraEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}
	
}

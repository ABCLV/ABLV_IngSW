package service;

import java.util.List;

import db.exception.SocietaEccezione;
import db.repository.SocietaDAO;
import model.Concorrente;
import model.Gara;
import model.Societa;
import service.exception.SocietaHomeEccezione;

public class SocietaService {
	
	private final SocietaDAO societaDAO;
	
	public SocietaService() {
		this.societaDAO = new SocietaDAO();
	}
	
	public Societa getSocieta(String nome) throws SocietaHomeEccezione {
		try {
			return this.societaDAO.getSocieta(nome);
		} catch(SocietaEccezione e) {
			throw new SocietaHomeEccezione(e.getMessage(), e);
		}
	}
	
	public List<Concorrente> getConcorrenti(String nome) throws SocietaHomeEccezione {
		try {
			return this.societaDAO.getConcorrentiPerSocieta(nome);
		} catch(SocietaEccezione e) {
			throw new SocietaHomeEccezione(e.getMessage(), e);
		}
	}
	
	public List<Gara> getGareProposte(String nome) throws SocietaHomeEccezione {
		try {
			return this.societaDAO.getGareProposteDaSocieta(nome);
		} catch(SocietaEccezione e) {
			throw new SocietaHomeEccezione(e.getMessage(), e);
		}
	}

}

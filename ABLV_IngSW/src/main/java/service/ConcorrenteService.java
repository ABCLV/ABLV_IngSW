package service;

import java.util.List;

import db.exception.ConcorrenteEccezione;
import db.exception.SocietaEccezione;
import db.repository.ConcorrenteDAO;
import db.repository.SocietaDAO;
import model.Concorrente;
import model.Gara;
import model.Societa;
import service.exception.ConcorrenteHomeEccezione;

public class ConcorrenteService {
	
	private final ConcorrenteDAO concorrenteDAO;
	private final SocietaDAO societaDAO;
	
	public ConcorrenteService() {
		this.concorrenteDAO = new ConcorrenteDAO();
		this.societaDAO = new SocietaDAO();
	}
	
	public Concorrente getConcorrente(String userName) throws ConcorrenteHomeEccezione {
		try {
			return this.concorrenteDAO.getConcorrente(userName);
		} catch(ConcorrenteEccezione e) {
			throw new ConcorrenteHomeEccezione(e.getMessage(), e);
		}
	}
	
	public List<Gara> getGareIscritte(String cf) throws ConcorrenteHomeEccezione {
		try {
			return this.concorrenteDAO.getGareConcorrente(cf);
		} catch(ConcorrenteEccezione e) {
			throw new ConcorrenteHomeEccezione(e.getMessage(), e);
		}
		
	}

	public Societa getDettagliSocieta(String nome) throws ConcorrenteHomeEccezione {
		try {
			return this.societaDAO.getSocieta(nome);
		} catch(SocietaEccezione e) {
			throw new ConcorrenteHomeEccezione(e.getMessage(), e);
		}
	}

	public Concorrente fromUsername(String cf) throws ConcorrenteHomeEccezione {
		try {
			return this.concorrenteDAO.getConcorrente(cf);
		} catch(ConcorrenteEccezione e) {
			throw new ConcorrenteHomeEccezione(e.getMessage(), e);
		}
	}

}

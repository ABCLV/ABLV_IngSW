package service;

import db.exception.ConcorrenteEccezione;
import db.repository.ConcorrenteDAO;
import service.exception.HomeEccezione;;

public class HomeService {
	
	private final ConcorrenteDAO concorrenteDAO;
	
	public HomeService() {
		this.concorrenteDAO = new ConcorrenteDAO();
	}
	
	public long getTotalePescatori() throws HomeEccezione {
		try {
			return this.concorrenteDAO.countPescatori();
		} catch (ConcorrenteEccezione e) {
			throw new HomeEccezione(e.getMessage(), e);
		}
	}
}
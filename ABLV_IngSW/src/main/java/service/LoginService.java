package service;

import db.exception.AuthEccezione;
import db.repository.AuthDAO;
import service.exception.LoginEccezione;

public class LoginService {
	
	private final AuthDAO authDAO;
	
	public LoginService() {
		this.authDAO = new AuthDAO();
	}
	
	public void login(String tipo, String id, String pwd) throws LoginEccezione {
	    try {
	        boolean valido = this.authDAO.checkPassword(tipo, id, pwd);
	        if (!valido) {
	            throw new LoginEccezione("Credenziali non valide");
	        }
	    } catch(AuthEccezione e) {
	        throw new LoginEccezione(e.getMessage(), e);
	    }
	}
	
}

package service;

import java.util.List;

import db.repository.PunteggioDAO;
import model.Concorrente;

public class PunteggioService {
	
	private final PunteggioDAO punteggioDAO = new PunteggioDAO();
	public PunteggioService() {}
		
	public void assegnazioneGruppi(String codiceGara, List<Concorrente> assenti){
		punteggioDAO.definizioneGruppi(codiceGara, assenti);
	}
}

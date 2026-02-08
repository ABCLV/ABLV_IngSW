package service;

import java.util.List;

import db.repository.ConcorrenteDAO;
import db.repository.GaraDAO;
import db.repository.PunteggioDAO;
import model.Concorrente;

public class PunteggioService {
	
	private final PunteggioDAO punteggioDAO = new PunteggioDAO();
	private final ConcorrenteDAO concDAO = new ConcorrenteDAO();
	public PunteggioService() {}
		
	public void assegnazioneGruppi(int codiceGara, List<Concorrente> assenti){
		punteggioDAO.definizioneGruppi(codiceGara, assenti);
	}
	
	public List<Concorrente> getConcorrentiSettore(int codiceGara, int settore){
		return concDAO.getConcorrentiPerSettore(codiceGara, settore);
	}
}

package service;

import java.sql.SQLException;
import java.util.List;

import db.repository.AmministratoreDAO;
import db.repository.GaraDAO;
import db.repository.TurnoDAO;
import model.Gara;
import service.exception.PropostaEccezione;
import db.exception.*;
//import model.interfaces.*;

public class AmministratoreService /*implements PropositoreIF*/ {
	
	private final GaraDAO garaDAO;
	private final AmministratoreDAO amministratoreDAO;
	private final TurnoDAO turnoDAO;
	
	public AmministratoreService() {
		this.garaDAO = new GaraDAO();
		this.amministratoreDAO = new AmministratoreDAO();
		this.turnoDAO = new TurnoDAO();
	};
	
	/*
	/**
	 * Crea e proponi una nuova gara.
	 * 
	 * @param gara oggetto gara da inserire nel sistema
	 
	public boolean proponiGara(Gara gara) {
		boolean ret = false;
		try {
			GaraDAO.insertGara(gara);
			ret = true;
		} catch(Exception e) {
			e.printStackTrace();
			new Alert(Alert.AlertType.INFORMATION, "Errore nell'inserimento della nuova gara!");
		}
		
		return ret;
	}*/

	/**
	 * Approva una proposta di gara.
	 * 
	 * @param numGara numero identificativo della gara
	 * @throws SQLException
	 */
	public void confermaProposta(String codiceGara, String ammId) throws PropostaEccezione {
		try {
			this.garaDAO.accettaGara(codiceGara, ammId);
		} catch(GaraEccezione e) {
			e.printStackTrace();
			throw new PropostaEccezione(e.getMessage(), e);
		}
	}

	/**
	 * Rifiuta una proposta di gara inviando il motivo.
	 * 
	 * @param numGara numero identificativo della gara
	 * @param motivo  motivazione del rifiuto
	 */
	public void negaProposta(String codiceGara, String ammId) throws PropostaEccezione {
		try {
			this.turnoDAO.eliminaTurniPerGara(codiceGara);
			this.garaDAO.rifiutaGara(codiceGara, ammId);
		} catch(GaraEccezione e) {
			e.printStackTrace();
			throw new PropostaEccezione(e.getMessage(), e);
		}
	}

	public List<Gara> mieProposte(String id) throws PropostaEccezione {
		List<Gara> ret = null;
		try {
			ret = this.amministratoreDAO.getGareProposteDaAmministratore(id);
		} catch(AmministratoreEccezione e) {
			e.printStackTrace();
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	public List<Gara> gareDaConfermare(String id) throws PropostaEccezione {
		List<Gara> ret = null;
		try {
			ret = this.garaDAO.getGareDaConfermare(id);
		} catch(GaraEccezione e) {
			e.printStackTrace();
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	/**
	 * Invia notifica alle società interessate.
	 */
	private void notificaSocieta() {
	}

	/**
	 * Invia notifica al comune competente.
	 */
	private void notificaComune() {
	}

	/**
	 * Restituisce il testo del regolamento aggiornato.
	 * 
	 * @return testo completo del regolamento
	 */
	public String regolamento() {
		return null;
	}
	
}

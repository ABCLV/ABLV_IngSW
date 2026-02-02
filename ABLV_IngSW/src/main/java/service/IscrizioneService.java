package service;

import java.time.LocalDate;
import java.util.List;

import db.exception.ConcorrenteEccezione;
import db.exception.GaraEccezione;
import db.exception.IscrizioneEccezioneDB;
import db.repository.GaraDAO;
import db.repository.IscrizioneDAO;
import model.Concorrente;
import model.Gara;
import service.exception.ConcorrenteHomeEccezione;
import service.exception.IscrizioneEccezione;
import service.exception.PropostaEccezione;
import service.exception.RicercaEccezione;

public class IscrizioneService {

	private final IscrizioneDAO iscrizioneDAO;
	private final GaraDAO garaDAO = new GaraDAO();
	
	public IscrizioneService() {
		this.iscrizioneDAO = new IscrizioneDAO();
	}
	
	public void iscriviConcorrenteAGara(String cf, String codiceGara) throws IscrizioneEccezione {
	    try {
	    	if (this.iscrizioneDAO.esisteIscrizione(cf, codiceGara)) {
	            throw new Exception("Il concorrente è già iscritto a questa gara!");
	        }
	    	
	        LocalDate dataIscrizione = LocalDate.now();
	        int nuovoNumIscrizione = this.calcolaNumeroIscrizione(codiceGara);
	        
	        int nuovoIdIscrizione = this.getUltimoCodiceIscrizione();
	        if(nuovoIdIscrizione < 0) {
	        	throw new Exception();
	        }
	        nuovoIdIscrizione++;
	        
	        System.out.println("Id iscrizione: " + nuovoIdIscrizione);
	        System.out.println("CF: " + cf);
	        System.out.println("Gara: " + codiceGara);
	        System.out.println("Data: " + dataIscrizione);
	        System.out.println("Numero iscrizione a gara: " + nuovoNumIscrizione);
	        
	        this.iscrizioneDAO.inserisciIscrizione(nuovoIdIscrizione, cf, codiceGara,
	        		dataIscrizione, nuovoNumIscrizione);
	    } catch (IscrizioneEccezioneDB e) {
	        throw new IscrizioneEccezione("Errore durante l'iscrizione del concorrente alla gara", e);
	    } catch(Exception e) {
	    	throw new IscrizioneEccezione("Errore durante l'iscrizione del concorrente alla gara", e);
	    }
	}

	private int calcolaNumeroIscrizione(String codiceGara) throws IscrizioneEccezione {
	    try {
	        Integer ultimaIscrizione = this.iscrizioneDAO.getUltimoNumeroIscrizione(codiceGara);
	        return (ultimaIscrizione == null) ? 1 : ultimaIscrizione + 1;
	    } catch (IscrizioneEccezioneDB e) {
	        throw new IscrizioneEccezione("Errore nel calcolare il numero iscrizione", e);
	    }
	}
	
	public int getUltimoCodiceIscrizione() throws PropostaEccezione {
		int ret = -1;
		try {
			ret = this.iscrizioneDAO.getUltimoCodiceIscrizione();
		} catch(Exception e) {
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	public List<String> getCodiciGareIscrittoPerConcorrente(String cf) throws IscrizioneEccezione {
		try {
			return this.iscrizioneDAO.getCodiciGareIscrittoPerConcorrente(cf);
		} catch(IscrizioneEccezioneDB e) {
			throw new IscrizioneEccezione(e.getMessage(), e);
		}
	}
	
	public List<Gara> getGareDisponibiliPerIscrizione(){
		List<Gara> ret = null;
		try {
			ret = this.garaDAO.getGareDisponibiliPerIscrizione();
		} catch(GaraEccezione e) {
			throw new RicercaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}
	
	
	public List<Concorrente> getIscrizioniGara(String codice) throws  IscrizioneEccezioneDB{
		try {
			return this.iscrizioneDAO.getConcorrenti(codice);
		} catch(ConcorrenteEccezione e) {
			throw new ConcorrenteHomeEccezione(e.getMessage(), e);
		}
	}

}

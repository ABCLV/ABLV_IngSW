package service;

import db.exception.ArbitroEccezione;
import db.exception.CampionatoEccezione;
import db.repository.AmministratoreDAO;
import db.repository.ArbitroDAO;
import db.repository.CampionatoDAO;
import db.repository.CampoGaraDAO;
import db.repository.GaraDAO;
import db.repository.SocietaDAO;
import model.Arbitro;
import model.Campionato;
import model.CampoGara;
import model.Gara;
import model.enums.*;
import model.interfaces.SoggettoIF;
import service.exception.PropostaEccezione;
import state.Session;

import java.time.LocalDate;
import java.util.List;

public class ProponiGaraService {
	
	private final GaraDAO garaDAO;
	private final SocietaDAO societaDAO;
	private final AmministratoreDAO amministratoreDAO;
	private final CampionatoDAO campionatoDAO;
	private final ArbitroDAO arbitroDAO;
	private final CampoGaraDAO campoGaraDAO;
	
	public ProponiGaraService() {
		this.garaDAO = new GaraDAO();
		this.societaDAO = new SocietaDAO();
		this.amministratoreDAO = new AmministratoreDAO();
		this.campionatoDAO = new CampionatoDAO();
		this.arbitroDAO = new ArbitroDAO();
		this.campoGaraDAO = new CampoGaraDAO();
	}


	/* ---------- carica liste da DB ---------- */
	public List<Campionato> caricaCampionati() throws PropostaEccezione {
		List<Campionato> ret = null;
		try {
			ret = this.campionatoDAO.getCampionati();
		} catch(CampionatoEccezione e) {
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	public List<Arbitro> caricaArbitri() throws PropostaEccezione {
		List<Arbitro> ret = null;
		try {
			ret = this.arbitroDAO.getArbitri();
		} catch(ArbitroEccezione e) {
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	public List<CampoGara> caricaCampiGara() throws PropostaEccezione {
		List<CampoGara> ret = null;
		try {
			ret = this.campoGaraDAO.getCampoGara();
		} catch(Exception e) {
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	/* ---------- verifica business ---------- */
	public boolean esisteGaraInCampionato(Campionato c, int numProva) throws PropostaEccezione {
		boolean ret = false;
		try {
			ret = this.campionatoDAO.esisteGaraInCampionato(c, numProva);
		} catch(Exception e) {
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	/* ---------- ricerche per chiave ---------- 
	public Campionato getSelectedCampionato(String titolo, List<Campionato> lista) {
		Campionato ret = null;
		if (titolo != null) {
			ret = lista.stream().filter(c -> c.getTitolo().equals(titolo)).findFirst().orElse(null);
		}
		
		return ret;
	}

	public Arbitro getSelectedArbitro(String cf, List<Arbitro> lista) {
		Arbitro ret = null;
		if (cf != null) {
			ret = lista.stream().filter(a -> a.getCfArbitro().equals(cf)).findFirst().orElse(null);
		}
	
		return ret;
	}

	public CampoGara getSelectedCampoGara(String id, List<CampoGara> lista) {
		CampoGara ret = null;
		if (id != null) {
			ret = lista.stream().filter(cg -> cg.getIdCampoGara().equals(id)).findFirst().orElse(null);
		}
		
		return ret;
	}*/

	/* ---------- genera codice ---------- */
	public String getUltimoCodiceGara() throws PropostaEccezione {
		String ret = null;
		try {
			ret = this.garaDAO.getUltimoCodiceGara();
		} catch(Exception e) {
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	/* ---------- carica propositore ---------- */
	public SoggettoIF caricaPropositore(String tipo, String nome) throws PropostaEccezione {
		SoggettoIF ret = null;
		try {
			switch(tipo) {
			case "Societa":
				ret = this.societaDAO.getSocieta(nome);
				break;
			case "Amministratore":
				ret = this.amministratoreDAO.getAmministratore(nome);
				break;
			default:
				throw new Exception();
			}
		} catch(Exception e) {
			throw new PropostaEccezione(e.getMessage(), e);
		}
		
		return ret;
	}

	/* ---------- salva gara ---------- */
	public void insertGara(Tecnica tecnica,  CriterioPunti criterio, LocalDate data, int minPersone,
			int maxPersone, TipologiaGara tipo, Campionato campionato, int numProva, 
			Arbitro arbitro, CampoGara campoGara, String userType, String userName) throws PropostaEccezione {
		try {
			// Controllo: se è stato selezionato un campionato, verificare che non esista già 
	        // una gara con lo stesso numero di prova in quel campionato
	        if (campionato != null && this.esisteGaraInCampionato(campionato, numProva)) {
	            throw new Exception("Esiste gia' una gara con quel numero di prova in quel campionato!");
	        }
			SoggettoIF propositore = null;
            if(userType.equals("Societa")) {
                propositore = this.caricaPropositore("Societa", Session.getUserName());
            } else if(userType.equals("Amministratore")) {
                propositore = this.caricaPropositore("Amministratore", Session.getUserName());
            }
            
            String ultimoCodice = this.getUltimoCodiceGara();
            int numero = Integer.parseInt(ultimoCodice.substring(1));
            numero++;
            String nuovoCodice = String.format("G%03d", numero);

            Gara g = new Gara();
            g.setCodice(nuovoCodice);
            g.setNumProva(numProva);
            g.setTecnica(tecnica);
            g.setCriterioPunti(criterio);
            g.setData(data);
            g.setMaxPersone(maxPersone);
            g.setMinPersone(minPersone);
            g.setStatoConferma(StatoConferma.IN_ATTESA);
            g.setStatoGara(StatoGara.NON_INIZIATA);
            g.setTipoGara(tipo);
            g.setPropositore(propositore);
            g.setCampionato(campionato);
            g.setArbitro(arbitro);
            g.setCampoGara(campoGara);
            
			this.garaDAO.insertGara(g);
		} catch(Exception e) {
			throw new PropostaEccezione(e.getMessage(), e);
		}
	}

}
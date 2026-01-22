package applicazione.service;

import database.dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

import applicazione.entita.Arbitro;
import applicazione.entita.Campionato;
import applicazione.entita.CampoGara;
import applicazione.entita.Gara;
import applicazione.interfacce.PropositoreIF;

public final class ProponiGaraService {

	/* ---------- carica liste da DB ---------- */
	public static ObservableList<Campionato> caricaCampionati() {
		return FXCollections.observableArrayList(CampionatoDAO.getCampionati());
	}

	public static ObservableList<Arbitro> caricaArbitri() {
		return FXCollections.observableArrayList(ArbitroDAO.getArbitri());
	}

	public static ObservableList<CampoGara> caricaCampiGara() {
		return FXCollections.observableArrayList(CampoGaraDAO.getCampiGara());
	}

	/* ---------- verifica business ---------- */
	public static boolean esisteGaraInCampionato(Campionato c, int numProva) {
		return CampionatoDAO.esisteGaraInCampionato(c, numProva);
	}

	/* ---------- ricerche per chiave ---------- */
	public static Campionato getSelectedCampionato(String titolo, List<Campionato> lista) {
		if (titolo == null)
			return null;
		return lista.stream().filter(c -> c.getTitolo().equals(titolo)).findFirst().orElse(null);
	}

	public static Arbitro getSelectedArbitro(String cf, List<Arbitro> lista) {
		if (cf == null)
			return null;
		return lista.stream().filter(a -> a.getCfArbitro().equals(cf)).findFirst().orElse(null);
	}

	public static CampoGara getSelectedCampoGara(String id, List<CampoGara> lista) {
		if (id == null)
			return null;
		return lista.stream().filter(cg -> cg.getIdCampoGara().equals(id)).findFirst().orElse(null);
	}

	/* ---------- genera codice ---------- */
	public static String getUltimoCodiceGara() {
		return GaraDAO.getUltimoCodiceGara();
	}

	/* ---------- carica propositore ---------- */
	public static PropositoreIF caricaPropositore(String tipo, String nome) throws SQLException {
		return switch (tipo) {
		case "Societa" -> SocietaDAO.getSocieta(nome);
		case "Amministratore" -> AmministratoreDAO.getAmministratore(nome);
		default -> throw new IllegalArgumentException("Tipo propositore non valido: " + tipo);
		};
	}

	/* ---------- salva gara ---------- */
	public static void insertGara(Gara g) {
		GaraDAO.insertGara(g);
	}

	private ProponiGaraService() {
	}
}
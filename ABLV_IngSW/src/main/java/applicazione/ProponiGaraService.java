package applicazione;

import database.Consultazioni;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public final class ProponiGaraService {

    /* ---------- carica liste da DB ---------- */
    public static ObservableList<Campionato> caricaCampionati() {
        return FXCollections.observableArrayList(Consultazioni.getCampionati());
    }
    public static ObservableList<Arbitro> caricaArbitri() {
        return FXCollections.observableArrayList(Consultazioni.getArbitri());
    }
    public static ObservableList<CampoGara> caricaCampiGara() {
        return FXCollections.observableArrayList(Consultazioni.getCampiGara());
    }

    /* ---------- verifica business ---------- */
    public static boolean esisteGaraInCampionato(Campionato c, int numProva) {
        return Consultazioni.esisteGaraInCampionato(c, numProva);
    }

    /* ---------- ricerche per chiave ---------- */
    public static Campionato getSelectedCampionato(String titolo, List<Campionato> lista) {
        if (titolo == null) return null;
        return lista.stream()
                    .filter(c -> c.getTitolo().equals(titolo))
                    .findFirst()
                    .orElse(null);
    }
    public static Arbitro getSelectedArbitro(String cf, List<Arbitro> lista) {
        if (cf == null) return null;
        return lista.stream()
                    .filter(a -> a.getCfArbitro().equals(cf))
                    .findFirst()
                    .orElse(null);
    }
    public static CampoGara getSelectedCampoGara(String id, List<CampoGara> lista) {
        if (id == null) return null;
        return lista.stream()
                    .filter(cg -> cg.getIdCampoGara().equals(id))
                    .findFirst()
                    .orElse(null);
    }

    /* ---------- genera codice ---------- */
    public static String getUltimoCodiceGara() {
        return Consultazioni.getUltimoCodiceGara();
    }

    /* ---------- carica propositore ---------- */
    public static PropositoreIF caricaPropositore(String tipo, String nome) throws SQLException {
        return switch (tipo) {
            case "Societa"   -> Consultazioni.getSocieta(nome);
            case "Amministratore" -> Consultazioni.getAmministratore(nome);
            default -> throw new IllegalArgumentException("Tipo propositore non valido: " + tipo);
        };
    }

    /* ---------- salva gara ---------- */
    public static void insertGara(Gara g) {
        Consultazioni.insertGara(g);
    }

    private ProponiGaraService() { }
}
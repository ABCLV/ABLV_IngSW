package database;

import org.jooq.tools.csv.CSVReader;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class PopolaDB {

    public static void main(String[] args) {
        
        try {
            //importCampoGara();
        	//importArbitro();
        	//importSettore();
        	//importAmministratore();
        	//importSponsor();
        	//importSede();
        	//importSocieta();
        	//importConcorrente();
        	//importCampionato();
        	
            
        	importGara();
        	
            
        	
            
            
            
            
            
            
            //importTurno();
            //importPartecipa();
            //importRecensisce();
            //importIscrive();
            //importContratto();
            
            
            System.out.println("Tutti i CSV importati con successo!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void importCSV(String filePath, String sqlInsert, int numColumns) throws Exception {
        
    	String url = "jdbc:sqlite:database/gare.db";
    	try (Connection conn = DriverManager.getConnection(url);
            CSVReader reader = new CSVReader(new FileReader(filePath))) {

            String[] line;
            reader.readNext(); // Salta header
            while ((line = reader.readNext()) != null) {
                if (line.length != numColumns) continue; // evita errori
                try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                    for (int i = 0; i < numColumns; i++) {
                        pstmt.setString(i + 1, line[i]);
                    }
                    pstmt.executeUpdate();
                }
            }
        }
    }
    
    private static void importCampoGara() throws Exception {
        String sql = "INSERT INTO CAMPOGARA(ID, Paese, CorpoIdrico, Lunghezza, Descrizione) VALUES (?, ?, ?, ?, ?)";
        importCSV("database/csv/CAMPOGARA.csv", sql, 5);
    }

    private static void importSede() throws Exception {
        String sql = "INSERT INTO SEDE(ID, Indirizzo, Citta, Cap) VALUES (?, ?, ?, ?)";
        importCSV("database/csv/SEDE.csv", sql, 4);
    }

    private static void importSocieta() throws Exception {
        String sql = "INSERT INTO SOCIETA(Nome, Email, Sede) VALUES (?, ?, ?)";
        importCSV("database/csv/SOCIETA.csv", sql, 3);
    }

    private static void importCampionato() throws Exception {
        String sql = "INSERT INTO CAMPIONATO(Titolo, Categoria) VALUES (?, ?)";
        importCSV("database/csv/CAMPIONATO.csv", sql, 2);
    }

    private static void importArbitro() throws Exception {
        String sql = "INSERT INTO ARBITRO(CF, Nome, Cognome, Sezione) VALUES (?, ?, ?, ?)";
        importCSV("database/csv/ARBITRO.csv", sql, 4);
    }

    private static void importAmministratore() throws Exception {
        String sql = "INSERT INTO AMMINISTRATORE(CF, Nome, Cognome, Email) VALUES (?, ?, ?, ?)";
        importCSV("database/csv/AMMINISTRATORE.csv", sql, 4);
    }

    private static void importConcorrente() throws Exception {
        String sql = "INSERT INTO CONCORRENTE(CF, Nome, Cognome, Email, Nascita, Societa) VALUES (?, ?, ?, ?, ?, ?)";
        importCSV("database/csv/CONCORRENTE.csv", sql, 6);
    }

    private static void importSponsor() throws Exception {
        String sql = "INSERT INTO SPONSOR(ID, Nome, Sito) VALUES (?, ?, ?)";
        importCSV("database/csv/SPONSOR.csv", sql, 3);
    }

    private static void importSettore() throws Exception {
        String sql = "INSERT INTO SETTORE(ID, Lunghezza, Descrizione, CampoGara) VALUES (?, ?, ?, ?)";
        importCSV("database/csv/SETTORE.csv", sql, 4);
    }

    private static void importGara() throws Exception {
        String sql = "INSERT INTO GARA(Codice, NumProva, Tecnica, CriterioPunti, MinPersone, MaxPersone, StatoGara, StatoConferma, TipoGara, Data, Campionato, Arbitro, AmministratoreProposta, AmministratoreAccettazione, Societa, CampoGara) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        importCSV("database/csv/GARA.csv", sql, 16);
    }

    private static void importTurno() throws Exception {
        String sql = "INSERT INTO TURNO(Codice, Numero, Durata, Settore, Gara) VALUES (?, ?, ?, ?, ?)";
        importCSV("database/csv/TURNO.csv", sql, 5);
    }

    private static void importPartecipa() throws Exception {
        String sql = "INSERT INTO PARTECIPA(IdPunteggio, IdTurno, Concorrente, NumPunti, Squalifica) VALUES (?, ?, ?, ?, ?)";
        importCSV("database/csv/PARTECIPA.csv", sql, 5);
    }

    private static void importRecensisce() throws Exception {
        String sql = "INSERT INTO RECENSISCE(IdRecensione, CodiceGara, Concorrente, Titolo, Voto, Commento) VALUES (?, ?, ?, ?, ?, ?)";
        importCSV("database/csv/RECENSISCE.csv", sql, 6);
    }

    private static void importIscrive() throws Exception {
        String sql = "INSERT INTO ISCRIVE(IdIscrizione, CodiceGara, Concorrente, DataIscrizione, NumIscrizione, Societa) VALUES (?, ?, ?, ?, ?, ?)";
        importCSV("database/csv/ISCRIVE.csv", sql, 6);
    }

    private static void importContratto() throws Exception {
        String sql = "INSERT INTO CONTRATTO(IdContratto, Sponsor, Concorrente, DataFirma, DataScadenza, Premio) VALUES (?, ?, ?, ?, ?, ?)";
        importCSV("database/csv/CONTRATTO.csv", sql, 6);
    }
    
    
    
}

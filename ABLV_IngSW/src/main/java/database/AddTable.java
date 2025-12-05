package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddTable {

    public static void main(String[] args) {
        // Percorso del database (uguale a quello creato prima)
        String url = "jdbc:sqlite:database/gare.db";

        
        String[] sqlStatements = {
        	    "CREATE TABLE IF NOT EXISTS CAMPOGARA (ID VARCHAR(50) PRIMARY KEY NOT NULL, Paese VARCHAR(50) NOT NULL, CorpoIdrico VARCHAR(100) NOT NULL, Lunghezza INT);",
        	    "CREATE TABLE IF NOT EXISTS SEDE (ID INT PRIMARY KEY NOT NULL, Indirizzo VARCHAR(100) NOT NULL, Citta VARCHAR(50) NOT NULL, Cap VARCHAR(10) NOT NULL);",
        	    "CREATE TABLE IF NOT EXISTS SOCIETA (Nome VARCHAR(100) PRIMARY KEY NOT NULL, Email VARCHAR(100) NOT NULL, Sede INT NOT NULL, FOREIGN KEY (Sede) REFERENCES SEDE(ID));",
        	    "CREATE TABLE IF NOT EXISTS CAMPIONATO (Titolo VARCHAR(100) PRIMARY KEY NOT NULL, Categoria VARCHAR(50) NOT NULL);",
        	    "CREATE TABLE IF NOT EXISTS ARBITRO (CF VARCHAR(16) PRIMARY KEY NOT NULL, Nome VARCHAR(50) NOT NULL, Cognome VARCHAR(50) NOT NULL, Sezione VARCHAR(50));",
        	    "CREATE TABLE IF NOT EXISTS AMMINISTRATORE (CF VARCHAR(16) PRIMARY KEY NOT NULL, Nome VARCHAR(50) NOT NULL, Cognome VARCHAR(50) NOT NULL, Email VARCHAR(100) NOT NULL);",
        	    "CREATE TABLE IF NOT EXISTS CONCORRENTE (CF VARCHAR(16) PRIMARY KEY NOT NULL, Nome VARCHAR(50) NOT NULL, Cognome VARCHAR(50) NOT NULL, Email VARCHAR(100) NOT NULL, Nascita DATE, Societa VARCHAR(100) NOT NULL, FOREIGN KEY (Societa) REFERENCES SOCIETA(Nome));",
        	    "CREATE TABLE IF NOT EXISTS SPONSOR (ID VARCHAR(50) PRIMARY KEY NOT NULL, Nome VARCHAR(50) NOT NULL, Sito VARCHAR(100));",
        	    "CREATE TABLE IF NOT EXISTS SETTORE (ID VARCHAR(50) PRIMARY KEY NOT NULL, Lunghezza INT, Descrizione VARCHAR(100), CampoGara VARCHAR(50) NOT NULL, FOREIGN KEY (CampoGara) REFERENCES CAMPOGARA(ID));",
        	    "CREATE TABLE IF NOT EXISTS GARA (Codice VARCHAR(50) PRIMARY KEY NOT NULL, NumProva INT, Tecnica VARCHAR(50) NOT NULL, CriterioPunti VARCHAR(50) NOT NULL, MinPersone INT NOT NULL, MaxPersone INT NOT NULL, StatoGara VARCHAR(50) NOT NULL DEFAULT 'NON_INIZIATA', StatoConferma VARCHAR(50) NOT NULL DEFAULT 'IN_ATTESA', TipoGara VARCHAR(50) NOT NULL, Data DATE NOT NULL, Campionato VARCHAR(100), Arbitro VARCHAR(16), AmministratoreProposta VARCHAR(16), AmministratoreAccettazione VARCHAR(16), Societa VARCHAR(100), CampoGara VARCHAR(50) NOT NULL, FOREIGN KEY (Campionato) REFERENCES CAMPIONATO(Titolo), FOREIGN KEY (Arbitro) REFERENCES ARBITRO(CF), FOREIGN KEY (AmministratoreProposta) REFERENCES AMMINISTRATORE(CF), FOREIGN KEY (AmministratoreAccettazione) REFERENCES AMMINISTRATORE(CF), FOREIGN KEY (Societa) REFERENCES SOCIETA(Nome), FOREIGN KEY (CampoGara) REFERENCES CAMPOGARA(ID));",
        	    "CREATE TABLE IF NOT EXISTS TURNO (Codice VARCHAR(50) PRIMARY KEY NOT NULL, Numero INT NOT NULL, Durata INT, Settore VARCHAR(50) NOT NULL, Gara VARCHAR(50) NOT NULL, FOREIGN KEY (Settore) REFERENCES SETTORE(ID), FOREIGN KEY (Gara) REFERENCES GARA(Codice));",
        	    "CREATE TABLE IF NOT EXISTS PARTECIPA (IdPunteggio VARCHAR(50) PRIMARY KEY NOT NULL, IdTurno VARCHAR(50) NOT NULL, Concorrente VARCHAR(16) NOT NULL, NumPunti INT NOT NULL, Squalifica BOOLEAN NOT NULL DEFAULT 0, FOREIGN KEY (IdTurno) REFERENCES TURNO(Codice), FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF));",
        	    "CREATE TABLE IF NOT EXISTS RECENSISCE (IdRecensione VARCHAR(50) PRIMARY KEY NOT NULL, CodiceGara VARCHAR(50) NOT NULL, Concorrente VARCHAR(16) NOT NULL, Titolo VARCHAR(100) NOT NULL, Voto INT NOT NULL, Commento TEXT, FOREIGN KEY (CodiceGara) REFERENCES GARA(Codice), FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF));",
        	    "CREATE TABLE IF NOT EXISTS ISCRIVE (IdIscrizione INT PRIMARY KEY NOT NULL, CodiceGara VARCHAR(50) NOT NULL, Concorrente VARCHAR(16) NOT NULL, DataIscrizione DATE NOT NULL, NumIscrizione INT NOT NULL, Societa VARCHAR(100) NOT NULL, FOREIGN KEY (CodiceGara) REFERENCES GARA(Codice), FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF), FOREIGN KEY (Societa) REFERENCES SOCIETA(Nome));",
        	    "CREATE TABLE IF NOT EXISTS CONTRATTO (IdContratto INT PRIMARY KEY NOT NULL, Sponsor VARCHAR(50) NOT NULL, Concorrente VARCHAR(16) NOT NULL, DataFirma DATE NOT NULL, DataScadenza DATE NOT NULL, Premio VARCHAR(100), FOREIGN KEY (Sponsor) REFERENCES SPONSOR(ID), FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF));"
        	};
            

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

        	for (String sql : sqlStatements) {
                stmt.execute(sql);
            }
            System.out.println("Tutte le tabelle create");

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

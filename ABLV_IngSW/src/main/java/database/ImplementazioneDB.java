package database;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ImplementazioneDB {

    public static void main(String[] args) {

        // File locale: ./mydb.mv.db
    	String url = "jdbc:h2:./database/mydb;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=CREATE SCHEMA IF NOT EXISTS PUBLIC";
        String user = "sa";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            DSLContext create = DSL.using(conn);

            String sql = """
                CREATE TABLE CAMPOGARA (
                    ID VARCHAR(50) PRIMARY KEY,
                    Paese VARCHAR(50),
                    CorpoIdrico VARCHAR(100),
                    Lunghezza INT
                );
                
                CREATE TABLE SEDE (
                    ID INT PRIMARY KEY,
                    Indirizzo VARCHAR(100),
                    Citta VARCHAR(50),
                    Cap VARCHAR(10)
                );
                
                CREATE TABLE SOCIETA (
                    Nome VARCHAR(100) PRIMARY KEY,
                    Email VARCHAR(100),
                    Sede INT,
                    FOREIGN KEY (Sede) REFERENCES SEDE(ID)
                );
                
                CREATE TABLE CAMPIONATO (
                    Titolo VARCHAR(100) PRIMARY KEY,
                    Categoria VARCHAR(50)
                );
                
                CREATE TABLE ARBITRO (
                    CF VARCHAR(16) PRIMARY KEY,
                    Nome VARCHAR(50),
                    Cognome VARCHAR(50),
                    Sezione VARCHAR(50)
                );
                
                CREATE TABLE AMMINISTRATORE (
                    CF VARCHAR(16) PRIMARY KEY,
                    Nome VARCHAR(50),
                    Cognome VARCHAR(50),
                    Email VARCHAR(100)
                );
                
                CREATE TABLE CONCORRENTE (
                    CF VARCHAR(16) PRIMARY KEY,
                    Nome VARCHAR(50),
                    Cognome VARCHAR(50),
                    Email VARCHAR(100),
                    Nascita DATE,
                    Societa VARCHAR(100),
                    FOREIGN KEY (Societa) REFERENCES SOCIETA(Nome)
                );
                
                CREATE TABLE SPONSOR (
                    ID VARCHAR(50) PRIMARY KEY,
                    Nome VARCHAR(50),
                    Sito VARCHAR(100)
                );
                
                CREATE TABLE SETTORE (
                    ID VARCHAR(50) PRIMARY KEY,
                    Lunghezza INT,
                    Descrizione VARCHAR(100),
                    CampoGara VARCHAR(50),
                    FOREIGN KEY (CampoGara) REFERENCES CAMPOGARA(ID)
                );
                
                CREATE TABLE GARA (
                    Codice VARCHAR(50) PRIMARY KEY,
                    NumProva INT,
                    Tecnica VARCHAR(50),
                    CriterioPunti VARCHAR(50),
                    MinPersone INT,
                    MaxPersone INT,
                    StatoGara VARCHAR(50),
                    StatoOrganizzazione VARCHAR(50),
                    TipoGara VARCHAR(50),
                    Data DATE,
                    Campionato VARCHAR(100),
                    Arbitro VARCHAR(16),
                    AmministratoreProposta VARCHAR(16),
                    AmministratoreAccettazione VARCHAR(16),
                    Societa VARCHAR(100),
                    CampoGara VARCHAR(50),
                    FOREIGN KEY (Campionato) REFERENCES CAMPIONATO(Titolo),
                    FOREIGN KEY (Arbitro) REFERENCES ARBITRO(CF),
                    FOREIGN KEY (AmministratoreProposta) REFERENCES AMMINISTRATORE(CF),
                    FOREIGN KEY (AmministratoreAccettazione) REFERENCES AMMINISTRATORE(CF),
                    FOREIGN KEY (Societa) REFERENCES SOCIETA(Nome),
                    FOREIGN KEY (CampoGara) REFERENCES CAMPOGARA(ID)
                );
                
                CREATE TABLE TURNO (
                    Codice VARCHAR(50) PRIMARY KEY,
                    Numero INT,
                    Durata INT,
                    Settore VARCHAR(50),
                    Gara VARCHAR(50),
                    FOREIGN KEY (Settore) REFERENCES SETTORE(ID),
                    FOREIGN KEY (Gara) REFERENCES GARA(Codice)
                );
                
                CREATE TABLE PARTECIPA (
                    IdPunteggio VARCHAR(50) PRIMARY KEY,
                    IdTurno VARCHAR(50),
                    Concorrente VARCHAR(16),
                    NumPunti INT,
                    Squalifica BOOLEAN,
                    FOREIGN KEY (IdTurno) REFERENCES TURNO(Codice),
                    FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF)
                );
                
                CREATE TABLE RECENSISCE (
                    IdRecensione VARCHAR(50) PRIMARY KEY,
                    CodiceGara VARCHAR(50),
                    Concorrente VARCHAR(16),
                    Titolo VARCHAR(100),
                    Voto INT,
                    Commento TEXT,
                    FOREIGN KEY (CodiceGara) REFERENCES GARA(Codice),
                    FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF)
                );
                
                CREATE TABLE ISCRIVE (
                    IdIscrizione INT PRIMARY KEY,
                    CodiceGara VARCHAR(50),
                    Concorrente VARCHAR(16),
                    DataIscrizione DATE,
                    NumIscrizione INT,
                    Societa VARCHAR(100),
                    FOREIGN KEY (CodiceGara) REFERENCES GARA(Codice),
                    FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF),
                    FOREIGN KEY (Societa) REFERENCES SOCIETA(Nome)
                );
                
                CREATE TABLE CONTRATTO (
                    IdContratto INT PRIMARY KEY,
                    Sponsor VARCHAR(50),
                    Concorrente VARCHAR(16),
                    DataFirma DATE,
                    DataScadenza DATE,
                    Premio VARCHAR(100),
                    FOREIGN KEY (Sponsor) REFERENCES SPONSOR(ID),
                    FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF)
                );
            """;

            // Esegue tutte le CREATE (multiplo)
            for (String statement : sql.split(";")) {
                if (!statement.trim().isEmpty()) {
                    create.execute(statement);
                }
            }

            System.out.println("Database creato correttamente!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

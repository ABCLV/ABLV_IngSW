package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AddTable {

    public static void main(String[] args) {

        String[] dropStatements = {

            // Tabelle figlie prima
            "DROP TABLE IF EXISTS CONTRATTO;",
            "DROP TABLE IF EXISTS RECENSISCE;",
            "DROP TABLE IF EXISTS PARTECIPA;",
            "DROP TABLE IF EXISTS ISCRIVE;",
            "DROP TABLE IF EXISTS TURNO;",
            "DROP TABLE IF EXISTS GARA;",
            "DROP TABLE IF EXISTS SETTORE;",
            "DROP TABLE IF EXISTS SPONSOR;",
            "DROP TABLE IF EXISTS CONCORRENTE;",
            "DROP TABLE IF EXISTS AMMINISTRATORE;",
            "DROP TABLE IF EXISTS ARBITRO;",
            "DROP TABLE IF EXISTS CAMPIONATO;",
            "DROP TABLE IF EXISTS SOCIETA;",
            "DROP TABLE IF EXISTS CAMPOGARA;"
        };

        String[] createStatements = {

            """
            CREATE TABLE CAMPOGARA (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Paese TEXT NOT NULL,
                CorpoIdrico TEXT NOT NULL,
                Lunghezza INTEGER
            );
            """,

            """
            CREATE TABLE SOCIETA (
                Nome TEXT PRIMARY KEY,
                Email TEXT NOT NULL,
                Sede INTEGER NOT NULL,
                Indirizzo TEXT NOT NULL,
                Citta TEXT NOT NULL,
                Cap TEXT NOT NULL
            );
            """,

            """
            CREATE TABLE CAMPIONATO (
                Titolo TEXT PRIMARY KEY,
                Categoria TEXT NOT NULL
            );
            """,

            """
            CREATE TABLE ARBITRO (
                CF TEXT PRIMARY KEY,
                Nome TEXT NOT NULL,
                Cognome TEXT NOT NULL,
                Sezione TEXT
            );
            """,

            """
            CREATE TABLE AMMINISTRATORE (
                CF TEXT PRIMARY KEY,
                Nome TEXT NOT NULL,
                Cognome TEXT NOT NULL,
                Email TEXT NOT NULL
            );
            """,

            """
            CREATE TABLE CONCORRENTE (
                CF TEXT PRIMARY KEY,
                Nome TEXT NOT NULL,
                Cognome TEXT NOT NULL,
                Email TEXT NOT NULL,
                Nascita DATE,
                Societa TEXT NOT NULL,
                FOREIGN KEY (Societa) REFERENCES SOCIETA(Nome)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT
            );
            """,

            """
            CREATE TABLE SPONSOR (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Nome TEXT NOT NULL,
                Sito TEXT
            );
            """,

            """
            CREATE TABLE SETTORE (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Lunghezza INTEGER,
                Descrizione TEXT,
                CampoGara INTEGER NOT NULL,
                FOREIGN KEY (CampoGara) REFERENCES CAMPOGARA(ID)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE
            );
            """,

            """
            CREATE TABLE GARA (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                NumProva INTEGER,
                Tecnica TEXT NOT NULL,
                CriterioPunti TEXT NOT NULL,
                MinPersone INTEGER NOT NULL,
                MaxPersone INTEGER NOT NULL,
                StatoGara TEXT NOT NULL DEFAULT 'NON_INIZIATA',
                StatoConferma TEXT NOT NULL DEFAULT 'IN_ATTESA',
                TipoGara TEXT NOT NULL,
                Data DATE NOT NULL,

                Campionato TEXT,
                Arbitro TEXT,
                AmministratoreProposta TEXT,
                AmministratoreAccettazione TEXT,
                Societa TEXT,
                CampoGara INTEGER NOT NULL,

                FOREIGN KEY (Campionato) REFERENCES CAMPIONATO(Titolo)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT,
                FOREIGN KEY (Arbitro) REFERENCES ARBITRO(CF)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT,
                FOREIGN KEY (AmministratoreProposta) REFERENCES AMMINISTRATORE(CF)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT,
                FOREIGN KEY (AmministratoreAccettazione) REFERENCES AMMINISTRATORE(CF)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT,
                FOREIGN KEY (Societa) REFERENCES SOCIETA(Nome)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT,
                FOREIGN KEY (CampoGara) REFERENCES CAMPOGARA(ID)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE
            );
            """,

            """
            CREATE TABLE TURNO (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Numero INTEGER NOT NULL,
                Durata INTEGER,
                Settore INTEGER NOT NULL,
                Gara INTEGER NOT NULL,
                FOREIGN KEY (Settore) REFERENCES SETTORE(ID)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE,
                FOREIGN KEY (Gara) REFERENCES GARA(ID)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE
            );
            """,

            """
            CREATE TABLE ISCRIVE (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Gara INTEGER NOT NULL,
                Concorrente TEXT NOT NULL,
                DataIscrizione DATE NOT NULL,
                NumIscrizione INTEGER NOT NULL,
                FOREIGN KEY (Gara) REFERENCES GARA(ID)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE,
                FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT
            );
            """,

            """
            CREATE TABLE PARTECIPA (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Turno INTEGER NOT NULL,
                Concorrente TEXT NOT NULL,
                NumPunti INTEGER NOT NULL,
                Squalifica BOOLEAN NOT NULL DEFAULT 0,
                FOREIGN KEY (Turno) REFERENCES TURNO(ID)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE,
                FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT
            );
            """,

            """
            CREATE TABLE RECENSISCE (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Gara INTEGER NOT NULL,
                Concorrente TEXT NOT NULL,
                Titolo TEXT NOT NULL,
                Voto INTEGER NOT NULL,
                Commento TEXT,
                FOREIGN KEY (Gara) REFERENCES GARA(ID)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE,
                FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT
            );
            """,

            """
            CREATE TABLE CONTRATTO (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Sponsor INTEGER NOT NULL,
                Concorrente TEXT NOT NULL,
                DataFirma DATE NOT NULL,
                DataScadenza DATE NOT NULL,
                Premio TEXT,
                FOREIGN KEY (Sponsor) REFERENCES SPONSOR(ID)
                    ON UPDATE CASCADE
                    ON DELETE CASCADE,
                FOREIGN KEY (Concorrente) REFERENCES CONCORRENTE(CF)
                    ON UPDATE CASCADE
                    ON DELETE RESTRICT
            );
            """
        };

        try (Connection conn = SQLiteConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {

            // fondamentale in SQLite
            stmt.execute("PRAGMA foreign_keys = OFF;");

            for (String sql : dropStatements) {
                stmt.execute(sql);
            }

            for (String sql : createStatements) {
                stmt.execute(sql);
            }

            stmt.execute("PRAGMA foreign_keys = ON;");

            System.out.println("✅ DB ricreato da zero con schema aggiornato");

        } catch (SQLException e) {
            System.err.println("❌ Errore DB: " + e.getMessage());
        }
    }
}

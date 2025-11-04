package dbconSQLJOOQ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddTable {

    public static void main(String[] args) {
        // Percorso del database (uguale a quello creato prima)
        String url = "jdbc:sqlite:src/db/miodb2.db";

        // SQL per creare la tabella Studente qui ancora normale
        String sql = """
            CREATE TABLE IF NOT EXISTS Studente (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                annoNascita INTEGER
            );
            """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabella 'Studente' creata o già esistente in miodb2.db");

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

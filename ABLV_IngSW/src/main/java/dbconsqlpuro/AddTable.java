package dbconsqlpuro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddTable {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:src/db/miodb.db"; // database locale

        // SQL per creare la tabella Auto
        String sql = """
            CREATE TABLE IF NOT EXISTS Auto (
                targa TEXT PRIMARY KEY,
                stirng TEXT NOT NULL,
                anno INTEGER
            );
            """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabella 'Auto' creata o già esistente.");

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

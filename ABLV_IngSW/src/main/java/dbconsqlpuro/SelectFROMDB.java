package dbconsqlpuro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectFROMDB {
//selziona sollo le auto con anno dispari
    public static void main(String[] args) {
        // Percorso del database (assicurati che esista già)
        String url = "jdbc:sqlite:src/db/miodb.db";

        // Query per selezionare solo le auto con anno dispari
        String sql = "SELECT * FROM Auto WHERE anno % 2 = 1";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("🚗 Auto con anno dispari:\n");
            System.out.printf("%-10s | %-20s | %-4s%n", "Targa", "Stirng", "Anno");
            System.out.println("-----------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                String targa = rs.getString("targa");
                String stirng = rs.getString("stirng");
                int anno = rs.getInt("anno");

                System.out.printf("%-10s | %-20s | %-4d%n", targa, stirng, anno);
            }

            if (!found) {
                System.out.println("Nessuna auto con anno dispari trovata.");
            }

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

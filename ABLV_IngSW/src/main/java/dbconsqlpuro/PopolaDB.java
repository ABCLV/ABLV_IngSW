package dbconsqlpuro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class PopolaDB {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:src/db/miodb.db";

        String sqlInsert = "INSERT INTO Auto(targa, stirng, anno) VALUES(?, ?, ?)";
        String[] modelli = {
            "Fiat Panda", "Audi A3", "BMW X1", "Ford Focus", "Tesla Model 3",
            "Volkswagen Golf", "Toyota Yaris", "Renault Clio", "Peugeot 208", "Opel Corsa",
            "Hyundai i20", "Kia Sportage", "Mercedes A-Class", "Nissan Qashqai", "Jeep Renegade",
            "Volvo XC40", "Mazda CX-3", "Mini Cooper", "Seat Ibiza", "Citroen C3"
        };

        Random rnd = new Random();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

            for (int i = 0; i < 20; i++) {
                String targa = generaTarga(rnd);
                String stirng = modelli[rnd.nextInt(modelli.length)];
                int anno = 2000 + rnd.nextInt(26); // 2000–2025

                pstmt.setString(1, targa);
                pstmt.setString(2, stirng);
                pstmt.setInt(3, anno);

                pstmt.executeUpdate();
            }

            System.out.println("20 auto inserite con successo nel database!");

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private static String generaTarga(Random rnd) {
        // formato tipo AB123CD
        String lettere = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return "" +
            lettere.charAt(rnd.nextInt(26)) +
            lettere.charAt(rnd.nextInt(26)) +
            (100 + rnd.nextInt(900)) +
            lettere.charAt(rnd.nextInt(26)) +
            lettere.charAt(rnd.nextInt(26));
    }
}

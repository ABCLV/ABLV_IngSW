package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {

    public static void main(String[] args) {

        try (Connection conn = SQLiteConnectionManager.getConnection()) {
            if (conn != null) {
                System.out.println("Database creato o già esistente: gare.db");
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

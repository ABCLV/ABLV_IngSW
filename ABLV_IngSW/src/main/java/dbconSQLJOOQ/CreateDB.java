package dbconSQLJOOQ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:src/db/miodb2.db"; // nome del file del database

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Database creato o già esistente: miodb.db");
            }
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

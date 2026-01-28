package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class SQLiteConnectionManager {
	private static final String DB_URL = "jdbc:sqlite:../database/gare.db";

    // Costruttore privato: classe solo statica
    private SQLiteConnectionManager() {}

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        enableForeignKeys(conn);
        return conn;
    }

    private static void enableForeignKeys(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
    }
}

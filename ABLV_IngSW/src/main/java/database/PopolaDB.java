package database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import static dbconSQLJOOQ.generated.Tables.STUDENTE; // importa la tabella generata

public class PopolaDB {

    public static void main(String[] args) {
        String url = "jdbc:sqlite:database/miodb2.db";
        String[] nomi = {
            "Mario", "Luigi", "Giulia", "Sara", "Marco",
            "Anna", "Luca", "Paolo", "Chiara", "Simone",
            "Laura", "Giorgio", "Francesca", "Matteo",
            "Elisa", "Davide", "Irene", "Fabio", "Silvia", "Andrea"
        };
        Random random = new Random();

        try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
            	//NO SQL SO NO SQL INJECTION !!!
            for (String nome : nomi) {
                int anno = 1985 + random.nextInt(20);
                ctx.insertInto(STUDENTE)
                   .set(STUDENTE.NOME, nome)
                   .set(STUDENTE.ANNONASCITA, anno)
                   .execute();
            }

            System.out.println("✅ Inseriti 20 studenti nel database (via jOOQ)");

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

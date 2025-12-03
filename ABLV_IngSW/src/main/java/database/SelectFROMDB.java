package database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static dbconSQLJOOQ.generated.Tables.STUDENTE; // importa la tabella generata

public class SelectFROMDB {

    public static void main(String[] args) {

        String url = "jdbc:sqlite:database/miodb2.db";

        try (Connection conn = DriverManager.getConnection(url)) {

            DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

            System.out.println("🎓 Studenti nati in anni dispari:");
            System.out.println("--------------------------------");

            ctx.selectFrom(STUDENTE)
               .where(STUDENTE.ANNONASCITA.mod(2).ne(0)) // anno dispari
               .fetch()
               .forEach(record -> {
                   System.out.printf("ID: %-3d Nome: %-10s Anno nascita: %d%n",
                       record.get(STUDENTE.ID),
                       record.get(STUDENTE.NOME),
                       record.get(STUDENTE.ANNONASCITA));
               });

        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}

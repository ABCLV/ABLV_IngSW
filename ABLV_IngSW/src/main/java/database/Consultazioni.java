package database;

import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import applicazione.Concorrente;

public abstract class Consultazioni {
	

	public static List<Concorrente> getConcorrenti() {
		
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx.select(
	                CONCORRENTE.CF,
	                CONCORRENTE.NOME,
	                CONCORRENTE.COGNOME,
	                CONCORRENTE.EMAIL,
	                CONCORRENTE.NASCITA
	            )
	            .from(CONCORRENTE)
	            .fetchInto(Concorrente.class);


	    } catch (SQLException e) {
	        e.printStackTrace();
	        return List.of();
	    }
	}


}

package database.dao;

import static dbconSQLJOOQ.generated.Tables.CAMPIONATO;
import static dbconSQLJOOQ.generated.Tables.GARA;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import database.SQLiteConnectionManager;
import applicazione.entita.Campionato;

public final class CampionatoDAO {

	private CampionatoDAO() {
	}

	public static List<Campionato> getCampionati() {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(CAMPIONATO.TITOLO, CAMPIONATO.CATEGORIA).from(CAMPIONATO).fetchInto(Campionato.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public static boolean esisteGaraInCampionato(Campionato campionato, int numeroProva) {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Integer count = ctx.selectCount().from(GARA).where(GARA.CAMPIONATO.eq(campionato.getTitolo()))
					.and(GARA.NUMPROVA.eq(numeroProva)).fetchOne(0, int.class);

			return count != null && count > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false; // In caso di errore, assume che non esista per sicurezza
		}
	}

}

package db.repository;

import static dbconSQLJOOQ.generated.Tables.CAMPIONATO;
import static dbconSQLJOOQ.generated.Tables.GARA;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import db.SQLiteConnectionManager;

import model.Campionato;
import db.exception.*;

public class CampionatoDAO {

	public CampionatoDAO() {}

	public List<Campionato> getCampionati() throws CampionatoEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(CAMPIONATO.TITOLO, CAMPIONATO.CATEGORIA).from(CAMPIONATO).fetchInto(Campionato.class);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CampionatoEccezione("Errore nel recuperare la lista di campionati!", e);
		}
	}

	public boolean esisteGaraInCampionato(Campionato campionato, int numeroProva) throws CampionatoEccezione {
		boolean ret = false;
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Integer count = ctx.selectCount().from(GARA).where(GARA.CAMPIONATO.eq(campionato.getTitolo()))
					.and(GARA.NUMPROVA.eq(numeroProva)).fetchOne(0, int.class);

			ret = count != null && count > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CampionatoEccezione("Errore nel controllare l'esistenza della gara nel campionato!", e);
		}
		
		return ret;
	}

}

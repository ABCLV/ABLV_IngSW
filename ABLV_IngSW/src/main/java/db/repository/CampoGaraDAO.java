package db.repository;

import static dbconSQLJOOQ.generated.Tables.CAMPOGARA;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import model.CampoGara;
import db.exception.CampoGaraEccezione;

import db.SQLiteConnectionManager;

public class CampoGaraDAO {

	public CampoGaraDAO() {}

	public List<CampoGara> getCampoGara() throws CampoGaraEccezione {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			System.out.println(ctx.selectFrom(CAMPOGARA).fetchInto(CampoGara.class));

			return ctx.select(CAMPOGARA.ID.as("idCampoGara"), CAMPOGARA.PAESE, CAMPOGARA.CORPOIDRICO,
					CAMPOGARA.LUNGHEZZA, CAMPOGARA.DESCRIZIONE).from(CAMPOGARA).fetchInto(CampoGara.class);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CampoGaraEccezione("Errore nel recuperare il campo gara!", e);
		}
	}

	public CampoGara trovaCampoGara(String codice) throws CampoGaraEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.selectFrom(CAMPOGARA).where(CAMPOGARA.ID.eq(codice)).fetchOneInto(CampoGara.class);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CampoGaraEccezione("Errore nel cercare il campo gara!", e);
		}
	}

	public List<CampoGara> getCampiGara() throws CampoGaraEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(CAMPOGARA.ID.as("idCampoGara"), CAMPOGARA.PAESE, CAMPOGARA.CORPOIDRICO,
					CAMPOGARA.LUNGHEZZA, CAMPOGARA.DESCRIZIONE).from(CAMPOGARA).orderBy(CAMPOGARA.ID)
					.fetchInto(CampoGara.class);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CampoGaraEccezione("Errore nel recuperare la lista dei campi gara!", e);
		}
	}

}

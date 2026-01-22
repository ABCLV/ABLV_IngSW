package database.dao;

import static dbconSQLJOOQ.generated.Tables.SETTORE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import database.SQLiteConnectionManager;
import applicazione.entita.CampoGara;
import applicazione.entita.Settore;

public final class SettoreDAO {

	private SettoreDAO() {
	}

	public static List<Settore> esploraSettori(CampoGara c) {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.selectDistinct(SETTORE.ID, SETTORE.LUNGHEZZA, SETTORE.DESCRIZIONE).from(SETTORE)
					.where(SETTORE.CAMPOGARA.eq(c.getIdCampoGara())).fetchInto(Settore.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

}

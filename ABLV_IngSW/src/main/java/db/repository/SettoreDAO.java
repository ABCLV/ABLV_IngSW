package db.repository;

import static dbconSQLJOOQ.generated.Tables.SETTORE;

import db.SQLiteConnectionManager;
import db.exception.SettoreEccezione;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;

import model.CampoGara;
import model.Settore;

public class SettoreDAO {

	public SettoreDAO() {
	}

	public List<Settore> esploraSettori(CampoGara c) throws SettoreEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.selectDistinct(SETTORE.ID, SETTORE.LUNGHEZZA, SETTORE.DESCRIZIONE).from(SETTORE)
					.where(SETTORE.CAMPOGARA.eq(c.getIdCampoGara())).fetchInto(Settore.class);

		}  catch (DataAccessException e) {
			throw new SettoreEccezione("Errore nell'esplorare i settori!", e);
		} catch (SQLException e) {
			throw new SettoreEccezione("Errore nell'esplorare i settori!", e);
		}
	}

}
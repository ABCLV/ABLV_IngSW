package db.repository;

import static dbconSQLJOOQ.generated.Tables.TURNO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;

import db.SQLiteConnectionManager;
import db.exception.GaraEccezione;
import model.Settore;

public class TurnoDAO {
	public TurnoDAO() {
	};

	public boolean creaTurni(List<Settore> sett, List<Integer> durate, int codiceGara) throws GaraEccezione {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			// 🔹 2 — ciclo settori
			for (Settore s : sett) {

				int numeroTurno = 1;

				// 🔹 3 — ciclo durate
				for (Integer durata : durate) {


					ctx.insertInto(TURNO, TURNO.NUMERO, TURNO.DURATA, TURNO.SETTORE, TURNO.GARA)
							.values(numeroTurno, durata, s.getIdSettore(), codiceGara).execute();

					numeroTurno++;
				}
			}

			return true;

		} catch (IntegrityConstraintViolationException e) {
			throw new GaraEccezione("Errore nella creazione dei turni!", e);
		} catch (DataAccessException e) {
			throw new GaraEccezione("Errore nella creazione dei turni!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nella creazione dei turni!", e);
		}
	}
	
	public boolean eliminaTurniPerGara(int codiceGara) throws GaraEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        ctx.deleteFrom(TURNO)
	           .where(TURNO.GARA.eq(codiceGara))
	           .execute();

	        return true;

	    } catch (SQLException e) {
	        throw new GaraEccezione("Errore nell'eliminazione dei turni!", e);
	    }
	}


}
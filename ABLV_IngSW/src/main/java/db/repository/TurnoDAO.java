package db.repository;

import static dbconSQLJOOQ.generated.Tables.TURNO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import db.SQLiteConnectionManager;
import db.exception.GaraEccezione;
import model.Settore;


public class TurnoDAO {
	public TurnoDAO() {};
	
	public boolean creaTurni(List<Settore> sett, List<Integer> durate, String codiceGara) throws GaraEccezione {

	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        // 🔹 1 — leggo ultimo codice turno
	        String ultimoCodice = ctx.select(TURNO.CODICE)
	                .from(TURNO)
	                .orderBy(TURNO.CODICE.desc())
	                .limit(1)
	                .fetchOneInto(String.class);

	        int progressivo = 0;

	        if (ultimoCodice != null) {
	            progressivo = Integer.parseInt(ultimoCodice.substring(1));
	        }

	        // 🔹 2 — ciclo settori
	        for (Settore s : sett) {

	            int numeroTurno = 1;

	            // 🔹 3 — ciclo durate
	            for (Integer durata : durate) {

	                progressivo++;
	                String nuovoCodice = String.format("T%03d", progressivo);

	                ctx.insertInto(TURNO,
	                        TURNO.CODICE,
	                        TURNO.NUMERO,
	                        TURNO.DURATA,
	                        TURNO.SETTORE,
	                        TURNO.GARA)
	                   .values(
	                        nuovoCodice,
	                        numeroTurno,
	                        durata,
	                        s.getIdSettore(),
	                        codiceGara
	                   )
	                   .execute();

	                numeroTurno++;
	            }
	        }

	        return true;

	    } catch (SQLException e) {
	        throw new GaraEccezione("Errore nella creazione dei turni!", e);
	    }
	}
	
	public boolean eliminaTurniPerGara(String codiceGara) throws GaraEccezione {
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

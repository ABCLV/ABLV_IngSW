package database;

import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.CAMPOGARA;
import static dbconSQLJOOQ.generated.Tables.SETTORE;
import static dbconSQLJOOQ.generated.Tables.GARA;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import applicazione.CampoGara;
import applicazione.Concorrente;
import applicazione.Gara;
import applicazione.Settore;
import applicazione.StatoConferma;
import applicazione.StatoGara;
import applicazione.Tecnica;
import applicazione.TipologiaGara;

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
	
	public static List<CampoGara> getCampoGara() {
		
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        

	        return ctx.selectFrom(CAMPOGARA)
	                  .fetchInto(CampoGara.class);



	    } catch (SQLException e) {
	        e.printStackTrace();
	        return List.of();
	    }
	}
	
	public static List<Settore> esploraSettori(CampoGara c) {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx
	            .selectDistinct(SETTORE.ID,SETTORE.LUNGHEZZA, SETTORE.DESCRIZIONE)
	            .from(SETTORE)
	            .where(SETTORE.CAMPOGARA.eq(c.getId()))
	            .fetchInto(Settore.class);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return List.of();
	    }
	}
	
	public static List<Gara> esploraGare(CampoGara c) {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        //mappaggio manuale
	        return ctx.select(
	                GARA.CODICE,
	                GARA.NUMPROVA,
	                GARA.TECNICA,
	                GARA.CRITERIOPUNTI,
	                GARA.DATA,
	                GARA.MAXPERSONE,
	                GARA.MINPERSONE,
	                GARA.STATOGARA,
	                GARA.STATOCONFERMA,
	                GARA.TIPOGARA
	            )
	            .from(GARA)
	            .where(GARA.CAMPOGARA.eq(c.idCampoGara))
	            .fetch(record -> {
	                Gara g = new Gara();
	                g.setCodice(record.get(GARA.CODICE));
	                g.setNumProva(record.get(GARA.NUMPROVA));
	                g.setTecnica(Tecnica.valueOf(record.get(GARA.TECNICA).toUpperCase())); // enum
	                g.setCriterioPunti(record.get(GARA.CRITERIOPUNTI));
	                g.setData(record.get(GARA.DATA));
	                g.setMaxPersone(record.get(GARA.MAXPERSONE));
	                g.setMinPersone(record.get(GARA.MINPERSONE));
	                g.setStatoGara(StatoGara.valueOf(record.get(GARA.STATOGARA).toUpperCase()));
	                g.setStatoConferma(StatoConferma.valueOf(record.get(GARA.STATOCONFERMA).toUpperCase()));
	                g.setTipoGara(TipologiaGara.valueOf(record.get(GARA.TIPOGARA).toUpperCase()));
	                return g;
	            });



	    } catch (SQLException e) {
	        e.printStackTrace();
	        return List.of();
	    }
	}

	
	public static CampoGara trovaCampoGara(String codice) {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx
	            .selectFrom(CAMPOGARA)
	            .where(CAMPOGARA.ID.eq(codice))
	            .fetchOneInto(CampoGara.class);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}



}

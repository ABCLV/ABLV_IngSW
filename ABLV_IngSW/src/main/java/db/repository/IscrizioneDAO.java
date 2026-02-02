package db.repository;

import static dbconSQLJOOQ.generated.Tables.ISCRIVE;
import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import db.SQLiteConnectionManager;
import db.exception.ConcorrenteEccezione;
import db.exception.IscrizioneEccezioneDB;
import model.Concorrente;

public class IscrizioneDAO {

	public IscrizioneDAO() {}
	
	public Integer getUltimoNumeroIscrizione(String codiceGara) throws IscrizioneEccezioneDB {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx.select(DSL.max(ISCRIVE.NUMISCRIZIONE))
	            .from(ISCRIVE)
	            .where(ISCRIVE.CODICEGARA.eq(codiceGara))
	            .fetchOne(0, Integer.class);

	    } catch(SQLException e) {
	        throw new IscrizioneEccezioneDB("Errore nel recuperare l'ultimo numero iscrizione!", e);
	    }
	}
	
	public List<Concorrente> getConcorrenti(String codiceGara) {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx.select(
	                    CONCORRENTE.CF,
	                    CONCORRENTE.NOME,
	                    CONCORRENTE.COGNOME,
	                    CONCORRENTE.EMAIL,
	                    CONCORRENTE.NASCITA,
	                    CONCORRENTE.SOCIETA
	                )
	                .from(CONCORRENTE)
	                .join(ISCRIVE)
	                    .on(CONCORRENTE.CF.eq(ISCRIVE.CONCORRENTE))
	                .where(ISCRIVE.CODICEGARA.eq(codiceGara))
	                .fetchInto(Concorrente.class);

	    } catch (SQLException e) {
	        throw new ConcorrenteEccezione(
	                "Errore nel recupero dei concorrenti iscritti alla gara",
	                e
	        );
	    }
	}
	
	


	public void inserisciIscrizione(Integer codiceIscrizione,String cf, String codiceGara, LocalDate dataIscrizione, Integer numIscrizione) throws IscrizioneEccezioneDB {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        ctx.insertInto(ISCRIVE)
	        		.set(ISCRIVE.IDISCRIZIONE, codiceIscrizione)
	                .set(ISCRIVE.CODICEGARA, codiceGara) 
	                .set(ISCRIVE.CONCORRENTE, cf)
	                .set(ISCRIVE.DATAISCRIZIONE, dataIscrizione)
	                .set(ISCRIVE.NUMISCRIZIONE, numIscrizione)
	            .execute();

	    } catch(SQLException e) {
	        throw new IscrizioneEccezioneDB("Errore nell'inserire l'iscrizione!", e);
	    }
	}
	
	public int getUltimoCodiceIscrizione() throws IscrizioneEccezioneDB {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			int num =  ctx.select(ISCRIVE.IDISCRIZIONE).from(ISCRIVE)
					.orderBy(ISCRIVE.IDISCRIZIONE.desc())
					.limit(1).fetchOneInto(Integer.class);
			
			return num;

		} catch (SQLException e) {
			throw new IscrizioneEccezioneDB("Errore nel recuperare l'ultimo codice iscrizione!", e);
		}
	}
	
	public boolean esisteIscrizione(String cf, String codiceGara) throws IscrizioneEccezioneDB {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
	        
	        Integer count = ctx.selectCount()
	            .from(ISCRIVE)
	            .where(ISCRIVE.CONCORRENTE.eq(cf)
	                .and(ISCRIVE.CODICEGARA.eq(codiceGara)))
	            .fetchOne(0, Integer.class);
	        
	        return count != null && count > 0;
	        
	    } catch(SQLException e) {
	        throw new IscrizioneEccezioneDB("Errore nel verificare l'esistenza dell'iscrizione!", e);
	    }
	}
	
	public List<String> getCodiciGareIscrittoPerConcorrente(String cf) throws IscrizioneEccezioneDB {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
	        
	        return ctx.select(ISCRIVE.CODICEGARA)
	            .from(ISCRIVE)
	            .where(ISCRIVE.CONCORRENTE.eq(cf))
	            .fetch(ISCRIVE.CODICEGARA);
	            
	    } catch(SQLException e) {
	        throw new IscrizioneEccezioneDB("Errore nel recuperare le gare iscritte!", e);
	    }
	}

}

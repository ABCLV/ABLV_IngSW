package db.repository;

import static dbconSQLJOOQ.generated.Tables.CAMPOGARA;
import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.GARA;
import static dbconSQLJOOQ.generated.Tables.ISCRIVE;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;
import static dbconSQLJOOQ.generated.Tables.TURNO;
import static dbconSQLJOOQ.generated.Tables.PARTECIPA;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;

import model.CampoGara;
import model.Concorrente;
import model.Gara;
import model.enums.Tecnica;

import db.SQLiteConnectionManager;
import db.exception.ConcorrenteEccezione;
import db.exception.GaraEccezione;

public class ConcorrenteDAO {

	public ConcorrenteDAO() {}

	public List<Concorrente> getConcorrenti() throws ConcorrenteEccezione {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx
					.select(CONCORRENTE.CF, CONCORRENTE.NOME, CONCORRENTE.COGNOME, CONCORRENTE.EMAIL,
							CONCORRENTE.NASCITA, SOCIETA.NOME.as("societa"))
					.from(CONCORRENTE).join(SOCIETA).on(CONCORRENTE.SOCIETA.eq(SOCIETA.NOME))
					.fetchInto(Concorrente.class);

		}  catch (DataAccessException e) {
			throw new ConcorrenteEccezione("Errore nel recuperare i concorrente!", e);
		} catch (SQLException e) {
			throw new ConcorrenteEccezione("Errore nel recuperare i concorrente!", e);
		}
	}

	public long countPescatori() throws ConcorrenteEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			return ctx.fetchCount(ctx.selectFrom(CONCORRENTE));
		}  catch (DataAccessException e) {
			throw new ConcorrenteEccezione("Errore nel recuperare il numero di pescatori!", e);
		} catch (SQLException e) {
			throw new ConcorrenteEccezione("Errore nel recuperare il numero di pescatori!", e);
		}
	}

	public void registraConcorrente(String cf, String nome, String cognome, String email, LocalDate nascita,
			String societaNome, String pwdChiara) throws ConcorrenteEccezione {

		String hash = DigestUtils.sha256Hex(pwdChiara);

		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			ctx.insertInto(CONCORRENTE, CONCORRENTE.CF, CONCORRENTE.NOME, CONCORRENTE.COGNOME, CONCORRENTE.EMAIL,
					CONCORRENTE.NASCITA, CONCORRENTE.SOCIETA, CONCORRENTE.PASSWORD_HASH)
					.values(cf, nome, cognome, email, nascita, societaNome, hash).execute();
		} catch (IntegrityConstraintViolationException e) {
			throw new ConcorrenteEccezione("Errore nel registrare il concorrente!", e);
		} catch (DataAccessException e) {
			throw new ConcorrenteEccezione("Errore nel registrare il concorrente!", e);
		} catch (SQLException e) {
			throw new ConcorrenteEccezione("Errore nel registrare il concorrente!", e);
		}
	}

	public Concorrente getConcorrente(String cf) throws ConcorrenteEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Record6<String, String, String, String, LocalDate, String> r = ctx
					.select(CONCORRENTE.CF, CONCORRENTE.COGNOME, CONCORRENTE.NOME, CONCORRENTE.EMAIL,
							CONCORRENTE.NASCITA, CONCORRENTE.SOCIETA)
					.from(CONCORRENTE).where(CONCORRENTE.CF.eq(cf)).fetchOne();

			if (r == null)
				return null;

			return new Concorrente(r.get(CONCORRENTE.CF), r.get(CONCORRENTE.COGNOME), r.get(CONCORRENTE.NOME),
					r.get(CONCORRENTE.EMAIL), r.get(CONCORRENTE.NASCITA), r.get(CONCORRENTE.SOCIETA));
		} catch (DataAccessException e) {
			throw new ConcorrenteEccezione("Errore nel recuperare il concorrente!", e);
		} catch (SQLException e) {
			throw new ConcorrenteEccezione("Errore nel recuperare il concorrente!", e);
		}
	}
	public void eliminaConcorrente(String cf) throws ConcorrenteEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
	        ctx.deleteFrom(CONCORRENTE)
	           .where(CONCORRENTE.CF.eq(cf))
	           .execute();
	    } catch (DataAccessException e) {
	        throw new ConcorrenteEccezione("Errore nell'eliminare il concorrente", e);
	    } catch (SQLException e) {
	        throw new ConcorrenteEccezione("Errore di connessione", e);
	    }
	}

	public List<Gara> getGareConcorrente(String cf) throws ConcorrenteEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Result<Record6<Integer, Integer, String, LocalDate, Integer, String>> rs = ctx
					.select(GARA.ID, GARA.NUMPROVA, GARA.TECNICA, GARA.DATA, CAMPOGARA.ID, CAMPOGARA.DESCRIZIONE)
					.from(GARA).join(ISCRIVE).on(GARA.ID.eq(ISCRIVE.GARA)).join(CAMPOGARA)
					.on(GARA.CAMPOGARA.eq(CAMPOGARA.ID)).where(ISCRIVE.CONCORRENTE.eq(cf)).fetch();

			List<Gara> out = new ArrayList<>();
			for (Record6<Integer, Integer, String, LocalDate, Integer, String> r : rs) {
				CampoGara campo = new CampoGara();
				campo.setIdCampoGara(r.value5());
				campo.setDescrizione(r.value6());

				Gara g = new Gara();
				g.setCodice(r.value1());
				g.setNumProva(r.value2());
				g.setTecnica(Tecnica.valueOf(r.value3().toUpperCase()));
				g.setData(r.value4());
				g.setCampoGara(campo);

				out.add(g);
			}
			return out;
		} catch (DataAccessException e) {
			throw new ConcorrenteEccezione("Errore nel recuperare le gare a cui il concorrente è iscritto!", e);
		} catch (SQLException e) {
			throw new ConcorrenteEccezione("Errore nel recuperare le gare a cui il concorrente è iscritto!", e);
		}
	}
	
	
	public List<Concorrente> getConcorrentiPerSettore(int codiceGara, int codiceSettore)
	        throws GaraEccezione {

	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx
	        	    .selectDistinct(
	        	        CONCORRENTE.CF,
	        	        CONCORRENTE.NOME,
	        	        CONCORRENTE.COGNOME,
	        	        CONCORRENTE.EMAIL,
	        	        CONCORRENTE.NASCITA,
	        	        CONCORRENTE.SOCIETA
	        	    )
	        	    .from(CONCORRENTE)
	        	    .join(PARTECIPA)
	        	        .on(CONCORRENTE.CF.eq(PARTECIPA.CONCORRENTE))
	        	    .join(TURNO)
	        	        .on(PARTECIPA.TURNO.eq(TURNO.ID))
	        	    .where(TURNO.GARA.eq(codiceGara))
	        	    .and(TURNO.SETTORE.eq(codiceSettore))
	        	    .fetchInto(Concorrente.class);


	    } catch (DataAccessException | SQLException e) {
	        throw new GaraEccezione(
	            "Errore nel recupero dei concorrenti del settore "
	            + codiceSettore + " per la gara " + codiceGara,
	            e
	        );
	    }
	}


}
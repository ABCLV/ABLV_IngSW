package db.repository;

import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;

import db.SQLiteConnectionManager;
import db.exception.GaraEccezione;
import db.exception.ModificaConcorrenteEccezione;

public class ModificaDAO {

	public ModificaDAO() {};
	
	public boolean salvaConcorrente(String cfVecchio, /*String cfNuovo, */String nome, String cognome, String email, LocalDate nascita) throws ModificaConcorrenteEccezione{
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			int rowsAffected = ctx.update(CONCORRENTE)
					//.set(CONCORRENTE.CF, cfNuovo)
					.set(CONCORRENTE.NOME, nome)
					.set(CONCORRENTE.COGNOME, cognome)
					.set(CONCORRENTE.EMAIL, email)
					.set(CONCORRENTE.NASCITA, nascita)
					.where(CONCORRENTE.CF.eq(cfVecchio))
					.execute();

			return rowsAffected > 0;
		} catch (IntegrityConstraintViolationException e) {
			throw new GaraEccezione("Errore nell'accettare la gara!", e);
		} catch (DataAccessException e) {
			throw new GaraEccezione("Errore nell'accettare la gara!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nell'accettare la gara!", e);
		}
	}
	
	public boolean salvaSocieta(String nomeVecchio,/* String nomeNuovo,*/ String email, String cap, String citta, String indirizzo) throws ModificaConcorrenteEccezione{
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			int rowsAffected = ctx.update(SOCIETA)
					//.set(SOCIETA.NOME, nomeNuovo)
					.set(SOCIETA.EMAIL, email)
					.set(SOCIETA.CAP, cap)
					.set(SOCIETA.CITTA, citta)
					.set(SOCIETA.INDIRIZZO, indirizzo)
					.where(SOCIETA.NOME.eq(nomeVecchio))
					.execute();

			return rowsAffected > 0;
		} catch (IntegrityConstraintViolationException e) {
			throw new GaraEccezione("Errore nell'accettare la gara!", e);
		} catch (DataAccessException e) {
			throw new GaraEccezione("Errore nell'accettare la gara!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nell'accettare la gara!", e);
		}
	}
	
}

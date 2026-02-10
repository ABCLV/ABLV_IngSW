package db.repository;

import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;
import static dbconSQLJOOQ.generated.Tables.ARBITRO;
import static dbconSQLJOOQ.generated.Tables.AMMINISTRATORE;



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
import db.exception.ModificaAmministratoreEccezione;
import db.exception.ModificaArbitroEccezione;
import db.exception.ModificaConcorrenteEccezione;
import db.exception.ModificaSocietaEccezione;

public class ModificaDAO {

	public ModificaDAO() {};
	
	public void salvaConcorrente(String cf, String nome, String cognome, String email, LocalDate nascita) throws ModificaConcorrenteEccezione{
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			boolean rowsAffected = ctx.update(CONCORRENTE)
					.set(CONCORRENTE.NOME, nome)
					.set(CONCORRENTE.COGNOME, cognome)
					.set(CONCORRENTE.EMAIL, email)
					.set(CONCORRENTE.NASCITA, nascita)
					.where(CONCORRENTE.CF.eq(cf))
					.execute() > 0;

			if(!rowsAffected) {
				throw new RuntimeException();
			}
					
		} catch (IntegrityConstraintViolationException e) {
			throw new ModificaConcorrenteEccezione("Errore nell'aggiornare i dati del concorrente!", e);
		} catch (DataAccessException e) {
			throw new ModificaConcorrenteEccezione("Errore nell'aggiornare i dati del concorrente!", e);
		} catch (SQLException e) {
			throw new ModificaConcorrenteEccezione("Errore nell'aggiornare i dati del concorrente!", e);
		} catch(RuntimeException e) {
			throw new ModificaConcorrenteEccezione("Errore nell'aggiornare i dati del concorrente!", e);
		}
	}
	
	public void salvaSocieta(String nome, String email, String cap, String citta, String indirizzo) throws ModificaSocietaEccezione{
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			boolean rowsAffected = ctx.update(SOCIETA)
					.set(SOCIETA.EMAIL, email)
					.set(SOCIETA.CAP, cap)
					.set(SOCIETA.CITTA, citta)
					.set(SOCIETA.INDIRIZZO, indirizzo)
					.where(SOCIETA.NOME.eq(nome))
					.execute() > 0;

			if(!rowsAffected) {
				throw new RuntimeException();
			}
			
		} catch (IntegrityConstraintViolationException e) {
			throw new ModificaSocietaEccezione("Errore nell'aggiornare i dati della societa'!", e);
		} catch (DataAccessException e) {
			throw new ModificaSocietaEccezione("Errore nell'aggiornare i dati della societa'!", e);
		} catch (SQLException e) {
			throw new ModificaSocietaEccezione("Errore nell'aggiornare i dati della societa'!", e);
		} catch(RuntimeException e) {
			throw new ModificaSocietaEccezione("Errore nell'aggiornare i dati della societa'!", e);
		}
	}
	
	public void salvaArbitro(String cf, String nome, String cognome, String sezione) throws ModificaArbitroEccezione{
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			boolean rowsAffected = ctx.update(ARBITRO)
					.set(ARBITRO.NOME, nome)
					.set(ARBITRO.COGNOME, cognome)
					.set(ARBITRO.SEZIONE, sezione)
					.where(ARBITRO.CF.eq(cf))
					.execute() > 0;

			if(!rowsAffected) {
				throw new RuntimeException();
			}
			
		} catch (IntegrityConstraintViolationException e) {
			throw new ModificaArbitroEccezione("Errore nell'aggiornare i dati dell'arbitro!", e);
		} catch (DataAccessException e) {
			throw new ModificaArbitroEccezione("Errore nell'aggiornare i dati dell'arbitro!", e);
		} catch (SQLException e) {
			throw new ModificaArbitroEccezione("Errore nell'aggiornare i dati dell'arbitro!", e);
		} catch(RuntimeException e) {
			throw new ModificaArbitroEccezione("Errore nell'aggiornare i dati dell'arbitro!", e);
		}
	}
	
	public void salvaAmministratore(String cf, String nome, String cognome) throws ModificaAmministratoreEccezione{
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			boolean rowsAffected = ctx.update(AMMINISTRATORE)
					.set(AMMINISTRATORE.NOME, nome)
					.set(AMMINISTRATORE.COGNOME, cognome)
					.where(AMMINISTRATORE.CF.eq(cf))
					.execute() > 0;

			if(!rowsAffected) {
				throw new RuntimeException();
			}
			
		} catch (IntegrityConstraintViolationException e) {
			throw new ModificaAmministratoreEccezione("Errore nell'aggiornare i dati dell'amministratore!", e);
		} catch (DataAccessException e) {
			throw new ModificaAmministratoreEccezione("Errore nell'aggiornare i dati dell'amministratore!", e);
		} catch (SQLException e) {
			throw new ModificaAmministratoreEccezione("Errore nell'aggiornare i dati dell'amministratore!", e);
		} catch(RuntimeException e) {
			throw new ModificaAmministratoreEccezione("Errore nell'aggiornare i dati dell'amministratore!", e);
		}
	}
	
}
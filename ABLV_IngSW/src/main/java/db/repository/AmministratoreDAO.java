package db.repository;

import static dbconSQLJOOQ.generated.Tables.AMMINISTRATORE;
import static dbconSQLJOOQ.generated.Tables.CAMPIONATO;
import static dbconSQLJOOQ.generated.Tables.GARA;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.jooq.Record8;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;

import dbconSQLJOOQ.generated.tables.records.AmministratoreRecord;
import model.Amministratore;
import model.Campionato;
import model.CampoGara;
import model.Gara;
import model.enums.StatoConferma;
import model.enums.Tecnica;

import db.SQLiteConnectionManager;
import db.exception.AmministratoreEccezione;

public class AmministratoreDAO {

	public AmministratoreDAO() {
	}

	public Amministratore getAmministratoreByCF(String cf) throws AmministratoreEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			AmministratoreRecord record = ctx.selectFrom(AMMINISTRATORE).where(AMMINISTRATORE.CF.eq(cf)).fetchOne(); // ←
																														// restituisce
																														// AmministratoreRecord,
																														// non
																														// Record

			if (record != null) {
				return new Amministratore(record.getCf(), record.getNome(), record.getCognome());
			} else {
				throw new AmministratoreEccezione("Amministratore con CF=" + cf + " non trovato");
			}

		} catch (DataAccessException e) {
			throw new AmministratoreEccezione("Errore nel recuperare l'amministratore!", e);
		} catch (SQLException e) {
			throw new AmministratoreEccezione("Errore nel recuperare l'amministratore!", e);
		}
	}

	public void registraAmministratore(String cf, String nome, String cognome, String email, String pwdChiara)
			throws AmministratoreEccezione {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.insertInto(AMMINISTRATORE, AMMINISTRATORE.CF, AMMINISTRATORE.NOME, AMMINISTRATORE.COGNOME,
					AMMINISTRATORE.EMAIL, AMMINISTRATORE.PASSWORD_HASH).values(cf, nome, cognome, email, hash)
					.execute();
		} catch (IntegrityConstraintViolationException e) {
			// vincolo di unicità violato
			throw new AmministratoreEccezione("Amministratore già esistente con CF = " + cf, e);

		} catch (DataAccessException e) {
			// altri errori jOOQ
			throw new AmministratoreEccezione("Errore di accesso al database", e);

		} catch (SQLException e) {
			// problemi di connessione
			throw new AmministratoreEccezione("Errore di connessione al database", e);
		}
	}

	public Amministratore getAmministratore(String cf) throws AmministratoreEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Amministratore amm = ctx
					.select(AMMINISTRATORE.CF.as("cfAmministratore"), AMMINISTRATORE.NOME, AMMINISTRATORE.COGNOME)
					.from(AMMINISTRATORE).where(AMMINISTRATORE.CF.eq(cf)).fetchOneInto(Amministratore.class);

			if (amm == null) {
				throw new AmministratoreEccezione("Amministratore con CF=" + cf + " non trovato");
			}

			return amm;

		} catch (DataAccessException | SQLException e) {
			throw new AmministratoreEccezione("Errore nel recuperare l'amministratore", e);
		}
	}

	public List<Gara> getGareProposteDaAmministratore(String amm) throws AmministratoreEccezione {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Result<Record8<String, Integer, String, LocalDate, String, String, String, String>> rs = ctx
					.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.DATA, GARA.CAMPOGARA, CAMPIONATO.TITOLO,
							CAMPIONATO.CATEGORIA, GARA.STATOCONFERMA)
					.from(GARA).leftJoin(CAMPIONATO).on(GARA.CAMPIONATO.eq(CAMPIONATO.TITOLO))
					.where(GARA.AMMINISTRATOREPROPOSTA.eq(amm)).fetch();

			List<Gara> out = new ArrayList<>();

			for (Record8<String, Integer, String, LocalDate, String, String, String, String> r : rs) {

				Gara g = new Gara();
				g.setCodice(r.value1());
				g.setNumProva(r.value2());
				g.setTecnica(Tecnica.valueOf(r.value3().toUpperCase()));
				g.setData(r.value4());
				g.setStatoConferma(StatoConferma.valueOf(r.value8().toUpperCase()));

				// CampoGara con setter
				CampoGara campo = new CampoGara();
				campo.setIdCampoGara(r.value5());
				g.setCampoGara(campo);

				// Campionato (se non è null)
				Campionato campionato = new Campionato();
				if (r.value6() != null) {
					campionato.setTitolo(r.value6());
					campionato.setCategoria(r.value7());
					g.setCampionato(campionato);
				}

				out.add(g);
			}

			return out;
		} catch (DataAccessException e) {
			throw new AmministratoreEccezione("Errore nel recuperare la liste delle gare proposte dall'amministratore!",
					e);
		} catch (SQLException e) {
			throw new AmministratoreEccezione("Errore nel recuperare la liste delle gare proposte dall'amministratore!",
					e);
		}
	}

}
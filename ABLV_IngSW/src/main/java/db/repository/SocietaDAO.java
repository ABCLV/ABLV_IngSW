package db.repository;

import static dbconSQLJOOQ.generated.Tables.CAMPIONATO;
import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.GARA;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.Record8;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;

import model.Campionato;
import model.CampoGara;
import model.Concorrente;
import model.Gara;
import model.Societa;
import model.enums.StatoConferma;
import model.enums.Tecnica;

import db.SQLiteConnectionManager;
import db.exception.SocietaEccezione;

public class SocietaDAO {

	public SocietaDAO() {}

	public void registraSocieta(String nome, String indirizzo, String citta, String cap, String email,
			String pwdChiara) throws SocietaEccezione {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.insertInto(SOCIETA, SOCIETA.NOME, SOCIETA.INDIRIZZO, SOCIETA.CITTA, SOCIETA.CAP, SOCIETA.EMAIL,
					SOCIETA.PASSWORD_HASH).values(nome, indirizzo, citta, cap, email, hash).execute();
		} catch (IntegrityConstraintViolationException e) {
			throw new SocietaEccezione("Società già esistente con nome = " + nome, e);
		} catch (DataAccessException e) {
			throw new SocietaEccezione("Errore nel registrare la società!", e);
		} catch (SQLException e) {
			throw new SocietaEccezione("Errore nel registrare la società!", e);
		}
	}
	
	public void eliminaSocieta(String nome) throws SocietaEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
	        ctx.deleteFrom(SOCIETA)
	           .where(SOCIETA.NOME.eq(nome))
	           .execute();
	    } catch (DataAccessException e) {
	        throw new SocietaEccezione("Errore nell'eliminare la società", e);
	    } catch (SQLException e) {
	        throw new SocietaEccezione("Errore di connessione", e);
	    }
	}

	public Societa getSocieta(String nome) throws SocietaEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			Record5<String, String, String, String, String> r = ctx
					.select(SOCIETA.NOME, SOCIETA.INDIRIZZO, SOCIETA.CITTA, SOCIETA.CAP, SOCIETA.EMAIL).from(SOCIETA)
					.where(SOCIETA.NOME.eq(nome)).fetchOne();
			if (r == null)
				return null;
			return new Societa(
					r.get(SOCIETA.NOME),
					r.get(SOCIETA.EMAIL),
					r.get(SOCIETA.CAP),
					r.get(SOCIETA.CITTA),
					r.get(SOCIETA.INDIRIZZO)
					);
		}  catch (DataAccessException e) {
			throw new SocietaEccezione("Errore nel recuperare la società!", e);
		} catch (SQLException e) {
			throw new SocietaEccezione("Errore nel recuperare la società!", e);
		}
	}

	public boolean esisteSocieta(String nome) throws SocietaEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			return ctx.fetchExists(ctx.selectFrom(SOCIETA).where(SOCIETA.NOME.eq(nome)));
		} catch (DataAccessException e) {
			throw new SocietaEccezione("Errore nel cercare la società!", e);
		} catch (SQLException e) {
			throw new SocietaEccezione("Errore nel cercare la società!", e);
		}
	}

	public List<Gara> getGareProposteDaSocieta(String soc) throws SocietaEccezione {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Result<Record8<String, Integer, String, LocalDate, String, String, String, String>> rs = ctx
					.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.DATA, GARA.CAMPOGARA, CAMPIONATO.TITOLO,
							CAMPIONATO.CATEGORIA, GARA.STATOCONFERMA)
					.from(GARA).leftJoin(CAMPIONATO).on(GARA.CAMPIONATO.eq(CAMPIONATO.TITOLO))
					.where(GARA.SOCIETA.eq(soc)).fetch();

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
		}catch (DataAccessException e) {
			throw new SocietaEccezione("Errore nel recuperare le gare proposte dalla società!", e);
		} catch (SQLException e) {
			throw new SocietaEccezione("Errore nel recuperare le gare proposte dalla società!", e);
		}
	}

	public List<Concorrente> getConcorrentiPerSocieta(String nomeSocieta) throws SocietaEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			var rs = ctx
					.select(CONCORRENTE.CF, CONCORRENTE.COGNOME, CONCORRENTE.NOME, CONCORRENTE.EMAIL,
							CONCORRENTE.NASCITA, CONCORRENTE.SOCIETA)
					.from(CONCORRENTE).where(CONCORRENTE.SOCIETA.eq(nomeSocieta)).fetch();

			List<Concorrente> out = new ArrayList<>();
			for (var r : rs) {
				out.add(new Concorrente(r.get(CONCORRENTE.CF), r.get(CONCORRENTE.COGNOME), r.get(CONCORRENTE.NOME),
						r.get(CONCORRENTE.EMAIL), r.get(CONCORRENTE.NASCITA), r.get(CONCORRENTE.SOCIETA)));
			}
			return out;
		} catch (DataAccessException e) {
			throw new SocietaEccezione("Errore nel recuperare i concorrenti iscritti alla società!", e);
		} catch (SQLException e) {
			throw new SocietaEccezione("Errore nel recuperare i concorrenti iscritti alla società!", e);
		}
	}

}
package database.dao;

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
import org.jooq.impl.DSL;
import database.SQLiteConnectionManager;
import dbconSQLJOOQ.generated.tables.records.AmministratoreRecord;
import applicazione.entita.Amministratore;
import applicazione.entita.Campionato;
import applicazione.entita.CampoGara;
import applicazione.entita.Gara;
import applicazione.enumerazioni.StatoConferma;
import applicazione.enumerazioni.Tecnica;

public final class AmministratoreDAO {

	private AmministratoreDAO() {
	}

	public static Amministratore getAmministratoreByCF(String cf) {
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
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void registraAmministratore(String cf, String nome, String cognome, String email, String pwdChiara)
			throws SQLException {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.insertInto(AMMINISTRATORE, AMMINISTRATORE.CF, AMMINISTRATORE.NOME, AMMINISTRATORE.COGNOME,
					AMMINISTRATORE.EMAIL, AMMINISTRATORE.PASSWORD_HASH).values(cf, nome, cognome, email, hash)
					.execute();
		}
	}

	public static Amministratore getAmministratore(String cf) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			Amministratore amm = ctx
					.select(AMMINISTRATORE.CF.as("cfAmministratore"), AMMINISTRATORE.NOME, AMMINISTRATORE.COGNOME)
					.from(AMMINISTRATORE).where(AMMINISTRATORE.CF.eq(cf)).fetchOneInto(Amministratore.class);
			if (amm == null)
				throw new IllegalArgumentException("Amministratore non trovato");
			return amm;
		}
	}

	public static List<Gara> getGareProposteDaAmministratore(String amm) throws SQLException {

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
		}
	}

}

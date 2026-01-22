package database.dao;

import static dbconSQLJOOQ.generated.Tables.CAMPOGARA;
import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.GARA;
import static dbconSQLJOOQ.generated.Tables.ISCRIVE;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;

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
import org.jooq.impl.DSL;
import database.SQLiteConnectionManager;
import applicazione.entita.CampoGara;
import applicazione.entita.Concorrente;
import applicazione.entita.Gara;
import applicazione.enumerazioni.Tecnica;

public final class ConcorrenteDAO {

	private ConcorrenteDAO() {
	}

	public static List<Concorrente> getConcorrenti() {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx
					.select(CONCORRENTE.CF, CONCORRENTE.NOME, CONCORRENTE.COGNOME, CONCORRENTE.EMAIL,
							CONCORRENTE.NASCITA, SOCIETA.NOME.as("societa"))
					.from(CONCORRENTE).join(SOCIETA).on(CONCORRENTE.SOCIETA.eq(SOCIETA.NOME))
					.fetchInto(Concorrente.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public static long countPescatori() {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			return ctx.fetchCount(ctx.selectFrom(CONCORRENTE));
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static void registraConcorrente(String cf, String nome, String cognome, String email, LocalDate nascita,
			String societaNome, String pwdChiara) throws SQLException {

		String hash = DigestUtils.sha256Hex(pwdChiara);

		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			ctx.insertInto(CONCORRENTE, CONCORRENTE.CF, CONCORRENTE.NOME, CONCORRENTE.COGNOME, CONCORRENTE.EMAIL,
					CONCORRENTE.NASCITA, CONCORRENTE.SOCIETA, CONCORRENTE.PASSWORD_HASH)
					.values(cf, nome, cognome, email, nascita, societaNome, hash).execute();
		}
	}

	public static Concorrente getConcorrente(String cf) throws SQLException {
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
		}
	}

	public static List<Gara> getGareConcorrente(String cf) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Result<Record6<String, Integer, String, LocalDate, String, String>> rs = ctx
					.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.DATA, CAMPOGARA.ID, CAMPOGARA.DESCRIZIONE)
					.from(GARA).join(ISCRIVE).on(GARA.CODICE.eq(ISCRIVE.CODICEGARA)).join(CAMPOGARA)
					.on(GARA.CAMPOGARA.eq(CAMPOGARA.ID)).where(ISCRIVE.CONCORRENTE.eq(cf)).fetch();

			List<Gara> out = new ArrayList<>();
			for (Record6<String, Integer, String, LocalDate, String, String> r : rs) {
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
		}
	}

}

package database;

import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.CAMPOGARA;
import static dbconSQLJOOQ.generated.Tables.SETTORE;
import static dbconSQLJOOQ.generated.Tables.GARA;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;
import static dbconSQLJOOQ.generated.Tables.ARBITRO;
import static dbconSQLJOOQ.generated.Tables.AMMINISTRATORE;
import static dbconSQLJOOQ.generated.Tables.ISCRIVE;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Result;
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
import client.ConcorrenteRow;
import client.GaraRow;

import org.apache.commons.codec.digest.DigestUtils;

import applicazione.*;


public abstract class Consultazioni {

	public static List<Concorrente> getConcorrenti() {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(CONCORRENTE.CF, CONCORRENTE.NOME, CONCORRENTE.COGNOME, CONCORRENTE.EMAIL,
					CONCORRENTE.NASCITA).from(CONCORRENTE).fetchInto(Concorrente.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public static List<CampoGara> getCampoGara() {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.selectFrom(CAMPOGARA).fetchInto(CampoGara.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public static List<Settore> esploraSettori(CampoGara c) {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.selectDistinct(SETTORE.ID, SETTORE.LUNGHEZZA, SETTORE.DESCRIZIONE).from(SETTORE)
					.where(SETTORE.CAMPOGARA.eq(c.getId())).fetchInto(Settore.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public static List<Gara> esploraGare(CampoGara c) {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			// mappaggio manuale
			return ctx
					.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.CRITERIOPUNTI, GARA.DATA, GARA.MAXPERSONE,
							GARA.MINPERSONE, GARA.STATOGARA, GARA.STATOCONFERMA, GARA.TIPOGARA)
					.from(GARA).where(GARA.CAMPOGARA.eq(c.idCampoGara)).fetch(record -> {
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

			return ctx.selectFrom(CAMPOGARA).where(CAMPOGARA.ID.eq(codice)).fetchOneInto(CampoGara.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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

	public static void registraArbitro(String cf, String nome, String cognome, String sezione, String pwdChiara)
			throws SQLException {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.insertInto(ARBITRO, ARBITRO.CF, ARBITRO.NOME, ARBITRO.COGNOME, ARBITRO.SEZIONE, ARBITRO.PASSWORD_HASH)
					.values(cf, nome, cognome, sezione, hash).execute();
		}
	}

	public static void registraSocieta(String nome, String indirizzo, String citta, String cap, String email,
			String pwdChiara) throws SQLException {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.insertInto(SOCIETA, SOCIETA.NOME, SOCIETA.INDIRIZZO, SOCIETA.CITTA, SOCIETA.CAP, SOCIETA.EMAIL,
					SOCIETA.PASSWORD_HASH).values(nome, indirizzo, citta, cap, email, hash).execute();
		}
	}

	public static boolean checkPassword(String tipo, String id, String pwdChiara) throws SQLException {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			int cnt = 0;
			switch (tipo) {
			case "Concorrente" -> cnt = ctx.fetchCount(
					ctx.selectFrom(CONCORRENTE).where(CONCORRENTE.CF.eq(id)).and(CONCORRENTE.PASSWORD_HASH.eq(hash)));
			case "Società" -> cnt = ctx
					.fetchCount(ctx.selectFrom(SOCIETA).where(SOCIETA.NOME.eq(id)).and(SOCIETA.PASSWORD_HASH.eq(hash)));
			case "Amministratore" -> cnt = ctx.fetchCount(ctx.selectFrom(AMMINISTRATORE).where(AMMINISTRATORE.CF.eq(id))
					.and(AMMINISTRATORE.PASSWORD_HASH.eq(hash)));
			case "Arbitro" -> cnt = ctx
					.fetchCount(ctx.selectFrom(ARBITRO).where(ARBITRO.CF.eq(id)).and(ARBITRO.PASSWORD_HASH.eq(hash)));
			}
			return cnt > 0;
		}
	}

	public static void registraConcorrente(String cf, String nome, String cognome, String email, String nascita,
			String societaNome, String pwdChiara) throws SQLException {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		LocalDate dataNascita = LocalDate.parse(nascita); // yyyy-MM-dd
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.insertInto(CONCORRENTE, CONCORRENTE.CF, CONCORRENTE.NOME, CONCORRENTE.COGNOME, CONCORRENTE.EMAIL,
					CONCORRENTE.NASCITA, CONCORRENTE.SOCIETA, CONCORRENTE.PASSWORD_HASH)
					.values(cf, nome, cognome, email, dataNascita, societaNome, hash).execute();
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

	/* ---------- dati concorrente ---------- */
	public record ConcorrenteDto(String cf, String nome, String cognome, String email, String nascita, String societa) {
	}

	public static ConcorrenteDto getConcorrente(String cf) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			Record6<String, String, String, String, LocalDate, String> r = ctx
					.select(CONCORRENTE.CF, CONCORRENTE.NOME, CONCORRENTE.COGNOME, CONCORRENTE.EMAIL,
							CONCORRENTE.NASCITA, CONCORRENTE.SOCIETA)
					.from(CONCORRENTE).where(CONCORRENTE.CF.eq(cf)).fetchOne();
			if (r == null)
				throw new IllegalArgumentException("Concorrente non trovato");
			return new ConcorrenteDto(r.value1(), r.value2(), r.value3(), r.value4(), r.value5().toString(),
					r.value6());
		}
	}

	/* ---------- dati società ---------- */
	public record SocietaDto(String nome, String indirizzo, String citta, String cap, String email) {
	}

	public static Societa getSocieta(String nome) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			Record5<String, String, String, String, String> r = ctx
					.select(SOCIETA.NOME, SOCIETA.INDIRIZZO, SOCIETA.CITTA, SOCIETA.CAP, SOCIETA.EMAIL).from(SOCIETA)
					.where(SOCIETA.NOME.eq(nome)).fetchOne();
			if (r == null)
				throw new IllegalArgumentException("Società non trovata");
			return new Societa(r.value1(), r.value2(), r.value3(), r.value4(), r.value5());
		}
	}

	/* ---------- elenco gare a cui è iscritto il concorrente ---------- */
	public static List<GaraRow> getGareConcorrente(String cf) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			Result<Record4<String, LocalDate, String, String>> rs = ctx
					.select(GARA.CODICE, GARA.DATA, GARA.TECNICA, CAMPOGARA.DESCRIZIONE).from(GARA).join(ISCRIVE)
					.on(GARA.CODICE.eq(ISCRIVE.CODICEGARA)).join(CAMPOGARA).on(GARA.CAMPOGARA.eq(CAMPOGARA.ID))
					.where(ISCRIVE.CONCORRENTE.eq(cf)).fetch();

			List<GaraRow> out = new ArrayList<>();
			for (Record4<String, LocalDate, String, String> r : rs) {
				out.add(new GaraRow(r.value1(), r.value2().toString(), r.value3(), r.value4()));
			}
			return out;
		}
	}

	/* --- elenco concorrenti di una data società --- */
	public static List<ConcorrenteRow> getConcorrentiPerSocieta(String nomeSocieta) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			var rs = ctx.select(CONCORRENTE.CF, CONCORRENTE.NOME, CONCORRENTE.COGNOME, CONCORRENTE.EMAIL,
					CONCORRENTE.NASCITA).from(CONCORRENTE).where(CONCORRENTE.SOCIETA.eq(nomeSocieta)).fetch();

			List<ConcorrenteRow> out = new ArrayList<>();
			for (var r : rs) {
				out.add(new ConcorrenteRow(r.get(CONCORRENTE.CF), r.get(CONCORRENTE.NOME), r.get(CONCORRENTE.COGNOME),
						r.get(CONCORRENTE.EMAIL), r.get(CONCORRENTE.NASCITA).toString()));
			}
			return out;
		}
	}

	public static boolean esisteArbitro(String cf) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			return ctx.fetchExists(ctx.selectFrom(ARBITRO).where(ARBITRO.CF.eq(cf)));
		}
	}

	public static boolean esisteSocieta(String nome) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			return ctx.fetchExists(ctx.selectFrom(SOCIETA).where(SOCIETA.NOME.eq(nome)));
		}
	}
	
	public static boolean insertGara(Gara gara) {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        ctx.insertInto(GARA,
	                GARA.CODICE,
	                GARA.NUMPROVA,
	                GARA.TECNICA,
	                GARA.CRITERIOPUNTI,
	                GARA.DATA,
	                GARA.MAXPERSONE,
	                GARA.MINPERSONE,
	                GARA.STATOGARA,
	                GARA.STATOCONFERMA,
	                GARA.TIPOGARA/*,
	                GARA.PROPOSITORE,
	                GARA.ACCETTATORE*/
	        )
	        .values(
	                gara.getCodice(),
	                gara.getNumProva(),
	                gara.getTecnica().name(),          // se Tecnica è enum, salvo come stringa
	                gara.getCriterioPunti(),
	                gara.getData(), // LocalDate -> java.sql.Date
	                gara.getMaxPersone(),
	                gara.getMinPersone(),
	                gara.getStatoGara().name(),       // enum -> stringa
	                gara.getStatoConferma().name(),   // enum -> stringa
	                gara.getTipoGara().name()/*,         enum -> stringa
	                gara.getPropositore().getIdentificatore(),
	                gara.getAccettatore().getIdentificatore(),*/
	        )
	        .execute();
	        
	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static String getUltimoCodiceGara() {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx
	                .select(GARA.CODICE)
	                .from(GARA)
	                .orderBy(GARA.CODICE.desc())
	                .limit(1)
	                .fetchOneInto(String.class);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}




}

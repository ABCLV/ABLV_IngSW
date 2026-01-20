package database;

import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.CAMPOGARA;
import static dbconSQLJOOQ.generated.Tables.SETTORE;
import static dbconSQLJOOQ.generated.Tables.GARA;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;
import static dbconSQLJOOQ.generated.Tables.ARBITRO;
import static dbconSQLJOOQ.generated.Tables.AMMINISTRATORE;
import static dbconSQLJOOQ.generated.Tables.ISCRIVE;
import static dbconSQLJOOQ.generated.Tables.CAMPIONATO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
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
import dbconSQLJOOQ.generated.tables.records.AmministratoreRecord;

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
					.where(SETTORE.CAMPOGARA.eq(c.getIdCampoGara())).fetchInto(Settore.class);

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
						g.setTecnica(Tecnica.valueOf(record.get(GARA.TECNICA).toUpperCase()));
						g.setCriterioPunti(CriterioPunti.valueOf(record.get(GARA.CRITERIOPUNTI).toUpperCase()));
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
			case "Societa" -> cnt = ctx
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

	/* ---------- dati amministratore ---------- */
	public record AmministratoreDto(String cf, String nome, String cognome) {
	}

	public static Amministratore getAmministratore(String cf) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			Amministratore amm = ctx.select(AMMINISTRATORE.CF.as("cfAmministratore"), AMMINISTRATORE.NOME, AMMINISTRATORE.COGNOME)
					.from(AMMINISTRATORE).where(AMMINISTRATORE.CF.eq(cf)).fetchOneInto(Amministratore.class);
			if (amm == null)
				throw new IllegalArgumentException("Amministratore non trovato");
			return amm;
		}
	}

	/* ---------- elenco gare a cui è iscritto il concorrente ---------- */
	public static List<Gara> getGareConcorrente(String cf) throws SQLException {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        Result<Record6<String, Integer, String, LocalDate, String, String>> rs = ctx
	                .select(
	                        GARA.CODICE,
	                        GARA.NUMPROVA,
	                        GARA.TECNICA,
	                        GARA.DATA,
	                        CAMPOGARA.ID,
	                        CAMPOGARA.DESCRIZIONE
	                )
	                .from(GARA)
	                .join(ISCRIVE).on(GARA.CODICE.eq(ISCRIVE.CODICEGARA))
	                .join(CAMPOGARA).on(GARA.CAMPOGARA.eq(CAMPOGARA.ID))
	                .where(ISCRIVE.CONCORRENTE.eq(cf))
	                .fetch();

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

	/* --- elenco concorrenti di una data società --- */
	public static List<Concorrente> getConcorrentiPerSocieta(String nomeSocieta) throws SQLException {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        var rs = ctx
	                .select(
	                        CONCORRENTE.CF,
	                        CONCORRENTE.COGNOME,
	                        CONCORRENTE.NOME,
	                        CONCORRENTE.EMAIL,
	                        CONCORRENTE.NASCITA
	                )
	                .from(CONCORRENTE)
	                .where(CONCORRENTE.SOCIETA.eq(nomeSocieta))
	                .fetch();

	        List<Concorrente> out = new ArrayList<>();
	        for (var r : rs) {
	            out.add(new Concorrente(
	                    r.get(CONCORRENTE.CF),
	                    r.get(CONCORRENTE.COGNOME),
	                    r.get(CONCORRENTE.NOME),
	                    r.get(CONCORRENTE.EMAIL),
	                    r.get(CONCORRENTE.NASCITA)
	            ));
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

			ctx.insertInto(GARA, GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.CRITERIOPUNTI, GARA.MINPERSONE,
					GARA.MAXPERSONE, GARA.STATOGARA, GARA.STATOCONFERMA, GARA.TIPOGARA, GARA.DATA, GARA.CAMPIONATO,
					GARA.ARBITRO, GARA.AMMINISTRATOREPROPOSTA, GARA.AMMINISTRATOREACCETTAZIONE, GARA.SOCIETA,
					GARA.CAMPOGARA)
					.values(gara.getCodice(), gara.getNumProva(), gara.getTecnica().name(),
							gara.getCriterioPunti().name(), gara.getMinPersone(), gara.getMaxPersone(),
							gara.getStatoGara().name(), gara.getStatoConferma().name(), gara.getTipoGara().name(),
							gara.getData(), gara.getCampionato() != null ? gara.getCampionato().getTitolo() : null,
							gara.getArbitro() != null ? gara.getArbitro().getCfArbitro() : null,
							gara.getPropositore() instanceof Amministratore ? gara.getPropositore().getIdentificatore() : null,
							gara.getAccettatore() != null ? gara.getAccettatore().getIdentificatore() : null,
							gara.getPropositore() instanceof Societa ? gara.getPropositore().getIdentificatore() : null,
							gara.getCampoGara().getIdCampoGara())
					.execute();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean esisteGaraInCampionato(Campionato campionato, int numeroProva) {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Integer count = ctx.selectCount().from(GARA).where(GARA.CAMPIONATO.eq(campionato.getTitolo()))
					.and(GARA.NUMPROVA.eq(numeroProva)).fetchOne(0, int.class);

			return count != null && count > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false; // In caso di errore, assume che non esista per sicurezza
		}
	}

	public static String getUltimoCodiceGara() {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(GARA.CODICE).from(GARA).orderBy(GARA.CODICE.desc()).limit(1).fetchOneInto(String.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Campionato> getCampionati() {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(CAMPIONATO.TITOLO, CAMPIONATO.CATEGORIA).from(CAMPIONATO).fetchInto(Campionato.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public static List<Arbitro> getArbitri() {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(ARBITRO.CF, ARBITRO.NOME, ARBITRO.COGNOME, ARBITRO.SEZIONE).from(ARBITRO)
					.orderBy(ARBITRO.CF).fetchInto(Arbitro.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public static List<CampoGara> getCampiGara() {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(CAMPOGARA.ID.as("idCampoGara"), CAMPOGARA.PAESE, CAMPOGARA.CORPOIDRICO,
					CAMPOGARA.LUNGHEZZA, CAMPOGARA.DESCRIZIONE).from(CAMPOGARA).orderBy(CAMPOGARA.ID)
					.fetchInto(CampoGara.class);

		} catch (SQLException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public static List<Gara> getGareProposteDaAmministratore(String amm) throws SQLException {

		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Result<Record7<String, Integer, String, LocalDate, String, String, String>> rs = ctx
					.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.DATA, GARA.CAMPOGARA, CAMPIONATO.TITOLO,
							CAMPIONATO.CATEGORIA)
					.from(GARA).leftJoin(CAMPIONATO).on(GARA.CAMPIONATO.eq(CAMPIONATO.TITOLO))
					.where(GARA.AMMINISTRATOREPROPOSTA.eq(amm)).fetch();

			List<Gara> out = new ArrayList<>();

			for (Record7<String, Integer, String, LocalDate, String, String, String> r : rs) {

				Gara g = new Gara();
				g.setCodice(r.value1());
				g.setNumProva(r.value2());
				g.setTecnica(Tecnica.valueOf(r.value3().toUpperCase()));
				g.setData(r.value4());

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

	public static List<Gara> getGareDaConfermare(String amm) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			Result<Record8<String, Integer, String, LocalDate, String, String, String, String>> gareAmm = ctx
					.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.DATA, GARA.CAMPOGARA,
							GARA.AMMINISTRATOREPROPOSTA, CAMPIONATO.TITOLO, CAMPIONATO.CATEGORIA)
					.from(GARA).leftJoin(CAMPIONATO).on(GARA.CAMPIONATO.eq(CAMPIONATO.TITOLO))
					.where(GARA.AMMINISTRATOREACCETTAZIONE.isNull(), GARA.AMMINISTRATOREPROPOSTA.notEqual(amm)).fetch();

			Result<Record8<String, Integer, String, LocalDate, String, String, String, String>> gareSoc = ctx
					.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.DATA, GARA.CAMPOGARA, GARA.SOCIETA,
							CAMPIONATO.TITOLO, CAMPIONATO.CATEGORIA)
					.from(GARA).leftJoin(CAMPIONATO).on(CAMPIONATO.TITOLO.eq(GARA.CAMPIONATO))
					.where(GARA.AMMINISTRATOREACCETTAZIONE.isNull(), GARA.SOCIETA.isNotNull()).fetch();

			List<Gara> out = new ArrayList<>();
			for (Record8<String, Integer, String, LocalDate, String, String, String, String> r : gareAmm) {

				Campionato campionato = null;
				if (r.value7() != null) {
					campionato = new Campionato(r.value6(), r.value7());
				}
				CampoGara campo = new CampoGara();
				campo.setIdCampoGara(r.value5());
				Amministratore propositore = new Amministratore();
				propositore.setCfAmministratore(r.value6());

				Gara g = new Gara();
				g.setCodice(r.value1());
				g.setNumProva(r.value2());
				g.setTecnica(Tecnica.valueOf(r.value3().toUpperCase()));
				g.setData(r.value4());
				g.setCampionato(campionato);
				g.setCampoGara(campo);
				g.setPropositore(propositore);

				out.add(g);
			}

			for (Record8<String, Integer, String, LocalDate, String, String, String, String> r : gareSoc) {

				Campionato campionato = null;
				if (r.value6() != null) {
					campionato = new Campionato(r.value6(), r.value7());
				}
				CampoGara campo = new CampoGara();
				campo.setIdCampoGara(r.value5());
				Societa propositore = new Societa();
				propositore.setNome(r.value6());

				Gara g = new Gara();
				g.setCodice(r.value1());
				g.setNumProva(r.value2());
				g.setTecnica(Tecnica.valueOf(r.value3().toUpperCase()));
				g.setData(r.value4());
				g.setCampionato(campionato);
				g.setCampoGara(campo);
				g.setPropositore(propositore);

				out.add(g);
			}

			return out;
		}
	}

	public static boolean accettaGara(String codice, String amm) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			int rowsAffected = ctx.update(GARA).set(GARA.STATOCONFERMA, StatoConferma.CONFERMATA.name())
					.set(GARA.AMMINISTRATOREACCETTAZIONE, amm)
					.where(GARA.CODICE.eq(codice).and(GARA.STATOCONFERMA.eq(StatoConferma.IN_ATTESA.name()))
							.and(GARA.AMMINISTRATOREACCETTAZIONE.isNull()) // Solo se non è già stata accettata
							.and(GARA.AMMINISTRATOREPROPOSTA.notEqual(amm))) // Non può accettare se stesso
					.execute();

			return rowsAffected > 0;
		}
	}

	public static boolean rifiutaGara(String codice, String amm) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			int rowsAffected = ctx.update(GARA).set(GARA.STATOCONFERMA, StatoConferma.ANNULLATA.name())
					.set(GARA.AMMINISTRATOREACCETTAZIONE, amm)
					.where(GARA.CODICE.eq(codice).and(GARA.STATOCONFERMA.eq(StatoConferma.IN_ATTESA.name()))
							.and(GARA.AMMINISTRATOREACCETTAZIONE.isNull()) // Solo se non è già stata rifiutata
							.and(GARA.AMMINISTRATOREPROPOSTA.notEqual(amm)))
					.execute();

			return rowsAffected > 0;
		}
	}

	public static Amministratore getAmministratoreByCF(String cf) {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        AmministratoreRecord record = ctx
	            .selectFrom(AMMINISTRATORE)
	            .where(AMMINISTRATORE.CF.eq(cf))
	            .fetchOne();  // ← restituisce AmministratoreRecord, non Record

	        if (record != null) {
	            return new Amministratore(
	                record.getCf(),
	                record.getNome(),
	                record.getCognome()
	            );
	        } else {
	            return null;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}

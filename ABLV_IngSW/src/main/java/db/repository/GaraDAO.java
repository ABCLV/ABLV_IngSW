package db.repository;

import static dbconSQLJOOQ.generated.Tables.CAMPIONATO;

import db.SQLiteConnectionManager;
import db.exception.AmministratoreEccezione;
import db.exception.GaraEccezione;

import static dbconSQLJOOQ.generated.Tables.GARA;
import static dbconSQLJOOQ.generated.Tables.ISCRIVE;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jooq.DSLContext;
import org.jooq.Record11;
import org.jooq.Record8;
import org.jooq.Record18;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;

import model.Amministratore;
import model.Campionato;
import model.CampoGara;
import model.Concorrente;
import model.Gara;
import model.Societa;
import model.enums.CriterioPunti;
import model.enums.StatoConferma;
import model.enums.StatoGara;
import model.enums.Tecnica;
import model.enums.TipologiaGara;
import service.exception.RicercaEccezione;

public class GaraDAO {


	public List<Gara> getGare() throws GaraEccezione {
		List<Gara> gare = new ArrayList<>();

		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			var result = ctx.select(GARA.CODICE, GARA.NUMPROVA, GARA.DATA, GARA.TIPOGARA, GARA.TECNICA,
					GARA.CRITERIOPUNTI, GARA.MAXPERSONE, GARA.MINPERSONE, GARA.STATOGARA, GARA.STATOCONFERMA).from(GARA)
					.fetch();

			for (var r : result) {
				Gara g = new Gara();

				g.setCodice(r.get(GARA.CODICE));
				g.setNumProva(r.get(GARA.NUMPROVA));

				// Converte la data da java.sql.Date a LocalDate
				LocalDate sqlDate = r.get(GARA.DATA);
				g.setData(sqlDate);

				// Converte stringhe in enum (se Tecnica, TipologiaGara, StatoGara,
				// StatoConferma sono enum)
				String tecnicaStr = r.get(GARA.TECNICA);
				if (tecnicaStr != null) {
					g.setTecnica(Tecnica.fromString(tecnicaStr));
				}

				g.setTipoGara(TipologiaGara.INDIVIDUALE);

				g.setCriterioPunti(CriterioPunti.valueOf(r.get(GARA.CRITERIOPUNTI)));

				g.setMaxPersone(r.get(GARA.MAXPERSONE));
				g.setMinPersone(r.get(GARA.MINPERSONE));

				String statoGaraStr = r.get(GARA.STATOGARA);
				if (statoGaraStr != null) {
					g.setStatoGara(StatoGara.valueOf(statoGaraStr));
				}

				String statoConfStr = r.get(GARA.STATOCONFERMA);
				if (statoConfStr != null) {
					g.setStatoConferma(StatoConferma.valueOf(statoConfStr));
				}

				// Autori non popolati, se vuoi puoi aggiungere logica per propositore e
				// accettatore

				gare.add(g);
			}

		}  catch (DataAccessException e) {
			throw new GaraEccezione("Errore nel recuperare la lista di gare!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nel recuperare la lista di gare!", e);
		}

		return gare;
	}

	public String trovaCodiceCampoGara(String codiceGara) throws GaraEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(GARA.CAMPOGARA).from(GARA).where(GARA.CODICE.eq(codiceGara)).fetchOne(GARA.CAMPOGARA);

		}  catch (DataAccessException e) {
			throw new GaraEccezione("Errore nel recupero del campo gara!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nel recupero del campo gara!", e);
		}
	}

	public List<Gara> esploraGare(CampoGara c) throws GaraEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			// mappaggio manuale
			return ctx
					.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.CRITERIOPUNTI, GARA.DATA, GARA.MAXPERSONE,
							GARA.MINPERSONE, GARA.STATOGARA, GARA.STATOCONFERMA, GARA.TIPOGARA)
					.from(GARA).where(GARA.CAMPOGARA.eq(c.getIdCampoGara())).fetch(record -> {
						try {
							Gara g = new Gara();

							g.setCodice(record.get(GARA.CODICE));
							g.setNumProva(record.get(GARA.NUMPROVA));

							g.setTecnica(Tecnica.valueOf(record.get(GARA.TECNICA).trim().toUpperCase()));
							g.setCriterioPunti(
									CriterioPunti.valueOf(record.get(GARA.CRITERIOPUNTI).trim().toUpperCase()));

							g.setMinPersone(record.get(GARA.MINPERSONE));
							g.setMaxPersone(record.get(GARA.MAXPERSONE));

							g.setStatoGara(StatoGara.valueOf(record.get(GARA.STATOGARA).trim().toUpperCase()));
							g.setStatoConferma(
									StatoConferma.valueOf(record.get(GARA.STATOCONFERMA).trim().toUpperCase()));
							g.setTipoGara(TipologiaGara.valueOf(record.get(GARA.TIPOGARA).trim().toUpperCase()));

							// NO validazione business nel DAO
							g.setData(record.get(GARA.DATA));

							return g;
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}).stream().filter(Objects::nonNull).toList();

		}  catch (DataAccessException e) {
			throw new GaraEccezione("Errore nell'esplorare le gare!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nell'esplorare le gare!", e);
		}
	}

	public boolean insertGara(Gara gara) throws GaraEccezione {
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
							gara.getPropositore() instanceof Amministratore ? gara.getPropositore().getIdentificatore()
									: null,
							gara.getAccettatore() != null ? gara.getAccettatore().getIdentificatore() : null,
							gara.getPropositore() instanceof Societa ? gara.getPropositore().getIdentificatore() : null,
							gara.getCampoGara().getIdCampoGara())
					.execute();

			return true;

		} catch (IntegrityConstraintViolationException e) {
			throw new GaraEccezione("Errore nell'inserire la nuova gara!", e);
		} catch (DataAccessException e) {
			throw new GaraEccezione("Errore nell'inserire la nuova gara!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nell'inserire la nuova gara!", e);
		}
	}

	public String getUltimoCodiceGara() throws GaraEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx
	            .select(
	                DSL.coalesce(
	                    DSL.max(GARA.CODICE),
	                    "G000"
	                )
	            )
	            .from(GARA)
	            .fetchOneInto(String.class);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new GaraEccezione(
	            "Errore nel recuperare l'ultimo codice gara!", e
	        );
	    }
	}

	
	

	public List<Gara> getGareDaConfermare(String amm) throws GaraEccezione {
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
		} catch (DataAccessException e) {
			throw new GaraEccezione("Errore nel recuperare la lista delle gare da confermare!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nel recuperare la lista delle gare da confermare!", e);
		}
	}

	public boolean accettaGara(String codice, String amm) throws GaraEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			int rowsAffected = ctx.update(GARA).set(GARA.STATOCONFERMA, StatoConferma.CONFERMATA.name())
					.set(GARA.AMMINISTRATOREACCETTAZIONE, amm)
					.where(GARA.CODICE.eq(codice).and(GARA.STATOCONFERMA.eq(StatoConferma.IN_ATTESA.name()))
							.and(GARA.AMMINISTRATOREACCETTAZIONE.isNull()) // Solo se non è già stata accettata
							.and(GARA.AMMINISTRATOREPROPOSTA.notEqual(amm))) // Non può accettare se stesso
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

	public boolean rifiutaGara(String codice, String amm) throws GaraEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			int rowsAffected = ctx.update(GARA).set(GARA.STATOCONFERMA, StatoConferma.ANNULLATA.name())
					.set(GARA.AMMINISTRATOREACCETTAZIONE, amm)
					.where(GARA.CODICE.eq(codice).and(GARA.STATOCONFERMA.eq(StatoConferma.IN_ATTESA.name()))
							.and(GARA.AMMINISTRATOREACCETTAZIONE.isNull()) // Solo se non è già stata rifiutata
							.and(GARA.AMMINISTRATOREPROPOSTA.notEqual(amm).or(GARA.AMMINISTRATOREPROPOSTA.isNull())))
					.execute();

			return rowsAffected > 0;
		} catch (IntegrityConstraintViolationException e) {
			throw new GaraEccezione("Errore nel rifiutare la gara!", e);
		} catch (DataAccessException e) {
			throw new GaraEccezione("Errore nel rifiutare la gara!", e);
		} catch (SQLException e) {
			throw new GaraEccezione("Errore nel rifiutare la gara!", e);
		}
	}
	
	public List<Gara> getGareDisponibiliPerIscrizione() throws RicercaEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        Result<Record11<String, Integer, String, String, Integer, Integer, String, LocalDate, String, String, String>> rs = ctx
	        	.select(GARA.CODICE, GARA.NUMPROVA, GARA.TECNICA, GARA.CRITERIOPUNTI, GARA.MINPERSONE, 
	        			GARA.MAXPERSONE, GARA.TIPOGARA, GARA.DATA, GARA.CAMPOGARA, CAMPIONATO.TITOLO, 
	        			CAMPIONATO.CATEGORIA)
	            .from(GARA)
	            .leftJoin(CAMPIONATO).on(GARA.CAMPIONATO.eq(CAMPIONATO.TITOLO))
	            .where(GARA.MAXPERSONE.gt(
	                DSL.coalesce(
	                    DSL.select(DSL.count())
	                        .from(ISCRIVE)
	                        .where(ISCRIVE.CODICEGARA.eq(GARA.CODICE))
	                        .asField(),
	                    0
	                )
	            ))
	            .and(GARA.STATOGARA.eq(StatoGara.NON_INIZIATA.name()))
	            .and(GARA.STATOCONFERMA.eq(StatoConferma.CONFERMATA.name()))
	            .fetch();

	        List<Gara> out = new ArrayList<>();

	        for (Record11<String, Integer, String, String, Integer, Integer, String, LocalDate, String, String, String>
	        	r : rs) {
	        	
	            Gara g = new Gara();

	            g.setCodice(r.get(GARA.CODICE));
	            g.setData(r.get(GARA.DATA));
	            g.setNumProva(r.get(GARA.NUMPROVA));
	            g.setCriterioPunti(CriterioPunti.valueOf(r.get(GARA.CRITERIOPUNTI).toUpperCase()));
	            g.setMinPersone(r.get(GARA.MINPERSONE));
	            g.setMaxPersone(r.get(GARA.MAXPERSONE));
	            g.setTecnica(Tecnica.valueOf(r.get(GARA.TECNICA).toUpperCase()));
	            g.setTipoGara(TipologiaGara.valueOf(r.get(GARA.TIPOGARA).toUpperCase()));

	            // CampoGara
	            CampoGara campo = new CampoGara();
	            campo.setIdCampoGara(r.get(GARA.CAMPOGARA));
	            g.setCampoGara(campo);

	            // Campionato (se presente)
	            if (r.get(CAMPIONATO.TITOLO) != null) {
	                Campionato campionato = new Campionato();
	                campionato.setTitolo(r.get(CAMPIONATO.TITOLO));
	                campionato.setCategoria(r.get(CAMPIONATO.CATEGORIA));
	                g.setCampionato(campionato);
	            }

	            out.add(g);
	        }

	        return out;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new GaraEccezione("Errore nel recuperare le gare disponibili per l'iscrizione!", e);
	    }
	}
	
	
	
	

}
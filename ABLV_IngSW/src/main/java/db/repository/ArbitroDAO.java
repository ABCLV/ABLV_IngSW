package db.repository;

import static dbconSQLJOOQ.generated.Tables.ARBITRO;
import static dbconSQLJOOQ.generated.Tables.CAMPIONATO;
import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.GARA;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.jooq.Record8;
import org.jooq.Record11;
import org.jooq.Record4;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;

import model.Arbitro;
import model.Campionato;
import model.CampoGara;
import model.Concorrente;
import model.Gara;
import model.enums.CriterioPunti;
import model.enums.StatoConferma;
import model.enums.StatoGara;
import model.enums.Tecnica;
import model.enums.TipologiaGara;
import db.SQLiteConnectionManager;
import db.exception.AmministratoreEccezione;
import db.exception.ArbitroEccezione;
import db.exception.ConcorrenteEccezione;

public class ArbitroDAO {
	
	public Arbitro getArbitro(String cf) throws ArbitroEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			Record4<String, String, String, String> r = ctx
					.select(ARBITRO.CF, ARBITRO.COGNOME, ARBITRO.NOME, ARBITRO.SEZIONE)
					.from(ARBITRO).where(ARBITRO.CF.eq(cf)).fetchOne();

			if (r == null)
				return null;

			return new Arbitro(r.get(ARBITRO.CF), r.get(ARBITRO.NOME), r.get(ARBITRO.COGNOME),
					r.get(ARBITRO.SEZIONE));
		} catch (DataAccessException e) {
			throw new ArbitroEccezione("Errore nel recuperare l'arbitro!", e);
		} catch (SQLException e) {
			throw new ArbitroEccezione("Errore nel recuperare l'arbitro!", e);
		}
	}

	public void registraArbitro(String cf, String nome, String cognome, String sezione, String pwdChiara)
			throws ArbitroEccezione {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.insertInto(ARBITRO, ARBITRO.CF, ARBITRO.NOME, ARBITRO.COGNOME, ARBITRO.SEZIONE, ARBITRO.PASSWORD_HASH)
					.values(cf, nome, cognome, sezione, hash).execute();
		} catch (IntegrityConstraintViolationException e) {
	        throw new ArbitroEccezione(
	            "Arbitro già esistente con CF = " + cf, e
	        );
	    } catch (DataAccessException | SQLException e) {
	        throw new ArbitroEccezione(
	            "Errore di accesso al database durante la registrazione dell'arbitro", e
	        );
	    }
	}

	public boolean esisteArbitro(String cf) throws ArbitroEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			return ctx.fetchExists(ctx.selectFrom(ARBITRO).where(ARBITRO.CF.eq(cf)));
		} catch (DataAccessException e) {
			throw new ArbitroEccezione("Errore nel trovare l'arbitro!", e);
		} catch (SQLException e) {
			throw new ArbitroEccezione("Errore nel trovare l'arbitro!", e);
		}
	}

	public List<Arbitro> getArbitri() throws ArbitroEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(ARBITRO.CF, ARBITRO.NOME, ARBITRO.COGNOME, ARBITRO.SEZIONE).from(ARBITRO)
					.orderBy(ARBITRO.CF).fetchInto(Arbitro.class);

		} catch (DataAccessException e) {
			throw new ArbitroEccezione("Errore nel recuperare la lista degli arbitri!", e);
		} catch (SQLException e) {
			throw new ArbitroEccezione("Errore nel recuperare la lista degli arbitri!", e);
		}
	}
	
	public List<Gara> getGareAggiornabiliPerArbitro(String arb) throws ArbitroEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
	        
	        LocalDate oggi = LocalDate.now();

	        Result<Record11<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String, String>> rs = ctx
	                .select(GARA.ID, GARA.CAMPOGARA, GARA.NUMPROVA, GARA.CRITERIOPUNTI, GARA.STATOGARA,
	                        GARA.DATA, GARA.TECNICA, GARA.TIPOGARA, CAMPIONATO.TITOLO, CAMPIONATO.CATEGORIA,
	                        GARA.STATOCONFERMA)
	                .from(GARA).leftJoin(CAMPIONATO).on(GARA.CAMPIONATO.eq(CAMPIONATO.TITOLO))
	                .where(GARA.ARBITRO.eq(arb)
	                		.and(GARA.STATOCONFERMA.eq(StatoConferma.CONFERMATA.name()))
	                        .and(GARA.DATA.gt(oggi)))
	                .fetch();

	        List<Gara> out = new ArrayList<>();

	        for (Record11<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String, String> r : rs) {

	            Gara g = new Gara();
	            
	            g.setCodice(r.value1());                    // GARA.CODICE
	            g.setNumProva(r.value3());                  // GARA.NUMPROVA
	            g.setCriterioPunti(CriterioPunti.valueOf(r.value4().toUpperCase()));  // GARA.CRITERIOPUNTI
	            g.setStatoGara(StatoGara.valueOf(r.value5().toUpperCase()));          // GARA.STATOGARA
	            g.setData(r.value6());                      // GARA.DATA
	            g.setTecnica(Tecnica.valueOf(r.value7().toUpperCase()));              // GARA.TECNICA
	            g.setTipoGara(TipologiaGara.valueOf(r.value8().toUpperCase()));       // GARA.TIPOGARA
	            g.setStatoConferma(StatoConferma.valueOf(r.value11().toUpperCase())); // GARA.STATOCONFERMA

	            // CampoGara
	            CampoGara campo = new CampoGara();
	            campo.setIdCampoGara(r.value2());           // GARA.CAMPOGARA
	            g.setCampoGara(campo);

	            // Campionato (se non è null)
	            if (r.value9() != null) {
	                Campionato campionato = new Campionato();
	                campionato.setTitolo(r.value9());       // CAMPIONATO.TITOLO
	                campionato.setCategoria(r.value10());   // CAMPIONATO.CATEGORIA
	                g.setCampionato(campionato);
	            }

	            out.add(g);
	        }
	        return out;
	    } catch(SQLException e) {
	        e.printStackTrace();
	        throw new ArbitroEccezione("Errore nel recuperare la liste delle gare assegnate all'arbitro!", e);
	    }
	}
	
	
	public List<Gara> getGarePerArbitro() throws ArbitroEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
	        
	        LocalDate oggi = LocalDate.now();

	        Result<Record11<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String, String>> rs = ctx
	                .select(GARA.ID, GARA.CAMPOGARA, GARA.NUMPROVA, GARA.CRITERIOPUNTI, GARA.STATOGARA,
	                        GARA.DATA, GARA.TECNICA, GARA.TIPOGARA, CAMPIONATO.TITOLO, CAMPIONATO.CATEGORIA,
	                        GARA.STATOCONFERMA)
	                .from(GARA).leftJoin(CAMPIONATO).on(GARA.CAMPIONATO.eq(CAMPIONATO.TITOLO))
	                .where(GARA.ARBITRO.isNull())
	                		//.and(GARA.DATA.gt(oggi.plusDays(3))))
	                .fetch();

	        List<Gara> out = new ArrayList<>();

	        for (Record11<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String, String> r : rs) {

	            Gara g = new Gara();
	            
	            g.setCodice(r.value1());                    // GARA.CODICE
	            g.setNumProva(r.value3());                  // GARA.NUMPROVA
	            g.setCriterioPunti(CriterioPunti.valueOf(r.value4().toUpperCase()));  // GARA.CRITERIOPUNTI
	            g.setStatoGara(StatoGara.valueOf(r.value5().toUpperCase()));          // GARA.STATOGARA
	            g.setData(r.value6());                      // GARA.DATA
	            g.setTecnica(Tecnica.valueOf(r.value7().toUpperCase()));              // GARA.TECNICA
	            g.setTipoGara(TipologiaGara.valueOf(r.value8().toUpperCase()));       // GARA.TIPOGARA
	            g.setStatoConferma(StatoConferma.valueOf(r.value11().toUpperCase())); // GARA.STATOCONFERMA

	            // CampoGara
	            CampoGara campo = new CampoGara();
	            campo.setIdCampoGara(r.value2());           // GARA.CAMPOGARA
	            g.setCampoGara(campo);

	            // Campionato (se non è null)
	            if (r.value9() != null) {
	                Campionato campionato = new Campionato();
	                campionato.setTitolo(r.value9());       // CAMPIONATO.TITOLO
	                campionato.setCategoria(r.value10());   // CAMPIONATO.CATEGORIA
	                g.setCampionato(campionato);
	            }

	            out.add(g);
	        }
	        return out;
	    } catch(SQLException e) {
	        e.printStackTrace();
	        throw new ArbitroEccezione("Errore nel recuperare la liste delle gare assegnate all'arbitro!", e);
	    }
	}
	
	public List<Gara> getGareDiArbitro(String arb) throws ArbitroEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        Result<Record11<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String, String>> rs = ctx
	                .select(GARA.ID, GARA.CAMPOGARA, GARA.NUMPROVA, GARA.CRITERIOPUNTI, GARA.STATOGARA,
	                        GARA.DATA, GARA.TECNICA, GARA.TIPOGARA, CAMPIONATO.TITOLO, CAMPIONATO.CATEGORIA,
	                        GARA.STATOCONFERMA)
	                .from(GARA).leftJoin(CAMPIONATO).on(GARA.CAMPIONATO.eq(CAMPIONATO.TITOLO))
	                .where(GARA.ARBITRO.eq(arb))
	                .fetch();

	        List<Gara> out = new ArrayList<>();

	        for (Record11<Integer, Integer, Integer, String, String, LocalDate, String, String, String, String, String> r : rs) {

	            Gara g = new Gara();
	            
	            g.setCodice(r.value1());                    // GARA.CODICE
	            g.setNumProva(r.value3());                  // GARA.NUMPROVA
	            g.setCriterioPunti(CriterioPunti.valueOf(r.value4().toUpperCase()));  // GARA.CRITERIOPUNTI
	            g.setStatoGara(StatoGara.valueOf(r.value5().toUpperCase()));          // GARA.STATOGARA
	            g.setData(r.value6());                      // GARA.DATA
	            g.setTecnica(Tecnica.valueOf(r.value7().toUpperCase()));              // GARA.TECNICA
	            g.setTipoGara(TipologiaGara.valueOf(r.value8().toUpperCase()));       // GARA.TIPOGARA
	            g.setStatoConferma(StatoConferma.valueOf(r.value11().toUpperCase())); // GARA.STATOCONFERMA

	            // CampoGara
	            CampoGara campo = new CampoGara();
	            campo.setIdCampoGara(r.value2());           // GARA.CAMPOGARA
	            g.setCampoGara(campo);

	            // Campionato (se non è null)
	            if (r.value9() != null) {
	                Campionato campionato = new Campionato();
	                campionato.setTitolo(r.value9());       // CAMPIONATO.TITOLO
	                campionato.setCategoria(r.value10());   // CAMPIONATO.CATEGORIA
	                g.setCampionato(campionato);
	            }

	            out.add(g);
	        }
	        return out;
	    } catch(SQLException e) {
	        e.printStackTrace();
	        throw new ArbitroEccezione("Errore nel recuperare la liste delle gare assegnate all'arbitro!", e);
	    }
	}
	
	public void eliminaArbitro(String cf) throws ArbitroEccezione {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {
	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
	        ctx.deleteFrom(ARBITRO)
	           .where(ARBITRO.CF.eq(cf))
	           .execute();
	    }  catch (DataAccessException e) {
	        throw new ArbitroEccezione("Errore nell'eliminare l'arbitro", e);
	    } catch (SQLException e) {
	        throw new ArbitroEccezione("Errore di connessione", e);
	    }
	}
	
	public int assegnaArbitroAGara(int codiceGara, String cfArbitro)
	        throws ArbitroEccezione {

	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        return ctx.update(GARA)
	                .set(GARA.ARBITRO, cfArbitro)
	                .where(GARA.ID.eq(codiceGara)
	                        .and(GARA.ARBITRO.isNull()))
	                .execute();
	        

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new ArbitroEccezione(
	            "Errore durante l'assegnazione dell'arbitro alla gara.", e
	        );
	    }
	}
	
	public void disiscriviArbitro(int codiceGara, String cfArbitro)
	        throws ArbitroEccezione {

	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
	        
	        LocalDate oggi = LocalDate.now();

	        boolean updated = ctx.update(GARA)
	                .set(GARA.ARBITRO, (String) null)
	                .where(
	                    GARA.ID.eq(codiceGara)
	                    .and(GARA.ARBITRO.eq(cfArbitro))
	                    //.and(GARA.DATA.gt(oggi.plusDays(3)))
	                )
	                .execute() > 0;
	                
            if(!updated) {
	        	throw new ArbitroEccezione("Errore nel disiscrivere l'arbitro dalla gara!", new RuntimeException());
	        }

	    } catch (SQLException e) {
	        throw new ArbitroEccezione("Errore durante la disiscrizione dell'arbitro dalla gara!", e);
	    }
	}


	
	public void aggiornaStatoGara(int codice, StatoGara stato) throws ArbitroEccezione {
		
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.update(GARA)
				.set(GARA.STATOGARA, stato.name())
				.where(GARA.ID.eq(codice))
				.execute();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new ArbitroEccezione("Errore nell'aggiornare lo stato della gara!", e);
		}
		
	}
	
	
	public void aggiornaDataGara(int codice, LocalDate data) throws ArbitroEccezione {
		
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			
			LocalDate oggi = LocalDate.now();
			
			if(data.isAfter(oggi.plusDays(3))) {
				DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
				ctx.update(GARA)
					.set(GARA.DATA, data)
					.where(GARA.ID.eq(codice))
					.execute();
			} else {
				throw new ArbitroEccezione("La nuova data deve essere almeno 3 giorni dopo oggi!", new RuntimeException());
			}
		} catch(SQLException e) {
			throw new ArbitroEccezione("Errore nell'aggiornare la data della gara!", e);
		}
		
	}

}
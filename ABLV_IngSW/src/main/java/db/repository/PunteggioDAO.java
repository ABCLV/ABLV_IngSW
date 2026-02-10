package db.repository;

import static dbconSQLJOOQ.generated.Tables.PARTECIPA;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;
import static dbconSQLJOOQ.generated.Tables.TURNO;
import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.ISCRIVE;
import static dbconSQLJOOQ.generated.Tables.SETTORE;
import static dbconSQLJOOQ.generated.Tables.CONTRATTO;
import static dbconSQLJOOQ.generated.Tables.GARA;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import db.SQLiteConnectionManager;
import db.exception.GaraEccezione;
import javafx.collections.ObservableList;
import model.ClassificaRiga;
import model.Concorrente;
import model.RisultatoTurno;
import model.Settore;
import model.Turno;
import model.enums.StatoConferma;

import org.jooq.Query;
import org.jooq.impl.DSL;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;


public class PunteggioDAO {
	public PunteggioDAO() {}
	private Map<String, Integer> settorePerConcorrente;
	
	
	
	
	
	public ObservableList<ClassificaRiga> calcolaClassifica(int codiceGara) {

	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        // --- Strutture di appoggio ---
	        Map<String, Double> penalitaTotali = new HashMap<>();
	        Map<String, List<Double>> penalitaPerTurno = new HashMap<>();

	        // --- Recupero turni ---
	        List<Integer> turni = ctx.select(TURNO.ID)
	                .from(TURNO)
	                .where(TURNO.GARA.eq(codiceGara))
	                .fetchInto(Integer.class);

	        // --- Calcolo classifiche di ogni turno ---
	        for (Integer idTurno : turni) {

	            var records = ctx.select(
	                        PARTECIPA.CONCORRENTE,
	                        PARTECIPA.NUMPUNTI
	                    )
	                    .from(PARTECIPA)
	                    .where(PARTECIPA.TURNO.eq(idTurno))
	                    .orderBy(PARTECIPA.NUMPUNTI.desc())
	                    .fetch();

	            int posizione = 1;
	            int i = 0;

	            while (i < records.size()) {

	                int j = i;
	                int punti = records.get(i).get(PARTECIPA.NUMPUNTI);

	                // gruppo di pari merito
	                while (j < records.size()
	                        && records.get(j).get(PARTECIPA.NUMPUNTI) == punti) {
	                    j++;
	                }

	                int count = j - i;
	                double penalitaMedia =
	                        (posizione + (posizione + count - 1)) / 2.0;

	                for (int k = i; k < j; k++) {
	                    String cf = records.get(k).get(PARTECIPA.CONCORRENTE);

	                    penalitaTotali.merge(cf, penalitaMedia, Double::sum);

	                    penalitaPerTurno
	                            .computeIfAbsent(cf, x -> new java.util.ArrayList<>())
	                            .add(penalitaMedia);
	                }

	                posizione += count;
	                i = j;
	            }
	        }

	        // --- Ordinamento per piazzamento ---
	        List<Map.Entry<String, Double>> ordinati =
	                penalitaTotali.entrySet().stream()
	                        .sorted(Map.Entry.comparingByValue())
	                        .toList();

	        ObservableList<ClassificaRiga> classifica =
	                javafx.collections.FXCollections.observableArrayList();

	        int posizioneFinale = 1;
	        int i = 0;

	        while (i < ordinati.size()) {

	            int j = i;
	            double piazzamento = ordinati.get(i).getValue();

	            while (j < ordinati.size()
	                    && ordinati.get(j).getValue().equals(piazzamento)) {
	                j++;
	            }

	            double penalitaFinale =
	                    (posizioneFinale + (posizioneFinale + (j - i) - 1)) / 2.0;

	            for (int k = i; k < j; k++) {
	                String cf = ordinati.get(k).getKey();

	                var conc = ctx.select(
	                            CONCORRENTE.CF,
	                            CONCORRENTE.SOCIETA,
	                            CONTRATTO.SPONSOR
	                        )
	                        .from(CONCORRENTE)
	                        .leftJoin(CONTRATTO)
	                            .on(CONCORRENTE.CF.eq(CONTRATTO.CONCORRENTE))
	                        .where(CONCORRENTE.CF.eq(cf))
	                        .fetchOne();

	                String piazzamentiTurni = penalitaPerTurno.get(cf).stream()
	                        .map(p -> p % 1 == 0 ? String.valueOf(p.intValue()) : p.toString())
	                        .collect(Collectors.joining("-"));

	                String sponsor = conc != null && conc.get(CONTRATTO.SPONSOR) != null
	                        ? conc.get(CONTRATTO.SPONSOR).toString()
	                        : null;


	                classifica.add(new ClassificaRiga(
	                        posizioneFinale,
	                        cf,
	                        conc != null ? conc.get(CONCORRENTE.SOCIETA) : null,
	                        sponsor,
	                        penalitaFinale,
	                        piazzamento,
	                        piazzamentiTurni
	                ));
	            }


	            posizioneFinale += (j - i);
	            i = j;
	        }
	        

	        return classifica;

	    } catch (Exception e) {
	        throw new GaraEccezione(
	                "Errore nel calcolo della classifica per la gara " + codiceGara,
	                e
	        );
	    }
	}

	

	public void terminaGara(int codiceGara) {

	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        ctx.update(GARA)
	           .set(GARA.STATOCONFERMA, "TERMINATA")
	           .where(GARA.ID.eq(codiceGara))
	           .execute();


	    } catch (Exception e) {
	        throw new GaraEccezione(
	                "Errore nella terminazione della gara " + codiceGara,
	                e
	        );
	    }
	}


	

	public void salvaTurno(int codiceGara, int numeroTurno, List<RisultatoTurno> risultati) {
	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        ctx.transaction(configuration -> {
	            DSLContext tx = DSL.using(configuration);

	            // Recupera gli ID dei turni
	            List<Integer> turni = ctx.select(TURNO.ID)
	                    .from(TURNO)
	                    .where(TURNO.NUMERO.eq(numeroTurno))
	                    .and(TURNO.GARA.eq(codiceGara))
	                    .fetchInto(Integer.class);


	            // Aggiorna i risultati dei concorrenti per tutti i turni trovati
	            List<Query> queries = risultati.stream()
	                    .map(r -> (Query) tx.update(PARTECIPA)
	                            .set(PARTECIPA.NUMPUNTI, r.isSqualificato() ? 0 : (int) r.getPunteggio())
	                            .set(PARTECIPA.SQUALIFICA, r.isSqualificato())
	                            .where(PARTECIPA.CONCORRENTE.eq(r.getIdConcorrente()))
	                            .and(PARTECIPA.TURNO.in(turni)) // <- qui usiamo la lista di ID
	                    )
	                    .collect(Collectors.toList());

	            tx.batch(queries).execute();
	        });

	    } catch (Exception e) {
	        throw new GaraEccezione(
	                "Errore nel salvataggio del turno " + numeroTurno +
	                        " per la gara " + codiceGara,
	                e
	        );
	    }
	}







	public void definizioneGruppi(int codiceGara, List<Concorrente> assenti) {

	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        List<Concorrente> presenti =
	                caricaConcorrentiPresenti(ctx, codiceGara, assenti);

	        List<Turno> turni =
	                caricaTurniGara(ctx, codiceGara);

	        List<Settore> settori =
	                estraiSettori(turni);


	        assegnaSettoriRoundRobin(presenti, settori);

	        creaPartecipazioni(ctx, presenti, turni, settorePerConcorrente);

	    } catch (SQLException e) {
	        throw new GaraEccezione(
	            "Errore nella definizione dei gruppi per la gara " + codiceGara,
	            e
	        );
	    }
	}
	
	
	
	private List<Concorrente> caricaConcorrentiPresenti(
	        DSLContext ctx,
	        int codiceGara,
	        List<Concorrente> assenti
	) {

	    Set<String> cfAssenti = assenti.stream()
	            .map(Concorrente::getCf)
	            .collect(Collectors.toSet());

	    Condition cond = ISCRIVE.GARA.eq(codiceGara);

	    if (!cfAssenti.isEmpty()) {
	        cond = cond.and(CONCORRENTE.CF.notIn(cfAssenti));
	    }

	    List<Concorrente> presenti =
	        ctx.select(
	                CONCORRENTE.CF,
	                CONCORRENTE.NOME,
	                CONCORRENTE.COGNOME,
	                CONCORRENTE.EMAIL,
	                CONCORRENTE.NASCITA,
	                CONCORRENTE.SOCIETA
	        )
	        .from(CONCORRENTE)
	        .join(ISCRIVE)
	            .on(CONCORRENTE.CF.eq(ISCRIVE.CONCORRENTE))
	        .where(cond)
	        .fetchInto(Concorrente.class);

	    return presenti;
	}

	
	private List<Turno> caricaTurniGara(DSLContext ctx, int codiceGara) {

	    List<Turno> turni =
	        ctx.select(
	                TURNO.ID,
	                TURNO.DURATA,
	                TURNO.SETTORE,
	                TURNO.NUMERO
	        )
	        .from(TURNO)
	        .where(TURNO.GARA.eq(codiceGara))
	        .fetch()
	        .map(r -> {

	            Turno t = new Turno();
	            t.setCodiceTurno(r.get(TURNO.ID));
	            t.setDurata(r.get(TURNO.DURATA));
	            t.setNumero(r.get(TURNO.NUMERO));

	            Settore s = ctx
	                .select(SETTORE.ID, SETTORE.LUNGHEZZA, SETTORE.DESCRIZIONE)
	                .from(SETTORE)
	                .where(SETTORE.ID.eq(r.get(TURNO.SETTORE)))
	                .fetchOneInto(Settore.class);

	            t.setSett(s);
	            return t;
	        });

	    return turni;
	}

	private List<Settore> estraiSettori(List<Turno> turni) {

	    List<Settore> settori = turni.stream()
	            .map(Turno::getSett)
	            .distinct()
	            .toList();

	    return settori;
	}

	
	private void assegnaSettoriRoundRobin(
	        List<Concorrente> presenti,
	        List<Settore> settori
	) {

	    settorePerConcorrente = new HashMap<>();
	    int i = 0;
	    
	    // Mischia l’ordine dei concorrenti
	    Collections.shuffle(presenti);

	    for (Concorrente c : presenti) {
	    	settorePerConcorrente.put(
	                c.getCf(),
	                settori.get(i % settori.size()).getIdSettore()
	        );
	        i++;
	    }

	}

	
	private void creaPartecipazioni(
	        DSLContext ctx,
	        List<Concorrente> presenti,
	        List<Turno> turni,
	        Map<String, Integer> settorePerConcorrente
	) {

	    for (Concorrente c : presenti) {

	        int settore = settorePerConcorrente.get(c.getCf());

	        for (Turno t : turni) {

	            if (t.getSett().getIdSettore() != settore) continue;

	            ctx.insertInto(PARTECIPA)
	                .set(PARTECIPA.CONCORRENTE, c.getCf())
	                .set(PARTECIPA.NUMPUNTI, 0)
	                .set(PARTECIPA.TURNO, t.getCodiceTurno())
	                .set(PARTECIPA.SQUALIFICA, false)
	                .execute();
	        }
	    }
	}




}

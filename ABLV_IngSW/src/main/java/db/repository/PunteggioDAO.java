package db.repository;

import static dbconSQLJOOQ.generated.Tables.PARTECIPA;
import static dbconSQLJOOQ.generated.Tables.SOCIETA;
import static dbconSQLJOOQ.generated.Tables.TURNO;
import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.ISCRIVE;
import static dbconSQLJOOQ.generated.Tables.SETTORE;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import db.SQLiteConnectionManager;
import db.exception.GaraEccezione;
import model.Concorrente;
import model.Settore;
import model.Turno;


public class PunteggioDAO {
	public PunteggioDAO() {}
	private Map<String, Integer> settorePerConcorrente;
	
	
	



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

	    System.out.println("presenti: " + presenti);
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

	    System.out.println("turni: " + turni);
	    return turni;
	}

	private List<Settore> estraiSettori(List<Turno> turni) {

	    List<Settore> settori = turni.stream()
	            .map(Turno::getSett)
	            .distinct()
	            .toList();

	    System.out.println("settori gara: " + settori);
	    return settori;
	}

	
	private void assegnaSettoriRoundRobin(
	        List<Concorrente> presenti,
	        List<Settore> settori
	) {

	    settorePerConcorrente = new HashMap<>();
	    int i = 0;

	    for (Concorrente c : presenti) {
	    	settorePerConcorrente.put(
	                c.getCf(),
	                settori.get(i % settori.size()).getIdSettore()
	        );
	        i++;
	    }

	    System.out.println("mappa settori: " + settorePerConcorrente);
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

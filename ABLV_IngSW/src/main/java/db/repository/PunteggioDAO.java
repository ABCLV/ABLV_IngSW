package db.repository;

import static dbconSQLJOOQ.generated.Tables.PARTECIPA;
import static dbconSQLJOOQ.generated.Tables.TURNO;
import static dbconSQLJOOQ.generated.Tables.CONCORRENTE;
import static dbconSQLJOOQ.generated.Tables.ISCRIVE;
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
	
	private String nextIdPunteggio(String last) {
	    int num = Integer.parseInt(last.substring(1));
	    return String.format("P%04d", num + 1);
	}

	private String getUltimoIdPunteggio(DSLContext ctx) {
	    String last = ctx.select(PARTECIPA.IDPUNTEGGIO)
	            .from(PARTECIPA)
	            .orderBy(PARTECIPA.IDPUNTEGGIO.desc())
	            .limit(1)
	            .fetchOneInto(String.class);

	    return last != null ? last : "P0000";
	}
	
	public void definizioneGruppi(String codiceGara, List<Concorrente> assenti) {

	    try (Connection conn = SQLiteConnectionManager.getConnection()) {

	        DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

	        Set<String> cfAssenti = assenti.stream()
	                .map(Concorrente::getCf)
	                .collect(Collectors.toSet());

	        Condition cond = ISCRIVE.CODICEGARA.eq(codiceGara);

	        if (!cfAssenti.isEmpty()) {
	            cond = cond.and(CONCORRENTE.CF.notIn(cfAssenti));
	        }

	        List<Concorrente> presenti =
	            ctx.select(CONCORRENTE.fields())
	               .from(CONCORRENTE)
	               .join(ISCRIVE)
	                   .on(CONCORRENTE.CF.eq(ISCRIVE.CONCORRENTE))
	               .where(cond)
	               .fetchInto(Concorrente.class);

	        if (presenti.isEmpty()) {
	            throw new GaraEccezione("Nessun concorrente presente alla gara " + codiceGara, null);
	        }

	        /* ===============================
	           3️⃣ Turni della gara
	           =============================== */
	        List<Turno> turni = ctx.selectFrom(TURNO)
	                .where(TURNO.GARA.eq(codiceGara))
	                .fetchInto(Turno.class);

	        /* ===============================
	           4️⃣ Settori disponibili
	           =============================== */
	        List<Settore> settori = turni.stream()
	                .map(Turno::getSett)
	                .distinct()
	                .toList();

	        if (settori.isEmpty()) {
	            throw new GaraEccezione("Nessun settore definito per la gara " + codiceGara, null);
	        }

	        /* ===============================
	           5️⃣ Assegnazione settori (round-robin)
	           =============================== */
	        Map<String, String> settorePerConcorrente = new HashMap<>();
	        int i = 0;

	        for (Concorrente c : presenti) {
	            settorePerConcorrente.put(
	                    c.getCf(),
	                    settori.get(i % settori.size()).getIdSettore()
	            );
	            i++;
	        }

	        /* ===============================
	           6️⃣ Creazione PARTECIPA
	           =============================== */
	        String lastId = getUltimoIdPunteggio(ctx);

	        for (Concorrente c : presenti) {

	            String settore = settorePerConcorrente.get(c.getCf());

	            for (Turno t : turni) {

	                if (!t.getSett().getIdSettore().equals(settore)) continue;

	                lastId = nextIdPunteggio(lastId);

	                ctx.insertInto(PARTECIPA)
	                        .set(PARTECIPA.IDPUNTEGGIO, lastId)
	                        .set(PARTECIPA.IDTURNO, t.getCodiceTurno())
	                        .set(PARTECIPA.CONCORRENTE, c.getCf())
	                        .set(PARTECIPA.NUMPUNTI, 0)
	                        .set(PARTECIPA.SQUALIFICA, (Boolean) null)
	                        .execute();
	            }
	        }

	    } catch (SQLException e) {
	        throw new GaraEccezione(
	                "Errore nella definizione dei gruppi per la gara " + codiceGara,
	                e
	        );
	    }
	}



}

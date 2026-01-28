package db.repository;

import static dbconSQLJOOQ.generated.Tables.ARBITRO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import model.Arbitro;

import db.SQLiteConnectionManager;
import db.exception.ArbitroEccezione;

public class ArbitroDAO {

	public ArbitroDAO() {}

	public void registraArbitro(String cf, String nome, String cognome, String sezione, String pwdChiara)
			throws ArbitroEccezione {
		String hash = DigestUtils.sha256Hex(pwdChiara);
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			ctx.insertInto(ARBITRO, ARBITRO.CF, ARBITRO.NOME, ARBITRO.COGNOME, ARBITRO.SEZIONE, ARBITRO.PASSWORD_HASH)
					.values(cf, nome, cognome, sezione, hash).execute();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new ArbitroEccezione("Errore nel registrare il nuovo arbitro!", e);
		}
	}

	public boolean esisteArbitro(String cf) throws ArbitroEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			return ctx.fetchExists(ctx.selectFrom(ARBITRO).where(ARBITRO.CF.eq(cf)));
		} catch(SQLException e) {
			e.printStackTrace();
			throw new ArbitroEccezione("Errore nel trovare l'arbitro!", e);
		}
	}

	public List<Arbitro> getArbitri() throws ArbitroEccezione {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {

			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);

			return ctx.select(ARBITRO.CF, ARBITRO.NOME, ARBITRO.COGNOME, ARBITRO.SEZIONE).from(ARBITRO)
					.orderBy(ARBITRO.CF).fetchInto(Arbitro.class);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ArbitroEccezione("Errore nel recuperare la lista degli arbitri!", e);
		}
	}

}

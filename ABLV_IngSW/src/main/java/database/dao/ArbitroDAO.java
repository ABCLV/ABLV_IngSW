package database.dao;

import static dbconSQLJOOQ.generated.Tables.ARBITRO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import database.SQLiteConnectionManager;
import applicazione.entita.Arbitro;

public final class ArbitroDAO {

	private ArbitroDAO() {
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

	public static boolean esisteArbitro(String cf) throws SQLException {
		try (Connection conn = SQLiteConnectionManager.getConnection()) {
			DSLContext ctx = DSL.using(conn, SQLDialect.SQLITE);
			return ctx.fetchExists(ctx.selectFrom(ARBITRO).where(ARBITRO.CF.eq(cf)));
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

}

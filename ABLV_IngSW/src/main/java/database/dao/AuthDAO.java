package database.dao;

import static dbconSQLJOOQ.generated.Tables.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import database.SQLiteConnectionManager;
import org.apache.commons.codec.digest.DigestUtils;

public final class AuthDAO {

	private AuthDAO() {
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

}
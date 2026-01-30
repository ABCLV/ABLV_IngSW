package db.repository;

import static dbconSQLJOOQ.generated.Tables.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.impl.DSL;
import org.apache.commons.codec.digest.DigestUtils;

import db.SQLiteConnectionManager;
import db.exception.AuthEccezione;

public class AuthDAO {

	public AuthDAO() {
	}
	//!!DEBITO TECNICO sostituire le stringhe con le enum!!
	public boolean checkPassword(String tipo, String id, String pwdChiara) throws AuthEccezione {
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
		} catch (DataAccessException e) {
			throw new AuthEccezione("Errore nell'autenticazione!", e);
		} catch (SQLException e) {
			throw new AuthEccezione("Errore nell'autenticazione!", e);
		}
	}

}
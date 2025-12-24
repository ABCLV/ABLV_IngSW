package database;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

public class GenerateJooqCode {

    public static void main(String[] args) throws Exception {

        String dbUrl = "jdbc:sqlite:database/gare.db"; // il tuo DB SQLite

        Configuration configuration = new Configuration()
            .withJdbc(new Jdbc()
                .withDriver("org.sqlite.JDBC")
                .withUrl(dbUrl))
            .withGenerator(new Generator()
                .withDatabase(new Database()
                    .withName("org.jooq.meta.sqlite.SQLiteDatabase")
                    .withIncludes(".*") // tutte le tabelle
                    .withExcludes("")) // nessuna esclusa
                .withTarget(new Target()
                    .withPackageName("dbconSQLJOOQ.generated") // pacchetto per le classi generate
                    .withDirectory("src/generated/"))); // dove salvare i file

        GenerationTool.generate(configuration);

        System.out.println("✅ Classi jOOQ generate in src/generated/dbconSQLJOOQ/generated");
    }
}
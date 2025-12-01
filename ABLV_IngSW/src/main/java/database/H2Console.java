package database;
import org.h2.tools.Server;


/**
 * H2 è un database relazionale scritto interamente in Java.
 * È molto leggero, veloce e semplice da usare, ed è spesso utilizzato nei progetti Java 
 * (soprattutto con Maven, Spring, jOOQ, Hibernate) per: sviluppo locale
 * 
 * Questa classe serve solo ad avviare la console web di H2, 
 * cioè l’interfaccia grafica accessibile dal browser.
 * dopo averlo lanciato andare su http://localhost:9092
 * nei campi inserire:
 * Nome configurazione: Generic H2
 * Class driver: org.h2.Driver
 * JDBC URL: jdbc:h2:./database/mydb
 */
public class H2Console {
    public static void main(String[] args) throws Exception {

        Server.createWebServer(
                "-webPort", "9092",
                "-baseDir", "./database",
                "-browser"
        ).start();

        System.out.println("Console H2 avviata su http://localhost:9092");
    }
}

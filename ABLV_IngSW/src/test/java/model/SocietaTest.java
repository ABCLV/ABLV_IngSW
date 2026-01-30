package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SocietaTest {

	private Societa societa;
	private static final String NOME_TEST = "Pesca Club Bergamo";
	private static final String EMAIL_TEST = "info@pescaclub.it";
	private static final String CAP_TEST = "24100";
	private static final String CITTA_TEST = "Bergamo";
	private static final String INDIRIZZO_TEST = "Via Milano 10";

	@BeforeEach
	void setUp() {
		societa = new Societa(NOME_TEST, EMAIL_TEST, CAP_TEST, CITTA_TEST, INDIRIZZO_TEST);
	}

	@Test
	@DisplayName("Costruttore inizializza tutti i campi correttamente")
	void testCostruttore() {
		assertAll("Verifica inizializzazione", () -> assertEquals(NOME_TEST, societa.getNome()),
				() -> assertEquals(EMAIL_TEST, societa.getEmail()), () -> assertEquals(CAP_TEST, societa.getCap()),
				() -> assertEquals(CITTA_TEST, societa.getCitta()),
				() -> assertEquals(INDIRIZZO_TEST, societa.getIndirizzo()));
	}

	@Test
	@DisplayName("Costruttore vuoto crea istanza valida")
	void testCostruttoreVuoto() {
		Societa s = new Societa();
		assertNotNull(s);
	}

	@Test
	@DisplayName("Validazione: nome vuoto o null lancia eccezione")
	void testNomeNonValido() {
		try {
			societa.setNome("");
			fail("Dovrebbe lanciare eccezione per nome vuoto");
		} catch (IllegalArgumentException e) {
			// OK
		}

		try {
			societa.setNome("   ");
			fail("Dovrebbe lanciare eccezione per nome blank");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	@Test
	@DisplayName("Validazione: email vuota lancia eccezione")
	void testEmailNonValida() {
		try {
			societa.setEmail("");
			fail("Dovrebbe lanciare eccezione per email vuota");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	@Test
	@DisplayName("Setter e getter modificano correttamente i valori")
	void testSetterGetter() {
		societa.setNome("Nuova Societa");
		assertEquals("Nuova Societa", societa.getNome());

		societa.setEmail("nuova@email.it");
		assertEquals("nuova@email.it", societa.getEmail());

		societa.setCap("20100");
		assertEquals("20100", societa.getCap());

		societa.setCitta("Milano");
		assertEquals("Milano", societa.getCitta());

		societa.setIndirizzo("Via Roma 1");
		assertEquals("Via Roma 1", societa.getIndirizzo());
	}

	@Test
	@DisplayName("SoggettoIF: getIdentificatore ritorna nome")
	void testGetIdentificatore() {
		assertEquals(NOME_TEST, societa.getIdentificatore());
	}

}
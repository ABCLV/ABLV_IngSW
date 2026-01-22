package applicazione.service;

import static org.junit.Assert.*;
import org.junit.Test;

public class AutenticazioneTest {

    @Test
    public void verificaRestituisceFalseConParametriNull() {
        // il metodo intercetta l'eccezione e ritorna false
        assertFalse(Autenticazione.verifica(null, null, null));
    }

    @Test
    public void verificaRestituisceFalseConPasswordVuota() {
        assertFalse(Autenticazione.verifica("admin", "user1", ""));
    }
}
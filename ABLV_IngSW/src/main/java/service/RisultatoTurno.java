package service;

public class RisultatoTurno {

    private final String idConcorrente;
    private final double punteggio;
    private final boolean squalificato;

    public RisultatoTurno(String idConcorrente, double punteggio, boolean squalificato) {
        this.idConcorrente = idConcorrente;
        this.punteggio = punteggio;
        this.squalificato = squalificato;
    }

    public String getIdConcorrente() {
        return idConcorrente;
    }

    public double getPunteggio() {
        return punteggio;
    }

    public boolean isSqualificato() {
        return squalificato;
    }
    
    @Override
    public String toString() {
        return String.format(
            "RisultatoTurno{cf='%s', punteggio=%.2f, squalificato=%s}",
            idConcorrente, punteggio, squalificato
        );
    }

}

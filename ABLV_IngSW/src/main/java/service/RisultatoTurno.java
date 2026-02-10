package service;

public class RisultatoTurno {

    private String idConcorrente;
    private double punteggio;
    private boolean squalificato;

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
    
    public void setSqualificato() {
    	this.squalificato = true;
    }
    
    public void azzeraPunti() {
    	this.punteggio = 0.0;
    }
    
    @Override
    public String toString() {
        return String.format(
            "RisultatoTurno{cf='%s', punteggio=%.2f, squalificato=%s}",
            idConcorrente, punteggio, squalificato
        );
    }

}

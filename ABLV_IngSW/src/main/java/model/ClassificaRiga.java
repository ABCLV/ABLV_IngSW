package model;

public class ClassificaRiga {

    private int posizione;
    private String cfConcorrente;
    private String societa;
    private int sponsor;
    private Double penalita;
    private Double piazzamento;
    private String piazzamentiTurni;

    public ClassificaRiga(int posizione,
                          String cfConcorrente,
                          String societa,
                          int sponsor,
                          Double penalita,
                          Double piazzamento,
                          String piazzamentiTurni) {
        this.posizione = posizione;
        this.cfConcorrente = cfConcorrente;
        this.societa = societa;
        this.sponsor = sponsor;
        this.penalita = penalita;
        this.piazzamento = piazzamento;
        this.piazzamentiTurni = piazzamentiTurni;
    }

    public int getPosizione() {
        return posizione;
    }

    public String getCfConcorrente() {
        return cfConcorrente;
    }

    public String getSocieta() {
        return societa;
    }

    public int getSponsor() {
        return sponsor;
    }

    public Double getPenalita() {
        return penalita;
    }

    public Double getPiazzamento() {
        return piazzamento;
    }

    public String getPiazzamentiTurni() {
        return piazzamentiTurni;
    }
}

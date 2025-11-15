package unibg.it.ABLV_IngSW.test;

import java.util.Date;

public class Gara {

	public String codiceGara;
	private int nProva;
	private String organizzatore;
	private Tecnica tipoTecnica;
	private String criterioPunti;
	private int maxPersone;
	private int minPersone;
	private StatoGara stato;
	private TipologiaGara tipoGara;
	private Date annoGara;
	
	public Gara(String codiceGara, int nProva, String organizzatore, Tecnica tipoTecnica, String criterioPunti,
			int maxPersone, int minPersone, StatoGara stato, TipologiaGara tipoGara, Date annoGara) {
		this.codiceGara = codiceGara;
		this.nProva = nProva;
		this.organizzatore = organizzatore;
		this.tipoTecnica = tipoTecnica;
		this.criterioPunti = criterioPunti;
		this.maxPersone = maxPersone;
		this.minPersone = minPersone;
		this.stato = stato;
		this.tipoGara = tipoGara;
		this.annoGara = annoGara;
	}
	
	public void notifica() {};
	
	public boolean iscrivi(String CF) {
		return false;
	};
	
	public void annullaGara() {};
	
	public void inviaClassifica() {};
	
	public void ciao() {};
	
}

package model;

/**
 * Rappresenta un campo gara (tratto di fiume/torrente) dove si svolgono le
 * competizioni.
 */
public class CampoGara {

	private int idCampoGara;
	private String corpoIdrico;
	private String paese;
	private int lunghezza;
	private String descrizione;

	/**
	 * Costruttore completo.
	 * 
	 * @param idCampoGara identificativo del campo gara
	 * @param corpoIdrico nome del fiume/torrente
	 * @param paese       comune o località
	 * @param lunghezza   lunghezza in metri
	 */
	public CampoGara(int idCampoGara, String paese, String corpoIdrico, int lunghezza, String descrizione) {
		this.idCampoGara = idCampoGara;
		this.corpoIdrico = corpoIdrico;
		this.paese = paese;
		this.lunghezza = lunghezza;
		this.descrizione = descrizione;
	}

	public CampoGara() {
	}

	public int getIdCampoGara() {
		return idCampoGara;
	}

	public void setIdCampoGara(int idCampoGara) {
		this.idCampoGara = idCampoGara;
	}

	public String getCorpoIdrico() {
		return corpoIdrico;
	}

	public void setCorpoIdrico(String corpoIdrico) {
		this.corpoIdrico = corpoIdrico;
	}

	public String getPaese() {
		return paese;
	}

	public void setPaese(String paese) {
		this.paese = paese;
	}

	public int getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(int lunghezza) {
		this.lunghezza = lunghezza;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return this.idCampoGara + ", " + this.corpoIdrico + ", " + this.paese + ", " + this.descrizione;
	}

}
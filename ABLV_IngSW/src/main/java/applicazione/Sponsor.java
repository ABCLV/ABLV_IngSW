package applicazione;

/**
 * Rappresenta uno sponsor delle gare di pesca.
 */
public class Sponsor {

	public String IdSponsor;
	private String nome;
	private String paginaWeb;

	/**
	 * Costruttore completo.
	 * 
	 * @param IdSponsor identificativo dello sponsor
	 * @param nome      nome dello sponsor
	 * @param paginaWeb URL della pagina web dello sponsor
	 */
	public Sponsor(String IdSponsor, String nome, String paginaWeb) {
		this.IdSponsor = IdSponsor;
		this.nome = nome;
		this.paginaWeb = paginaWeb;
	}
}
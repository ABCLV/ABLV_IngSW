package service;

import java.util.List;

import db.repository.CampoGaraDAO;
import db.repository.GaraDAO;
import db.repository.SettoreDAO;
import model.CampoGara;
import model.Settore;

public class SalvataggioTurniService {
	private GaraDAO cercaCodiceCampoGara = new GaraDAO();
	private CampoGaraDAO cercaCampoGara = new CampoGaraDAO();
	private SettoreDAO cercaSettori = new SettoreDAO();
	
	public void insertTurni(List<Integer> durate, String CodiceGara) {
		String codiceCampoGara = cercaCodiceCampoGara.trovaCodiceCampoGara(CodiceGara);
		CampoGara c = cercaCampoGara.trovaCampoGara(codiceCampoGara);
		System.out.println(c.toString());
		List<Settore> sett = cercaSettori.esploraSettori(c);
		
		System.out.println(sett.toString());
		
	}
	
	
	public SalvataggioTurniService() {}
	
}

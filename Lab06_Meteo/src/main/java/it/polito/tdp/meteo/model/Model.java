package it.polito.tdp.meteo.model;

import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	MeteoDAO dao;
	
	public Model() {
		 dao  = new MeteoDAO();
	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		
		/*int umiditàTorino=0;
		int umiditàMilano=0;
		int umiditàGenova=0;
		for(Rilevamento r: dao.getUmidita(mese)) {
			if(r.getLocalita().compareTo("Torino")==0)
				umiditàTorino+=r.getUmidita(); 
			
			if(r.getLocalita().compareTo("Milano")==0) {
				umiditàMilano+=r.getUmidita();
			}else {
				umiditàGenova+=r.getUmidita();
			}*/ 
		
		//}
			
		//return "Torino   "+umiditàTorino/30+"\n" + "Milano   "+umiditàMilano/30+ "\nGenova   "+umiditàGenova/30;
		
		return null;
	}
	
	public String getUmidita(int mese,String localita){
		return dao.getUmidita(mese,localita);
	}
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		return "TODO!";
	}
	
	//per calcolare la media dell'umidità
	//public String getAllRilevaventi(int mese,String localita) {
	//	return dao.getAllRilevamentiLocalitaMese(mese, localita);
	//}
	
}

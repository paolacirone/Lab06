package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	private List<Citta> leCitta;
	private List<Citta> best; // conterrà la soluzione migliore
	private int N = 30;

	MeteoDAO dao;

	public List<Citta> getLeCitta() {
		return leCitta;
	}

	public Model() {
		dao = new MeteoDAO();
		this.leCitta = dao.getAllCitta();
	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese, String localita) {
		return dao.getUmidita(mese, localita);
	}

	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {

		List<Citta> parziale = new ArrayList<>();
		//int livello = 0;
		// inizializzo best
		best = null;

		MeteoDAO d = new MeteoDAO();
		for (Citta c : leCitta) {

			c.setRilevamenti(d.getAllRilevamentiLocalitaMese(mese, c.getNome()));
			// mi salvo così tutti i rilevamneti per il mese interessato
		}

		ricorsiva(parziale, 0);

		return best;
	}

	public void ricorsiva(List<Citta> parziale, int livello) {

		// caso terminale quando si ha una soluzione lunga 15 giorni
		if (livello == NUMERO_GIORNI_TOTALI) {
			Double costo = calcolaCosto(parziale);
			// ci permettera di conoscere il costo della soluzione

			if (best == null || costo < calcolaCosto(best)) {
				// abbiamo una nuova soluzione migliore
				best = new ArrayList<>(parziale);
			}
		} else {

		for (Citta prova : leCitta) {
			if (aggiuntaValida(prova, parziale)) {
				parziale.add(prova);
				ricorsiva(parziale, livello + 1);
				parziale.remove(parziale.size() - 1);
			}
		}
		}

	}

	private Double calcolaCosto(List<Citta> parziale) {
		Double costo = 0.0;

		for (int giorno = 1; giorno <= this.NUMERO_GIORNI_TOTALI; giorno++) {
			// al costo aggiungo l'umidità della città
			Citta c = parziale.get(giorno - 1);
			double umid = c.getRilevamenti().get(giorno - 1).getUmidita();
			costo += umid;

		}

		for (int giorno = 2; giorno < this.NUMERO_GIORNI_TOTALI; giorno++) {
			if (!parziale.get(giorno - 1).equals(parziale.get(giorno - 2))) {
				// se le citta visitate nei giorni vicini cambiano allora bisogna aggiungere la
				// quantità
				costo += COST;
			}
		}
		return costo;
	}

	public boolean aggiuntaValida(Citta prova, List<Citta> parziale) {
		int conta = 0;
		for (Citta precedente : parziale) {
			if (precedente.equals(prova))
				conta++;
		}
		 if(conta >= this.NUMERO_GIORNI_CITTA_MAX)
		return false;

		if (parziale.size() == 0) {
			return true; // ogni primo elemento va bene
		}

	 /*	if (parziale.size() < this.NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
			return parziale.get(parziale.size() - 1).equals(prova);
		}

		if (parziale.get(parziale.size() - 1).equals(prova))
			return true;

		for (int i = 0; i < this.NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN - 1; i++) {
			if (!parziale.get(parziale.size() - (i + 1)).equals(parziale.get(parziale.size() - (i + 2)))) {
				return false;
			}
		} */
		
		if (parziale.size()==1 || parziale.size()==2) {
			//siamo al secondo o terzo giorno, non posso cambiare
			//quindi l'aggiunta è valida solo se la città di prova coincide con la sua precedente
			return parziale.get(parziale.size()-1).equals(prova); 
		}
		//nel caso generale, se ho già passato i controlli sopra, non c'è nulla che mi vieta di rimanere nella stessa città
		//quindi per i giorni successivi ai primi tre posso sempre rimanere
		if (parziale.get(parziale.size()-1).equals(prova))
			return true; 
		// se cambio città mi devo assicurare che nei tre giorni precedenti sono rimasto fermo 
		if (parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) 
		&& parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
			return true;
		return false;
	}
}

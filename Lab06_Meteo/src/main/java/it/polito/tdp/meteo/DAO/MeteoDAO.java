package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		return null;
	
	}
	
	
	public String getUmidita(int mese, String localita) {
		
		int mese1=mese-1; 
		int mese2=mese+1;
		final String sql;
		
		if(mese<8) {
		sql= "SELECT localita, AVG(Umidita) AS media FROM situazione WHERE Localita=? AND (DATA>20130?31 AND DATA<20130?01)";
		} else if( mese==9 || mese==10) {
			sql= "SELECT localita, AVG(Umidita) AS media FROM situazione WHERE Localita=? AND (DATA>20130?31 AND DATA<2013?01)";
		} 
		else 
			sql= "SELECT localita, AVG(Umidita) AS media FROM situazione WHERE Localita=? AND (DATA>2013?31 AND DATA<2013?01)";
		
		
		String risultato=null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			st.setInt(2, mese1);
			st.setInt(3, mese2);
		
			

			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				risultato = localita+" "+rs.getInt("media");
			}
			 return risultato;
		
			}  catch (SQLException e) {

				e.printStackTrace();
				throw new RuntimeException(e);
			}
	
	}


}

package br.com.jira;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TreatmentAssigne {
	
	static Logger logger = Logger.getLogger(ConfigJira_Login.class);
	

	
	@SuppressWarnings("unchecked")
	public static String getAssigns(String assigne_vivo) {
	
		
		
		String assigne_indra=null,assigne_default=null;
		ArrayList<String> all_assigne_indra = new ArrayList<String>();
		ArrayList<String> all_assigne_vivo = new ArrayList<String>();
		
		JSONObject jsonObject = Create_Load_Delete_Read.getJson("users_indra_vivo.json");
		
		all_assigne_indra= (JSONArray) jsonObject.get("indra");
		all_assigne_vivo=(JSONArray) jsonObject.get("vivo");
		assigne_default = (String) jsonObject.get("generico");
		for (int x = 0; x < all_assigne_vivo.size();x++)
		{
			if (assigne_vivo.equals(all_assigne_vivo.get(x)))
			{
				assigne_indra = all_assigne_indra.get(x);
				logger.info("user indra: "+assigne_indra);
				break;
			}
		}
		if (assigne_indra==null)
		{
			assigne_indra = assigne_default;
			logger.error("user vivo not found,used generic user: "+assigne_indra);
		}
		
		return assigne_indra;

	 }	

}

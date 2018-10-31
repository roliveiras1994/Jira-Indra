package br.com.jira;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TreatmentAssigne {

	static Logger logger = Logger.getLogger(ConfigJira_Login.class);
	static JSONObject jsonObject = Create_Load_Delete_Read.ReadJson("users_indra_vivo.json");

	@SuppressWarnings("unchecked")
	public static String getAssigns(String assigne_vivo) {

		String assigne_indra = null;
		ArrayList<String> all_assigne = new ArrayList<String>();
		ArrayList<String> assigne_leader = new ArrayList<String>();
		all_assigne = (JSONArray) jsonObject.get("vivo_indra");
		assigne_leader = (JSONArray) jsonObject.get("responsavel_projeto");
	
		if(assigne_vivo!=null) {
		for (int x = 0; x < all_assigne.size(); x++) {
			if (assigne_vivo.equals(all_assigne.get(x))) {
				assigne_indra = all_assigne.get(x+1);
				logger.info("user indra: " + assigne_indra);
				break;
			}
		}
		}
		if (assigne_indra == null) {
			String key = TreatmentData.getkey();
			for (int x = 0 ; x<assigne_leader.size();x++)
			{
				
				if (assigne_leader.get(x).equals(key.substring(0,key.indexOf("-"))))
				{
					assigne_indra = assigne_leader.get(x+1);
					break;
				}
			}
			logger.error("user vivo not found,used leader user: " + assigne_indra);
			}

		return assigne_indra;

	}
	
	@SuppressWarnings("unchecked")
	public static boolean check_assignee(String vivo_assignee)
	{
		boolean assignee=false;
		
		ArrayList<String> all_assigne = new ArrayList<String>();
		all_assigne = (JSONArray) jsonObject.get("vivo_indra");
		for (int x = 0 ; x<all_assigne.size();x=x+2)
		{
			if (vivo_assignee.equals(all_assigne.get(x)))
			{
				assignee = true;
			}
		}
		
		return assignee;
		
	}

}

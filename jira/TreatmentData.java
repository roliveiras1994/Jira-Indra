package br.com.jira;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TreatmentData {

		//Class to capture and deal with the fields of the jira vivo to jira indra. 
		 

	static ArrayList<String> children_keys = new ArrayList<String>();
	static JSONObject jsonObject = null;
	static JSONParser parser = new JSONParser();
	static String key = null;
	static String key_parent = null;
	public static String[] Treatment(String getpath) throws ParseException {
		
		

		String[] getdatas = new String[11];
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		
		for (int x=0; x<11;x++ )
		{
			getdatas[x]=null;
		}
		
		try {
			//read every content in  getpath json. 
			
			jsonObject = (JSONObject) parser.parse(new FileReader(getpath));
			
			//treatment of the json file fields vivo jira 

			JSONObject inside_fields = (JSONObject) jsonObject.get("fields");
			
			if (inside_fields!=null) {
				
			

			JSONObject fields_parent = (JSONObject) inside_fields.get("parent");

			
			
			JSONObject fabricas = (JSONObject) inside_fields.get("customfield_11102");
			
			String fabricas_value = null;
			
			if (fabricas != null) {
				
				
				fabricas_value = (String) fabricas.get("value");
				getdatas[7]=fabricas_value;
			}
			
			JSONObject tec_alocation =(JSONObject) inside_fields.get("customfield_10536");
			
			String alocation_value= null;
			if(tec_alocation !=null) {
				alocation_value = (String) tec_alocation.get("value");
				getdatas[6]=alocation_value;
			}


			if (fields_parent != null) {
				
				@SuppressWarnings("unused")
				String id_parent = null;
				key_parent = null;
				
				id_parent = (String) fields_parent.get("id");
				key_parent = (String) fields_parent.get("key");
				getdatas[10]=key_parent;
				
				
			}else
			{
				getdatas[10]="no";		
			}

			key = null;
			String summary2 = null;
			String description2 = null;
			String created = null;
			created = (String) inside_fields.get("created");
			
			//Format creation time from json to USA format
			SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date dateok=sdf.parse(created);
			
			description2 = (String) inside_fields.get("description");
			key = (String) jsonObject.get("key");
			summary2 = (String) inside_fields.get("summary");

			if (created!=null)
			{
				getdatas[9]= form.format(dateok);;
			}
			
			if (description2==null || description2.isEmpty()) {
				description2=summary2;
				getdatas[5] = summary2;
			} else {
				getdatas[5] = description2;
			}
			if (summary2==null||summary2.isEmpty()) {
				getdatas[4] = key+"-"+description2;
			} else {
				getdatas[4] = key+"-"+summary2;
			}
			if ((description2==null  && summary2==null) || 
					(description2.isEmpty()&&summary2.isEmpty())) {
				getdatas[4] = key;
				getdatas[5] = key;
			}

			String ini_date = (String) inside_fields.get("customfield_10818");

			if (ini_date != null) {
				getdatas[0] = "\"" + ini_date + "T12:00:00.000+0200\"";
			}

			String due_date = (String) inside_fields.get("duedate");

			if (due_date != null) {
				getdatas[1] = "\"" + due_date + "T12:00:00.000+0200\"";
			}

			Double storypoints = (Double) inside_fields.get("customfield_10106");

			if (storypoints != null) {
				storypoints = storypoints * 4;
				getdatas[2] = "\"" + storypoints.toString() + "\"";

			}

			Double e_storypoints = (Double) inside_fields.get("customfield_11110");

			if (e_storypoints != null) {
				e_storypoints = e_storypoints * 4;
				getdatas[3] = "\"" + e_storypoints.toString() + "\"";
			}
			
			
			
			

			System.out.println("\nKey:" + key + "\nDuedate:" + " " + due_date
					+ "\nData de Inicio " + ini_date + "\nstorypoints :" + storypoints + "\ne_storypoints:"
					+ e_storypoints +"\nTecnologia Alocacao: "+alocation_value+"\nFabricas: "+fabricas_value+"\n"
							+ "Data de criacao: "+getdatas[9]);
			}
			
			

		} catch (NullPointerException e) //capture the possible exception.
		{
			//exception handling. 
			e.printStackTrace();
		}
		// Treats exceptions that can be thrown in the process 
		catch (org.json.simple.parser.ParseException | IOException e) {
			e.printStackTrace();
		}
		return getdatas;
	}
	
	
	
	public static ArrayList<String> Children_key(String getpath) {
		
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		
		try {
		jsonObject = (JSONObject) parser.parse(new FileReader(getpath));
		//confirms if json contains subtasks 

		JSONObject inside_fields = (JSONObject) jsonObject.get("fields");
		if(inside_fields!=null)
		{
			children_keys = new ArrayList<String>();
		JSONArray subtasks = (JSONArray) inside_fields.get("subtasks");
		
		for (int i = 0; i < subtasks.size(); i++) {
			JSONObject obj1 = (JSONObject) subtasks.get(i);
			
			children_keys.add((String) obj1.get("key"));
			
		}
		}
		else
		{
			
			
			children_keys = new ArrayList<String>();
		}
		}
		
		catch (NullPointerException e) //capture the possible exception 
		{
			//exception handling 
			e.printStackTrace();
		}
		catch (org.json.simple.parser.ParseException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Todos os itens do array: "+children_keys);
		
		return children_keys;
	}
}
package br.com.jira;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TreatmentData {

	public static String[] Treatment(String getpath) {
		String [] dates = new String[4];
		JSONObject jsonObject = null;
		// Cria o parse de tratamento
		JSONParser parser = new JSONParser();
		// Variaveis que irao armazenar os dados do arquivo JSON
		@SuppressWarnings("unused")
		String id = null;
		@SuppressWarnings("unused")
		String key_filho = null;
		@SuppressWarnings("unused")
		String summary1 = null;

		try {

			// Salva no objeto JSONObject o que o parse tratou do arquivo
			jsonObject = (JSONObject) parser.parse(new FileReader(getpath));
			JSONObject tempJsonObj = (JSONObject) jsonObject.get("fields");

			JSONArray subtasks = (JSONArray) tempJsonObj.get("subtasks");

			// Salva nas variaveis os dados retirados do arquivo

			JSONObject fields_parent = (JSONObject) tempJsonObj.get("parent");
			if (fields_parent != null) 
			{
				id = (String) fields_parent.get("id");
				key_filho = (String) fields_parent.get("key");
			}
			
			JSONObject parent_fields = null;
			
			if (fields_parent != null) 
			{
				parent_fields = (JSONObject) fields_parent.get("fields");
			}

			if (parent_fields != null) {
				summary1 = (String) parent_fields.get("summary");

			}
			JSONObject inside_fields = (JSONObject) jsonObject.get("fields");

			//JSONObject fields_reporter = (JSONObject) tempJsonObj.get("reporter");

			//String created = (String) inside_fields.get("created");
			//String updated = (String) inside_fields.get("updated");
			String ini_date = (String) inside_fields.get("customfield_10818");
			dates[1]=ini_date;
			String due_date = (String) inside_fields.get("duedate");
			dates[2]=due_date;
			//String resolution_date = (String) inside_fields.get("resolutiondate");
			Double storypoints = (Double) inside_fields.get("customfield_10106");
			dates[3]=storypoints.toString();
			Double e_storypoints = (Double) inside_fields.get("customfield_11110");
			
			//String name = (String) fields_reporter.get("name");
			storypoints=storypoints*4;
			dates[3]=storypoints.toString();
			e_storypoints=e_storypoints*4;
			dates[4]=e_storypoints.toString();
			

			System.out.println("Duedate: "+due_date+"\n Data de Inicio "+ini_date+"\nstorypoints :"+storypoints+ "\ne_storypoints :"+e_storypoints);

			
			System.out.println();
			if (subtasks != null) {
				@SuppressWarnings("unchecked")
				Iterator<Object> iterator = subtasks.iterator();
				while (iterator.hasNext()) {
					System.out.println(iterator.next());
					// tempJsonObj = (JSONObject) jsonObject.get("fields");
					// subtasks = (JSONArray) tempJsonObj.get("subtasks");

				}
			}


		} catch (NullPointerException e) // CAPTURA DA POSSÍVEL exceção.
		{
			// TRATAMENTO DA exceção
			e.printStackTrace();
		}
		// Trata as exceptions que podem ser lançadas no decorrer do processo
		catch (org.json.simple.parser.ParseException | IOException e) {
			e.printStackTrace();
		}
		return dates;
	}
}

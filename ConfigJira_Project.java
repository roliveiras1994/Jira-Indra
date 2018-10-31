package br.com.jira;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConfigJira_Project {

	// reading all projects from json projects
	static Logger logger = Logger.getLogger(ConfigJira_Project.class);
	static String lastissue = null;
	static ArrayList<String> received = new ArrayList<String>();
	static JSONObject jsonObject = Create_Load_Delete_Read.ReadJson("projects.json");
	@SuppressWarnings("unchecked")
	static List<String> all_projects = (JSONArray) jsonObject.get("projetos_vivo_indra");
	
	public static ArrayList<String> project_vivo() {
		// reading all vivo projects from json projects
		ArrayList<String> project = new ArrayList<String>();

		
		for (int x = 0; x < all_projects.size(); x=x+3) {
			
			project.add(all_projects.get(x));
			project.add(all_projects.get(x+1));
			logger.info("projeto vivo: " + all_projects.get(x));
			logger.info("projeto vivo: " + all_projects.get(x+1));
			
		}

		return project;
	}

	public static ArrayList<String> project_indra() {
		// reading all indra projects from json projects
		ArrayList<String> project = new ArrayList<String>();

		for (int x = 2; x < all_projects.size(); x=x+3) {
			
			project.add(all_projects.get(x));
			logger.info("project_indra :" + all_projects.get(x));
		}
		return project;
	}

	@SuppressWarnings("unchecked")
	public static void putlastissue(String project_v) {
		// saving the last issue
		FileWriter writeFile = null;

		for (int x = 0; x < all_projects.size(); x++) {
			if (all_projects.get(x).equals(project_v)) {
				String issue = TreatmentData.getkey().substring(TreatmentData.getkey().indexOf("-") + 1);
				all_projects.set(x + 1, issue);
				jsonObject.put("projetos_vivo", all_projects);
				break;
			}
		}

		try {
			writeFile = new FileWriter("projects.json");
			writeFile.write(jsonObject.toJSONString());
			writeFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}

	}

	public static String getlastissue(String project) {
		// reading the last issue
		lastissue = null;
		String lastissue2 = null;
		int begin = 0;

		for (int x = 0; x < all_projects.size(); x++) {
			if (all_projects.get(x).equals(project)) {
				lastissue = (String) all_projects.get(x + 1);
				break;
			}
		}

		begin = Integer.parseInt(lastissue);
		begin = begin + 1;
		lastissue2 = String.valueOf(begin);
		return lastissue2;
	}

	public static String issuetype() {
		String issue_type = (String) jsonObject.get("issuetype");
		return issue_type;
	}

	public static String increment_issue(String datauser) {

		int issue;
		issue = Integer.parseInt(datauser);
		issue++;
		datauser = String.valueOf(issue);
		return datauser;
	}
	
	public static String getlastissue()
	{
		return lastissue;
	}

}
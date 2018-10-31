package br.com.jira;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConfigJira_Project {

	// reading all projects from json projects
	static Logger logger = Logger.getLogger(ConfigJira_Project.class);
	static String lastissue = null;
	static ArrayList<String> received = new ArrayList<String>();
	static JSONObject jsonObject = Create_Load_Delete_Read.getJson("projects.json");

	@SuppressWarnings("unchecked")
	public static ArrayList<String> project_vivo() {
		// reading all vivo projects from json projects
		ArrayList<String> project = new ArrayList<String>();
		project = (JSONArray) jsonObject.get("projetos_vivo");
		for (int x = 0; x < project.size(); x++) {
			logger.info("projeto vivo: " + project.get(x));
		}

		return project;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> project_indra() {
		// reading all indra projects from json projects
		ArrayList<String> project = new ArrayList<String>();

		project = (JSONArray) jsonObject.get("projetos_indra");

		for (int x = 0; x < project.size(); x++) {
			logger.info("project_indra :" + project.get(x));
		}
		return project;
	}

	@SuppressWarnings("unchecked")
	public static void putlastissue(String project_v) {
		// saving the last issue
		FileWriter writeFile = null;
		JSONArray projects_vivo = (JSONArray) jsonObject.get("projetos_vivo");

		for (int x = 0; x < projects_vivo.size(); x++) {
			if (projects_vivo.get(x).equals(project_v)) {
				String issue = TreatmentData.key.substring(TreatmentData.key.indexOf("-") + 1);
				projects_vivo.set(x + 1, issue);
				jsonObject.put("projetos_vivo", projects_vivo);
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
		JSONArray projects_vivo = (JSONArray) jsonObject.get("projetos_vivo");

		for (int x = 0; x < projects_vivo.size(); x++) {
			if (projects_vivo.get(x).equals(project)) {
				lastissue = (String) projects_vivo.get(x + 1);
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
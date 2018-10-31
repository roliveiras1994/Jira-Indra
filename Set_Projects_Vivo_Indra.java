package br.com.jira;

import java.util.ArrayList;

public class Set_Projects_Vivo_Indra {

	static ArrayList<String> project_indra = new ArrayList<String>();
	static ArrayList<String> project_vivo = new ArrayList<String>();
	static int set_project = 0;
	static boolean end = false;

	static public void Set_Projects_Vivo_Indra_all() {
		// getting all projects
		project_indra = ConfigJira_Project.project_indra();
		project_vivo = ConfigJira_Project.project_vivo();
		for (int x = 0; x < project_vivo.size(); x = x + 2) {
			// deleting all files before it starts the application
			ConfigJira_Login.delete_jsons(project_vivo.get(x));
		}

	}

	static public ArrayList<String> Set_Project() {
		// Define a project to search for indra issues
		ArrayList<String> project_return = new ArrayList<String>();

		if (set_project < project_vivo.size()) {
			project_return.add(project_vivo.get(set_project));
			project_return.add(project_vivo.get(set_project + 1));
			int begin = 0;
			begin = Integer.parseInt(project_return.get(1)) + 1;
			project_return.set(1, String.valueOf(begin));

			if (set_project > 0) {
				project_return.add(project_indra.get(set_project - 1));
			} else {
				project_return.add(project_indra.get(set_project));
			}
			project_return.add(ConfigJira_Project.issuetype());
		} else {
			end = true;
		}

		set_project = set_project + 2;

		return project_return;
	}

	public static boolean getend() {
		return end;
	}
}

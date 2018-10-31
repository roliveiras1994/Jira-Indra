package br.com.jira;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Api_Jira_Main {

	/*
	 * datauser[0] = log_vivo=""; datauser[1] = pass_vivo=""; datauser[2] =
	 * log_indra=""; datauser[3] = pass_indra="";
	 * 
	 * projectuser[0] = project_vivo=""; projectuser[1] = issue_vivo ="";
	 * projectuser[2] = project_indra=""; projectuser[3] = issuetype="ToDo";
	 * 
	 * 
	 */

	static String uploadpath_json = null;
	static String content_indra = null;
	static String[] datauser = new String[4];
	static String[] projectuser = new String[4];
	static String[] getdata = new String[10];
	static ArrayList<String> received = new ArrayList<String>();
	static ArrayList<String> received_vivo = new ArrayList<String>();
	static int error = 0;
	static Logger logger = Logger.getLogger(Api_Jira_Main.class);

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// getting the configs from json logins
		logger.info(
				"*********************************************Application Started*********************************************");

		// Starts the thread to prevent the application stops working
		Timer_Close time = new Timer_Close();
		Thread threadtime = new Thread(time);
		threadtime.start();

		received = ConfigJira_Login.Logins();
		for (int x = 0; x < received.size(); x++) {
			datauser[x] = received.get(x);
			logger.info("Users info: " + datauser[x]);
		}
		received = new ArrayList<String>();
		// getting the config from json project
		Set_Projects_Vivo_Indra.Set_Projects_Vivo_Indra_all();
		received = Set_Projects_Vivo_Indra.Set_Project();

		for (int x = 0; x < received.size(); x++) {
			projectuser[x] = received.get(x);
			logger.info("Users info: " + projectuser[x]);
		}

		while (true) {

			// Reading the Issue and saving in the Json file
			Create_Get_Everything_Load cgel = new Create_Get_Everything_Load(datauser[0], datauser[1], projectuser[0],
					projectuser[1]);

			if (cgel.getduplicated() == true) {
				projectuser[1] = (ConfigJira_Project.increment_issue(projectuser[1]));
				continue;
			}

			String path_task = cgel.getpath_json();

			if (path_task.contains("ERRORMSG")) {
				error++;
				if (error > 20) {
					// changing the project.

					Create_Load_Delete_Read.DeleteJsonFile(path_task);
					received = new ArrayList<String>();
					received = Set_Projects_Vivo_Indra.Set_Project();

					if (Set_Projects_Vivo_Indra.getend()== true) {
						logger.info("*********************************END OF PROJECTS*****************************");
						System.exit(0);
					}

					for (int x = 0; x < received.size(); x++) {
						projectuser[x] = received.get(x);
						logger.info("Users info: " + projectuser[x]);
					}
					error = 0;
					continue;
				}
				Create_Load_Delete_Read.DeleteJsonFile(path_task);
				projectuser[1] = (ConfigJira_Project.increment_issue(projectuser[1]));
				continue;
			}
			if (path_task.contains("OFFLINE")) {
				logger.info("Disconnected or Wrong User Information/Project \" + \"or check captcha on Website jira");
				Create_Load_Delete_Read.DeleteJsonFile(path_task);
				continue;
			}
			Verify_Authenticity va = new Verify_Authenticity();
			// Verifing if the issue is ours.
			getdata = va.Verifing_Indra_issue(path_task);

			switch (getdata[9]) {
			case "Atividade de Apoio":
			case "Desenvolvimento Fabrica":
			case "Tarefas Fabrica":
				getdata[9] = "1";
				break;
			default:
				getdata[9] = "2";
				break;
			}

			if (!getdata[7].equals("yes") || !getdata[9].equals("1") || getdata[2] == null) {
				Create_Load_Delete_Read.DeleteJsonFile(path_task);
				projectuser[1] = (ConfigJira_Project.increment_issue(projectuser[1]));

				continue;

			}

			// Creating the json file to upload on indra jira.

			Create_Load_Delete_Read new_json = new Create_Load_Delete_Read();
			uploadpath_json = new_json.Create_Json("uploaddata");

			// setting the uploaddata.json to create a new Subtask or Link
			ConfigJsonOutput conf = new ConfigJsonOutput();

			// Getting the datas to create a new subtask
			content_indra = conf.createsubtask(projectuser[2], projectuser[3], path_task, getdata);
			// loading the uploaddata with issue content create subtask
			new_json.Load_Json_File(uploadpath_json, content_indra);
			// creating a new issue
			CreateIssue ci = new CreateIssue();
			String created_issue = ci.Create_Issue(datauser[2], datauser[3], uploadpath_json,projectuser[0]);
			if (created_issue.contains("Error")) {
				logger.info("Wrong User Informations/Project or Captcha");
				Create_Load_Delete_Read.DeleteJsonFile(uploadpath_json);
				Create_Load_Delete_Read.DeleteJsonFile(path_task);
				continue;
			}
			
			try {
				Thread.sleep(500);
				Create_Load_Delete_Read.DeleteJsonFile(uploadpath_json);
				Create_Load_Delete_Read.DeleteJsonFile(path_task);
				Thread.sleep(500);
				projectuser[1] = (ConfigJira_Project.increment_issue(projectuser[1]));
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error(e);
				e.printStackTrace();
			}

		}
	}

}

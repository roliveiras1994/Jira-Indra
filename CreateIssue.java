package br.com.jira;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

public class CreateIssue {
	static String status = null;
	static Logger logger = Logger.getLogger(CreateIssue.class);

	public static String Create_Issue(String log_indra, String pass_indra, String path,String issue_number) {

		try {

			Path path_return = Paths.get(System.getProperty("java.io.tmpdir") + "status.txt");
			if(!Files.exists(path_return)) {
			Files.createFile(path_return);
			}

			Process p;
			// Create the issue using the data .json and save it on status.txt
			String command = "cmd /c curl -k -D- -u " + log_indra + ":" + pass_indra + " -X POST --data @" + path
					+ " -H \"Content-Type: application/json\" https://jira.indra.es/rest/api/latest/issue/>"
					+ path_return;
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			logger.info(command);

			// getting the result from status.txt
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("java.io.tmpdir") + "status.txt"));
			status = br.readLine();
			logger.info("status: " + status);
			br.close();
			File file = new File(System.getProperty("java.io.tmpdir") + "status.txt");
			file.delete();

		} catch (IOException | InterruptedException ex) {
			logger.error(ex);
			ex.getStackTrace();

		}

		String retorno = null;
		if (status.contains("HTTP/1.1 201")) {
			ConfigJira_Project.putlastissue(issue_number);
			logger.info("Last Issue Saved!");
			retorno = "ok";

		} else {

			retorno = "Error";
			logger.error("Failed to create subtask");

		}

		return retorno;

	}

}

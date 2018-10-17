package br.com.jira;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetEverything {
	public static String[] Get_Everything(String login_v, String password_v, String project_v, String issue_v,String get_path) {
			StringBuilder cmdread= new StringBuilder();
			String[] cmdreturn = new String[3];
		try {
			
			String command;
			Process p;
			// it executes a command on cmd to get a issue from jira.
			command="curl -k -D- -u " + login_v + ":" + password_v
					+ " -X GET -H \"Content-Type: application/json\" http://10.240.39.83:8080/rest/api/latest/issue/"
					+ project_v + "-" + issue_v;
			p = Runtime.getRuntime().exec(command);
			// showing the command
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			
			while (stdInput.readLine() != null) {
				//loading the cmdread with the return content
				cmdread.append(stdInput.readLine());
		
			}
			//showing the content on cmdread
			System.out.println(cmdread.toString());
			
			
			
			

		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		//normal issues 
		if (cmdread.toString().contains("{\"expand")) {
		cmdreturn [0]= cmdread.toString().substring(cmdread.indexOf("{\"expand"));
		cmdreturn[1] = project_v+issue_v;
		}else
		{//invalid or not authorized issues
			cmdreturn[0] = cmdread.toString().substring(cmdread.indexOf("{\"errorMessages"));
			cmdreturn[1] = project_v+issue_v+"ERRORMSG";
			
		}
	return cmdreturn;
			}
}

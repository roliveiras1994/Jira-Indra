package br.com.jira;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConfigJira_Login {
	
	//method that reads the json file containing the login information for the application to work
	static Logger logger = Logger.getLogger(ConfigJira_Login.class);

	
	 public static ArrayList<String> Logins()
	 {
		 //reading the logins from json logins
		 JSONArray logins = null;
		 String[]content_vivo = new String[2];
		 ArrayList<String>log_pass_vivo = new ArrayList<String>();
		 JSONObject jsonObject = null;
		 Logger logger = Logger.getLogger(ConfigJira_Login.class);
     
	 jsonObject = Create_Load_Delete_Read.getJson("logins.json");
	 JSONObject logins_vivo = (JSONObject) jsonObject.get("logins_vivo");
	 logins = (JSONArray) logins_vivo.get("logins");
	 JSONArray login_generic = (JSONArray) jsonObject.get("login_generico");
	 for (int x = 0; x<logins.size();x=x+2)
	 { 
		 //testing the logins.
		 content_vivo = GetEverything.Get_Everything((String)logins.get(x),(String)logins.get(x+1),"TRA3513","1");
		 if (content_vivo[1].contains("OFFLINE"))
		 {	
			 logger.error("Wrong login or password "+logins.get(x));
			 continue;
		 }
		 else
		 {
			 logger.info("Access allowed");
		 }
		 
		 log_pass_vivo.add((String)logins.get(x));
		 log_pass_vivo.add((String)logins.get(x+1));
		 break;
	 }
	log_pass_vivo.add((String)login_generic.get(0));
	log_pass_vivo.add((String)login_generic.get(1));
     
     return log_pass_vivo;
	 }
	 
	 

	 
	 
	 public static void delete_jsons(final String project)
	 {
		 //delete all files before start the application
		 
		 File diretorio = new File(System.getProperty("java.io.tmpdir"));
			
		 File[] listFiles = diretorio.listFiles(new FileFilter() {
		 	public boolean accept(File pathname) {
		 		boolean return_ = false;
		 		if(pathname.getName().startsWith(project))
		 		{
		 		return_ = pathname.getName().startsWith(project);
		 		}
		 		if(pathname.getName().startsWith("uploaddata"))
		 		{
		 		return_ = pathname.getName().startsWith("uploaddata");
		 		}
		 		
		 		return 	return_; 	}
		 });

		 for(File f : listFiles) {
		 	System.out.println(f.getName());
		 	Create_Load_Delete_Read.DeleteJsonFile(System.getProperty("java.io.tmpdir")+f.getName());
		 	
		 }
		 
	 }
	 
	 	 
}

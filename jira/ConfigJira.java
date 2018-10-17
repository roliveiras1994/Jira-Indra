package br.com.jira;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigJira {

	public static String log_vivo="";
	public static String pass_vivo="";
	public static String project_vivo="";
	public static String issue_vivo ="";
	public static String log_indra="";
	public static String pass_indra="";
	public static String project_indra="";
	//method that reads the properties file containing the information for the application to work
	 public static Properties getProp() throws IOException {
	        Properties props = new Properties();
	        FileInputStream jira = new FileInputStream(
	                "jira.properties");
	        props.load(jira);
	        return props;
	 }
	 public static String[] DataConfig()
	 {
		 String [] datauser = new String[8];
		 
     Properties prop;
	try {
			 prop = getProp();
		     datauser[0] = prop.getProperty("config.jira.log_vivo");
		     datauser[1] = prop.getProperty("config.jira.pass_vivo");
		     datauser[2] = prop.getProperty("config.jira.project_vivo");
		     datauser[3] = prop.getProperty("config.jira.issue_vivo");
		     datauser[4] = prop.getProperty("config.jira.log_indra");
		     datauser[5] = prop.getProperty("config.jira.pass_indra");
		     datauser[6] = prop.getProperty("config.jira.project_indra");
		     datauser[7] = prop.getProperty("config.jira.issuetype");
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
  
     
   
      
     return datauser;
	 }
	 
	 	 
}

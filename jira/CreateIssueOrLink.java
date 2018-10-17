package br.com.jira;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateIssueOrLink {

	
	public static String Create_Issue(String log_indra, String pass_indra,String path){
		StringBuilder cmdread= new StringBuilder();
		int begin;
		int finish;
		@SuppressWarnings("unused")
		String line="";
		int cont=0;
	
	  try {
		  
		  
         
          Process p;                
          //Create the issue using the data .json
          p = Runtime.getRuntime().exec("curl -k -D- -u "+log_indra+":"+pass_indra+" -X POST --data @"+path+" -H \"Content-Type: application/json\" https://jira.indra.es/rest/api/latest/issue/");
 
          //getting the return from cmd
          BufferedReader stdInput = new BufferedReader(new 
                   InputStreamReader(p.getInputStream()));
          
          
       
      
			while (cont!=2)  {
				if (stdInput.readLine()!=null)
				{
				line = stdInput.readLine();
				}
				else {
					cont++;
				}
				//loading the cmdread with the return content
				cmdread.append(stdInput.readLine());
		
			}
			//showing the content on cmdread
			System.out.println(cmdread.toString());
			
      
      } catch (IOException ex) {
          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
      }
	  begin = cmdread.indexOf("\"key\":\"");
	  finish = cmdread.indexOf("\",\"self\"");
	  System.out.println();
	  return cmdread.toString().substring(begin+7,finish);
	

}
	
	public static void Create_Link(String log_indra, String pass_indra,String path){
		
		  try {
			  
			  
	          String line;
	          Process p;                
	          //Create the link using the data .json
	          p = Runtime.getRuntime().exec("curl -k -D- -u "+log_indra+":"+pass_indra+" -X POST --data @"+path+" -H \"Content-Type: application/json\" https://jira.indra.es/rest/api/latest/issueLink");
	 
	          //getting the return from cmd
	          BufferedReader stdInput = new BufferedReader(new 
	                   InputStreamReader(p.getInputStream()));
	          //showing it on system out
	          while ((line = stdInput.readLine()) != null) {
	                  System.out.println(line);
	          }
	      
	      } catch (IOException ex) {
	          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	      }
		
		

	}
}


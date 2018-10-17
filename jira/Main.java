package br.com.jira;

import java.text.ParseException;
import java.util.ArrayList;

public class Main {
	
   /*datauser[0] = log_vivo="";
	 datauser[1] = pass_vivo="";
	 datauser[2] = project_vivo="";
	 datauser[3] = issue_vivo ="";
	 datauser[4] = log_indra="";
	 datauser[5] = pass_indra="";
	 datauser[6] = project_indra="";
	 datauser[7] = issuetype="ToDo";
	 */
	 
	 static String uploadpath_json = null;
	 static String content_indra = null;
	 static String[] datauser = new String[8];
	 static String[] getdata = new String[11];
	 static int issue=0,error=0;
	 static String task_key=null;
	 static ArrayList<String> key_child = new ArrayList<String>();
	 
		@SuppressWarnings("static-access")
		public static void main(String[] args) throws ParseException, InterruptedException {
			//getting the configs from jira.properties
		 datauser=Create_Get_Everything_Load.getuser_datas();
	// showing the configs
	    for (int x = 0 ; x<8 ; x++) {
	        System.out.println(datauser[x]);
	         }
	    
	    
	    
	
	    while(true)
	    {
    	
    	// Reading the Issue and saving in the Json file
    	Create_Get_Everything_Load cgel = new Create_Get_Everything_Load(datauser);

    	// Verifing if the issue is ours.
    	String path_task = cgel.getpath_json;
    	if (path_task.contains("ERRORMSG"))
		{
			error++;
			if (error>100)
			{// sleep de 4 horas: 14400000
				Thread.sleep(14400000);
				issue=issue-110;
				datauser[3]= String.valueOf(issue);
				error=0;
				continue;
			}
			Create_Load_Json_Delete.DeleteJsonFile(path_task);
			issue = Integer.parseInt(datauser[3]);
	    	issue++;
	    	datauser[3]= String.valueOf(issue);
			continue;
		}
    	Verify_Authenticity va = new Verify_Authenticity();
    
    	getdata=va.Verifing_Indra_issue(path_task);
    	//if the getdata[8] is "1" then it means that this task has a indra subtask.
		if (((!getdata[6].equals("yes")&&(!getdata[7].equals("yes")))&& !getdata[8].equals("1")) || (!getdata[9].equals("yes")) ||!getdata[10].equals("no")) 
		{
			Create_Load_Json_Delete.DeleteJsonFile(path_task);
			issue = Integer.parseInt(datauser[3]);
	    	issue++;
	    	datauser[3]= String.valueOf(issue);
	    	
			continue;

		}
	
		System.out.println("é da indra");
		//Creating the json file to upload on indra jira.
		
		Create_Load_Json_Delete new_json = new Create_Load_Json_Delete();
    	uploadpath_json = new_json.Create_Json("uploaddata");
    	
    	
    	
    	//setting the uploaddata.json to create a new Subtask or Link
    	ConfigJsonOutput conf = new ConfigJsonOutput();
    	
    	//Getting the datas to create a new subtask
    	content_indra= conf.createsubtask(datauser[6], datauser[7], datauser[4], path_task, getdata);
    	// loading the uploaddata with issue content create subtask
    	new_json.Load_Json_File(uploadpath_json,content_indra);
    	//creating a new issue
    	CreateIssueOrLink ci = new CreateIssueOrLink();
    	String parent;
    	key_child=va.key_child;
    	parent=ci.Create_Issue(datauser[4], datauser[5], uploadpath_json);
    	
    	Create_Load_Json_Delete.DeleteJsonFile(uploadpath_json);
    	Create_Load_Json_Delete.DeleteJsonFile(path_task);
    	
    	
    	
    	//loading the uploaddata with issue content createlink
    	if(key_child.size()>0) {
    	for(int x = 0;x<key_child.size();x++)
    	{
    		System.out.println("/n Pai: "+parent);
    		System.out.println("/n Filho: "+key_child.get(x));
    		//creating a new issue link
	    	content_indra= conf.createlink(parent, key_child.get(x));
	    	new_json.Load_Json_File(uploadpath_json, content_indra);
	    	ci.Create_Link(datauser[4], datauser[5], uploadpath_json);
    	}
    		Create_Load_Json_Delete.DeleteJsonFile(uploadpath_json);
    	}
    	else
    	{
    		Create_Load_Json_Delete.Load_Json_File("parents_key_children.json",Load_Json_Parents_Keys.Parent_keys_child(key_child,TreatmentData.key,"parents_key_children.json"));
    	}
    	
    		key_child = new ArrayList<String>();
	    	issue = Integer.parseInt(datauser[3]);
	    	issue++;
	    	datauser[3]= String.valueOf(issue);
       
	    }
    }
   
}





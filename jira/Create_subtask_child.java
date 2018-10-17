package br.com.jira;

import java.util.ArrayList;

public class Create_subtask_child {
	//creates the substask in Indra's Jira
	static String path,content_indra,uploadpath_json;
	static ArrayList<String> key_child = new ArrayList<String>();
	@SuppressWarnings("static-access")
	public static void child_key(String []datauser, String[] get_treatment_child){
		ConfigJsonOutput conf = new ConfigJsonOutput();
		content_indra= conf.createsubtask(datauser[6], datauser[7], datauser[4], path, get_treatment_child);
		Create_Load_Json_Delete new_json = new Create_Load_Json_Delete();
		uploadpath_json = new_json.Create_Json("uploaddata");
		new_json.Load_Json_File(uploadpath_json,content_indra);
		CreateIssueOrLink ci = new CreateIssueOrLink();
		Children_Treatment.key_child.add(ci.Create_Issue(datauser[4], datauser[5], uploadpath_json));
		Create_Load_Json_Delete.DeleteJsonFile(uploadpath_json);
			
	}

}

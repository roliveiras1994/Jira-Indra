package br.com.jira;

import java.text.ParseException;
import java.util.ArrayList;

public class Children_Treatment {
	static String [] get_treatment_child = new String[10];
	static String task_indra,path,content_indra,uploadpath_json;
	static ArrayList<String> key_child_created = new ArrayList<String>();
	static String time;
	static ArrayList<String> key_child = new ArrayList<String>();
	public static ArrayList<String> Children_Treatment_(String datauser[], ArrayList<String> children_key) throws ParseException
	{	
		key_child_created = new ArrayList<String>();
		key_child = new ArrayList<String>();
		//Scanning for all subtasks to see if there is any subtask from indra.
	for (int x = 0; x<children_key.size(); x++)
	{
			//datauser[3] = issue_v...
		datauser[3] = (children_key.get(x).toString().substring(children_key.get(x).indexOf("-")+1));
		System.out.println(datauser[3]);
		@SuppressWarnings("unused")
		Create_Get_Everything_Load cgel = new Create_Get_Everything_Load(datauser);
		path = Create_Get_Everything_Load.getpath_json;
		//Getting all the fields from subtask.
		get_treatment_child = TreatmentData.Treatment(Create_Get_Everything_Load.getpath_json);
		
		if (get_treatment_child[6]!=null&& get_treatment_child[7]!=null)
		{
	
		if (get_treatment_child[6].equals("OSS Inventario")|| (get_treatment_child[7].equals("Indra OSS"))) 
		{
			System.out.println("é da indra !!");
			time = Get_Date_Hour_Compare.getDateTime(get_treatment_child[9]);
			if (time=="1") {
			Create_subtask_child.child_key(datauser, get_treatment_child);
			key_child_created.add(TreatmentData.key);
			}
		}
		}
		Create_Load_Json_Delete.DeleteJsonFile(Create_Get_Everything_Load.getpath_json);
		}
	if (key_child_created!=null && !key_child_created.isEmpty())
	{
		Create_Load_Json_Delete.Load_Json_File("parents_key_children.json",Load_Json_Parents_Keys.Parent_keys_child(key_child_created,TreatmentData.key_parent,"parents_key_children.json"));
    	
	}
	
		
	return key_child;
	}
	
	}


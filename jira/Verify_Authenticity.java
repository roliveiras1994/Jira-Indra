package br.com.jira;

import java.text.ParseException;
import java.util.ArrayList;

public class Verify_Authenticity {
	/*
	 * Position [6] = alocation_value Position [7] = fabricas_value
	 */
	String[] get_treatment_data = new String[11];
	String[] datauser = new String[8];
	ArrayList<String> children_key = new ArrayList<String>();
	static ArrayList<String> key_child = new ArrayList<String>();

	public String[] Verifing_Indra_issue(String getpath_json) throws ParseException {
		key_child = new ArrayList<String>();
		// getting the pass and log from both jiras.
		datauser = Create_Get_Everything_Load.getuser_datas();
		
		// getting the treated data.
		get_treatment_data = TreatmentData.Treatment(getpath_json);
		
		// checking if is there any subtasks.
		children_key = TreatmentData.Children_key(getpath_json);

		 
		if (get_treatment_data[6] != null) {
			if (get_treatment_data[6].equals("OSS Inventario")) {
				System.out.println("\nOSS Inventario = True");
				get_treatment_data[6] = "yes";
			} else {
				System.out.println("\nOSS Inventario = False");
				get_treatment_data[6] = "no";
				
			}
		} else {
			System.out.println("\nOSS Inventario = False");
			get_treatment_data[6] = "no";
		}
		if (get_treatment_data[7] != null) {
			if (get_treatment_data[7].equals("Indra OSS")) {
				System.out.println("\nIndra OSS = True\n");
				get_treatment_data[7] = "yes";
			} else {
				System.out.println("\nIndra OSS = False\n");
				get_treatment_data[7] = "no";
			}
		} else {
			System.out.println("\nIndra OSS = False");
			get_treatment_data[7] = "no";
		}
		if (get_treatment_data[6].equals("no") &&get_treatment_data[7].equals("no"))
		{
			get_treatment_data[8]="2";
		}
		else {
			get_treatment_data[8]="1";
		}
		
		String time = Get_Date_Hour_Compare.getDateTime(get_treatment_data[9]);
		if (time=="1") {
			get_treatment_data[9] = "yes";
		}
		else {
			get_treatment_data[9] = "no";
			get_treatment_data[6] = "no";
			get_treatment_data[7] = "no";
			get_treatment_data[8] = "2";
		}
			

		if (children_key != null && !children_key.isEmpty()) {
			@SuppressWarnings("unused")
			Children_Treatment ct = new Children_Treatment();
			//if this Issue has a subtask then do this.
			
			key_child=Children_Treatment.Children_Treatment_(datauser, children_key);
			
			if (key_child.size()>0)
			{
				get_treatment_data[8]="1";
			}
			
		}
	

		return get_treatment_data;
	}

}

package br.com.jira;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//Class to capture and deal with the fields of the jira vivo to jira indra.
public class TreatmentData {

	static Logger logger = Logger.getLogger(TreatmentData.class);
	static String key = null;
	private static Double storypoints = null;
	private static Double e_storypoints = null;

	public static String[] Treatment(String getpath) {

		String[] getdatas = new String[10];
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();

		for (int x = 0; x < 10; x++) {
			getdatas[x] = null;
		}

		try {

			// read every content in getpath json.
			jsonObject = (JSONObject) parser.parse(new FileReader(getpath));

			// treatment of the json file fields vivo jira

			JSONObject inside_fields = (JSONObject) jsonObject.get("fields");
			JSONObject issuetype = (JSONObject) inside_fields.get("issuetype");

			if (inside_fields != null) {

				JSONObject fabricas = (JSONObject) inside_fields.get("customfield_11102");

				String fabricas_value = null;

				String name_issuetype = null;

				if (issuetype != null) {
					name_issuetype = (String) issuetype.get("name");
					getdatas[9] = name_issuetype;

				}

				if (fabricas != null) {

					fabricas_value = (String) fabricas.get("value");
					getdatas[7] = fabricas_value;
				}

				JSONObject tec_alocation = (JSONObject) inside_fields.get("customfield_10536");

				String alocation_value = null;
				if (tec_alocation != null) {
					alocation_value = (String) tec_alocation.get("value");
					getdatas[6] = alocation_value;
				}

				ArrayList<String> fields_test = new ArrayList<String>();
				fields_test.add("assignee");
				fields_test.add("reporter");
				fields_test.add("customfield_10204");
				fields_test.add("customfield_10207");

				for (int x = 0; x < fields_test.size(); x++) {
					JSONObject assignee = (JSONObject) inside_fields.get(fields_test.get(x));
					String assignee_value = null;
					if (assignee != null) {
						assignee_value = (String) assignee.get("key");

						if (assignee_value.startsWith("80")) {
							getdatas[8] = assignee_value;
							break;
						}
					}
				}

				key = null;
				String summary2 = null;
				String description2 = null;

				description2 = (String) inside_fields.get("description");
				key = (String) jsonObject.get("key");
				summary2 = (String) inside_fields.get("summary");

				if (description2 == null || description2.isEmpty()) {
					description2 = summary2;
					getdatas[5] = summary2;
					getdatas[5] = getdatas[5].replace("\t", " ");
				} else {
					getdatas[5] = description2;
					getdatas[5] = getdatas[5].replace("\t", " ");
				}
				if (summary2 == null || summary2.isEmpty()) {
					getdatas[4] = key + "-" + description2;
					getdatas[4] = getdatas[4].replace("\t", " ");
				} else {
					getdatas[4] = key + "-" + summary2;
					getdatas[4] = getdatas[4].replace("\t", " ");
				}
				if ((description2 == null && summary2 == null) || (description2.isEmpty() && summary2.isEmpty())) {
					getdatas[4] = key;
					getdatas[5] = key;
					getdatas[4] = getdatas[4].replace("\t", " ");
					getdatas[5] = getdatas[5].replace("\t", " ");
				}

				String ini_date = (String) inside_fields.get("customfield_10818");

				if (ini_date != null) {
					getdatas[0] = "\"" + ini_date + "T12:00:00.000+0200\"";
				}

				String due_date = (String) inside_fields.get("duedate");

				if (due_date != null) {
					getdatas[1] = "\"" + due_date + "T12:00:00.000+0200\"";
				}

				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error(e);
					e.printStackTrace();
				}

				storypoints = (Double) inside_fields.get("customfield_10106");
				e_storypoints = (Double) inside_fields.get("customfield_11110");
				if (storypoints != null || e_storypoints != null) {

					if (storypoints != null) {
						storypoints = storypoints * 4;
						getdatas[2] = storypoints.toString();
					} else {
						storypoints = e_storypoints * 4;
						getdatas[2] = storypoints.toString();
					}

					if (e_storypoints != null) {
						e_storypoints = e_storypoints * 4;
						getdatas[3] = e_storypoints.toString();
					} else {
						e_storypoints = storypoints;
						getdatas[3] = e_storypoints.toString();
					}
				}
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					logger.error(e);
					e.printStackTrace();
				}

				System.out.println("\nKey:" + key + "\nDuedate:" + " " + due_date + "\nData de Inicio " + ini_date
						+ "\nstorypoints :" + storypoints + "\ne_storypoints:" + e_storypoints
						+ "\nTecnologia Alocacao: " + alocation_value + "\nFabricas: " + fabricas_value + "\n"
						+ "Reporter or Assigne: " + getdatas[8] + "\nType: " + getdatas[9]);
				logger.info("key: " + key);
			}

		} catch (NullPointerException e) // capture the possible exception.
		{
			// exception handling.
			logger.error(e);
			e.printStackTrace();
		}
		// Treats exceptions that can be thrown in the process
		catch (org.json.simple.parser.ParseException | IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return getdatas;
	}

}
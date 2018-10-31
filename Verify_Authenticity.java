package br.com.jira;

import org.apache.log4j.Logger;

public class Verify_Authenticity {

	static Logger logger = Logger.getLogger(Verify_Authenticity.class);

	/*
	 * Position [6] = alocation_value Position [7] = fabricas_value Position [8] =
	 * Assigne
	 */
	String[] get_treatment_data = new String[10];
	static String created = null;

	public String[] Verifing_Indra_issue(String getpath_json) {

		// getting the treated data.
		get_treatment_data = TreatmentData.Treatment(getpath_json);

		if (get_treatment_data[7] != null) {
			if (get_treatment_data[7].equals("Indra OSS")) {
				logger.info("\nIndra OSS = True\n");
				get_treatment_data[7] = "yes";
				get_treatment_data[8] = TreatmentAssigne.getAssigns(get_treatment_data[8]);
			} else {
				logger.info("\nIndra OSS = False\n");
				get_treatment_data[7] = "no";
			}
		} else {
			logger.info("\nIndra OSS = False");
			get_treatment_data[7] = "no";
		}

		return get_treatment_data;
	}

}

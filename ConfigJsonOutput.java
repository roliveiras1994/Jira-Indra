package br.com.jira;

public class ConfigJsonOutput {
	static String[] getdatas = new String[6];

	public String createsubtask(String project_indra, String issuetype, String getpath_json, String getdatas[]) {
		// subtasks treated fields

		String content = "{\"fields\":{\"project\":{\"key\": \"BLOQUEV\"},\"parent\":" + "{\"key\": \"" + project_indra
				+ "\"},\"summary\": \"" + getdatas[4] + "\",\"description\":\"" + getdatas[5] + "\",\"issuetype"
				+ "\":{\"name\":\"ToDo\"},\"assignee\":{\"name\": \"" + getdatas[8] + "\"},\"customfield_16600\":" + ""
				+ getdatas[1] + ",\"customfield_10313\": " + getdatas[1] + ",\"customfield_11801\": " + getdatas[0]
				+ "," + " \"timetracking\":{\"originalEstimate\":\"" + getdatas[2] + "\",\"remainingEstimate\":\""
				+ getdatas[2] + "\"}}}";
		System.out.println("\n" + content);

		return content;
	}

}

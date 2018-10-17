package br.com.jira;

public class ConfigJsonOutput {
	 static String [] getdatas = new String[6];
	public String createtask(String project_indra, String issuetype){
		//captured issues needed fields, for indra jira
		String content="{\"fields\":{\"project\":{\"key\": \"BLOQUEV\"},\"summary\": "
				+ "\"Teste Automacao Jira.\",\"description\":\"Automacao Indra Jira\",\"issuetype"
				+ "\":{\"name\":\""+issuetype+"\"}}}";
		return content;
	}
	
	public String createsubtask(String project_indra, String issuetype, String log_indra,String getpath_json, String getdatas[]){
		//subtasks treated fields 
    
		String content="{\"fields\":{\"project\":{\"key\": \"BLOQUEV\"},\"parent\":"
				+ "{\"key\": \""+project_indra+"\"},\"summary\": \""+getdatas[4]+"\",\"description\":\""+getdatas[5]+"\",\"issuetype"
				+ "\":{\"name\":\"ToDo\"},\"assignee\":{\"name\": \""+log_indra+"\"},\"customfield_16600\":"
				+ ""+getdatas[1]+",\"customfield_10313\": "+getdatas[1]+",\"customfield_11801\": "+getdatas[0]+","
				+ " \"timetracking\":{\"originalEstimate\":"+getdatas[2]+",\"remainingEstimate\": "+getdatas[2]+"}}}";
		System.out.println("\n"+content);
		return content;
	}
	
	public String createlink(String parent, String child){
		//links to subtask as a daughter of the main issue. 
		String content="{\"type\": { \"name\": \"Child-Issue\"},\"inwardIssue\": {\"key\": \""
		+parent+"\"},\"outwardIssue\": { \"key\": \""+child+"\"}}";
		System.out.println("\n"+content);
		return content;
	}
}

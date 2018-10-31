package br.com.jira;

public class Create_Get_Everything_Load {
	static String getpath_json = null;
	static String[] content_vivo = new String[2];
	static boolean duplicated = false;
		
	public Create_Get_Everything_Load(String login_v,String password_v, String project_v, String issue_v)
	
	{
		
	// getting the content issue
		content_vivo = GetEverything.Get_Everything(login_v,password_v,project_v,issue_v);
		ConfigJira_Project.getlastissue(project_v);

if (ConfigJira_Project.lastissue!=issue_v)
{

// getting the path from file created "getdata.json"
getpath_json = Create_Load_Delete_Read.Create_Json(content_vivo[1]+"-");
// loading the getdata with issue content
Create_Load_Delete_Read.Load_Json_File(getpath_json,content_vivo[0]);
}
else
{
		duplicated=true;
}
	}
	

}

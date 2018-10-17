package br.com.jira;

public class Create_Get_Everything_Load {
	static String getpath_json = null;
	static String[] content_vivo = new String[3];
	static String[] datauser = new String[8];
	
	
	public static String[] getuser_datas()
	{
		datauser = ConfigJira.DataConfig();
		
		return datauser;
	}
	
	public Create_Get_Everything_Load(String datauser[])
	
	{
	
		

	// getting the content issue
	content_vivo = GetEverything.Get_Everything(datauser[0],datauser[1],datauser[2],datauser[3],getpath_json);
	// getting the path from file created "getdata.json"
	getpath_json = Create_Load_Json_Delete.Create_Json(content_vivo[1]+"-");
	// loading the getdata with issue content
	Create_Load_Json_Delete.Load_Json_File(getpath_json,content_vivo[0]);

	}
	

}

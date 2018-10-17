package br.com.jira;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Load_Json_Parents_Keys {
static int x=0;
	@SuppressWarnings("unchecked")
	public static String Parent_keys_child(List<String> child, String owner,String key_path) {
		
		
				//stores the parent and child key in a json file for comparisons. 
		
		

				JSONObject jsonObjectWrite = new JSONObject();
				JSONObject jsonObjectRead = new JSONObject();
				JSONParser parser = new JSONParser();
				String Read = null;
				String all = null;
				try {

					jsonObjectRead = (JSONObject) parser.parse(new FileReader(key_path));
					Read = jsonObjectRead.toJSONString();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				JSONObject parent = new JSONObject();

					
				parent.put("Key-parent", owner);

				parent.put("Child", child);

				jsonObjectWrite.put("Task "+owner, parent);

					if (Read == null) {
						all = jsonObjectWrite.toJSONString();
					} else {
						all = Read + jsonObjectWrite.toJSONString();
						all = all.replace("}{", ",");
					}
				
				System.out.println(owner);
				 for (int x=0; x<child.size();x++)
			        {
			        	System.out.println("Filhos: "+child.get(x));
			        }
        

				 return all;
	}

}

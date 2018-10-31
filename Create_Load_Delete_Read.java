package br.com.jira;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Create_Load_Delete_Read {
	static Logger logger = Logger.getLogger(Create_Load_Delete_Read.class);
	static String path = null;

	public static String Create_Json(String name) {

		try {
			/* creating a JsonFile */
			File temp = File.createTempFile(name, ".json");
			path = temp.getAbsolutePath();
			System.out.println(path);
			/* returning the path of our Json file. */

		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();

		}
		return path;
	}

	public static void Load_Json_File(String pathfilename, String content_indra) {
		// writing data on Json file.

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String content = null;

			fw = new FileWriter(pathfilename);
			bw = new BufferedWriter(fw);
			content = content_indra;
			bw.write(content);

		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				logger.error(ex);
				ex.printStackTrace();

			}

		}

	}

	public static void DeleteJsonFile(String getdeletepath)

	{

		boolean erased = false;
		try {

			System.gc();
			Thread.sleep(500);

			File file = new File(getdeletepath);

			while (erased == false) {
				System.gc();
				if (erased = file.delete()) {
					System.out.println(file.getName() + " is deleted!");
				} else {
					logger.error("Delete operation is failed.");
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}

	}

	public static JSONObject getJson(String file_name) {
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(file_name));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
		return jsonObject;
	}

}

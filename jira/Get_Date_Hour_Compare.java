package br.com.jira;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Get_Date_Hour_Compare {
	
	

	static String getDateTime(String json_hour_date) throws ParseException { 
		
		String return_case;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		
		Date date = new Date(); 
		long milisec = date.getTime();
		Date set = dateFormat.parse(json_hour_date);
		
		long setmili = set.getTime();
		
		long dif = milisec-setmili;
		
		System.out.println("Systema: "+milisec+"\nSetado: "+ setmili+"\ndif: "+dif+"\nhoras: "
				+ dif/(1000*60*60));
		
		
	
		//dif de 4 horas = 14.400.000 = 14400000 // dif de 5 dias = 432000000
		long update = 999999999;
		if(dif<update && dif>0)
		{
			System.out.println("DATE OK!!");
			return_case="1";
		}
		else {
			System.out.println("Date too old");
			return_case="2";
		}
	
		
				return return_case;
	}

}

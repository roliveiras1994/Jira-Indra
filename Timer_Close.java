package br.com.jira;

import org.apache.log4j.Logger;

public class Timer_Close implements Runnable {
	static Logger logger = Logger.getLogger(Timer_Close.class);

	public void run() {
		try {
			Thread.sleep(10800000);
			//Thread.sleep(60000);
			logger.info("Application closed by Thread");
			System.exit(0);
	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

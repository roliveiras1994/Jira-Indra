package br.com.jira;

import org.apache.log4j.Logger;

public class Timer_Close implements Runnable {
	static Logger logger = Logger.getLogger(Timer_Close.class);

	public void run() {
		try {
			Thread.sleep(10800000);
			System.exit(0);
			logger.info("Application closed by Thread");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

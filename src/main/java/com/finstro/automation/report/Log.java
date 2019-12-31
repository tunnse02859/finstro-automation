package com.finstro.automation.report;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;

public class Log {
	
	private static Logger log;

	private static Logger getLogger() {
		try {
			if(log == null) {
				initLog();
			}
			return log;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void initLog() throws Exception {
		try {
			URI configURI = Log.class.getResource("/config/log4j2.xml").toURI();
			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			context.setConfigLocation(configURI);
			log = LogManager.getLogger();
			String logFileName = "log_" + Thread.currentThread().getName();
			ThreadContext.put("ROUTINGKEY", logFileName);
		} catch (Exception e) {
			throw e;
		}
	}
	

	/**
	 * This method is used at the start of test case, help for developer can trace
	 * log easier.
	 * 
	 * @author Hanoi Automation team
	 * @param sTestCaseName The name of test case
	 * @throws Exception
	 */
	public static void startTestCase(String sTestCaseName){
		getLogger().info("****************************************************************************************");

		getLogger().info("****************************************************************************************");

		getLogger().info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");

		getLogger().info("****************************************************************************************");

		getLogger().info("****************************************************************************************");

	}

	/**
	 * This method is used at the end of test case, help for developer can trace log
	 * easier.
	 * 
	 * @author Hanoi Automation team
	 * @param sTestCaseName The name of test case
	 * @throws Exception
	 */
	public static void endTestCase(String sTestCaseName){

		getLogger()
				.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");

		getLogger().info("X");

		getLogger().info("X");

		getLogger().info("X");

		getLogger().info("X");

	}

	/**
	 * Writing information message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param info The information meesage
	 * @throws Exception
	 */
	public static void info(String info) {
		getLogger().info(info);
	}

	/**
	 * Writing warning message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param warn The warning message
	 * @throws Exception
	 */
	public static void warn(String warn) {

		getLogger().warn(warn);

	}

	/**
	 * Writing error message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param error The error message
	 * @throws Exception
	 */
	public static void error(String error) {

		getLogger().error(error);

	}

	/**
	 * Writing fatal message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param fatal The fatal message
	 * @throws Exception
	 */
	public static void fatal(String fatal){

		getLogger().fatal(fatal);

	}

	/**
	 * Writing debug message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param debug The debug message
	 * @throws Exception
	 */
	public static void debug(String debug){

		getLogger().debug(debug);

	}

}

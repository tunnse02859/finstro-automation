package com.finstro.automation.logger;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;

import com.finstro.automation.common.Common;

public class Log {

	private static Logger getLogger() {
		try {
			String strConfigFilePath = Common.constants.getProperty("configLogFilePath");
			URI configURI = Log.class.getClassLoader().getResource(strConfigFilePath).toURI();
			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			context.setConfigLocation(configURI);
			Logger log = LogManager.getLogger();
			String logFileName = "log_" + Thread.currentThread().getName();
			ThreadContext.put("ROUTINGKEY", logFileName);
			return log;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
	public static void startTestCase(String sTestCaseName) throws Exception {
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
	public static void endTestCase(String sTestCaseName) throws Exception {

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
	public static void info(String info) throws Exception {

		getLogger().info(info);

	}

	/**
	 * Writing warning message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param warn The warning message
	 * @throws Exception
	 */
	public static void warn(String warn) throws Exception {

		getLogger().warn(warn);

	}

	/**
	 * Writing error message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param error The error message
	 * @throws Exception
	 */
	public static void error(String error) throws Exception {

		getLogger().error(error);

	}

	/**
	 * Writing fatal message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param fatal The fatal message
	 * @throws Exception
	 */
	public static void fatal(String fatal) throws Exception {

		getLogger().fatal(fatal);

	}

	/**
	 * Writing debug message to log file
	 * 
	 * @author Hanoi Automation team
	 * @param debug The debug message
	 * @throws Exception
	 */
	public static void debug(String debug) throws Exception {

		getLogger().debug(debug);

	}

}

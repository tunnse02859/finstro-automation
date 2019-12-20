package com.finstro.automation.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.finstro.automation.logger.Log;

public class Common {


	// Setting Path
	public static String strWorkspacepath = System.getProperty("user.dir");
	public static Properties constants;
	public static Properties config;
	public static Properties environment;
	static {
		try {
			String constantsFile = correctPath(
					strWorkspacepath + "\\src\\test\\resources\\config\\constants.properties");
			constants = readProperties(constantsFile);
			String configFile = correctPath(strWorkspacepath + constants.getProperty("configFilePath"));
			config = readProperties(configFile);
			String environmentFile = constants.getProperty("environmentFilePath");
			if(environmentFile != null && !environmentFile.equals("")) {
				environmentFile = correctPath(strWorkspacepath + constants.getProperty("environmentFilePath"));
				environment = readProperties(environmentFile);
			}
		} catch (Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * This method is used to get the project path
	 * 
	 * @author Hanoi Automation team
	 * @return The absolute path of project
	 */
	public static String getProjectPath() {

		return strWorkspacepath;

	}

	/**
	 * This method is used to read the configuration file
	 * 
	 * @author Hanoi Automation team
	 * @param strKey
	 *            The key in the configuration file
	 * @return The value of key
	 * @throws Exception
	 */
	protected static Properties readProperties(String path) throws Exception {
		Properties prop = new Properties();
		try {
			InputStream input = null;
			input = new FileInputStream(path);
			prop.load(input);
			for (String key : prop.stringPropertyNames()) {
				String configValue = System.getProperty(key);
				if (configValue != null) {
					prop.setProperty(key, configValue);
				}
			}
		} catch (Exception e) {
			System.out.print("Cannot read property file: [" + path + "]");
			throw e;
		}
		return prop;
	}
	
	/**
	 * This method is used to read the configuration file
	 * 
	 * @author Hanoi Automation team
	 * @param strKey
	 *            The key in the configuration file
	 * @return The value of key
	 * @throws Exception
	 */
	protected static Properties readResourceProperties(String fileName) throws Exception {
		Properties prop = new Properties();
		try {
			InputStream input = null;
			input = Common.class.getClassLoader().getResourceAsStream(fileName);
			prop.load(input);
			for (String key : prop.stringPropertyNames()) {
				String configValue = System.getProperty(key);
				if (configValue != null) {
					prop.setProperty(key, configValue);
				}
			}
		} catch (Exception e) {
			System.out.print("Cannot read property file: [" + fileName + "]");
			throw e;
		}
		return prop;
	}

	/**
	 * Delay method
	 * 
	 * @author Hanoi Automation team
	 * 
	 * @param seconds
	 *            The delay time in seconds
	 * @throws Exception
	 */
	public static void sleep(int seconds) throws Exception {
		try {
			long milliSeconds = seconds * 1000;
			Thread.sleep(milliSeconds);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Correct the file path based on the OS system type
	 * 
	 * @param path
	 *            the path to file
	 * @throws Exception
	 */
	public static String correctPath(String path) {

		return path.replaceAll("\\\\|/", "\\" + System.getProperty("file.separator"));

	}

	/**
	 * Verify file or path is existing on system
	 * 
	 * @param path
	 *            the path to file
	 * @throws Exception
	 */
	public static boolean pathExist(String path) throws Exception {
		try {
			return Files.exists(new File(path).toPath());
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Delete a file or a path
	 * 
	 * @param path
	 *            the path to file
	 * @throws Exception
	 */
	public static void deletePath(String path) throws Exception {
		try {
			Files.delete(new File(path).toPath());
			Log.info("File [" + path + "] is deleted successfully");
		} catch (Exception e) {
			Log.error("Failed to delete File [" + path + "]");
			throw e;
		}
	}

	/**
	 * create a path
	 * 
	 * @param path
	 *            the path to file
	 * @throws Exception
	 */
	public static void createFile(String path) throws Exception {
		try {
			if (!pathExist(path))
				Files.createFile(new File(path).toPath());
			Log.info("File [ " + path + " ] is created successfully");
		} catch (Exception e) {
			Log.error("Failed to create file: [" + path + "]");
			throw e;
		}
	}

	/**
	 * create a path
	 * 
	 * @param path
	 *            the path to file
	 * @throws Exception
	 */
	public static void createDirectory(String path) throws Exception {
		try {
			if (!pathExist(path))
				Files.createDirectory(new File(path).toPath());
			Log.info("File [ " + path + " ] is created successfully");
		} catch (Exception e) {
			Log.error("Failed to create file: [" + path + "]");
			throw e;
		}
	}

	/**
	 * To copy a file from the source file to the destination file
	 * 
	 * @param src
	 * @param dest
	 * @throws Exception
	 */
	public static void copyFile(String src, String dest) throws Exception {
		try {
			File sourceFile = new File(src);
			File destFile = new File(dest);
			Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Log.info("[" + src + "] is copyed to [" + dest + "]");
		} catch (Exception e) {
			Log.error("Failed to copy [" + src + "] into [" + dest + "]");
			throw e;
		}
	}

	/**
	 * To copy a folder from the source folder to the destination folder
	 * 
	 * @param src
	 * @param dest
	 * @throws Exception
	 * @throws Exception
	 */
	public static void copyDirectory(String src, String dest) throws Exception {
		try {
			File sourceFile = new File(src);
			File destFile = new File(dest);
			createDirectory(dest);
			FileUtils.copyDirectory(sourceFile, destFile, true);
			Log.info("[" + src + "] is copyed to [" + dest + "]");
		} catch (Exception e) {
			Log.error("Failed to copy [" + src + "] into [" + dest + "]");
		}
	}

}

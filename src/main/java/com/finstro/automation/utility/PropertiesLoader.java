package com.finstro.automation.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesLoader {


	private static PropertiesLoader _instance;
	public Properties android_configuration;
	public Properties ios_configuration;
	public Properties browser_configuration;
	public Properties appium_configuration;
	
	
	public PropertiesLoader() throws Exception{
		
		android_configuration = readResourceProperties("/platform/android.properties");
		ios_configuration = readResourceProperties("/platform/ios.properties");
		browser_configuration = readResourceProperties("/platform/browser.properties");
		appium_configuration = readResourceProperties("/platform/appium.properties");
		
	}
	
	public static PropertiesLoader getPropertiesLoader() throws Exception {
		
		if(_instance == null) {
			
			return new PropertiesLoader();
			
		}
		else {
			
			return _instance;
			
		}
	}

	/**
	 * This method is used to read the configuration file
	 * 
	 * @param fileName
	 *            The path of property file located in project
	 * @return Properties set
	 * @throws Exception
	 */
	public static Properties readProperties(String path) throws Exception {
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
	 * @param filePath
	 *            The path of property file located in resource folder
	 * @return Properties set
	 * @throws IOException 
	 * @throws Exception
	 */
	public static Properties readResourceProperties(String filePath) throws Exception {
		Properties prop = new Properties();
		try (InputStream inputStream = PropertiesLoader.class.getResourceAsStream(filePath)) {
			prop.load(inputStream);
			for (String key : prop.stringPropertyNames()) {
				String configValue = System.getProperty(key);
				if (configValue != null) {
					prop.setProperty(key, configValue);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return prop;
	}

}

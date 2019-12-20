package com.finstro.automation.appium.driver;

import java.util.HashMap;

import org.openqa.selenium.WebElement;


import io.appium.java_client.AppiumDriver;

public class AppiumDriverFactory {
	private static final AppiumDriverFactory INSTANCE = new AppiumDriverFactory();

	// Private constructor to avoid client applications to use constructor
	private AppiumDriverFactory() {

	}

	public static AppiumDriverFactory getInstance() {
		return INSTANCE;
	}

	public AppiumDriver<WebElement> createDriver(HashMap<String, String> deviceInfo) throws Exception {
    	if(deviceInfo.get("platform").equalsIgnoreCase("android")) {
    		return new AppiumAndroidDriver().createDriver(deviceInfo);
    	}else if(deviceInfo.get("platform").equalsIgnoreCase("ios")) {
    		return new AppiumIOsDriver().createDriver(deviceInfo);
    	}
    	return null;
    }
	
	public AppiumDriver<WebElement> createDriver(String platform) throws Exception {
    	if(platform.equalsIgnoreCase("android")) {
    		return new AppiumAndroidDriver().createDriver();
    	}else if(platform.equalsIgnoreCase("ios")) {
    		return new AppiumIOsDriver().createDriver();
    	}
    	return null;
    }
	
	public AppiumDriver<WebElement> createAWSDriver(String platform) throws Exception {
    	if(platform.equalsIgnoreCase("android")) {
    		return new AppiumAndroidDriver().createAWSDriver();
    	}else if(platform.equalsIgnoreCase("ios")) {
    		return new AppiumIOsDriver().createAWSDriver();
    	}
    	return null;
    }
}

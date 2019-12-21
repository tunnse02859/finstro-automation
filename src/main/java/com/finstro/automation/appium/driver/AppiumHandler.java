package com.finstro.automation.appium.driver;

import java.util.HashMap;

import org.openqa.selenium.WebElement;

import com.finstro.automation.appium.action.AppiumBaseDriver;

import io.appium.java_client.AppiumDriver;

public class AppiumHandler {

	public AppiumBaseDriver startDriver(HashMap<String, String> deviceInfo) throws Exception {
		
    	if(deviceInfo.get("platform").equalsIgnoreCase("android")) {
    		
    		AppiumAndroidDriver android = new AppiumAndroidDriver();
    		android.createDriver(deviceInfo);
    		return android.getDriver();
    		
    	}else if(deviceInfo.get("platform").equalsIgnoreCase("ios")) {
    		
    		AppiumIOsDriver ios = new AppiumIOsDriver();
    		ios.createDriver(deviceInfo);
    		return ios.getDriver();
    		
    	}
    	
    	else {
    		throw new Exception();
    	}

    }
	
	public AppiumBaseDriver startDriver(String platform) throws Exception {
		
    	if(platform.equalsIgnoreCase("android")) {
    		
    		AppiumAndroidDriver android = new AppiumAndroidDriver();
    		android.createDriver();
    		return android.getDriver();
    		
    	}else if(platform.equalsIgnoreCase("ios")) {
    		
    		AppiumIOsDriver ios = new AppiumIOsDriver();
    		ios.createDriver();
    		return ios.getDriver();
    		
    	}
    	
    	else {
    		throw new Exception();
    	}
    	
	}
	
	public AppiumBaseDriver startAWSDriver(String platform) throws Exception {
		
    	if(platform.equalsIgnoreCase("android")) {
    		
    		AppiumAndroidDriver android = new AppiumAndroidDriver();
    		android.createAWSDriver();
    		return android.getDriver();
    		
    	}else if(platform.equalsIgnoreCase("ios")) {
    		
    		AppiumIOsDriver ios = new AppiumIOsDriver();
    		ios.createAWSDriver();
    		return ios.getDriver();
    		
    	}
    	else {
    		throw new Exception();
    	}
    	
    }
}

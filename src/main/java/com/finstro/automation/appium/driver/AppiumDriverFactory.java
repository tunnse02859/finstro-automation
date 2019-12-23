package com.finstro.automation.appium.driver;

import java.util.HashMap;

import org.openqa.selenium.WebElement;

import com.finstro.automation.common.Common;

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
		if (deviceInfo.get("platform").equalsIgnoreCase("android")) {
			return new AppiumAndroidDriver().createDriver(deviceInfo);
		} else if (deviceInfo.get("platform").equalsIgnoreCase("ios")) {
			return new AppiumIOsDriver().createDriver(deviceInfo);
		}
		return null;
	}

	public AppiumDriver<WebElement> createDriver() throws Exception {
		// check if we're executing on aws or not
		String currentAWSPlatformName = System.getProperty("DEVICEFARM_DEVICE_PLATFORM_NAME");
		if (currentAWSPlatformName != null) {
			if (currentAWSPlatformName.equalsIgnoreCase("android")) {
				return new AppiumAndroidDriver().createAWSDriver();
			}else if(currentAWSPlatformName.equalsIgnoreCase("ios")){
				return new AppiumIOsDriver().createAWSDriver();
			}
		}else {
			// if we arent executing test on aws, uses config.property to create driver
			String configTestPlatform = Common.config.getProperty("appium.platform");
			if(configTestPlatform.equalsIgnoreCase("android")) {
				return new AppiumAndroidDriver().createDriver();
			}else if(configTestPlatform.equalsIgnoreCase("ios")) {
				return new AppiumIOsDriver().createDriver();
			}
		}
		return null;
	}
}

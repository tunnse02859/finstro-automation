package com.finstro.automation.appium.driver;

import com.finstro.automation.utility.PropertiesLoader;

public class AppiumHandler {

	public AppiumBaseDriver startDriver() throws Exception {

		String platform = PropertiesLoader.getPropertiesLoader().appium_configuration.getProperty("appium.platform");
		String awsPlatform = System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME");
		if (awsPlatform != null) {
			if (awsPlatform.equalsIgnoreCase("android")) {
				AppiumAndroidDriver android = new AppiumAndroidDriver();
				android.createAWSDriver();
				return android;
			} else {
				AppiumIOsDriver ios = new AppiumIOsDriver();
				ios.createAWSDriver();
				return ios;
			}
		} else {
			if (platform.equalsIgnoreCase("android")) {
				AppiumAndroidDriver android = new AppiumAndroidDriver();
				android.createDriver();
				return android;
			} else if (platform.equalsIgnoreCase("ios")) {
				AppiumIOsDriver ios = new AppiumIOsDriver();
				ios.createDriver();
				return ios;
			}
			else {
				throw new Exception(String.format("The platform [%s] is not defined", platform));
			}
		}
	}


}

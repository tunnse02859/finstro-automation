package com.finstro.automation.appium.driver;

import com.finstro.automation.utility.PropertiesLoader;

public class AppiumHandler {

	public AppiumBaseDriver startDriver() throws Exception {

		String platform = PropertiesLoader.getPropertiesLoader().appium_configuration.getProperty("appium.platform");
		boolean isDeviceFarm = Boolean.getBoolean(
				PropertiesLoader.getPropertiesLoader().appium_configuration.getProperty("appium.device_farm"));

		if (isDeviceFarm) {
			String awsPlatform = System.getProperty("$DEVICEFARM_DEVICE_PLATFORM_NAME") != null
					? System.getProperty("$DEVICEFARM_DEVICE_PLATFORM_NAME") : platform;
					
			if (awsPlatform.equalsIgnoreCase("android")) {

				AppiumAndroidDriver android = new AppiumAndroidDriver();
				android.createAWSDriver();
				return android.getDriver();

			} else {

				AppiumIOsDriver ios = new AppiumIOsDriver();
				ios.createAWSDriver();
				return ios.getDriver();

			}

		} else {
			if (platform.equalsIgnoreCase("android")) {

				AppiumAndroidDriver android = new AppiumAndroidDriver();
				android.createDriver();
				return android.getDriver();

			} else if (platform.equalsIgnoreCase("ios")) {

				AppiumIOsDriver ios = new AppiumIOsDriver();
				ios.createDriver();
				return ios.getDriver();

			}

			else {
				throw new Exception(String.format("The platform [%s] is not defined", platform));
			}
		}

	}

	public AppiumBaseDriver startAWSDriver(String platform) throws Exception {

		if (platform.equalsIgnoreCase("android")) {

			AppiumAndroidDriver android = new AppiumAndroidDriver();
			android.createAWSDriver();
			return android.getDriver();

		} else if (platform.equalsIgnoreCase("ios")) {

			AppiumIOsDriver ios = new AppiumIOsDriver();
			ios.createAWSDriver();
			return ios.getDriver();

		} else {
			throw new Exception();
		}

	}
}

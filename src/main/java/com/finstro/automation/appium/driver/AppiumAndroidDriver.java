package com.finstro.automation.appium.driver;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.finstro.automation.common.Common;
import com.finstro.automation.extentreport.HtmlReporter;
import com.finstro.automation.logger.Log;

import freemarker.template.utility.NormalizeNewlines;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumAndroidDriver {

	protected AppiumDriver<WebElement> driver;

	/**
	 * This method is used to open a appium driver, use deviceInfo to create driver
	 * 
	 * @author tunn6
	 * @param deviceInfo
	 * @return AppiumDriver driver
	 * @throws Exception
	 */
	public AppiumDriver<WebElement> createDriver(HashMap<String, String> deviceInfo) throws Exception {
		// Appium Server Url
		String strAppiumServer = Common.config.getProperty("appium.server");
		
		boolean booleanMobileNoReset = Boolean.getBoolean(Common.config.getProperty("appium.noReset"));
		boolean booleanMobileFullReset = Boolean.getBoolean(Common.config.getProperty("appium.fullReset"));
		
		//base path to mobile app
		String mobileAppBasePath = System.getProperty("user.dir") + "\\src\\main\\resources\\app";

		// Android app configuration
		String strMobileAndroidApp = mobileAppBasePath + Common.config.getProperty("appium.android.app");
		String strMobileAppPackage = Common.config.getProperty("appium.android.appPackage");
		String strMobileAppActivity = Common.config.getProperty("appium.android.appActivity");

		// SauceLab configuration
		String saucelabEnable = Common.config.getProperty("appium.saucelab.enable");
		
		//appium version
		String strAppiumVersion = Common.config.getProperty("appium.version");

		DesiredCapabilities capabilities = null;
		try {
			capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceInfo.get("platform"));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceInfo.get("platformVersion"));
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceInfo.get("deviceName"));
			capabilities.setCapability(MobileCapabilityType.NO_RESET, booleanMobileNoReset);
			capabilities.setCapability(MobileCapabilityType.FULL_RESET, booleanMobileFullReset);
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
			// Use SauceLab remote server instead of local Appium server if configured
			if (saucelabEnable.equals("true")) {
				String strSauceAndroidApp = Common.config.getProperty("appium.saucelab.androidApp");
				String strSauceRemoteUrl = Common.config.getProperty("appium.saucelab.remoteUrl");
				String strSauceUserName = Common.config.getProperty("appium.saucelab.username");
				String strSauceAccessKey = Common.config.getProperty("appium.saucelab.accessKey");
				strAppiumServer = "https://" + strSauceUserName + ":" + strSauceAccessKey + "@" + strSauceRemoteUrl;
				strMobileAndroidApp = strSauceAndroidApp;
			}

			if (!(strAppiumVersion == null || strAppiumVersion.equals(""))) {
				capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, strAppiumVersion);
			}

			// check if test on mobile browser
			if (deviceInfo.get("browserName") != null) {
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, deviceInfo.get("browserName"));
			} else {
				capabilities.setCapability(MobileCapabilityType.APP, strMobileAndroidApp);
				if (!(strMobileAppPackage == null || strMobileAppPackage.equals(""))) {
					capabilities.setCapability("appPackage", strMobileAppPackage);
				}
				if (!(strMobileAppActivity == null || strMobileAppActivity.equals(""))) {
					capabilities.setCapability("appActivity", strMobileAppActivity);
				}
			}
			
			if(deviceInfo.containsKey("methodName")) {
				capabilities.setCapability("name", deviceInfo.get("methodName"));
			}
			Log.info(capabilities.toString());
			driver = new AndroidDriver<WebElement>(new URL(strAppiumServer), capabilities);

			Log.info("Starting remote webdriver for: Platform: " + deviceInfo.get("platform") + " ," + " version: "
					+ deviceInfo.get("platformVersion") + " on: " + strAppiumServer);
			HtmlReporter.pass("Starting remote webdriver for: Platform: " + deviceInfo.get("platform") + " ,"
					+ " version: " + deviceInfo.get("platformVersion") + " on: " + strAppiumServer);
			HtmlReporter.pass(capabilities.toString());
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Common.constants.getProperty("IMPLICIT_WAIT_TIME")),
					TimeUnit.SECONDS);
			return driver;
		} catch (Exception e) {
			Log.error("Can't start the webdriver!!! : \n"
					+ "Platform: " + deviceInfo.get("platform") + " ,  +  version: " + deviceInfo.get("platformVersion") + " on: " + strAppiumServer + "\n"
					+ e);
			HtmlReporter.fail("Can't start the webdriver!!! : \n"
					+ "Platform: " + deviceInfo.get("platform") + " ,  +  version: " + deviceInfo.get("platformVersion") + " on: " + strAppiumServer
					, e, "");
			throw (e);
		}
	}

	/**
	 * This method is used to open a appium driver, use configuration.property to
	 * create driver
	 * 
	 * @author tunn6
	 * @param deviceInfo
	 * @return AppiumDriver driver
	 * @throws Exception
	 */
	public AppiumDriver<WebElement> createDriver() throws Exception {
		// Appium Server Url
		String strAppiumServer = Common.config.getProperty("appium.server");
		String platform = Common.config.getProperty("appium.platform");
		String platformVersion = Common.config.getProperty("appium.platformVersion");
		String deviceName = Common.config.getProperty("appium.deviceName");
		boolean booleanMobileNoReset = Boolean.getBoolean(Common.config.getProperty("appium.noReset"));
		boolean booleanMobileFullReset = Boolean.getBoolean(Common.config.getProperty("appium.fullReset"));
		
		boolean mobileEnable = Boolean.getBoolean(Common.config.getProperty("appium.browser.enable"));

		String mobileAppBasePath = System.getProperty("user.dir") + "\\src\\main\\resources\\app\\";

		// Android app configuration
		String strMobileAndroidApp = mobileAppBasePath + Common.config.getProperty("appium.android.app");
		String strMobileAppPackage = Common.config.getProperty("appium.android.appPackage");
		String strMobileAppActivity = Common.config.getProperty("appium.android.appActivity");

		// SauceLab configuration
		Boolean saucelabEnable = Boolean.getBoolean(Common.config.getProperty("appium.saucelab.enable"));

		String strAppiumVersion = Common.config.getProperty("appium.version");

		DesiredCapabilities capabilities = null;
		try {
			capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			capabilities.setCapability(MobileCapabilityType.NO_RESET, booleanMobileNoReset);
			capabilities.setCapability(MobileCapabilityType.FULL_RESET, booleanMobileFullReset);
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
			// Use SauceLab remote server instead of local Appium server if configured
			if (saucelabEnable) {
				String strSauceAndroidApp = Common.config.getProperty("appium.saucelab.androidApp");
				String strSauceRemoteUrl = Common.config.getProperty("appium.saucelab.remoteUrl");
				String strSauceUserName = Common.config.getProperty("appium.saucelab.username");
				String strSauceAccessKey = Common.config.getProperty("appium.saucelab.accessKey");
				strAppiumServer = "https://" + strSauceUserName + ":" + strSauceAccessKey + "@" + strSauceRemoteUrl;
				strMobileAndroidApp = strSauceAndroidApp;
			}

			if (!(strAppiumVersion == null || strAppiumVersion.equals(""))) {
				capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, strAppiumVersion);
			}

			// check if test on mobile browser
			if (mobileEnable) {
				String browserName = Common.config.getProperty("appium.browser.name");
				String browserVersion = Common.config.getProperty("appium.browser.version");
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
				if (!(browserVersion == null || browserVersion.equals(""))) {
					capabilities.setCapability(MobileCapabilityType.BROWSER_VERSION, browserVersion);
				}
			} else {
				capabilities.setCapability(MobileCapabilityType.APP, strMobileAndroidApp);
				if (!(strMobileAppPackage == null || strMobileAppPackage.equals(""))) {
					capabilities.setCapability("appPackage", strMobileAppPackage);
				}
				if (!(strMobileAppActivity == null || strMobileAppActivity.equals(""))) {
					capabilities.setCapability("appActivity", strMobileAppActivity);
				}
			}

			driver = new AndroidDriver<WebElement>(new URL(strAppiumServer), capabilities);

			Log.info("Starting remote webdriver for: Platform: " + platform + " ," + " version: "
					+ platformVersion + " on: " + strAppiumServer);
			HtmlReporter.pass("Starting remote webdriver for: Platform: " + platform + " ,"
					+ " version: " + platformVersion + " on: " + strAppiumServer);
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Common.constants.getProperty("IMPLICIT_WAIT_TIME")),
					TimeUnit.SECONDS);
			return driver;
		} catch (Exception e) {
			Log.error("Can't start the webdriver!!! : " + e);
			HtmlReporter.fail("Can't start the webdriver!!! : ", e, "");
			throw (e);
		}
	}
	
	/**
	 * This method is used to open a appium driver, use configuration.property to
	 * create driver
	 * 
	 * @author tunn6
	 * @param deviceInfo
	 * @return AppiumDriver driver
	 * @throws Exception
	 */
	public AppiumDriver<WebElement> createAWSDriver() throws Exception {


		DesiredCapabilities capabilities = null;
		try {
			capabilities = new DesiredCapabilities();  
			driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

			Log.info("Starting Android driver on AWS");
			HtmlReporter.pass("Starting Andoid driver on AWS");
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Common.constants.getProperty("IMPLICIT_WAIT_TIME")),
					TimeUnit.SECONDS);
			return driver;
		} catch (Exception e) {
			Log.error("Can't start the android driver!!! : " + e);
			HtmlReporter.fail("Can't start the android driver!!! : ", e, "");
			throw (e);
		}
	}
}

package com.finstro.automation.base;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.finstro.automation.appium.action.AppiumDriverMethod;
import com.finstro.automation.common.*;
import com.finstro.automation.extentreport.HtmlReporter;

public class MobileTestBaseSetup   {

	// Web driver
	public AppiumDriverMethod driver;
	//hashmap contains device infor like: platform, deviceName, uuid, browser...... etc
	public HashMap<String, String> deviceInfo;
	
	@BeforeSuite
	public void beforeSuite() throws Exception {
		/*********** Create Web Report Folder ******************/
		HtmlReporter.strDailyReportFolder = Common
				.correctPath(HtmlReporter.strReportFolder + "\\" + HtmlReporter.date + "\\");
		HtmlReporter.initWebReportFolder();

		/*********** Init Html reporter *************************/
		HtmlReporter
				.getReporter(Common.correctPath(HtmlReporter.strDailyReportFolder + "\\" + Common.constants.getProperty("reportFileName")));
	}
	
	
	@BeforeClass
	public void beforeClass() throws Exception {
		HtmlReporter.createTest(this.getClass().getSimpleName(),"");
	}

	@BeforeMethod
	public void beforeMethod(Method method) throws Exception{
		HtmlReporter.createNode(this.getClass().getSimpleName(), method.getName(),
				"");
		driver = new AppiumDriverMethod("android", true);
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception {
		driver.closeDriver();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		HtmlReporter.flush();
	}
}

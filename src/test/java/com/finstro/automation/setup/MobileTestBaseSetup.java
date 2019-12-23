package com.finstro.automation.setup;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumHandler;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.utility.FilePaths;

public class MobileTestBaseSetup {

	// Web driver
	public AppiumBaseDriver driver;
	// hashmap contains device infor like: platform, deviceName, uuid,
	// browser...... etc
	public HashMap<String, String> deviceInfo;

	@BeforeSuite
	public void beforeSuite() throws Exception {

		/*********** Init Html reporter *************************/
		String reportFolder = FilePaths.getReportFolder();
		String reportFilePath = reportFolder + File.separator + "Report_"
				+ FilePaths.getCurrentDateTimeString("dd-MM-yyyy HHmmss") + ".html";
		String screenshotFolder = FilePaths.getScreenshotFolder();
		HtmlReporter.setReporter(reportFilePath).setSystemInfo("Application", "Finstro Pay Automation Tests");
		;
	}

	@BeforeClass
	public void beforeClass() throws Exception {
		HtmlReporter.createTest(this.getClass().getSimpleName(), "");
	}

	@BeforeMethod
	public void beforeMethod(Method method) throws Exception {
		HtmlReporter.createNode(this.getClass().getSimpleName(), method.getName(), "");
		// driver = new AppiumBaseDriver("android", true);
		driver = new AppiumHandler().startDriver();
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception {
		
		String mess = "";
		
		try {
			switch (result.getStatus()) {
			
				case ITestResult.SUCCESS:
					mess = String.format("The test [%s] is PASSED", result.getName());
					HtmlReporter.pass(mess);
					break;
					
				case ITestResult.SKIP:
					mess = String.format("The test [%s] is PASSED", result.getName());
					HtmlReporter.pass(mess);
					break;
				
				case ITestResult.FAILURE:
					mess = String.format("The test [%s] is FAILED", result.getName());
					HtmlReporter.fail(mess, result.getThrowable(), driver.takeScreenshot());;
					break;
					
				default:
					break;
			}
		} catch (Exception e) {
		}

		finally {
			driver.closeDriver();
		}
		
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		HtmlReporter.flush();
	}
}

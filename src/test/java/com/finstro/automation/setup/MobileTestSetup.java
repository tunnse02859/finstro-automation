package com.finstro.automation.setup;


import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.finstro.automation.excelhelper.ExcelHelper;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.report.Log;

public class MobileTestSetup extends MobileTestBaseSetup {

	public static String dataFilePath;
	public static String sheetName;
	
	
	
	public Object[][] getTestProvider(String filepPath, String sheetName) throws Exception {
		// return the data from excel file
		Object[][] data = ExcelHelper.getTableArray(filepPath,sheetName);
		return data;
	}
	
	

	@BeforeSuite
	public void beforeSuite() throws Exception {
		super.beforeSuite();
	}

	@BeforeClass
	public void beforeClass() throws Exception {
		super.beforeClass();
		Log.startTestCase(this.getClass().getName());
	}

	@BeforeMethod
	public void beforeMethod(Method method) throws Exception {
		Log.info("+++++++++ Start testing: " + method.getName() + " ++++++++++++++");
		super.beforeMethod(method);
		if(driver.isIOSDriver()) {
			new RegisterPage(driver).dontAllowNotification();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws Exception {
		super.afterMethod(result);
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws Exception {
		Log.endTestCase(this.getClass().getName());
		super.afterClass();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() throws Exception {
		super.afterSuite();
	}
}

package com.finstro.automation.setup;

import static org.testng.Assert.*;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.finstro.automation.excelhelper.ExcelHelper;
import com.finstro.automation.pages.HomePage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.report.Log;

public class MobileTestSetup extends MobileTestBaseSetup {

	public static String dataFilePath;
	public static String sheetName;
	
	protected LoginPage loginPage;
	protected RegisterPage registerPage;
	protected HomePage homePage;
	
	public Object[][] getTestProvider(String filepPath, String sheetName) throws Exception {
		// return the data from excel file
		Object[][] data = ExcelHelper.getTableArray(filepPath,sheetName);
		return data;
	}
	
	protected void doLogin(String email, String accessCode) throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(),"Login screen didnt showed after tap on login");
		loginPage.login(email, accessCode);
		assertTrue(homePage.isActive(),"Home screen didnt showed after login");
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
	public void afterSuite() {
		super.afterSuite();
	}
}

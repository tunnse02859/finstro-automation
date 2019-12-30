package com.finstro.automation.tests;

import static org.testng.AssertJUnit.assertTrue;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.setup.MobileTestSetup;

public class SampleTest extends MobileTestSetup {
	static {
		dataFilePath = System.getProperty("user.dir") + "//src//test//resources//dataprovider//sample.xlsx";
		sheetName = "Sheet1";
	}
	

	public SampleTest() {
	}


	@DataProvider(name = "dataprovider")
	public Object[][] LoginSuccessTestData() throws Exception {
		// return the data from excel file
		Object[][] data = getTestProvider(dataFilePath,sheetName);
		return data;
	}

	@Test
	public void MySampleTest() throws Exception {
		LoginPage login = new LoginPage(driver);
		
		login.setFirstName("phog");
	}
}

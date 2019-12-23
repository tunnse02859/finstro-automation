package com.finstro.automation.test;

import static org.testng.AssertJUnit.assertTrue;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
		System.setProperty("webdriver.http.factory", "apache");
		By inputComment = By.id("comments");
		driver.click("Comment textfield", inputComment);
		driver.inputText("Comment textfield", inputComment, "Test in put");
		assertTrue(true);
		//driver.click("close key board", h1Text);
		//Thread.sleep(10000);
	}
}

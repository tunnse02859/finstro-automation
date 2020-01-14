package com.finstro.automation.tests;

import com.finstro.automation.pages.*;
import com.finstro.automation.setup.MobileTestSetup;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.finstro.automation.utility.Assertion.*;

public class SelectBusinessCardTest extends MobileTestSetup {
	
	private BusinessDetailPage businessDetailPage;
	private LoginPage loginPage;
	private SelectBusinessCardPage businessCardPage;
	private static final String LOGIN_EMAIL_ADDRESS = "erick@finstro.com.au";
	private static final String LOGIN_ACCESS_CODE = "033933";

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		loginPage = new LoginPage(driver);
		businessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		loginPage.doSuccessLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
	}

	@Test
	public void FPC_1314_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_500() throws Exception {
		businessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
	}

	@Test
	public void FPC_1315_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_1000() throws Exception {
		businessCardPage.clickOnCard("1000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 1000",
				"Business Details is displayed after click on card 1000");
	}

	@Test
	public void FPC_1316_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_5000() throws Exception {
		businessCardPage.clickOnCard("5000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 5000",
				"Business Details is displayed after click on card 5000");
	}

	@Test
	public void FPC_1317_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_10000() throws Exception {
		businessCardPage.clickOnCard("10000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 10000",
				"Business Details is displayed after click on card 10000");
	}

	@Test
	public void FPC_1318_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_25000() throws Exception {
		businessCardPage.clickOnCard("25000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 25000",
				"Business Details is displayed after click on card 25000");
	}

	@Test
	public void FPC_1319_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_50000() throws Exception {
		businessCardPage.clickOnCard("50000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 50000",
				"Business Details is displayed after click on card 50000");
	}

	@Test
	public void FPC_1320_Verify_Home_Screen_Displayed_After_Click_On_Card_500000()
			throws Exception {
		businessCardPage.clickOnCard("500000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500000+",
				"Business Details is displayed after click on card 500000+");
	}

}

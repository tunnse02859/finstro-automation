package com.finstro.automation.tests.onboarding;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.finstro.automation.utility.Assertion.*;

public class SelectBusinessCardTests extends MobileTestSetup {
	
	private BusinessDetailPage businessDetailPage;
	private RegisterPage registerPage;
	private LoginPage loginPage;
	private SelectBusinessCardPage selectCardPage;
	private FinstroAPI finstroAPI;
	
	@BeforeClass
	public void setupAccessTosken() throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		finstroAPI.getBusinessDetailInfor();
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		selectCardPage = new SelectBusinessCardPage(driver);
		
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page in first installation");
		
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		
	}
	
	private void verifyBusinessData() throws Exception {
		String abn_acn = Common.getTestVariable("abn",true).equalsIgnoreCase("") ? Common.getTestVariable("acn",true) : Common.getTestVariable("abn",true);
		String businessName = Common.getTestVariable("businessName",true);
		businessName = businessName.equalsIgnoreCase("") ? "N/A" : businessName;
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName",true), businessName);
	}

	@Test
	public void FPC_1314_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_500() throws Exception {
		businessDetailPage = selectCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
		verifyBusinessData();
	}

	@Test
	public void FPC_1315_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_1000() throws Exception {
		businessDetailPage = selectCardPage.clickOnCard("1000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 1000",
				"Business Details is displayed after click on card 1000");
		verifyBusinessData();
	}

	@Test
	public void FPC_1316_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_5000() throws Exception {
		businessDetailPage = selectCardPage.clickOnCard("5000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 5000",
				"Business Details is displayed after click on card 5000");
		verifyBusinessData();
	}

	@Test
	public void FPC_1317_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_10000() throws Exception {
		businessDetailPage = selectCardPage.clickOnCard("10000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 10000",
				"Business Details is displayed after click on card 10000");
		verifyBusinessData();
	}

	@Test
	public void FPC_1318_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_25000() throws Exception {
		businessDetailPage = selectCardPage.clickOnCard("25000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 25000",
				"Business Details is displayed after click on card 25000");
		verifyBusinessData();
	}

	@Test
	public void FPC_1319_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_50000() throws Exception {
		businessDetailPage = selectCardPage.clickOnCard("50000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 50000",
				"Business Details is displayed after click on card 50000");
		verifyBusinessData();
	}

	@Test
	public void FPC_1320_Verify_Home_Screen_Displayed_After_Click_On_Card_500000()
			throws Exception {
		businessDetailPage = selectCardPage.clickOnCard("500000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500000+",
				"Business Details is displayed after click on card 500000+");
		verifyBusinessData();
	}

}

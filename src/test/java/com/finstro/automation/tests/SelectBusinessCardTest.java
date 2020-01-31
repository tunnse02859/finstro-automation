package com.finstro.automation.tests;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.setup_information.BusinessDetailPage;
import com.finstro.automation.pages.setup_information.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.finstro.automation.utility.Assertion.*;

public class SelectBusinessCardTest extends MobileTestSetup {
	
	private BusinessDetailPage businessDetailPage;
	private RegisterPage registerPage;
	private LoginPage loginPage;
	private SelectBusinessCardPage businessCardPage;
	private FinstroAPI finstroAPI;

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		businessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		finstroAPI = new FinstroAPI();
		
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page in first installation");
		
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		finstroAPI.getBusinessDetailInfor();
	}

	@Test
	public void FPC_1314_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_500() throws Exception {
		businessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
		String abn_acn = Common.getTestVariable("abn").equalsIgnoreCase("") ? Common.getTestVariable("acn") : Common.getTestVariable("abn");
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName"), Common.getTestVariable("businessName"));
	}

	@Test
	public void FPC_1315_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_1000() throws Exception {
		businessCardPage.clickOnCard("1000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 1000",
				"Business Details is displayed after click on card 1000");
		String abn_acn = Common.getTestVariable("abn").equalsIgnoreCase("") ? Common.getTestVariable("acn") : Common.getTestVariable("abn");
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName"), Common.getTestVariable("businessName"));
	}

	@Test
	public void FPC_1316_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_5000() throws Exception {
		businessCardPage.clickOnCard("5000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 5000",
				"Business Details is displayed after click on card 5000");
		String abn_acn = Common.getTestVariable("abn").equalsIgnoreCase("") ? Common.getTestVariable("acn") : Common.getTestVariable("abn");
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName"), Common.getTestVariable("businessName"));
	}

	@Test
	public void FPC_1317_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_10000() throws Exception {
		businessCardPage.clickOnCard("10000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 10000",
				"Business Details is displayed after click on card 10000");
		String abn_acn = Common.getTestVariable("abn").equalsIgnoreCase("") ? Common.getTestVariable("acn") : Common.getTestVariable("abn");
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName"), Common.getTestVariable("businessName"));
	}

	@Test
	public void FPC_1318_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_25000() throws Exception {
		businessCardPage.clickOnCard("25000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 25000",
				"Business Details is displayed after click on card 25000");
		String abn_acn = Common.getTestVariable("abn").equalsIgnoreCase("") ? Common.getTestVariable("acn") : Common.getTestVariable("abn");
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName"), Common.getTestVariable("businessName"));
	}

	@Test
	public void FPC_1319_Verify_Business_Detail_Screen_Displayed_After_Click_On_Card_50000() throws Exception {
		businessCardPage.clickOnCard("50000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 50000",
				"Business Details is displayed after click on card 50000");
		String abn_acn = Common.getTestVariable("abn").equalsIgnoreCase("") ? Common.getTestVariable("acn") : Common.getTestVariable("abn");
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName"), Common.getTestVariable("businessName"));
	}

	@Test
	public void FPC_1320_Verify_Home_Screen_Displayed_After_Click_On_Card_500000()
			throws Exception {
		businessCardPage.clickOnCard("500000");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500000+",
				"Business Details is displayed after click on card 500000+");
		String abn_acn = Common.getTestVariable("abn").equalsIgnoreCase("") ? Common.getTestVariable("acn") : Common.getTestVariable("abn");
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName"), Common.getTestVariable("businessName"));
	}

}

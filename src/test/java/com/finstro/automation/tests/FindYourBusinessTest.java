package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.pages.BusinessDetailPage;
import com.finstro.automation.pages.FindYourBusinessPage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.pages.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

public class FindYourBusinessTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private FindYourBusinessPage findYourBusinessScreenPage;
	private SelectBusinessCardPage businessCardPage;
	

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		businessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		findYourBusinessScreenPage = new FindYourBusinessPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");
		
		toBusinessDetailPage();
	}
	
	public void toBusinessDetailPage() throws Exception {
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		businessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
	}

	@Test
	public void FPC_1321_VerifyUserRedirectToTheFindYourBusinessScreenSuccessful() throws Exception {
		businessDetailPage.redirectToTheFindYourBusinessScrees();
		assertTrue(findYourBusinessScreenPage.isActive(), "Find Your Business Page is not showed", "Find Your Business Page is showed");
	}
}
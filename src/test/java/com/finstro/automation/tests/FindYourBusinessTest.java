package com.finstro.automation.tests;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.pages.BusinessDetailPage;
import com.finstro.automation.pages.FindYourBusinessPage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.pages.BusinessCardPage;
import com.finstro.automation.setup.MobileTestSetup;

public class FindYourBusinessTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private FindYourBusinessPage findYourBusinessScreenPage;
	private BusinessCardPage businessCardPage;

	private static final String LOGIN_EMAIL_ADDRESS = "erick@finstro.com.au";
	private static final String LOGIN_ACCESS_CODE = "033933";

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		businessCardPage = new BusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		findYourBusinessScreenPage = new FindYourBusinessPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");
	}

	@Test
	public void FPC_1321_VerifyUserRedirectToTheFindYourBusinessScreenSuccessful() throws Exception {
		loginPage.doSuccessLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
		businessCardPage.ClickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Detail Page is not showed After Select A Card");
		businessDetailPage.redirectToTheFindYourBusinessScrees();
		assertTrue(findYourBusinessScreenPage.isActive(), "Find Your Business Page is not showed");
	}
}
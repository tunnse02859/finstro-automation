package com.finstro.automation.regression.tests;

import static com.finstro.automation.utility.Assertion.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.setup.MobileTestSetup;

public class Signup extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	
	@BeforeClass
	public void setupPage() throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
	}
	
	@Test
	public void Signup_02() throws Exception {
		registerPage.tickPrivacyPolicyAndTermsCondition();
		registerPage.verifyPrivacyPolicyAndTermsConditionsIsChecked();
	}

	@Test
	public void Signup_03() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(), "Login page didnt showed as default page in first installation",
				"Login page showed as default page");
	}
	
}
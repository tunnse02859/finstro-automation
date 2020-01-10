package com.finstro.automation.tests;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.pages.ForgotAccessCodePage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.setup.MobileTestSetup;

public class ForgotAccessCodeTest extends MobileTestSetup {
	private ForgotAccessCodePage forgotAccessCodePage;
	private LoginPage loginPage;
	private RegisterPage registerPage;

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		forgotAccessCodePage = new ForgotAccessCodePage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");
	}

	@Test
	public void FPC_1312_VerifyUserGoToTheLoginScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		loginPage.toForgotAccessCodePage();
		forgotAccessCodePage.isActive();
		forgotAccessCodePage.cancelForgetAccesCode();
		assertTrue(loginPage.isActive(),
				"Login Page didn't showed after Users click on Cancel Button on forget access code page");
	}

	@Test
	public void FPC_1311_VerifyUserSubmitTheForgotAccessCodeUnsuccessulIfLeaveBlankAllField() throws Exception {
		registerPage.toLoginPage();
		loginPage.toForgotAccessCodePage();
		forgotAccessCodePage.isActive();
		forgotAccessCodePage.submitResetAccessFormWithAllBlankFields();
		assertTrue(forgotAccessCodePage.isActive(),
				"Login Page didn't showed after Users click on Cancel Button on forget access code page");
	}

}

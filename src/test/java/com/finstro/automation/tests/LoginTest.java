package com.finstro.automation.tests;

import com.finstro.automation.pages.ForgotAccessCodePage;
import com.finstro.automation.pages.HomePage;
import com.finstro.automation.pages.LoginPINPage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.setup.MobileTestSetup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.lang.reflect.Method;

public class LoginTest extends MobileTestSetup {
	private ForgotAccessCodePage forgotAccessCodePage;
	private LoginPINPage loginPINPage;

	private static final String LOGIN_EMAIL_ADDRESS = "erick@finstro.com.au";
	private static final String LOGIN_ACCESS_CODE = "033933";

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		forgotAccessCodePage = new ForgotAccessCodePage(driver);
		homePage = new HomePage(driver);
		loginPINPage = new LoginPINPage(driver);
		assertTrue(registerPage.isActive(),"Register page didnt showed as default page in first installation");
	}

	@Test
	public void login01_VerifyUserLoginSuccessful() throws Exception {
		doLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
	}

	@Test
	public void login07_VerifyUserGoToTheSignUpScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(),"Login screen didnt showed after tap on login");
		loginPage.toRegisterPage();
		assertTrue(registerPage.isActive(),"Register page didnt showed after tap on sign up");
	}

	@Test
	public void login08_VerifyUserGoToTheForgotAccessCodeScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(),"Login screen didnt showed after tap on login");
		loginPage.toForgotAccessCodePage();
		assertTrue(forgotAccessCodePage.isActive(),"Forgot access code page didnt showed after tap on forget access code");
	}

	@Test
	public void login09_VerifyUserGoToTheLoginScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(),"Login page didnt showed after click on login");
	}

	// @Test
	// public void login12_VerifyUserGoToTheSignUpScreenSuccessfully() throws
	// Exception {
	// registerPage.toLoginPage();
	// //loginPage.toRegisterPage();
	// assertTrue(registerPage.isActive());
	// }

	@Test
	public void login14_VerifyThePINScreenIsOpenAfterLogoutAndReopenTheApp() throws Exception {
		doLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
		driver.relaunchApp();
		assertTrue(loginPINPage.isActive(),"Login PIN Page didnt showed after login and re-launch app");
		assertEquals(loginPINPage.getLoggedEmail(),LOGIN_EMAIL_ADDRESS,"incorrect email displayed after login and re-launch app");
	}
}

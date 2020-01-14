package com.finstro.automation.tests;

import com.finstro.automation.pages.ForgotAccessCodePage;
import com.finstro.automation.pages.LoginPINPage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.setup.MobileTestSetup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

public class LoginTest extends MobileTestSetup {
	private ForgotAccessCodePage forgotAccessCodePage;
	private LoginPINPage loginPINPage;
	private LoginPage loginPage;
	private RegisterPage registerPage;

	private static final String LOGIN_EMAIL_ADDRESS = "huongltd2105@gmail.com";
	private static final String LOGIN_ACCESS_CODE = "930947";
	private static final String LOGIN_FAILED_EXPECTED_MESSAGE = "ERROR, Incorrect username or password.";
	
	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		forgotAccessCodePage = new ForgotAccessCodePage(driver);
		loginPINPage = new LoginPINPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
	}

	@Test
	public void FPC_1290_VerifyUserLoginSuccessful() throws Exception {
		loginPage.doSuccessLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
	}

	@Test
	public void FPC_1292_VerifyUserLoginUnsuccessfulIfHeInputInvalidEmailAddress() throws Exception {
		String invalidEmail = LOGIN_EMAIL_ADDRESS + "extra";
		String expectedMessaged = "ERROR, User not found";
		registerPage.toLoginPage();
		
		loginPage.login(invalidEmail, LOGIN_ACCESS_CODE);
		loginPage.verifyErrorMessage(expectedMessaged);
	}

	@Test
	public void FPC_1293_VerifyUserLoginUnsuccessfulIfHeDoesNotInputEmailAddress() throws Exception {
		String invalidEmail = "";
		registerPage.toLoginPage();
		loginPage.login(invalidEmail, LOGIN_ACCESS_CODE);
		loginPage.verifyErrorMessage(LOGIN_FAILED_EXPECTED_MESSAGE);
	}

	@Test
	public void FPC_1294_VerifyUserLoginUnsuccessfulIfHeInputInvalidTheAccessCode() throws Exception {
		registerPage.toLoginPage();
		String invalidCode = "033999";
		loginPage.login(LOGIN_EMAIL_ADDRESS, invalidCode);
		loginPage.verifyErrorMessage(LOGIN_FAILED_EXPECTED_MESSAGE);
	}

	@Test
	public void FPC_1295_VerifyUserLoginUnsuccessfulIfHeDoesNotInputAccessCode() throws Exception {
		registerPage.toLoginPage();
		String invalidCode = "";
		loginPage.login(LOGIN_EMAIL_ADDRESS, invalidCode);
		loginPage.verifyErrorMessage(LOGIN_FAILED_EXPECTED_MESSAGE);
	}

	@Test
	public void FPC_1296_VerifyUserGoToTheSignUpScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after click on Login from register creen");
		loginPage.toRegisterPage();
		assertTrue(registerPage.isActive(), "Register screen didnt showed after tap on sign up",
				"Register screen showed after click on sign up from login screen");
	}

	@Test
	public void FPC_1297_VerifyUserGoToTheForgotAccessCodeScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after click on Login from register creen");
		loginPage.toForgotAccessCodePage();
		assertTrue(forgotAccessCodePage.isActive(),
				"Forgot access code screen didnt showed after tap on forget access code",
				"Forgot access code screen showed after click on forget access code");
	}

	@Test
	public void FPC_1298_VerifyUserGoToTheLoginScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(), "Login page didnt showed after click on login",
				"Login page showed after click on login");
	}

	@Test
	public void FPC_1299_VerifyUserLoginUnsuccessfulIfHeInputInvalidTheAccessCodePin() throws Exception {
		loginPage.doSuccessLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
		driver.relaunchApp();
		String invalidCode = "123456";
		loginPINPage.login(invalidCode);
		loginPINPage.verifyErrorMessage(LOGIN_FAILED_EXPECTED_MESSAGE);
	}

	@Test
	public void FPC_1300_VerifyUserLoginUnsuccessfulIfHeDoesNotInputAccessCodePin() throws Exception {
		if (driver.isAndroidDriver()) {
			loginPage.doSuccessLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
			driver.relaunchApp();
			loginPINPage.clickOnSubmit();
			loginPINPage.verifyErrorMessage(LOGIN_FAILED_EXPECTED_MESSAGE);
		}
	}

	@Test
	public void FPC_1301_VerifyUserGoToTheSignUpScreenSuccessfullyPin() throws Exception {
		loginPage.doSuccessLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
		driver.relaunchApp();
		loginPINPage.toRegisterPage();
		assertTrue(registerPage.isActive(), "Register Page didnt showed after click on Signup from PIN page",
				"Register Page showed after click on Signup from PIN page");
	}

	@Test
	public void FPC_1302_VerifyTheCurrentUserIsLogoutWhenClickOnTheLinkNotYou() throws Exception {
		loginPage.doSuccessLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
		driver.relaunchApp();
		loginPINPage.clickOnLoggedEmail();
		assertTrue(loginPage.isActive(), "login screen didnot showed after on logged email in PIN screen",
				"login screen showed after on logged email in PIN screen");
	}

}

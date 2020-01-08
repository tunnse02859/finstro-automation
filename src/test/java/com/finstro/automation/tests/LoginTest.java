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
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private HomePage homePage;

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
	public void FPC_1290_VerifyUserLoginSuccessful() throws Exception {
		loginPage.doLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
	}

	@Test
	public void FPC_1292_VerifyUserLoginUnsuccessfulIfHeInputInvalidEmailAddress() throws Exception {
		String invalidEmail = LOGIN_EMAIL_ADDRESS + "extra";
		registerPage.toLoginPage();
		loginPage.loginWithEmailCode(invalidEmail,LOGIN_ACCESS_CODE);
		String actualMessage = loginPage.getErrorMessage();
		String expectedMessage = "Incorrect username or password.";
		assertEquals(expectedMessage,actualMessage);
	}
	@Test
	public void FPC_1293_VerifyUserLoginUnsuccessfulIfHeDoesNotInputEmailAddress() throws Exception {
		String invalidEmail = "";
		registerPage.toLoginPage();
		loginPage.loginWithEmailCode(invalidEmail,LOGIN_ACCESS_CODE);
		String actualMessage = loginPage.getErrorMessage();
		String expectedMessage = "Incorrect username or password.";
		assertEquals(expectedMessage,actualMessage);
	}

	@Test
	public void FPC_1294_VerifyUserLoginUnsuccessfulIfHeInputInvalidTheAccessCode() throws Exception {
		registerPage.toLoginPage();
		String invalidCode = "033999";
		loginPage.loginWithEmailCode(LOGIN_EMAIL_ADDRESS,invalidCode);
		String actualMessage = loginPage.getErrorMessage();
		String expectedMessage = "Incorrect username or password.";
		assertEquals(expectedMessage,actualMessage);
	}

	@Test
	public void FPC_1295_VerifyUserLoginUnsuccessfulIfHeDoesNotInputAccessCode() throws Exception {
		registerPage.toLoginPage();
		String invalidCode = "";
		loginPage.loginWithEmailCode(LOGIN_EMAIL_ADDRESS,invalidCode);
		String actualMessage = loginPage.getErrorMessage();
		String expectedMessage = "Incorrect username or password.";
		assertEquals(expectedMessage,actualMessage);
	}

	@Test
	public void FPC_1296_VerifyUserGoToTheSignUpScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(),"Login screen didnt showed after tap on login");
		loginPage.toRegisterPage();
		assertTrue(registerPage.isActive(),"Register page didnt showed after tap on sign up");
	}

	@Test
	public void FPC_1297_VerifyUserGoToTheForgotAccessCodeScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(),"Login screen didnt showed after tap on login");
		loginPage.toForgotAccessCodePage();
		assertTrue(forgotAccessCodePage.isActive(),"Forgot access code page didnt showed after tap on forget access code");
	}

	@Test
	public void FPC_1298_VerifyUserGoToTheLoginScreenSuccessfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(),"Login page didnt showed after click on login");
	}

	@Test
	public void FPC_1299_VerifyUserLoginUnsuccessfulIfHeInputInvalidTheAccessCodePin() throws Exception {
		registerPage.toLoginPage();
		loginPage.loginWithEmailCode(LOGIN_EMAIL_ADDRESS,LOGIN_ACCESS_CODE);
		Thread.sleep(3000);
		driver.relaunchApp();
		String invalidCode = "123456";
		loginPINPage.login(invalidCode);
		String actualMessage = loginPINPage.getErrorMessage();
		String expectedMessage = "Incorrect username or password.";
		assertEquals(expectedMessage,actualMessage);
	}

	@Test
	public void FPC_1300_VerifyUserLoginUnsuccessfulIfHeDoesNotInputAccessCodePin() throws Exception {
		registerPage.toLoginPage();
		loginPage.loginWithEmailCode(LOGIN_EMAIL_ADDRESS,LOGIN_ACCESS_CODE);
		Thread.sleep(3000);
		driver.relaunchApp();
		loginPINPage.clickOnSubmit();
		String actualMessage = loginPINPage.getErrorMessage();
		String expectedMessage = "Incorrect username or password.";
		assertEquals(expectedMessage,actualMessage);
	}

	@Test
	public void FPC_1301_VerifyUserGoToTheSignUpScreenSuccessfullyPin() throws Exception {
		registerPage.toLoginPage();
		loginPage.loginWithEmailCode(LOGIN_EMAIL_ADDRESS,LOGIN_ACCESS_CODE);
		Thread.sleep(3000);
		driver.relaunchApp();
		loginPINPage.toRegisterPage();
		assertTrue(registerPage.isActive());
	}

	@Test
	public void FPC_1302_VerifyTheCurrentUserIsLogoutWhenClickOnTheLinkNotYou() throws Exception {
		registerPage.toLoginPage();
		loginPage.loginWithEmailCode(LOGIN_EMAIL_ADDRESS,LOGIN_ACCESS_CODE);
		Thread.sleep(3000);
		driver.relaunchApp();
		loginPINPage.clickOnLoggedEmail();
		assertTrue(loginPage.isActive());
	}


//	@Test
//	public void login14_VerifyThePINScreenIsOpenAfterLogoutAndReopenTheApp() throws Exception {
//		loginPage.doLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
//		driver.relaunchApp();
//		assertTrue(loginPINPage.isActive(),"Login PIN Page didnt showed after login and re-launch app");
//		assertEquals(loginPINPage.getLoggedEmail(),LOGIN_EMAIL_ADDRESS,"incorrect email displayed after login and re-launch app");
//	}
}

package com.finstro.automation.tests;

import com.finstro.automation.pages.login_process.ForgotAccessCodePage;
import com.finstro.automation.pages.login_process.LoginPINPage;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.setup.Constant;
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

	private static final String LOGIN_FAILED_EXPECTED_MESSAGE = "Incorrect username or password.";

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
	public void FPC_1290_LoginSuccessfully() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
	}

	@Test
	public void FPC_1292_Login_with_Invalid_Email() throws Exception {
		String invalidEmail = Constant.ONBOARDING_EMAIL_ADDRESS + "extra";
		registerPage.toLoginPage();
		loginPage.login(invalidEmail, Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
		String status = loginPage.getErrorMessage();
		assertContains(status, "Incorrect username or password", "Error message displayed incorrectly",
				"Error message displayed correctly");

	}

	@Test
	public void FPC_1293_Login_with_blank_Email() throws Exception {
		String invalidEmail = "";
		registerPage.toLoginPage();
		loginPage.login(invalidEmail, Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
		if (driver.isAndroidDriver()) {
			String status = loginPage.getErrorMessage();
			assertContains(status, LOGIN_FAILED_EXPECTED_MESSAGE, "Error message displayed incorrectly",
					"Error message displayed correctly");
		} else {
			assertTrue(loginPage.isActive(), "Login should be failed", "You're still on login page correctly");
		}
	}

	@Test
	public void FPC_1294_Login_with_invalid_Access_Code() throws Exception {
		registerPage.toLoginPage();
		String invalidCode = "087878";
		loginPage.login(Constant.ONBOARDING_EMAIL_ADDRESS, invalidCode);
		String status = loginPage.getErrorMessage();
		assertContains(status, "Incorrect username or password", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}

	@Test
	public void FPC_1295_Login_with_blank_Access_Code() throws Exception {
		registerPage.toLoginPage();
		loginPage.login(Constant.ONBOARDING_EMAIL_ADDRESS, "");
		if (driver.isAndroidDriver()) {
			String status = loginPage.getErrorMessage();
			assertContains(status, "Incorrect username or password", "Error message displayed incorrectly",
					"Error message displayed correctly");
		} else {
			assertTrue(loginPage.isActive(), "Login should be failed", "You're still on login page correctly");
		}
	}

	@Test
	public void FPC_1296_Verify_User_GoTo_SignUp_Screen_Successfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after click on Login from register creen");
		loginPage.toRegisterPage();
		assertTrue(registerPage.isActive(), "Register screen didnt showed after tap on sign up",
				"Register screen showed after click on sign up from login screen");
	}

	@Test
	public void FPC_1297_Verify_User_GoTo_ForgotAccessCode_Screen_Successfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after click on Login from register creen");
		loginPage.toForgotAccessCodePage();
		assertTrue(forgotAccessCodePage.isActive(),
				"Forgot access code screen didnt showed after tap on forget access code",
				"Forgot access code screen showed after click on forget access code");
	}

	@Test(priority = 0)
	public void FPC_1298_Verify_User_GoTo_Login_Screen_Successfully() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(), "Login page didnt showed after click on login",
				"Login page showed after click on login");
	}

	@Test
	public void FPC_1299_LoginPIN_Relogin_with_invalid_Access_Code() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		driver.wait(5);
		driver.relaunchApp();
		loginPINPage.login("123456");
		String status = loginPINPage.getErrorMessage();
		assertContains(status, "Incorrect username or password", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}

	@Test
	public void FPC_1300_LoginPIN_Relogin_with_blank_Access_Code() throws Exception {
		if (driver.isAndroidDriver()) {
			loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, "");
			driver.wait(5);
			driver.relaunchApp();
			loginPINPage.clickOnSubmit();
			String status = loginPINPage.getErrorMessage();
			assertContains(status, "Incorrect username or password", "Error message displayed incorrectly",
					"Error message displayed correctly");
		}
	}

	@Test
	public void FPC_1301_Verify_UserGoTo_SignUp_Screen_From_LoginPIN_Successfully() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS,
				Constant.ONBOARDING_ACCESS_CODE);
		driver.relaunchApp();
		loginPINPage.toRegisterPage();
		assertTrue(registerPage.isActive(), "Register Page didnt showed after click on Signup from PIN page",
				"Register Page showed after click on Signup from PIN page");
	}

	@Test
	public void FPC_1302_LoginPIN_User_can_login_with_another_account_when_click_on_not_you() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS,
				Constant.ONBOARDING_ACCESS_CODE);
		driver.relaunchApp();
		loginPINPage.clickOnLoggedEmail();
		assertTrue(loginPage.isActive(), "login screen didnot showed after on logged email in PIN screen",
				"login screen showed after on logged email in PIN screen");
	}

}

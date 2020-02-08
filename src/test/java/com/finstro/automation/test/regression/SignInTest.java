package com.finstro.automation.test.regression;

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

public class SignInTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;

	
	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
	}

	@Test
	public void Verify_User_Login_Successfully() throws Exception {
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}



}

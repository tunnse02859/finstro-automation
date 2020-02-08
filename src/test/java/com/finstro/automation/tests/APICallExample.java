package com.finstro.automation.tests;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.login_process.ForgotAccessCodePage;
import com.finstro.automation.pages.login_process.LoginPINPage;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.google.common.base.Verify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

public class APICallExample extends MobileTestSetup {
	private ForgotAccessCodePage forgotAccessCodePage;
	private LoginPINPage loginPINPage;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private FinstroAPI finstroAPI;

	private static final String LOGIN_EMAIL_ADDRESS = "erick@finstro.com.au";
	private static final String LOGIN_ACCESS_CODE = "033933";
	private static final String LOGIN_FAILED_EXPECTED_MESSAGE = "ERROR, Incorrect username or password.";

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.login(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE).then().verifyResponseCode(200)
				.extractJsonValue("accessToken").flush();
		finstroAPI.setAccessToken(Common.getTestVariable("accessToken",true));
	}

	@Test
	public void FPC_1292_VerifyUserLoginUnsuccessfulIfHeInputInvalidEmailAddress() throws Exception {
	}

}

package com.finstro.automation.tests.setting;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BankStatementDetailPage;
import com.finstro.automation.pages.on_boarding.BankStatementRetrieveAccountlPage;
import com.finstro.automation.pages.on_boarding.SelectBankStatementPage;
import com.finstro.automation.pages.settings.approval.SettingsApprovalBankUploadPage;
import com.finstro.automation.pages.settings.approval.SettingsApproval_BankAccountConnectedPage;
import com.finstro.automation.pages.settings.approval.SettingsApproval_MultipleDirectorsPage;
import com.finstro.automation.pages.settings.globalpayment.SettingGlobalPaymentDatePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

public class SettingsGlobalPaymentTests extends MobileTestSetup {

	private FinstroAPI finstroAPI;
	private SettingGlobalPaymentDatePage settingGlobalPaymentDatePage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS,
				Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		RegisterPage registerPage = new RegisterPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		// Login
		loginPage.doSuccessLogin(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS,
				Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
	}


	@Test(priority = 0)
	public void FPC_2814_SettingApproval_IDCheckStatus() throws Exception {
		// go to bank account connected screen
		settingGlobalPaymentDatePage = WorkFlows.goToGlobalPaymentDatePage(driver);

		// get API and verify status displayed on screen
		HtmlReporter.label("Verify Data on screen");
		settingGlobalPaymentDatePage.selectWeekly(true);
	}

}

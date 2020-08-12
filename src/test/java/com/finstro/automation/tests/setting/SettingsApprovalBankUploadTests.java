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

public class SettingsApprovalBankUploadTests extends MobileTestSetup {

	private FinstroAPI finstroAPI;
	private SettingsApprovalBankUploadPage settingsApprovalBankUploadPage;

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

	@DataProvider(name = "SettingApprove_IdCheck_02")
	public Object[][] SettingBusinessDetail_01() {
		return new Object[][] { { "auto@finstro.au", "0123456789", "phong", "trinh", "04/03/1989", "vietnam" } };

	}

	@Test(priority = 0)
	public void FPC_2814_SettingApproval_IDCheckStatus() throws Exception {
		// go to bank account connected screen
		settingsApprovalBankUploadPage = WorkFlows.goToApprovalBankUploadPage(driver);

		// get API and verify status displayed on screen
		HtmlReporter.label("Verify Approval status on screen");
		finstroAPI.getApprovalStatus();
		settingsApprovalBankUploadPage.verifyIDCheckStatusIsPass(Common.getTestVariable("idCheckStatus", true));
		settingsApprovalBankUploadPage
				.verifyBankAccountStatusIsPass(Common.getTestVariable("bankStatementStatus", true));
		// settingsApprovalBankUploadPage
		// .verifyDirectDebitStatusIsPass(Common.getTestVariable("directDebitAuthorityStatus",
		// true));
		settingsApprovalBankUploadPage
				.verifyCreditAssessmentStatusIsPass(Common.getTestVariable("creditAssessmentStatus", true));
	}

	@Test(priority = 3)
	public void FPC_2536_SettingApproval_Direct_user_to_ProfileDetail_when_touch_on_IDCheck() throws Exception {
		// go to bank account connected screen
		settingsApprovalBankUploadPage = WorkFlows.goToApprovalBankUploadPage(driver);

		// get API and verify status displayed on screen
		HtmlReporter.label("Verify Approval status on screen");
		assertTrue(settingsApprovalBankUploadPage.isActive(), "Setting Approval Bank Upload screen is not displayed",
				"Setting Approval Bank Upload screen is not displayed");

		HtmlReporter.label("Tap on ID Check and verify Profile Detail screen is displayed");
		SettingProfile_ProfileDetailPage setingProfile = settingsApprovalBankUploadPage.gotoIDCheckPage();
		assertTrue(setingProfile.isActive(), "Setting Profile detail is not displayed",
				"Setting profile is displayed correctly");
	}

	@Test(priority = 2)
	public void FPC_1962_SettingApproval_BankAccountConnected_Verify_Bank_Accounts() throws Exception {
		// go to bank account connected screen
		settingsApprovalBankUploadPage = WorkFlows.goToApprovalBankUploadPage(driver);
		SettingsApproval_BankAccountConnectedPage settingBankAccountConnectedPage = settingsApprovalBankUploadPage
				.gotoBankAccountPage();
		assertTrue(settingBankAccountConnectedPage.isActive(), "Bank Account Connected screen is not displayed",
				"Bank Account Connected screen is displayed");

		// get bank account infor
		HtmlReporter.label("Verify data on screen with data from API");
		JSONArray bankAccounts = finstroAPI.getBankAccountsInfo();
		settingBankAccountConnectedPage.verifyBankAccountDisplayed(bankAccounts);
	}

	@Test(priority = 1)
	public void FPC_3181_SettingApproval_BankAccountConnected_Resubmit_bank_account() throws Exception {
		// go to bank account connected screen
		settingsApprovalBankUploadPage = WorkFlows.goToApprovalBankUploadPage(driver);
		SettingsApproval_BankAccountConnectedPage settingBankAccountConnectedPage = settingsApprovalBankUploadPage
				.gotoBankAccountPage();
		assertTrue(settingBankAccountConnectedPage.isActive(), "Bank Account Connected screen is not displayed",
				"Bank Account Connected screen is displayed");

		// Go to bank statement
		HtmlReporter.label("Start re-submit bank statement");
		SelectBankStatementPage selectBankStatementPage = settingBankAccountConnectedPage.clickSubmitBankAccount();
		assertTrue(selectBankStatementPage.isActive(), "Bank Statement screen is not displayed",
				"Bank Statement screen is displayed");

		// search for bank demo and select it
		HtmlReporter.label("Search for bank demo and login");
		selectBankStatementPage.inputSearch("demo");
		BankStatementDetailPage bankStatementDetailPage = selectBankStatementPage.selectBankDemo();
		assertTrue(bankStatementDetailPage.isActive(), "Bank Statement login screen is not displayed",
				"Bank Statement login screen is displayed");

		// login bank statement and submit
		bankStatementDetailPage.inputUsername("12345678");
		bankStatementDetailPage.inputPassword("TestMyMoney");
		bankStatementDetailPage.acceptTern();
		BankStatementRetrieveAccountlPage retrievePage = bankStatementDetailPage.clickSubmit();
		HtmlReporter.label("Verify retrieve screen displayed after login and submit it!");
		assertTrue(retrievePage.isActive(), "Retrieve screen is not displayed", "Retrieve screen is displayed");

		// submit retrieve, verify all done screen displayed and popup displayed
		retrievePage.clickSubmit();
		assertTrue(retrievePage.isDone(), "Retrieve All Done screen is not displayed",
				"Retrieve All Done screen is displayed");
		driver.wait(5);
		if (driver.isIOSDriver()) {
			retrievePage.verifyPopupBankStatementConnected();
		}
		assertTrue(settingBankAccountConnectedPage.isActive(), "Bank Account Connected screen is not displayed",
				"Bank Account Connected screen is displayed");
	}

	@Test
	public void FPC_3164_SettingApproval_MultipleDirectors_Update_Email_and_Phone() throws Exception {
		// go to bank account connected screen
		settingsApprovalBankUploadPage = WorkFlows.goToApprovalBankUploadPage(driver);
		SettingsApproval_MultipleDirectorsPage settingMultipleDirectorPage = settingsApprovalBankUploadPage
				.gotoMultipleDirectorsPage();
		JSONArray directorData = finstroAPI.getDirectors();
		for (int i = 0; i < directorData.length(); i++) {
			JSONObject director = directorData.getJSONObject(i);
			String directFirstName = director.get("firstName").toString().equals("null") ? "" : director.get("firstName").toString(); 
			String directLastName = director.get("lastName").toString().equals("null") ? "" : director.get("lastName").toString(); 
			String directMiddleName = director.get("middleName").toString().equals("null") ? "" : director.get("middleName").toString();
			String directorName = directFirstName + " " + directMiddleName + " " + directLastName;
			assertTrue(settingMultipleDirectorPage.isDirectorDisplayed(String.valueOf(i+1), directorName),
					String.format("Director [%s] is not dislpayed", directorName),
					String.format("Director [%s] is dislpayed", directorName));
		}
	}
}

package com.finstro.automation.test.regression;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.common.CommonFunction;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.excelhelper.ExcelHelper;
import com.finstro.automation.pages.home.MainNavigator;
import com.finstro.automation.pages.login_process.ForgotAccessCodePage;
import com.finstro.automation.pages.login_process.LoginPINPage;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.FindAddressPage;
import com.finstro.automation.pages.on_boarding.FindBusinessPage;
import com.finstro.automation.pages.on_boarding.MedicarePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.pages.settings.SettingsPage;
import com.finstro.automation.pages.settings.approval.SettingsApprovalBankUploadPage;
import com.finstro.automation.pages.settings.approval.SettingsApprovalIDCheck_ProfileDetailsPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsSecondPage;
import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.FilePaths;
import com.finstro.automation.utility.PropertiesLoader;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;
import java.util.HashMap;

public class SettingsApprovalBankUploadTests extends MobileTestSetup {

	private FinstroAPI finstroAPI;
	private SettingsApprovalBankUploadPage settingsApprovalBankUploadPage;
	private SettingsApprovalIDCheck_ProfileDetailsPage settingsApprovalIDCheck_ProfileDetailsPage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		RegisterPage registerPage = new RegisterPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		// Login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		// Go to the Approval/Bank Upload page
		settingsApprovalBankUploadPage = WorkFlows.goToApprovalBankUploadPage(driver);

	}

	@DataProvider(name = "SettingApprove_IdCheck_02")
	public Object[][] SettingBusinessDetail_01() {
		return new Object[][] { { "auto@finstro.au", "0123456789", "phong", "trinh", "04/03/1989", "vietnam" } };

	}

	@Test(dataProvider = "SettingApprove_IdCheck_02")
	public void SettingApprove_IdCheck_02(String email, String mobile, String firstname, String lastname,
			String dob, String residentialAddress) throws Exception {

		// Go to ID check page
		settingsApprovalIDCheck_ProfileDetailsPage = settingsApprovalBankUploadPage.gotoIDCheckPage();
		
		// Check Email
		settingsApprovalIDCheck_ProfileDetailsPage.setEmailAddress(email);
		assertEquals(settingsApprovalIDCheck_ProfileDetailsPage.getEmailAddress(), email,
				"Email field is not set sucessfully", "Email field is set to " + email);

		// Check Mobile Number
		settingsApprovalIDCheck_ProfileDetailsPage.setMobileNumber(mobile);
		assertEquals(settingsApprovalIDCheck_ProfileDetailsPage.getMobileNumber(), mobile, "Mobile field is not set sucessfully",
				"Mobile field is set to " + mobile);

		// Check First name
		settingsApprovalIDCheck_ProfileDetailsPage.setFirstName(firstname);
		assertEquals(settingsApprovalIDCheck_ProfileDetailsPage.getFirstName(), firstname, "firstname field is not set sucessfully",
				"firstname field is set to " + firstname);

		// Check last name
		settingsApprovalIDCheck_ProfileDetailsPage.setLastName(lastname);
		assertEquals(settingsApprovalIDCheck_ProfileDetailsPage.getLastName(), lastname,
				"lastname field is not set sucessfully", "lastname field is set to " + lastname);

		// Check dob
		settingsApprovalIDCheck_ProfileDetailsPage.setDOB(dob);;
		assertEquals(settingsApprovalIDCheck_ProfileDetailsPage.getDOB(), dob, "DOB field is not set sucessfully",
				"DOB field is set to " + dob);

	}

}

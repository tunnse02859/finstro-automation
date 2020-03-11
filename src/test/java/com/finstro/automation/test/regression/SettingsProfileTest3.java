package com.finstro.automation.test.regression;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.api.ProfileInforAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.profile.SettingProfile_DrivingLicensePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_MedicarePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;
import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class SettingsProfileTest3 extends MobileTestSetup {

	private ProfileInforAPI profileAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SettingProfile_ProfileDetailPage settingProfilePage;
	private SettingProfile_DrivingLicensePage settingProfileDrivingLicencePage;
	private SettingProfile_MedicarePage settingProfileMedicarePage;

	@BeforeClass
	public void beforeClass() throws Exception {
		super.beforeClass();
		profileAPI = new ProfileInforAPI();
		profileAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);

		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);

	}

	@Test
	public void SettingProfile_01_VerifyUserCanEditTheProfileInfomation() throws Exception {
		
	}

}
package com.finstro.automation.test.regression;

import org.testng.annotations.Test;
import org.testng.Assert;
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

public class SettingsProfileTest extends MobileTestSetup {

	private ProfileInforAPI profileAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SettingProfile_ProfileDetailPage settingProfilePage;
	private SettingProfile_DrivingLicensePage settingProfileDrivingLicencePage;
	private SettingProfile_MedicarePage settingProfileMedicarePage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		profileAPI = new ProfileInforAPI();
		profileAPI.loginForAccessToken(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS,
				Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);

		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Login
		loginPage.doSuccessLogin(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS,
				Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);

	}

	@Test
	public void SettingProfile_01_VerifyProfileDetails() throws Exception {
		settingProfilePage = WorkFlows.goToTheSettingProfilePage(driver);

		// verify data on screen with API
		profileAPI.recoveryData().then()
				.verifyJsonNodeEqual("contacts.firstGivenName", settingProfilePage.getFirstName())
				.verifyJsonNodeEqual("contacts.familyName", settingProfilePage.getLastName())
				.verifyJsonNodeEqual("contacts.mobilePhoneNumber", settingProfilePage.getPhoneNumber())
				.verifyJsonNodeEqual("contacts.emailAddress", settingProfilePage.getEmail());

		assertEquals(settingProfilePage.getResidential(), profileAPI.getResidentialAddress(),
				"Residential address matches", "Residential address doesn't match");

	}

	@Test
	public void SettingProfile_02_VerifyDrivingLicenceInformation() throws Exception {

		settingProfilePage = WorkFlows.goToTheSettingProfilePage(driver);

		// Go to the second setting driving license page
		settingProfileDrivingLicencePage = settingProfilePage.toSettingDrivingLicensePage();
		assertTrue(settingProfileDrivingLicencePage.isActive(),
				"Seting Profile - Driving License screen is not displayed",
				"Seting Profile - Driving License screen is displayed");

		// verify data on screen with API
		profileAPI.recoveryData().then()
				.verifyJsonNodeEqual("drivingLicence.gender", settingProfileDrivingLicencePage.getGender())
				.verifyJsonNodeEqual("drivingLicence.firstName", settingProfileDrivingLicencePage.getFirstName())
				.verifyJsonNodeEqual("drivingLicence.surname", settingProfileDrivingLicencePage.getLastName())
				.verifyJsonNodeEqual("drivingLicence.middleName", settingProfileDrivingLicencePage.getMiddleName())
				.verifyJsonNodeEqual("drivingLicence.state", settingProfileDrivingLicencePage.getState())
				.verifyJsonNodeEqual("drivingLicence.dateOfBirth", settingProfileDrivingLicencePage.getDob())
				.verifyJsonNodeEqual("drivingLicence.licenceNumber",
						settingProfileDrivingLicencePage.getDriverLicenseNumber())
				.verifyJsonNodeEqual("drivingLicence.validTo", settingProfileDrivingLicencePage.getExpireDate());

	}

	@Test
	public void SettingProfile_03_VerifyMedicareInformation() throws Exception {

		boolean resetData = false;
		settingProfilePage = WorkFlows.goToTheSettingProfilePage(driver);

		// Go to the setting medicare page
		settingProfileDrivingLicencePage = settingProfilePage.toSettingDrivingLicensePage();
		settingProfileMedicarePage = settingProfileDrivingLicencePage.toSettingMedicarePage();
		assertTrue(settingProfileMedicarePage.isActive(), "Seting Profile - Medicare screen is not displayed",
				"Seting Profile - Medicare screen is displayed");

		// If user doesn't have medicare
		if (profileAPI.recoveryData().then().getStringValueFromResponseJson("medicareCard").equals("")) {

			Assert.assertTrue(settingProfileMedicarePage.getFirstName().equals(""), "Firstname matches");
			Assert.assertTrue(settingProfileMedicarePage.getMiddleName().equals(""), "Middle name matches");
			Assert.assertTrue(settingProfileMedicarePage.getLastName().equals(""), "Last name matches");
			Assert.assertTrue(settingProfileMedicarePage.getGender().equals(""), "Gender matches");
			Assert.assertTrue(settingProfileMedicarePage.getDOB().equals(""), "D.O.B matches");
			Assert.assertTrue(settingProfileMedicarePage.getCardColor().equals(""), "Card Color matches");
			Assert.assertTrue(settingProfileMedicarePage.getMedicareNumber().equals(""), "Medicar number matches");
			Assert.assertTrue(settingProfileMedicarePage.getReferenceNumber().equals(""), "Reference number matches");
			Assert.assertTrue(settingProfileMedicarePage.getExpiryDate().equals(""), "Expiry date matches");

		}
		// If user has medicare
		else {

		}
	}

}
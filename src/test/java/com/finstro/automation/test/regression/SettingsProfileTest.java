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
		settingProfilePage = WorkFlows.goToTheSettingProfilePage(driver);

		// verify data on screen with API
		profileAPI.getProfileDetailInfor();
		settingProfilePage.verifyProfileInfor(Common.getTestVariable("contacts.firstGivenName", true),
				Common.getTestVariable("contacts.familyName", true),
				Common.getTestVariable("contacts.emailAddress", true),
				Common.getTestVariable("contacts.mobilePhoneNumber", true));

		// Input data
		settingProfilePage.inputProfileInfor("Phong", "Trinh");
	}

	@DataProvider(name = "SettingProfile02")
	public Object[][] SettingProfile_02_DataProvider() {
		return new Object[][] {
				{ "Female", "Phong", "Trinh", "Van", "ACT", "01/01/2021", "0123456789", "20/07/2025" } };
	}

	@Test(dataProvider = "SettingProfile02")
	public void SettingProfile_02_VerifyUserCanUpdateTheDrivingLicenceInformation(String genderName,
			String firstNameString, String lastNameString, String middleNameString, String stateName, String dobString,
			String licenseNumberString, String expireDateString) throws Exception {

		boolean resetData = false;
		settingProfilePage = WorkFlows.goToTheSettingProfilePage(driver);

		// Go to the second setting driving license page
		settingProfileDrivingLicencePage = settingProfilePage.toSettingDrivingLicensePage();
		assertTrue(settingProfileDrivingLicencePage.isActive(),
				"Seting Profile - Driving License screen is not displayed",
				"Seting Profile - Driving License screen is displayed");
		try {
			// verify data displayed on screen with API
			profileAPI.getDrivingLicenceInfor();

			settingProfileDrivingLicencePage.verifyDriverLicenseInfor(
					Common.getTestVariable("gender", true).equalsIgnoreCase("M") ? "Male" : "Female",
					Common.getTestVariable("firstName", true), Common.getTestVariable("surname", true),
					Common.getTestVariable("middleName", true), Common.getTestVariable("state", true),
					Common.getTestVariable("dateOfBirth", true), Common.getTestVariable("licenceNumber", true),
					Common.getTestVariable("validTo", true));

			// input data
			settingProfileDrivingLicencePage.inputDriverLicenseInfor(genderName, firstNameString, lastNameString,
					middleNameString, stateName, dobString, licenseNumberString, expireDateString);

			// click next and verify data saved
			settingProfileDrivingLicencePage.clickSaveSetting();
			Thread.sleep(10000);
			resetData = true;
			profileAPI.recoveryData().then().verifyResponseCode(200)
					.verifyJsonNodeEqual("drivingLicence.firstName", firstNameString)
					.verifyJsonNodeEqual("drivingLicence.surname", lastNameString)
					.verifyJsonNodeEqual("drivingLicence.middleName", middleNameString)
					.verifyJsonNodeEqual("drivingLicence.gender", genderName.equalsIgnoreCase("Female") ? "F" : "M")
					.verifyJsonNodeEqual("drivingLicence.licenceNumber", licenseNumberString)
					.verifyJsonNodeEqual("drivingLicence.state", stateName).flush();
			// .verifyJsonNodeEqual("drivingLicence.dateOfBirth", "2021/01/01")
			// .verifyJsonNodeEqual("drivingLicence.validTo", "08/2020").flush();
			
		} finally {
			if (resetData) {
				Log.info("---- reset driving license data ----");
				profileAPI.saveDrivingLicense(Common.getTestVariable("driverLicenseJson", true));
			}
		}
	}

	@DataProvider(name = "SettingProfile_03")
	public Object[][] SettingProfile_03_DataProvider() {
		return new Object[][] {
				{ "Phong", "Van", "Trinh", "Female", "27/02/1983", "Green", "2684483925", "1", "03/2020" } };
	}

	@Test(dataProvider = "SettingProfile_03")
	public void SettingProfile_03_VerifyUserCanUpdateTheMedicareInformation(String firstNameString,
			String middleNameString, String lastNameString, String genderName, String dobString, String cardColor,
			String medicareNumberString, String referenceNumberString, String expireDateString) throws Exception {

		boolean resetData = false;
		settingProfilePage = WorkFlows.goToTheSettingProfilePage(driver);

		// Go to the setting medicare page
		settingProfileDrivingLicencePage = settingProfilePage.toSettingDrivingLicensePage();
		settingProfileMedicarePage = settingProfileDrivingLicencePage.toSettingMedicarePage();
		assertTrue(settingProfileMedicarePage.isActive(), "Seting Profile - Medicare screen is not displayed",
				"Seting Profile - Medicare screen is displayed");

		try {
			// call API and verify data
			profileAPI.getMedicareInfor();
			settingProfileMedicarePage.verifyMedicareInfor(Common.getTestVariable("firstName", true),
					Common.getTestVariable("middleInitial", true), Common.getTestVariable("surname", true),
					Common.getTestVariable("gender", true).equalsIgnoreCase("M") ? "Male" : "Female",
					Common.getTestVariable("dateOfBirth", true), Common.getTestVariable("cardColor", true),
					Common.getTestVariable("cardNumber", true), Common.getTestVariable("cardNumberRef", true),
					Common.getTestVariable("validTo", true));

			// input data
			settingProfileMedicarePage.inputMedicareInfor(firstNameString, middleNameString, lastNameString, genderName,
					dobString, cardColor, medicareNumberString, referenceNumberString, expireDateString);

			// click save and verify
			settingProfileMedicarePage.clickSaveSetting();
			resetData = true;
			profileAPI.recoveryData().then().verifyResponseCode(200)
					.verifyJsonNodeEqual("medicareCard.identificationId", "null")
					.verifyJsonNodeEqual("medicareCard.cardNumber", medicareNumberString)
					.verifyJsonNodeEqual("medicareCard.cardNumberRef", referenceNumberString)
					.verifyJsonNodeEqual("medicareCard.firstName", firstNameString)
					.verifyJsonNodeEqual("medicareCard.middleInitial", middleNameString)
					.verifyJsonNodeEqual("medicareCard.surname", lastNameString)
					// .verifyJsonNodeEqual("medicareCard.dateOfBirth", "1983-02-27")
					.verifyJsonNodeEqual("medicareCard.gender", genderName.equalsIgnoreCase("Female") ? "F" : "M")
					// .verifyJsonNodeEqual("medicareCard.validTo", "2020-03-01")
					.flush();
			
		} finally {
			if (resetData) {
				Log.info("---- reset driving license data ----");
				profileAPI.saveMedicare(Common.getTestVariable("medicareJson", true));
			}
		}
	}

}
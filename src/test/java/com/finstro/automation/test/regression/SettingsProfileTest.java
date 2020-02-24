package com.finstro.automation.test.regression;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.profile.SettingProfile_DrivingLicensePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_MedicarePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class SettingsProfileTest extends MobileTestSetup {

	private FinstroAPI finstroAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SettingProfile_ProfileDetailPage settingProfilePage;
	private SettingProfile_DrivingLicensePage settingProfileDrivingLicencePage;
	private SettingProfile_MedicarePage settingProfileMedicarePage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
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
		finstroAPI.getProfileDetailInfor();
		settingProfilePage.verifyProfileInfor(Common.getTestVariable("contacts.firstGivenName", true),
				Common.getTestVariable("contacts.familyName", true),
				Common.getTestVariable("contacts.emailAddress", true),
				Common.getTestVariable("contacts.mobilePhoneNumber", true));

		// Input data
		settingProfilePage.inputProfileInfor("Phong", "Trinh");
	}

	@Test
	public void SettingProfile_02_VerifyUserCanEditTheDrivingLicenceInformation() throws Exception {
		settingProfilePage = WorkFlows.goToTheSettingProfilePage(driver);

		// Go to the second setting driving license page
		settingProfileDrivingLicencePage = settingProfilePage.toSettingDrivingLicensePage();
		assertTrue(settingProfileDrivingLicencePage.isActive(),
				"Seting Profile - Driving License screen is not displayed",
				"Seting Profile - Driving License screen is displayed");

		// verify data displayed on screen with API
		finstroAPI.getDrivingLicenceInfor();
		settingProfileDrivingLicencePage.verifyDriverLicenseInfor(
				Common.getTestVariable("gender", true).equalsIgnoreCase("M") ? "Male" : "Female",
				Common.getTestVariable("firstName", true), Common.getTestVariable("surname", true),
				Common.getTestVariable("middleName", true), Common.getTestVariable("state", true),
				Common.getTestVariable("dateOfBirth", true), Common.getTestVariable("licenceNumber", true),
				Common.getTestVariable("validTo", true));

		// input data
		String genderName = "Male";
		String firstNameString = "Phong";
		String lastNameString = "Trinh";
		String middleNameString = "Van";
		String stateName = "ACT";
		String dobString = "01/01/2021";
		String licenseNumberString = "0123456789";
		String expireDateString = "23/08/2020";
		settingProfileDrivingLicencePage.inputDriverLicenseInfor(genderName, firstNameString, lastNameString,
				middleNameString, stateName, dobString, licenseNumberString, expireDateString);

		// click next and verify data saved
		settingProfileDrivingLicencePage.clickSaveSetting();
		Thread.sleep(10000);

		finstroAPI.recoveryData().then().verifyResponseCode(200)
				.verifyJsonNodeEqual("drivingLicence.firstName", "Phong")
				.verifyJsonNodeEqual("drivingLicence.surname", "Trinh")
				.verifyJsonNodeEqual("drivingLicence.middleName", "Van")
				.verifyJsonNodeEqual("drivingLicence.gender", "M")
				.verifyJsonNodeEqual("drivingLicence.licenceNumber", "0123456789")
				.verifyJsonNodeEqual("drivingLicence.state", "ACT").flush();
		// .verifyJsonNodeEqual("drivingLicence.dateOfBirth", "2021/01/01")
		// .verifyJsonNodeEqual("drivingLicence.validTo", "08/2020").flush();
	}

	@Test
	public void SettingProfile_03_VerifyUserCanEditTheMedicareInformation() throws Exception {
		settingProfilePage = WorkFlows.goToTheSettingProfilePage(driver);
		// Go to the setting medicare page
		settingProfileDrivingLicencePage = settingProfilePage.toSettingDrivingLicensePage();
		settingProfileMedicarePage = settingProfileDrivingLicencePage.toSettingMedicarePage();
		assertTrue(settingProfileMedicarePage.isActive(), "Seting Profile - Medicare screen is not displayed",
				"Seting Profile - Medicare screen is displayed");

		// call API and verify data
		finstroAPI.getMedicareInfor();
		settingProfileMedicarePage.verifyMedicareInfor(Common.getTestVariable("firstName", true),
				Common.getTestVariable("middleInitial", true), Common.getTestVariable("surname", true),
				Common.getTestVariable("gender", true).equalsIgnoreCase("M") ? "Male" : "Female",
				Common.getTestVariable("dateOfBirth", true), Common.getTestVariable("cardColor", true),
				Common.getTestVariable("cardNumber", true), Common.getTestVariable("cardNumberRef", true),
				Common.getTestVariable("validTo", true));

		// input data
		String firstNameString = "Phong";
		String middleNameString = "Van";
		String lastNameString = "Trinh";
		String genderName = "Male";
		String dobString = "27/02/1983";
		String cardColor = "Green";
		String medicareNumberString = "2684483925";
		String referenceNumberString = "1";
		String expireDateString = "03/2020";
		settingProfileMedicarePage.inputMedicareInfor(firstNameString, middleNameString, lastNameString, genderName,
				dobString, cardColor, medicareNumberString, referenceNumberString, expireDateString);

		// click save and verify
		settingProfileMedicarePage.clickSaveSetting();
		finstroAPI.recoveryData().then().verifyResponseCode(200)
				.verifyJsonNodeEqual("medicareCard.identificationId", "null")
				.verifyJsonNodeEqual("medicareCard.cardNumber", "2684483925")
				.verifyJsonNodeEqual("medicareCard.cardNumberRef", "1")
				.verifyJsonNodeEqual("medicareCard.firstName", "Phong")
				.verifyJsonNodeEqual("medicareCard.middleInitial", "Van")
				.verifyJsonNodeEqual("medicareCard.surname", "Trinh")
				// .verifyJsonNodeEqual("medicareCard.dateOfBirth", "1983-02-27")
				 .verifyJsonNodeEqual("medicareCard.gender", "M")
				// .verifyJsonNodeEqual("medicareCard.validTo", "2020-03-01")
				.flush();
	}

}
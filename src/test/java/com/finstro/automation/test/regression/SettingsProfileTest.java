package com.finstro.automation.test.regression;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;

import com.finstro.automation.api.APIResponse;
import com.finstro.automation.api.ProfileInforAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.profile.SettingProfile_DrivingLicensePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_MedicarePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

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
		HtmlReporter.label("Verify data on screen with data from API");
		profileAPI.recoveryData().then()
				.verifyJsonNodeEqual("contacts.firstGivenName", settingProfilePage.getFirstName())
				.verifyJsonNodeEqual("contacts.familyName", settingProfilePage.getLastName())
				.verifyJsonNodeEqual("contacts.mobilePhoneNumber", settingProfilePage.getPhoneNumber())
				.verifyJsonNodeEqual("contacts.emailAddress", settingProfilePage.getEmail());

		assertContains(settingProfilePage.getResidentialAddress(), profileAPI.getResidentialAddress(),
				"Residential address matched", "Residential address doesn't match");

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
		HtmlReporter.label("Verify data on screen with data from API");
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
		APIResponse recoveryDataResponse = profileAPI.recoveryData().then();
		HtmlReporter.label("Verify data on screen with data from API");
		// If user doesn't have medicare
		if (recoveryDataResponse.getStringValueFromResponseJson("medicareCard").equals("")) {
			recoveryDataResponse.verifyResponseCode(200).flush();
			assertEquals(settingProfileMedicarePage.getFirstName(),"", "Firstname doesn't match", "Firstname matched");
			assertEquals(settingProfileMedicarePage.getMiddleName(),"", "Middle name doesnt match","Middle name match");
			assertEquals(settingProfileMedicarePage.getLastName(),"", "Last name doesnt match","Last name  matched");
			assertEquals(settingProfileMedicarePage.getGender(),"", "Gender doesnt match","Gender matched");
			assertEquals(settingProfileMedicarePage.getDOB(),"", "D.O.B doesnt match","D.O.B matched");
			assertEquals(settingProfileMedicarePage.getCardColor(),"", "Card Color doesnt match","Card Color matched");
			assertEquals(settingProfileMedicarePage.getMedicareNumber(),"", "Medicar number doesnt match","Medicar number matched");
			assertEquals(settingProfileMedicarePage.getReferenceNumber(),"", "Reference number doesnt match","Reference number matched");
			assertEquals(settingProfileMedicarePage.getExpiryDate(),"", "Expiry date doesnt match","Expiry date matched");

		}
		// If user has medicare
		else {
			recoveryDataResponse
				.verifyResponseCode(200)
				.verifyJsonNodeEqual("medicareCard.cardColor",settingProfileMedicarePage.getCardColor())
				.verifyJsonNodeEqual("medicareCard.cardNumber",settingProfileMedicarePage.getMedicareNumber())
				.verifyJsonNodeEqual("medicareCard.cardNumberRef",settingProfileMedicarePage.getReferenceNumber())
				.verifyJsonNodeEqual("medicareCard.dateOfBirth",settingProfileMedicarePage.getDOB())
				.verifyJsonNodeEqual("medicareCard.firstName",settingProfileMedicarePage.getFirstName())
				.verifyJsonNodeEqual("medicareCard.gender",settingProfileMedicarePage.getGender())
				.verifyJsonNodeEqual("medicareCard.middleInitial",settingProfileMedicarePage.getMiddleName())
				.verifyJsonNodeEqual("medicareCard.surname",settingProfileMedicarePage.getLastName())
				.verifyJsonNodeEqual("medicareCard.validTo",settingProfileMedicarePage.getExpiryDate())
			.flush();
		}
	}

}
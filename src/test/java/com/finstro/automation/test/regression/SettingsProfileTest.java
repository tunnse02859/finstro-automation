package com.finstro.automation.test.regression;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;
import com.finstro.automation.pages.home.HomePage;
import com.finstro.automation.pages.home.MainNavigator;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.pages.settings.SettingsPage;
import com.finstro.automation.pages.settings.profile.SettingProfile_DrivingLicensePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_MedicarePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.test.regression.SettingBusinessDetailsTests.BUSINESS_DETAIL;
import com.finstro.automation.utility.Common;

public class SettingsProfileTest extends MobileTestSetup {

	private FinstroAPI finstroAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SettingsPage settingPage;
	private SelectBusinessCardPage selectBusinessCardPage;
	private BusinessDetailPage businessDetailPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
	private CompleteAgreementPage completeAgreementPage;
	private SettingProfile_ProfileDetailPage settingProfilePage;
	private SettingProfile_DrivingLicensePage settingProfileDrivingLicencePage;
	private SettingProfile_MedicarePage settingProfileMedicarePage;

	class PROFILE_DETAIL {

		public static final String EMAIL_ADRRESS = "EMAIL_ADRRESS";
		public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
		public static final String FIRST_NAME = "FIRST_NAME";
		public static final String LAST_NAME = "LAST_NAME";
		public static final String D_O_B = "D_O_B";
		public static final String RESIDENTIAL_ADDRESS = "RESIDENTIAL_ADDRESS";

	}

	@DataProvider(name = "SettingProfileDetail_Or")
	public Object[][] SettingProfileDetail_Or() {
		return new Object[][] { { "erick@finstro.au", "+61435690919", "Erick", "Vavretchek", "01/01/2021",
				"60 Margaret, St , SYDNEY, NSW, 2000, AUS," } };
	}

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
		selectBusinessCardPage = loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);

	}

	private void toSettingProfilePage() throws Exception {
		// goto Business Details page
		businessDetailPage = selectBusinessCardPage.clickOnCard("500");
		residentialAddressPage = businessDetailPage.clickNext();
		photoIDPage = residentialAddressPage.clickNext();
		// wait for image load
		Thread.sleep(10000);
		drivingLisencePage = photoIDPage.clickNext();
		postalAddressPage = drivingLisencePage.clickNext();
		postalAddressPage.clickNext();
		completeAgreementPage = new CompleteAgreementPage(driver);
		completeAgreementPage.confirmAgreement();
		MainNavigator navigator = new MainNavigator(driver);
		settingPage = navigator.gotoSettingsPage();
		settingProfilePage = settingPage.goToProfileDetailsPage();

		assertTrue(settingProfilePage.isActive(), "Seting Profile screen is not displayed",
				"Seting Profile screen is displayed");
	}

	@Test
	public void SettingProfile_01_VerifyUserCanEditTheProfileInfomation() throws Exception {
		toSettingProfilePage();
		
		//verify data on screen with API
		finstroAPI.getProfileDetailInfor();
		settingProfilePage.verifyProfileInfor(
				Common.getTestVariable("contacts.firstGivenName", true),
				Common.getTestVariable("contacts.familyName", true),
				Common.getTestVariable("contacts.emailAddress", true),
				Common.getTestVariable("contacts.mobilePhoneNumber", true));
		
		//Input data
		settingProfilePage.inputProfileInfor("Phong", "Trinh");
	}

	@Test
	public void SettingProfile_02_VerifyUserCanEditTheDrivingLicenceInformation() throws Exception {
		toSettingProfilePage();

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
				.verifyJsonNodeEqual("drivingLicence.middleName", "Van").flush();
		// .verifyJsonNodeEqual("drivingLicence.gender", "M")
		// .verifyJsonNodeEqual("drivingLicence.dateOfBirth", "2021/01/01")
		// .verifyJsonNodeEqual("drivingLicence.licenceNumber", "0123456789")
		// .verifyJsonNodeEqual("drivingLicence.state", "ACE").flush();
		// .verifyJsonNodeEqual("drivingLicence.validTo", "08/2020").flush();
	}

	@Test
	public void SettingProfile_03_VerifyUserCanEditTheMedicareInformation() throws Exception {
		toSettingProfilePage();
		// Go to the setting medicare page
		settingProfileDrivingLicencePage = settingProfilePage.toSettingDrivingLicensePage();
		settingProfileMedicarePage = settingProfileDrivingLicencePage.toSettingMedicarePage();
		assertTrue(settingProfileMedicarePage.isActive(), "Seting Profile - Medicare screen is not displayed",
				"Seting Profile - Medicare screen is displayed");
		
		//call API and verify data
		finstroAPI.getMedicareInfor();
		settingProfileMedicarePage.verifyMedicareInfor(Common.getTestVariable("firstName", true),
				Common.getTestVariable("middleInitial", true), Common.getTestVariable("surname", true),
				Common.getTestVariable("gender", true).equalsIgnoreCase("M") ? "Male" : "Female",
				Common.getTestVariable("dateOfBirth", true), Common.getTestVariable("cardColor", true),
				Common.getTestVariable("cardNumber", true), Common.getTestVariable("cardNumberRef", true),
				Common.getTestVariable("validTo", true));
		
		
		//input data
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
			//.verifyJsonNodeEqual("medicareCard.dateOfBirth", "1983-02-27")
			//.verifyJsonNodeEqual("medicareCard.gender", "M")
			//.verifyJsonNodeEqual("medicareCard.validTo", "2020-03-01")
				.flush();
	}

}
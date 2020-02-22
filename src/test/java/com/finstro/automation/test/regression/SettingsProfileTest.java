package com.finstro.automation.test.regression;

import static com.finstro.automation.utility.Assertion.assertEquals;
import static com.finstro.automation.utility.Assertion.assertTrue;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
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
import com.finstro.automation.pages.settings.SettingProfile_DrivingLicensePage;
import com.finstro.automation.pages.settings.SettingProfile_ProfileDetailPage;
import com.finstro.automation.pages.settings.SettingsPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.test.regression.SettingBusinessDetailsTests.BUSINESS_DETAIL;
import com.finstro.automation.utility.Common;

public class SettingsProfileTest extends MobileTestSetup {

	private FinstroAPI finstroAPI;
	private SettingsPage settingPage;
	private SettingProfile_ProfileDetailPage settingProfilePage;
	private SettingProfile_DrivingLicensePage settingProfileDrivingLicencePage;

	class PROFILE_DETAIL {

		public static final String EMAIL_ADRRESS = "EMAIL_ADRRESS";
		public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
		public static final String FIRST_NAME = "FIRST_NAME";
		public static final String LAST_NAME = "LAST_NAME";
		public static final String D_O_B = "D_O_B";
		public static final String RESIDENTIAL_ADDRESS = "RESIDENTIAL_ADDRESS";

	}

	class DRIVING_LICENSE {

		public static final String GENDER = "GENDER";
		public static final String MIDDLE_NAME = "MIDDLE_NAME ";
		public static final String FIRST_NAME = "FIRST_NAME";
		public static final String LAST_NAME = "LAST_NAME";
		public static final String STATE = "STATE";
		public static final String EXPRISE_DATE = "EXPRISE_DATE";
		public static final String DRIVING_LICENCE_NUMBER = "DRIVING_LICENCE_NUMBER";

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
		RegisterPage registerPage = new RegisterPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		settingProfileDrivingLicencePage = new SettingProfile_DrivingLicensePage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		// Login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		// Go to the Setting Business Details page

		// Go to setting profile Page
		toSettingProfilePage();
	}

	private void toSettingProfilePage() throws Exception {
		// goto Business Details page
		SelectBusinessCardPage selectBusinessCardPage = new SelectBusinessCardPage(driver);
		selectBusinessCardPage.clickOnCard("500");
		BusinessDetailPage businessDetailPage = new BusinessDetailPage(driver);
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");

		// goto Residential Address page
		businessDetailPage.clickNext();
		ResidentialAddressPage residentialAddressPage = new ResidentialAddressPage(driver);
		assertTrue(residentialAddressPage.isActive(), "You're not on the Business Details page",
				"You're on the Business Details page");

		// goto Photo ID page
		residentialAddressPage.clickNext();
		PhotoIDPage photoIDPage = new PhotoIDPage(driver);
		assertTrue(photoIDPage.isActive(), "You're not on the Photo ID page", "You're on the Photo ID page");

		// goto Driving License page
		photoIDPage.clickNext();
		DriverLicensePage drivingLisencePage = new DriverLicensePage(driver);
		assertTrue(drivingLisencePage.isActive(), "You're not on the Driving Licence page",
				"You're on the Driving Licence page");

		// goto Postal Address page
		drivingLisencePage.clickNext();
		PostalAddressPage postalAddressPage = new PostalAddressPage(driver);
		assertTrue(postalAddressPage.isActive(), "You're not on the Postal Address page",
				"You're on the Postal Address page");

		// Agreement
		postalAddressPage.clickNext();
		CompleteAgreementPage completeAgreementPage = new CompleteAgreementPage(driver);
		assertTrue(completeAgreementPage.isActive(), "You're not on the Agreement page",
				"You're on the Agreement page");

		// Confirm and goto main page
		completeAgreementPage.confirmAgreement();
		MainNavigator navigator = new MainNavigator(driver);
		assertTrue(navigator.isActive(), "You're not on the Navigator", "You're on the Navigator");

		// goto Settings page
		SettingsPage settingsPage = navigator.gotoSettingsPage();
		assertTrue(settingsPage.isActive(), "You're not on the Settings page", "You're on the Settings page");

		// goto Settings Profile page
		settingPage.goToProfileDetailsPage();

		assertTrue(settingProfilePage.isActive(), "Setting page didnt showed as default page in first installation",
				"Setting page showed as default page");

	}

	@Test
	public void SettingProfile_01_VerifyUserCanEditTheProfileInfomation(String email, String mobile, String firstName,
			String lastName, String dob, String residentialAddress) throws Exception {
		// Save the business details to set back when test done
		HashMap<String, String> profileDetails = new HashMap<>();
		profileDetails.put(PROFILE_DETAIL.EMAIL_ADRRESS, settingProfilePage.getEmail());
		profileDetails.put(PROFILE_DETAIL.MOBILE_NUMBER, settingProfilePage.getPhoneNumber());
		profileDetails.put(PROFILE_DETAIL.FIRST_NAME, settingProfilePage.getFirstName());
		profileDetails.put(PROFILE_DETAIL.LAST_NAME, settingProfilePage.getLastName());
		profileDetails.put(PROFILE_DETAIL.D_O_B, settingProfilePage.getDOB());
		profileDetails.put(PROFILE_DETAIL.RESIDENTIAL_ADDRESS, settingProfilePage.getResidential());
		
		//finstroAPI.getProfileDetailInfor();

		compareDisplayedProfileDetailDataWithAPIResponse();

		try {

			// Check Email
			settingProfilePage.setEmail(email);
			assertEquals(settingProfilePage.getEmail(), email, "Email field is not set sucessfully",
					"Email field is set to " + email);

			// Check Phone number
			settingProfilePage.setPhoneNumber(mobile);
			assertEquals(settingProfilePage.getPhoneNumber(), mobile, "mobile field is not set sucessfully",
					"mobile field is set to " + mobile);

			// Check FirstName
			settingProfilePage.setFirstName(firstName);
			assertEquals(settingProfilePage.getFirstName(), firstName, "firstName field is not set sucessfully",
					"firstName field is set to " + firstName);

			// Check LastName
			settingProfilePage.setLastName(lastName);
			assertEquals(settingProfilePage.getLastName(), lastName, "lastName field is not set sucessfully",
					"lastName field is set to " + lastName);

			// Check Residential
			settingProfilePage.setResidential(residentialAddress);
			assertEquals(settingProfilePage.getResidential(), residentialAddress,
					"residentialAddress field is not set sucessfully",
					"residentialAddress field is set to " + residentialAddress);

			// Save Changes
			settingProfilePage.saveChanges();
			
			compareDisplayedProfileDetailDataWithAPIResponse();

		} catch (Exception ex) {
			throw ex;
		} finally {
			// Set back to original data
			settingProfilePage.setEmail(profileDetails.get(PROFILE_DETAIL.EMAIL_ADRRESS));
			settingProfilePage.setPhoneNumber(profileDetails.get(PROFILE_DETAIL.MOBILE_NUMBER));
			settingProfilePage.setFirstName(profileDetails.get(PROFILE_DETAIL.FIRST_NAME));
			settingProfilePage.setLastName(profileDetails.get(PROFILE_DETAIL.LAST_NAME));
			settingProfilePage.setResidential(profileDetails.get(PROFILE_DETAIL.RESIDENTIAL_ADDRESS));

		}

	}
	
	private void compareDisplayedProfileDetailDataWithAPIResponse() throws Exception {
//		String strEmail = settingProfilePage.getEmail();
//		String strPhoneNumber = settingProfilePage.getPhoneNumber();
//		String strFirstName = settingProfilePage.getFirstName();
//		String strLastName = settingProfilePage.getLastName();
//		String strResidential = settingProfilePage.getResidential();
//
//
//		String resEmail = Common.getTestVariable("gender", true);
//		assertEquals(strEmail, resEmail, "Email is different", "Email matches");
//
//		String resPhoneNumber = Common.getTestVariable("firstname", true);
//		assertEquals(strPhoneNumber, resPhoneNumber, "PhoneNumber is different", "PhoneNumber matches");
//
//		String resFirstName = Common.getTestVariable("surname", true);
//		assertEquals(strFirstName, resFirstName, "FirstName is different", "FirstName matches");
//
//		String resLastName = Common.getTestVariable("middleName", true);
//		assertEquals(strLastName, resLastName, "LastName is different", "LastName matches");
//
//		String resResidential = Common.getTestVariable("state", true);
//		assertEquals(strResidential, resResidential, "Residential is different", "Residential matches");

	}

	@Test
	public void SettingProfile_02_VerifyUserCanEditTheDrivingLicenceInformation(String genderValue,
			String firstNameValue, String lastNameValue, String middleNameValue, String stateValue,
			String drivingLicenceNumberValue) throws Exception {
		// Go to the second setting business page
		settingProfileDrivingLicencePage = settingProfilePage.toSettingDrivingLicensePage();

		// Save the business details to set back when test done
		HashMap<String, String> drivingLicense = new HashMap<>();
		drivingLicense.put(DRIVING_LICENSE.GENDER, settingProfileDrivingLicencePage.getGender());
		drivingLicense.put(DRIVING_LICENSE.FIRST_NAME, settingProfileDrivingLicencePage.getFirstName());
		drivingLicense.put(DRIVING_LICENSE.LAST_NAME, settingProfileDrivingLicencePage.getLastName());
		drivingLicense.put(DRIVING_LICENSE.MIDDLE_NAME, settingProfileDrivingLicencePage.getMiddleName());
		drivingLicense.put(DRIVING_LICENSE.EXPRISE_DATE, settingProfileDrivingLicencePage.getExpireDate());
		drivingLicense.put(DRIVING_LICENSE.STATE, settingProfileDrivingLicencePage.getState());
		drivingLicense.put(DRIVING_LICENSE.DRIVING_LICENCE_NUMBER,
				settingProfileDrivingLicencePage.getDriverLicenseNumber());

		finstroAPI.getDrivingLicenceInfor();

		compareDisplayedDrivingLicenceDataWithAPIResponse();

		try {

			// Check Gender
			settingProfileDrivingLicencePage.setGender(genderValue);
			assertEquals(settingProfileDrivingLicencePage.getGender(), genderValue,
					"gender field is not set sucessfully", "gender field is set to " + genderValue);

			// Check firstName
			settingProfileDrivingLicencePage.setFirstName(firstNameValue);
			assertEquals(settingProfileDrivingLicencePage.getFirstName(), firstNameValue,
					"firstName field is not set sucessfully", "firstName field is set to " + firstNameValue);

			// Check lastName
			settingProfileDrivingLicencePage.setLastName(lastNameValue);
			assertEquals(settingProfileDrivingLicencePage.getLastName(), lastNameValue,
					"lastName field is not set sucessfully", "lastName field is set to " + lastNameValue);

			// Check middleName
			settingProfileDrivingLicencePage.setMiddleName(middleNameValue);
			assertEquals(settingProfileDrivingLicencePage.getMiddleName(), middleNameValue,
					"middleName field is not set sucessfully", "middleName field is set to " + middleNameValue);

			// Check state
			settingProfileDrivingLicencePage.setState(stateValue);
			assertEquals(settingProfileDrivingLicencePage.getState(), stateValue, "state field is not set sucessfully",
					"state field is set to " + stateValue);

			// Check drivingLicenceNumber
			settingProfileDrivingLicencePage.setDriverLicenseNumber(drivingLicenceNumberValue);
			assertEquals(settingProfileDrivingLicencePage.getDriverLicenseNumber(), drivingLicenceNumberValue,
					"drivingLicenceNumber field is not set sucessfully",
					"drivingLicenceNumber field is set to " + drivingLicenceNumberValue);

			// Save Changes
			settingProfileDrivingLicencePage.clickSaveSetting();

			compareDisplayedDrivingLicenceDataWithAPIResponse();

		} catch (Exception ex) {
			throw ex;
		} finally {
			// Set back to original data

			settingProfileDrivingLicencePage.setGender(drivingLicense.get(DRIVING_LICENSE.GENDER));
			settingProfileDrivingLicencePage.setMiddleName(drivingLicense.get(DRIVING_LICENSE.MIDDLE_NAME));
			settingProfileDrivingLicencePage.setFirstName(drivingLicense.get(DRIVING_LICENSE.FIRST_NAME));
			settingProfileDrivingLicencePage.setLastName(drivingLicense.get(DRIVING_LICENSE.LAST_NAME));
			settingProfileDrivingLicencePage
					.setDriverLicenseNumber(drivingLicense.get(DRIVING_LICENSE.DRIVING_LICENCE_NUMBER));
			settingProfileDrivingLicencePage.setState(drivingLicense.get(DRIVING_LICENSE.STATE));

			// settingProfilePage.getPopupActionType();
		}

	}

	private void compareDisplayedDrivingLicenceDataWithAPIResponse() throws Exception {
		String strGender = settingProfileDrivingLicencePage.getGender();
		String strFirstName = settingProfileDrivingLicencePage.getFirstName();
		String strLastName = settingProfileDrivingLicencePage.getLastName();
		String strMiddileName = settingProfileDrivingLicencePage.getMiddleName();
		String strState = settingProfileDrivingLicencePage.getState();
		String strDrivingNumber = settingProfileDrivingLicencePage.getDriverLicenseNumber();

		String resGender = Common.getTestVariable("gender", true);
		assertEquals(strGender, resGender, "Gender is different", "gender matches");

		String resFirstName = Common.getTestVariable("firstname", true);
		assertEquals(strFirstName, resFirstName, "FirstName is different", "FirstName matches");

		String resLastName = Common.getTestVariable("surname", true);
		assertEquals(strLastName, resLastName, "LastName is different", "LastName matches");

		String resMiddileName = Common.getTestVariable("middleName", true);
		assertEquals(strMiddileName, resMiddileName, "middleName is different", "middleName matches");

		String resState = Common.getTestVariable("state", true);
		assertEquals(strState, resState, "State is different", "State matches");

		String resLicenceNumber = Common.getTestVariable("licenceNumber", true);
		assertEquals(strDrivingNumber, resLicenceNumber, "licenceNumber is different", "licenceNumber matches");
	}

	@Test
	public void SettingProfile_03_VerifyUserCanEditTheMedicareInformation() throws Exception {

	}

}
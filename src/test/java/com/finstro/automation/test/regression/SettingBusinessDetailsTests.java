package com.finstro.automation.test.regression;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.common.CommonFunction;
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
import com.finstro.automation.pages.settings.SettingsBusinessDetailsFirstPage;
import com.finstro.automation.pages.settings.SettingsBusinessDetailsSecondPage;
import com.finstro.automation.pages.settings.SettingsPage;
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

public class SettingBusinessDetailsTests extends MobileTestSetup {

	private FinstroAPI finstroAPI;
	private SettingsBusinessDetailsFirstPage settingBusinessDetailsFirstPage;

	class BUSINESS_DETAIL {

		public static final String CATEGORY_OF_BUSINESS = "CATEGORY_OF_BUSINESS";
		public static final String EMAIL = "EMAIL";
		public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
		public static final String WEBSITE = "WEBSITE";
		public static final String FACEBOOK_PAGE = "FACEBOOK_PAGE";
		public static final String TWITTER_URL = "TWITTER_URL";
		public static final String INSTAGRAM_URL = "INSTAGRAM_URL";
		public static final String SKYPE_ADDRESS = "SKYPE_ADDRESS";
		public static final String LINKEDIN = "LINKEDIN";
		public static final String OTHER = "OTHER";

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
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		// Login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		// Go to the Setting Business Details page
		goToTheSettingBusinessDetailsPage();

	}

	private void goToTheSettingBusinessDetailsPage() throws Exception {

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

		// goto Settings Business Details page
		settingBusinessDetailsFirstPage = settingsPage.gotoSettingsBusinessDetailsPage();
		assertTrue(settingBusinessDetailsFirstPage.isActive(), "You're not on the Settings Business Details Page",
				"You're on the Settings Business Details Page");

	}

	@DataProvider(name = "SettingBusinessDetail_01")
	public Object[][] SettingBusinessDetail_01() {
		return new Object[][] { { "Other Services", "erick@finstro.au", "0123456789", "finstro.com", "fb.com",
				"twitter.com", "insta.com", "skype", "linkedin", "just auto test" } };

	}

	@Test(dataProvider = "SettingBusinessDetail_01", enabled = false)
	public void SettingBusinessDetail_01(String category, String email, String mobile, String website, String facebook,
			String twitter, String instagram, String skype, String linkedin, String other) throws Exception {
		// Save the business details to set back when test done
		HashMap<String, String> businessDetails = new HashMap<>();
		businessDetails.put(BUSINESS_DETAIL.CATEGORY_OF_BUSINESS,
				settingBusinessDetailsFirstPage.getCategoryOfBusiness());
		businessDetails.put(BUSINESS_DETAIL.EMAIL, settingBusinessDetailsFirstPage.getEmail());
		businessDetails.put(BUSINESS_DETAIL.MOBILE_NUMBER, settingBusinessDetailsFirstPage.getMobileNumber());
		businessDetails.put(BUSINESS_DETAIL.WEBSITE, settingBusinessDetailsFirstPage.getWebsiteAddress());
		businessDetails.put(BUSINESS_DETAIL.FACEBOOK_PAGE, settingBusinessDetailsFirstPage.getFacebook());
		businessDetails.put(BUSINESS_DETAIL.TWITTER_URL, settingBusinessDetailsFirstPage.getTwitter());
		businessDetails.put(BUSINESS_DETAIL.INSTAGRAM_URL, settingBusinessDetailsFirstPage.getInstagram());
		businessDetails.put(BUSINESS_DETAIL.SKYPE_ADDRESS, settingBusinessDetailsFirstPage.getSkype());
		businessDetails.put(BUSINESS_DETAIL.LINKEDIN, settingBusinessDetailsFirstPage.getLinkedin());
		businessDetails.put(BUSINESS_DETAIL.OTHER, settingBusinessDetailsFirstPage.getOther());

		try {
			// Check Category of Business
			settingBusinessDetailsFirstPage.setCategoryOfBusiness(category);
			assertEquals(settingBusinessDetailsFirstPage.getCategoryOfBusiness(), category,
					"Category field is not set sucessfully", "Category field is set to " + category);

			// Check Email
			settingBusinessDetailsFirstPage.setEmail(email);
			assertEquals(settingBusinessDetailsFirstPage.getEmail(), email, "Email field is not set sucessfully",
					"Email field is set to " + email);

			// Check Phone number
			settingBusinessDetailsFirstPage.setMobileNumber(mobile);
			assertEquals(settingBusinessDetailsFirstPage.getMobileNumber(), mobile,
					"mobile field is not set sucessfully", "mobile field is set to " + mobile);

			// Check Website address
			settingBusinessDetailsFirstPage.setWebsiteAddress(website);
			assertEquals(settingBusinessDetailsFirstPage.getWebsiteAddress(), website,
					"website field is not set sucessfully", "website field is set to " + website);

			// Check Facebook page
			settingBusinessDetailsFirstPage.setFacebook(facebook);
			assertEquals(settingBusinessDetailsFirstPage.getFacebook(), facebook,
					"facebook field is not set sucessfully", "facebook field is set to " + facebook);

			// Check Twitter
			settingBusinessDetailsFirstPage.setTwitter(twitter);
			assertEquals(settingBusinessDetailsFirstPage.getTwitter(), twitter, "twitter field is not set sucessfully",
					"twitter field is set to " + twitter);

			// Check Instagram
			settingBusinessDetailsFirstPage.setInstagram(instagram);
			assertEquals(settingBusinessDetailsFirstPage.getInstagram(), instagram,
					"instagram field is not set sucessfully", "instagram field is set to " + instagram);

			// Check Skype address
			settingBusinessDetailsFirstPage.setSkype(skype);
			assertEquals(settingBusinessDetailsFirstPage.getSkype(), skype, "skype field is not set sucessfully",
					"skype field is set to " + skype);

			// Check Linkedin
			settingBusinessDetailsFirstPage.setLinkedin(linkedin);
			assertEquals(settingBusinessDetailsFirstPage.getLinkedin(), linkedin,
					"linkedin field is not set sucessfully", "linkedin field is set to " + linkedin);

			// Check Other
			settingBusinessDetailsFirstPage.setOther(other);
			assertEquals(settingBusinessDetailsFirstPage.getOther(), other, "other field is not set sucessfully",
					"other field is set to " + other);

			// Save Changes
			settingBusinessDetailsFirstPage.saveChanges();
			String popupMessage = settingBusinessDetailsFirstPage.getPopupMessage();
			String popupAction = settingBusinessDetailsFirstPage.getPopupActionType();
			assertEquals(popupMessage, "Successfully save new business detail.", "Save failed " + popupMessage,
					"Save business details success");
			assertEquals(popupAction, "SUCCESS", "Save failed " + popupAction, "Save business details success");
			
		} catch (Exception ex) {
			throw ex;
		} finally {
			// Set back to original data
			settingBusinessDetailsFirstPage.setCategoryOfBusiness(businessDetails.get(BUSINESS_DETAIL.CATEGORY_OF_BUSINESS));
			settingBusinessDetailsFirstPage.setEmail(businessDetails.get(BUSINESS_DETAIL.EMAIL));
			settingBusinessDetailsFirstPage.setMobileNumber(businessDetails.get(BUSINESS_DETAIL.MOBILE_NUMBER));
			settingBusinessDetailsFirstPage.setWebsiteAddress(businessDetails.get(BUSINESS_DETAIL.WEBSITE));
			settingBusinessDetailsFirstPage.setFacebook(businessDetails.get(BUSINESS_DETAIL.FACEBOOK_PAGE));
			settingBusinessDetailsFirstPage.setTwitter(businessDetails.get(BUSINESS_DETAIL.TWITTER_URL));
			settingBusinessDetailsFirstPage.setInstagram(businessDetails.get(BUSINESS_DETAIL.INSTAGRAM_URL));
			settingBusinessDetailsFirstPage.setSkype(businessDetails.get(BUSINESS_DETAIL.SKYPE_ADDRESS));
			settingBusinessDetailsFirstPage.setLinkedin(businessDetails.get(BUSINESS_DETAIL.LINKEDIN));
			settingBusinessDetailsFirstPage.setOther(businessDetails.get(BUSINESS_DETAIL.OTHER));
			settingBusinessDetailsFirstPage.saveChanges();
			
			settingBusinessDetailsFirstPage.getPopupActionType();
		}

	}
	
	@Test
	public void SettingBusinessDetail_02() throws Exception {
		// Go to the second setting business page
		SettingsBusinessDetailsSecondPage settingBusinessDetailsSecondPage = settingBusinessDetailsFirstPage.gotoSettingsBusinessDetailsSecondPage();
		assertTrue(settingBusinessDetailsSecondPage.isActive(), "You're not on the Setting Business Detail second page",
				"You're on the Setting Business Detail second page");
		
		// Check the information displayed on the page that matches with api response
		String strBusinessType = settingBusinessDetailsSecondPage.getTypeOfBusiness();
		String strBusinessName = settingBusinessDetailsSecondPage.getTradingBusinessName();
		String strTradingName = settingBusinessDetailsSecondPage.getTradingLegalName();
		String strABN = settingBusinessDetailsSecondPage.getABN();
		String strACN = settingBusinessDetailsSecondPage.getACN();
		String strBusinessAddr = settingBusinessDetailsSecondPage.getBusinessAddress();
		String strOtherName = settingBusinessDetailsSecondPage.getOtherName();
		String strIncorporation = settingBusinessDetailsSecondPage.getIncorporation();
		String strGSTDate = settingBusinessDetailsSecondPage.getGSTDate();
		String strTimeTrading = settingBusinessDetailsSecondPage.getTimeTrading();
		
		finstroAPI.getBusinessDetailInfor();
		String resBusinessType = Common.getTestVariable("type", true);
		assertEquals(strBusinessType, resBusinessType, "BusinessType is different", "BusinessType matches");
		
		String resBusinessName = Common.getTestVariable("businessName", true);
		assertEquals(strBusinessName, resBusinessName, "BusinessName is different", "BusinessName matches");
		
		String resTradingName = Common.getTestVariable("entityName", true);
		assertEquals(strTradingName, resTradingName, "TradingName is different", "TradingName matches");
		
		String resABN = Common.getTestVariable("abn", true);
		assertEquals(strABN.replace(" ",""), resABN.replace(" ",""), "ABN is different", "ABN matches");
		
		String resACN = Common.getTestVariable("acn", true);
		assertEquals(strACN.replace(" ",""), resACN.replace(" ",""), "ACN is different", "ACN matches");
		
		String resGstDate = Common.getTestVariable("gstDate", true);
		assertEquals(strGSTDate, resGstDate, "GST Date is different", "GST Date matches");
		
		String resTimeTrading = Common.getTestVariable("timeTrading", true);
		assertEquals(strTimeTrading, resTimeTrading, "timeTrading is different", "timeTrading matches");
		
		String resCountry = Common.getTestVariable("country", true);
		String resPostcode = Common.getTestVariable("postCode", true);
		String resState = Common.getTestVariable("state", true);
		String resSuburb = Common.getTestVariable("suburb", true);
		String resStreetType = Common.getTestVariable("streetType", true);
		String resStreetName = Common.getTestVariable("streetName", true);
		String resStreetNumber = Common.getTestVariable("streetNumber", true);
		String resFullAddress = String.format("%s %s,%s,%s,%s,%s,%s,", resStreetNumber, resStreetName, resStreetType, resSuburb, resState, resPostcode, resCountry);
		assertEquals(strBusinessAddr.replace(" ",""), resFullAddress.replace(" ",""), "Address is different", "Address matches");
		
	}


}

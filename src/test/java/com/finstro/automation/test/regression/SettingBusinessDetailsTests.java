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

	}

	@DataProvider(name = "SettingBusinessDetail_01")
	public Object[][] SettingBusinessDetail_01() {
		return new Object[][] { { "Other Services", "erick@finstro.au", "0123456789", "finstro.com", "fb.com",
				"twitter.com", "insta.com", "skype", "linkedin", "just auto test" } };

	}

	@Test(dataProvider = "SettingBusinessDetail_01")
	public void SettingBusinessDetail_01(String category, String email, String mobile, String website, String facebook,
			String twitter, String instagram, String skype, String linkedin, String other) throws Exception {
		// Go to the Setting Business Details page
		settingBusinessDetailsFirstPage = WorkFlows.goToTheSettingBusinessDetailsPage(driver);

		// Save the business details to set back when test done
//		HashMap<String, String> businessDetails = new HashMap<>();
//		businessDetails.put(BUSINESS_DETAIL.CATEGORY_OF_BUSINESS,
//				settingBusinessDetailsFirstPage.getCategoryOfBusiness());
//		businessDetails.put(BUSINESS_DETAIL.EMAIL, settingBusinessDetailsFirstPage.getEmail());
//		businessDetails.put(BUSINESS_DETAIL.MOBILE_NUMBER, settingBusinessDetailsFirstPage.getMobileNumber());
//		businessDetails.put(BUSINESS_DETAIL.WEBSITE, settingBusinessDetailsFirstPage.getWebsiteAddress());
//		businessDetails.put(BUSINESS_DETAIL.FACEBOOK_PAGE, settingBusinessDetailsFirstPage.getFacebook());
//		businessDetails.put(BUSINESS_DETAIL.TWITTER_URL, settingBusinessDetailsFirstPage.getTwitter());
//		businessDetails.put(BUSINESS_DETAIL.INSTAGRAM_URL, settingBusinessDetailsFirstPage.getInstagram());
//		businessDetails.put(BUSINESS_DETAIL.SKYPE_ADDRESS, settingBusinessDetailsFirstPage.getSkype());
//		businessDetails.put(BUSINESS_DETAIL.LINKEDIN, settingBusinessDetailsFirstPage.getLinkedin());
//		businessDetails.put(BUSINESS_DETAIL.OTHER, settingBusinessDetailsFirstPage.getOther());

		// call API and verify displayed data
		finstroAPI.getBusinessDetailInfor();
		settingBusinessDetailsFirstPage.verifyDisplayedData(Common.getTestVariable("email", true),
				Common.getTestVariable("phoneNumber", true), Common.getTestVariable("website", true),
				Common.getTestVariable("facebook", true), Common.getTestVariable("twitter", true),
				Common.getTestVariable("instagram", true), Common.getTestVariable("skype", true),
				Common.getTestVariable("linkedin", true), Common.getTestVariable("other", true));
		// Check Category of Business
		//settingBusinessDetailsFirstPage.setCategoryOfBusiness(category);
		// Check Email
		settingBusinessDetailsFirstPage.setEmail(email);
		// Check Phone number
		settingBusinessDetailsFirstPage.setMobileNumber(mobile);
		// Check Website address
		settingBusinessDetailsFirstPage.setWebsiteAddress(website);
		// Check Facebook page
		settingBusinessDetailsFirstPage.setFacebook(facebook);
		// Check Twitter
		settingBusinessDetailsFirstPage.setTwitter(twitter);
		// Check Instagram
		settingBusinessDetailsFirstPage.setInstagram(instagram);
		// Check Skype address
		settingBusinessDetailsFirstPage.setSkype(skype);
		// Check Linkedin
		settingBusinessDetailsFirstPage.setLinkedin(linkedin);
		// Check Other
		settingBusinessDetailsFirstPage.setOther(other);

		// Save Changes
		settingBusinessDetailsFirstPage.saveChanges();
		if (driver.isAndroidDriver()) {
			String popupMessage = settingBusinessDetailsFirstPage.getPopupMessage();
			String popupAction = settingBusinessDetailsFirstPage.getPopupActionType();
			assertEquals(popupMessage, "Successfully save new business detail.", "Save failed " + popupMessage,
					"Save business details success");
			assertEquals(popupAction, "SUCCESS", "Save failed " + popupAction, "Save business details success");
		}
		Thread.sleep(10000);
		finstroAPI.recoveryData().then().verifyResponseCode(200).verifyJsonNodeEqual("businessDetails.email", email)
				.verifyJsonNodeEqual("businessDetails.phoneNumber", mobile)
				.verifyJsonNodeEqual("businessDetails.website", website)
				.verifyJsonNodeEqual("businessDetails.facebook", facebook)
				.verifyJsonNodeEqual("businessDetails.twitter", twitter)
				.verifyJsonNodeEqual("businessDetails.instagram", instagram)
				.verifyJsonNodeEqual("businessDetails.skype", skype)
				.verifyJsonNodeEqual("businessDetails.linkedin", linkedin)
				.verifyJsonNodeEqual("businessDetails.other", other).flush();

	}

	@Test
	public void SettingBusinessDetail_02() throws Exception {
		// Go to the Setting Business Details page
		settingBusinessDetailsFirstPage = WorkFlows.goToTheSettingBusinessDetailsPage(driver);

		// Go to the second setting business page
		SettingsBusinessDetailsSecondPage settingBusinessDetailsSecondPage = settingBusinessDetailsFirstPage
				.gotoSettingsBusinessDetailsSecondPage();
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

		String expectedBusinessTradingAddress = finstroAPI.getBusinessDetailInfor();
		String resBusinessType = Common.getTestVariable("type", true);
		assertEquals(strBusinessType, resBusinessType, "BusinessType is different", "BusinessType matches");

		String resBusinessName = Common.getTestVariable("businessName", true);
		assertEquals(strBusinessName, resBusinessName, "BusinessName is different", "BusinessName matches");

		String resTradingName = Common.getTestVariable("entityName", true);
		assertEquals(strTradingName, resTradingName, "TradingName is different", "TradingName matches");

		String resABN = Common.getTestVariable("abn", true);
		assertEquals(strABN.replace(" ", ""), resABN.replace(" ", ""), "ABN is different", "ABN matches");

		String resACN = Common.getTestVariable("acn", true);
		assertEquals(strACN.replace(" ", ""), resACN.replace(" ", ""), "ACN is different", "ACN matches");

		String resGstDate = Common.getTestVariable("gstDate", true);
		assertEquals(strGSTDate, resGstDate, "GST Date is different", "GST Date matches");

		String resTimeTrading = Common.getTestVariable("timeTrading", true);
		assertEquals(strTimeTrading, resTimeTrading, "timeTrading is different", "timeTrading matches");

		assertEquals(strBusinessAddr.replace(",", "").trim(), expectedBusinessTradingAddress.trim(),
				"Address is different", "Address matches");

	}

}

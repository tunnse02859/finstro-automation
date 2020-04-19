package com.finstro.automation.tests;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsSecondPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

public class SettingBusinessDetailsTests extends MobileTestSetup {

	private FinstroAPI finstroAPI;
	private SettingsBusinessDetailsFirstPage settingBusinessDetailsFirstPage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS, Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		RegisterPage registerPage = new RegisterPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		// Login
		loginPage.doSuccessLogin(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS, Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);

	}

	@DataProvider(name = "SettingBusinessDetail_01")
	public Object[][] SettingBusinessDetail_01() {
		return new Object[][] { { "Mining", "erick@finstro.au", "+61433673183", "http://finctro.com",
				"http://facebook.com/myfacebook", "http://twitter.com/mytwitter", "http://instagram.com/myinsta",
				"phong.trinh", "http://linkedin.com/abc", "just auto test" } };

	}

	@Test(dataProvider = "SettingBusinessDetail_01")
	public void SettingBusinessDetail_01(String category, String email, String mobile, String website, String facebook,
			String twitter, String instagram, String skype, String linkedin, String other) throws Exception {
		// Go to the Setting Business Details page
		settingBusinessDetailsFirstPage = WorkFlows.goToTheSettingBusinessDetailsPage(driver);

		// call API and verify displayed data
		HtmlReporter.label("Verify data on screen with data from API");
		finstroAPI.getBusinessDetailInfor();
		settingBusinessDetailsFirstPage.verifyDisplayedData(Common.getTestVariable("email", true),
				Common.getTestVariable("phoneNumber", true), Common.getTestVariable("website", true),
				Common.getTestVariable("facebook", true), Common.getTestVariable("twitter", true),
				Common.getTestVariable("instagram", true), Common.getTestVariable("skype", true),
				Common.getTestVariable("linkedin", true), Common.getTestVariable("other", true));
		
		HtmlReporter.label("Input data and save changes");
		settingBusinessDetailsFirstPage.setCategoryOfBusiness(category);
		settingBusinessDetailsFirstPage.setEmail(email);
		settingBusinessDetailsFirstPage.setMobileNumber(mobile);
		settingBusinessDetailsFirstPage.setWebsiteAddress(website);
		settingBusinessDetailsFirstPage.setFacebook(facebook);
		settingBusinessDetailsFirstPage.setTwitter(twitter);
		settingBusinessDetailsFirstPage.setInstagram(instagram);
		settingBusinessDetailsFirstPage.setSkype(skype);
		settingBusinessDetailsFirstPage.setLinkedin(linkedin);
		settingBusinessDetailsFirstPage.setOther(other);
		// Save Changes
		settingBusinessDetailsFirstPage.saveChanges();
		HtmlReporter.label("Verify data after click on save");
		if (driver.isAndroidDriver()) {
			String popupMessage = settingBusinessDetailsFirstPage.getPopupMessage();
			String popupAction = settingBusinessDetailsFirstPage.getPopupActionType();
			assertEquals(popupMessage, "Successfully save new business detail.", "Save failed " + popupMessage,
					"Save business details success");
			assertEquals(popupAction, "SUCCESS", "Save failed " + popupAction, "Save business details success");
		}
		driver.wait(10);
		finstroAPI.recoveryData().then().verifyResponseCode(200)
				.verifyJsonNodeEqual("businessDetails.email", email)
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
		HtmlReporter.label("Verify data on screen with data from API");
		
		String expectedBusinessTradingAddress = finstroAPI.getBusinessDetailInfor();
		
		String strBusinessType = settingBusinessDetailsSecondPage.getTypeOfBusiness();
		String resBusinessType = Common.getTestVariable("type", true);
		assertEquals(strBusinessType, resBusinessType, "BusinessType is different", "BusinessType matches");

		String strBusinessName = settingBusinessDetailsSecondPage.getTradingBusinessName();
		String resBusinessName = Common.getTestVariable("businessName", true);
		assertEquals(strBusinessName, resBusinessName, "BusinessName is different", "BusinessName matches");

		String strTradingName = settingBusinessDetailsSecondPage.getTradingLegalName();
		String resTradingName = Common.getTestVariable("entityName", true);
		assertEquals(strTradingName, resTradingName, "TradingName is different", "TradingName matches");

		String strABN = settingBusinessDetailsSecondPage.getABN();
		String resABN = Common.getTestVariable("abn", true);
		assertEquals(strABN.replace(" ", ""), resABN.replace(" ", ""), "ABN is different", "ABN matches");

		String strACN = settingBusinessDetailsSecondPage.getACN();
		String resACN = Common.getTestVariable("acn", true);
		assertEquals(strACN.replace(" ", ""), resACN.replace(" ", ""), "ACN is different", "ACN matches");

		String strBusinessAddr = settingBusinessDetailsSecondPage.getBusinessAddress();
		assertContains(strBusinessAddr, expectedBusinessTradingAddress.trim(),
				"Address is different", "Address matches");

	}

}

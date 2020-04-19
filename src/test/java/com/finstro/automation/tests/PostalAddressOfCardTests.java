package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.assertEquals;
import static com.finstro.automation.utility.Assertion.assertTrue;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.OnboardingAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class PostalAddressOfCardTests extends MobileTestSetup {

	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage selectCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
	private CompleteAgreementPage completeAgreementPage;
	private OnboardingAPI onboardingAPI;

	@BeforeClass
	public void setupAPI() throws Exception {
		onboardingAPI = new OnboardingAPI();
		onboardingAPI.loginForAccessToken(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		selectCardPage = new SelectBusinessCardPage(driver);
		completeAgreementPage = new CompleteAgreementPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");

		toPostalAddressOfCardPage();
	}

	private void toPostalAddressOfCardPage() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS,
				Constant.ONBOARDING_ACCESS_CODE);
		HtmlReporter.label("Go to Postal Address page");
		selectCardPage = new SelectBusinessCardPage(driver);

		businessDetailPage = selectCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Detail screen is not  displayed",
				"Business Detail screen is displayed");

		residentialAddressPage = businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(), "Residential Address screen is not  displayed",
				"Residential Address screen is displayed");

		photoIDPage = residentialAddressPage.clickNext();
		assertTrue(photoIDPage.isActive(), "PhotoID screen is not  displayed", "PhotoID screen is displayed");

		drivingLisencePage = photoIDPage.clickNext();
		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed",
				"Driver License screen is displayed");

		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(), "Postal Address Of Card screen is not  displayed after click on next",
				"Postal Address Of Card screen is displayed after click on next");

	}

	@Test
	public void FPC_1388_Verify_Submit_Postal_address_successfully() throws Exception {
		// select business trading address
		HtmlReporter.label("Select Business Trading Address");
		postalAddressPage.selectBusinessTradingAddress();

		// click next verify
		HtmlReporter.label("Click Next and verify screen change and data with API");
		postalAddressPage.clickNext();
		completeAgreementPage = new CompleteAgreementPage(driver);
		assertTrue(completeAgreementPage.isActive(), "Complete Agreement Screen is not displayed",
				"Complete Agreement Screen is displayed");

		onboardingAPI.getPostalAddressInfor();
		assertEquals(Common.getTestVariable("postalAddress", true),
				Common.getTestVariable("businessTradingAddress", true),
				"Postal Address is not matched with Busienss Trading Address",
				"Postal Address is matched with Business Trading Address");

	}

}

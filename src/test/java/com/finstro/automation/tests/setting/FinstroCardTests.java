package com.finstro.automation.tests.setting;

import static com.finstro.automation.utility.Assertion.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.cards.CardsPage;
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
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

public class FinstroCardTests extends MobileTestSetup {
	private FinstroAPI finstroAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private CardsPage cardsPage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS, Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		cardsPage = new CardsPage(driver);

		// check if register page is default page
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Do login
		loginPage.doSuccessLogin(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS, Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);

		toCardPage();

	}

	private void toCardPage() throws Exception {
		// goto Business Details page
		SelectBusinessCardPage selectBusinessCardPage = new SelectBusinessCardPage(driver);
		selectBusinessCardPage.clickOnCard("500");
		BusinessDetailPage businessDetailPage = new BusinessDetailPage(driver);
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
		Thread.sleep(10000);

		// goto Residential Address page
		businessDetailPage.clickNext();
		ResidentialAddressPage residentialAddressPage = new ResidentialAddressPage(driver);
		assertTrue(residentialAddressPage.isActive(), "You're not on the Business Details page",
				"You're on the Business Details page");

		// goto Photo ID page
		residentialAddressPage.clickNext();
		PhotoIDPage photoIDPage = new PhotoIDPage(driver);
		assertTrue(photoIDPage.isActive(), "You're not on the Photo ID page", "You're on the Photo ID page");
		Thread.sleep(10000);

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
		HomePage homePage = new HomePage(driver);
		assertTrue(navigator.isActive(), "You're not on the Navigator", "You're on the Navigator");

		assertTrue(homePage.isActive(), "Home page didnt showed as default page in first installation",
				"Home page showed as default page");
		
		//Go To card Page
		homePage.goToCardTab();
		
		assertTrue(cardsPage.isActive(), "You're not on the Card Page", "You're on the Card Page");
	}
	
	@Test
	public void FinstroCard_01_VeryActiveNewCard() throws Exception {
		cardsPage.activeCard();
		assertTrue(cardsPage.verifyCardActive(), "User can not active his card by touch on \"Active your Finstro Card\" on the Card screen", "User can active his card by touch on \"Active your Finstro Card\" on the Card screen");
	}

}

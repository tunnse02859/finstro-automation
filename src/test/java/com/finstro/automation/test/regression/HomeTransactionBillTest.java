package com.finstro.automation.test.regression;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.home.HomeBalancePage;
import com.finstro.automation.pages.home.HomeNextBillPage;
import com.finstro.automation.pages.home.HomePage;
import com.finstro.automation.pages.home.HomeYearlyViewPage;
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
import com.finstro.automation.utility.Common;

public class HomeTransactionBillTest extends MobileTestSetup {
	private FinstroAPI finstroAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;

	private HomePage homePage;
	private HomeNextBillPage homeNextBillPage;
	private HomeBalancePage homeBalancePage;
	private HomeYearlyViewPage homeYearlyViewPage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		finstroAPI.getBalanceNumber();
		finstroAPI.getAvailableNumber();
		finstroAPI.getNextBillAmount();
		finstroAPI.getLimit();

	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);

		homePage = new HomePage(driver);
		homeNextBillPage = new HomeNextBillPage(driver);
		homeBalancePage = new HomeBalancePage(driver);
		homeYearlyViewPage = new HomeYearlyViewPage(driver);
		// check if register page is default page
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Do login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);

		toHomePage();

	}

	private void toHomePage() throws Exception {
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
		assertTrue(navigator.isActive(), "You're not on the Navigator", "You're on the Navigator");

		assertTrue(homePage.isActive(), "Home page didnt showed as default page in first installation",
				"Home page showed as default page");

	}

	@Test
	public void Home_06_GoToTheNextBillScreen() throws Exception {
		homePage.goToTheNextBillScreen();
		assertTrue(homeNextBillPage.isActive(), "Next bill page is not displayed", "Next bill page is displayed");
	}

	@Test
	public void Home_07_GoToBalanceScreen() throws Exception {
		homePage.goToBalanceScreen();
		assertTrue(homeBalancePage.isActive(), "Balance page is not displayed", "Balance page showed as default page");
	}

	@Test
	public void Home_08_GoToYearlyViewScreen() throws Exception {
		homePage.goToYearlyViewScreen();
		assertTrue(homeYearlyViewPage.isActive(), "Yearly View page is not displayed", "Yearly View page is displayed");
	}

	@Test
	public void Home_02_CheckAvailableNumber() throws Exception {
		assertEquals(homePage.getAvailableNumber(), Common.getTestVariable("availableBalance", true),
				"Available Amount Is Not correct with value from App recorvery ",
				"Available Amount Is correct with value from App recorvery ");
	}

	@Test
	public void Home_03_CheckLimitAmountNumber() throws Exception {
		assertEquals(homePage.getLimitNumber(), Common.getTestVariable("limit", true),
				"Limit Amount Is Not correct with value from App recorvery ",
				"Limit Amount Is correct with value from App recorvery ");
	}

	@Test
	public void Home_04_CheckNextBillValue() throws Exception {
		assertEquals(homePage.getNextBillNumber(), Common.getTestVariable("nextBillAmount", true),
				"Next Bill Amount Is Not correct with value from App recorvery ",
				"Next Bill Amount Is correct with value from App recorvery ");
	}

	@Test
	public void Home_05_CheckBalanceValue() throws Exception {
		assertEquals(homePage.getBalanceNumber(), Common.getTestVariable("balance", true),
				"Balance Amount Is Not correct with value from App recorvery ",
				"Balance Amount Is correct with value from App recorvery ");
	}

}
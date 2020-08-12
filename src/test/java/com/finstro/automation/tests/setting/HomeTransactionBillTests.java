package com.finstro.automation.tests.setting;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.apache.commons.lang3.text.WordUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.common.WorkFlows;
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

public class HomeTransactionBillTests extends MobileTestSetup {
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
		finstroAPI.loginForAccessToken(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS, Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);

		homeNextBillPage = new HomeNextBillPage(driver);
		homeBalancePage = new HomeBalancePage(driver);
		homeYearlyViewPage = new HomeYearlyViewPage(driver);
		// check if register page is default page
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Do login
		loginPage.doSuccessLogin(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS, Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);

	}

	@Test
	public void Home_02_CheckAvailableNumber() throws Exception {
		homePage = WorkFlows.goToHomePage(driver);
		if (driver.isAndroidDriver()) {
			finstroAPI.getHomePageValues();
			assertEquals(homePage.getAvailableNumber(), Common.getTestVariable("availableBalance", true),
					"Available Amount Is Not correct with value from App recorvery ",
					"Available Amount Is correct with value from App recorvery ");
		}
	}

	@Test
	public void Home_03_CheckLimitAmountNumber() throws Exception {
		homePage = WorkFlows.goToHomePage(driver);
		if (driver.isAndroidDriver()) {
			finstroAPI.getHomePageValues();
			assertEquals(homePage.getLimitNumber(), Common.getTestVariable("limit", true),
					"Limit Amount Is Not correct with value from App recorvery ",
					"Limit Amount Is correct with value from App recorvery ");
		}
	}

	@Test
	public void Home_04_CheckNextBillValue() throws Exception {
		homePage = WorkFlows.goToHomePage(driver);
		finstroAPI.getHomePageValues();
		assertEquals(homePage.getNextBillNumber(), Common.getTestVariable("nextBillAmount", true),
				"Next Bill Amount Is Not correct with value from App recorvery ",
				"Next Bill Amount Is correct with value from App recorvery ");
	}

	@Test
	public void Home_05_CheckBalanceValue() throws Exception {
		homePage = WorkFlows.goToHomePage(driver);
		finstroAPI.getHomePageValues();
		assertEquals(homePage.getBalanceNumber(), Common.getTestVariable("balance", true),
				"Balance Amount Is Not correct with value from App recorvery ",
				"Balance Amount Is correct with value from App recorvery ");
	}

	@Test
	public void Home_06_GoToTheNextBillScreen() throws Exception {
		homePage = WorkFlows.goToHomePage(driver);
		homePage.goToTheNextBillScreen();
		assertTrue(homeNextBillPage.isActive(), "Next bill page is not displayed", "Next bill page is displayed");
	}

	@Test
	public void Home_07_GoToBalanceScreen() throws Exception {
		homePage = WorkFlows.goToHomePage(driver);
		homePage.goToBalanceScreen();
		assertTrue(homeBalancePage.isActive(), "Balance page is not displayed", "Balance page is displayed");
	}

	@Test
	public void Home_08_GoToYearlyViewScreen() throws Exception {
		homePage = WorkFlows.goToHomePage(driver);
		homePage.goToYearlyViewScreen();
		assertTrue(homeYearlyViewPage.isActive(), "Yearly View page is not displayed", "Yearly View page is displayed");
	}

}
package com.finstro.automation.test.regression;

import static com.finstro.automation.utility.Assertion.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.home.HomeBalancePage;
import com.finstro.automation.pages.home.HomeFebBillPage;
import com.finstro.automation.pages.home.HomePage;
import com.finstro.automation.pages.home.HomeYearlyViewPage;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.pages.settings.SettingProfilePage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

public class HomeTransactionBillTest extends MobileTestSetup {
	private FinstroAPI finstroAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SelectBusinessCardPage selectBusinessCardPage;
	private BusinessDetailPage businessDetailPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
	private CompleteAgreementPage completeAgreementPage;
	private HomePage homePage;
	private HomeFebBillPage homeFebBillPage;
	private HomeBalancePage homeBalancePage;
	private HomeYearlyViewPage homeYearlyViewPage;
	
	@BeforeClass
	public void setupAccessTosken() throws Exception {
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		selectBusinessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		residentialAddressPage = new ResidentialAddressPage(driver);
		photoIDPage = new PhotoIDPage(driver);
		drivingLisencePage = new DriverLicensePage(driver);
		postalAddressPage = new PostalAddressPage(driver);
		completeAgreementPage = new CompleteAgreementPage(driver);
		homePage = new HomePage(driver);
		homeFebBillPage = new HomeFebBillPage(driver);
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
		selectBusinessCardPage.clickOnCard("500");
		Thread.sleep(10000);
		businessDetailPage.clickNext();
		residentialAddressPage.clickNext();
		Thread.sleep(10000);
		photoIDPage.clickNext();
		drivingLisencePage.clickNext();
		postalAddressPage.clickNext();
		completeAgreementPage.confirmAgreement();
		
		assertTrue(homePage.isActive(), "Home page didnt showed as default page in first installation",
				"Home page showed as default page");
		
	
	}
	
	@Test
	public void Home_06_GoToTheNextBillScreen() throws Exception {
		homePage.goToTheNextBillScreen();
		assertTrue(homeFebBillPage.isActive(), "Home page didnt showed as default page in first installation",
				"Home page showed as default page");
	}
	
	@Test
	public void Home_07_GoToBalanceScreen() throws Exception {
		homePage.goToBalanceScreen();
		assertTrue(homeBalancePage.isActive(), "Home page didnt showed as default page in first installation",
				"Home page showed as default page");
	}
	
	@Test
	public void Home_08_GoToYearlyViewScreen() throws Exception {
		homePage.goToYearlyViewScreen();
		assertTrue(homeYearlyViewPage.isActive(), "Home page didnt showed as default page in first installation",
				"Home page showed as default page");
	}
	
}

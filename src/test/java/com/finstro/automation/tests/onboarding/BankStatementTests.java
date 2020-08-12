package com.finstro.automation.tests.onboarding;

import static com.finstro.automation.utility.Assertion.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.OnboardingAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BankStatementDetailPage;
import com.finstro.automation.pages.on_boarding.BankStatementRetrieveAccountlPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.RepaymentPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBankStatementPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

public class BankStatementTests extends MobileTestSetup {
	
	private OnboardingAPI onboardingAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SelectBusinessCardPage selectBusinessCardPage;
	private BusinessDetailPage businessDetailPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		onboardingAPI = new OnboardingAPI();
		onboardingAPI.loginForAccessToken(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		onboardingAPI.setupBusinessDetail("1000");
		onboardingAPI.setupResidentialAddress();
		onboardingAPI.setupDrivingLicense();
		onboardingAPI.setupMedicare();
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {

		// check if register page is default page
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

	}
	
	@Test
	public void FPC_2827_OnBoarding_Above500$_Verify_submit_bank_statement_successfully() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		selectBusinessCardPage = new SelectBusinessCardPage(driver);
		// go to postal address
		HtmlReporter.label("Go to Bank Statement screen");
		
		//data provider here
		businessDetailPage = selectBusinessCardPage.clickOnCard("1000");
		assertTrue(businessDetailPage.isActive(), "Business Detail screen is not  displayed after click on next",
				"Business Detail screen is displayed after click on next");

		residentialAddressPage = businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(),
				"Residential Address screen is not  displayed after click on next",
				"Residential Address screen is displayed after click on next");

		photoIDPage = residentialAddressPage.clickNext();
		assertTrue(photoIDPage.isActive(), "PhotoID screen is not  displayed after click on next",
				"PhotoID screen is displayed after click on next");

		drivingLisencePage = photoIDPage.clickNext();
		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed after click on next",
				"Driver License screen is displayed after click on next");

		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(), "Postal Address Of Card screen is not displayed",
				"Postal Address Of Card screen is displayed");

		postalAddressPage.clickNext();
		SelectBankStatementPage selectBankStatementPage = new SelectBankStatementPage(driver);
		assertTrue(selectBankStatementPage.isActive(), "Bank Statement screen is not displayed",
				"Bank Statement screen is displayed");

		// search for bank demo and select it
		HtmlReporter.label("Search for bank demo and login");
		selectBankStatementPage.inputSearch("demo");
		BankStatementDetailPage bankStatementDetailPage = selectBankStatementPage.selectBankDemo();
		assertTrue(bankStatementDetailPage.isActive(), "Bank Statement login screen is not displayed",
				"Bank Statement login screen is displayed");

		// login bank statement and submit
		bankStatementDetailPage.inputUsername("12345678");
		bankStatementDetailPage.inputPassword("TestMyMoney");
		bankStatementDetailPage.acceptTern();
		BankStatementRetrieveAccountlPage retrievePage = bankStatementDetailPage.clickSubmit();
		HtmlReporter.label("Verify retrieve screen displayed after login and submit it!");
		assertTrue(retrievePage.isActive(), "Retrieve screen is not displayed", "Retrieve screen is displayed");

		// submit retrieve, verify all done screen displayed and popup displayed
		retrievePage.clickSubmit();
		assertTrue(retrievePage.isDone(), "Retrieve All Done screen is not displayed",
				"Retrieve All Done screen is displayed");
		driver.wait(5);
		RepaymentPage repaymentPage = new RepaymentPage(driver);
		assertTrue(repaymentPage.isActive(), "Repayment screen is not displayed after submit banks statement",
				"Repayment screen is displayed after submit banks statement");
		
	}

}

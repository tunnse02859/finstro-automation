package com.finstro.automation.tests;

import com.finstro.automation.api.OnboardingAPI;
import com.finstro.automation.common.CommonFunction;
import com.finstro.automation.excelhelper.ExcelHelper;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BankStatementDetailPage;
import com.finstro.automation.pages.on_boarding.BankStatementRetrieveAccountlPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.FindAddressPage;
import com.finstro.automation.pages.on_boarding.FindBusinessPage;
import com.finstro.automation.pages.on_boarding.MedicarePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.RepaymentPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBankStatementPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.DataGenerator;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.FilePaths;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;
import java.util.HashMap;

public class RepaymentTest extends MobileTestSetup {

	private OnboardingAPI onboardingAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SelectBusinessCardPage selectBusinessCardPage;
	private BusinessDetailPage businessDetailPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
	private CompleteAgreementPage completeAgreementPage;
	private RepaymentPage repaymentPage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		onboardingAPI = new OnboardingAPI();
		onboardingAPI.loginForAccessToken(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		onboardingAPI.setupBusinessDetail("500");
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

		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		selectBusinessCardPage = new SelectBusinessCardPage(driver);
	}

	public RepaymentPage gotoRepaymentScreen() throws Exception {
		// go to postal address
		HtmlReporter.label("Go to Bank Statement screen");

		// data provider here
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
		HtmlReporter.label("Do Bank Statement to get into Repayment screen");
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
		repaymentPage = new RepaymentPage(driver);
		assertTrue(repaymentPage.isActive(), "Repayment screen is not displayed after submit banks statement",
				"Repayment screen is displayed after submit banks statement");
		return repaymentPage;
	}

	@Test
	public void OnBoarding_Repayment_Submit_All_Field_Blank() throws Exception {
		gotoRepaymentScreen();
		HtmlReporter.label("Submit without input any data");
		repaymentPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = repaymentPage.getSaveStatus();
		assertContains(status, "Please enter all details on your card", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}
	
	@Test
	public void OnBoarding_Repayment_Submit_Card_Name_Blank() throws Exception {
		gotoRepaymentScreen();
		HtmlReporter.label("Submit with blank name on card data");
		
		String cardNumber = DataGenerator.generateDebitCardNumber();
		String expiry = "01/2022";
		
		repaymentPage.setCardNumber(cardNumber);
		repaymentPage.setCardExpiry(expiry);
		repaymentPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = repaymentPage.getSaveStatus();
		assertContains(status, "Please enter all details on your card", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}
	
	@Test
	public void OnBoarding_Repayment_Submit_Card_Number_Blank() throws Exception {
		gotoRepaymentScreen();
		HtmlReporter.label("Submit with blank card number data");

		String nameOnCard = DataGenerator.generateStringByDateTime("CardNumberBlank");
		String expiry = "01/2022";
		
		repaymentPage.setCardName(nameOnCard);
		repaymentPage.setCardExpiry(expiry);
		repaymentPage.saveChanges();
		// Get alert
		HtmlReporter.label("Verify error message");
		String status = repaymentPage.getSaveStatus();
		assertContains(status, "Please enter all details on your card", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}
	
	@Test
	public void OnBoarding_Repayment_Submit_ExpireDate_Blank() throws Exception {
		gotoRepaymentScreen();
		HtmlReporter.label("Submit with blank expire date");
		
		String nameOnCard = DataGenerator.generateStringByDateTime("ExpireDateBlank");
		String cardNumber = DataGenerator.generateDebitCardNumber();
		
		repaymentPage.setCardName(nameOnCard);
		repaymentPage.setCardNumber(cardNumber);
		repaymentPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = repaymentPage.getSaveStatus();
		assertContains(status, "Please enter all details on your card", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}
}
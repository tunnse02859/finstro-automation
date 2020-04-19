package com.finstro.automation.test.regression;

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

public class OnBoardingAbove500Test extends MobileTestSetup {

	private OnboardingAPI onboardingAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SelectBusinessCardPage selectBusinessCardPage;
	private BusinessDetailPage businessDetailPage;
	private FindBusinessPage findBusinessPage;
	private FindAddressPage findAddressPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
	private MedicarePage medicarePage;
	private CompleteAgreementPage completeAgreementPage;

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

//	@Test
//	public void OnBoarding_01_Verify_select_business_card_amount_successfully() throws Exception {
//		// select 500$ and verify business detail page displayed
//		HtmlReporter.label("Select card $500 and verify Business Detail screen displayed");
//		businessDetailPage = selectBusinessCardPage.clickOnCard("500");
//		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
//				"Business Details is displayed after click on card 500");
//	}
//
//	@Test
//	public void OnBoarding_02_03_04_Verify_update_business_detail_successfully() throws Exception {
//
//		// get business data from excel
//		HashMap<String, String> businessDataForTest;
//		Object[][] businessDataSource = ExcelHelper.getTableToHashMap(
//				FilePaths.getResourcePath("/dataprovider/business_detail/BusinessDetail.xlsx"), "Sheet1");
//
//		// select 500$ and verify business detail page displayed
//		HtmlReporter.label("Go to Business Detail screen");
//		businessDetailPage = selectBusinessCardPage.clickOnCard("500");
//		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
//				"Business Details is displayed after click on card 500");
//
//		businessDataForTest = CommonFunction.getBusinessDataByType("Company", businessDataSource);
//
//		// ----- START find business and select -----
//		// go to search business and search, verify first matched is correct
//		HtmlReporter.label("Go to Find Business and find business with ABN = " + businessDataForTest.get("ABN"));
//		findBusinessPage = businessDetailPage.clickFindBusiness();
//		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
//				"Find Your Business Page is displayed");
//
//		findBusinessPage.inputSearchBusiness(businessDataForTest.get("ABN"));
//		findBusinessPage.verifyFirstMatch(businessDataForTest.get("Entity name"), businessDataForTest.get("ABN"));
//
//		// select first match and verify filled data
//		HtmlReporter.label("Select first match result and verify screen change");
//		findBusinessPage.clickOnFirstMatched();
//		businessDetailPage.verifyBusinessData(businessDataForTest.get("ABN"), businessDataForTest.get("Entity name"),
//				"");
//		HtmlReporter.label("Select business name");
//		businessDetailPage.selectBusinessName(businessDataForTest.get("Second Business Name"));
//		// ---- END find business -----
//
//		// ----- START find business trading address and select -----
//		HtmlReporter.label("Go to find address and search for address");
//		String addressInforForSearch = "60 Margaret St, SYDNEY";
//		String expectedFirstMatchTitle = "60 Margaret St";
//		String expectedFirstMatchInfor = "SYDNEY NSW 2000";
//		String expectedBusinessTradingAddress = "60 Margaret St, SYDNEY, NSW, 2000";
//
//		// go to search address and
//		findAddressPage = businessDetailPage.clickBusinessTradingAddress();
//		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
//				"Find Address screen is displayed");
//		findAddressPage.inputSearchAddress(addressInforForSearch);
//		findAddressPage.verifyFirstMatch(expectedFirstMatchTitle, expectedFirstMatchInfor);
//
//		// select first matched result and verify field updated
//		HtmlReporter.label("Select first match result and verify screen change");
//		findAddressPage.clickOnFirstMatched();
//		assertTrue(businessDetailPage.isActive(),
//				"Business Detail screen is not  displayed after select matched resulf",
//				"Business Detail screen is displayed after select matched resulf");
//		businessDetailPage.verifyBusinessTradingAddress(expectedBusinessTradingAddress);
//		// ----- END find business trading address and select -----
//
//		// click NEXT and verify saved data
//		HtmlReporter.label("Click next and verify screen change and data with API");
//		residentialAddressPage = businessDetailPage.clickNext();
//		driver.wait(15);
//		assertTrue(residentialAddressPage.isActive(),
//				"Residential address screen is not displayed after save Business Detail",
//				"Residential address screen is displayed after save Business Detail");
//
//		String savedBusinessTradingAddress = onboardingAPI.getBusinessDetailInfor();
//		// verify saved value correctly!
//		assertEquals(Common.getTestVariable("abn", true), businessDataForTest.get("ABN"),
//				"ABN from API after save doesnt match with expectation",
//				"ABN from API after save matched with expectation");
//		assertEquals(Common.getTestVariable("entityName", true), businessDataForTest.get("Entity name"),
//				"entityName from API after save doesnt match with expectation",
//				"entityName from API after save matched with expectation");
//		assertEquals(Common.getTestVariable("businessName", true), businessDataForTest.get("Second Business Name"),
//				"businessName from API after save doesnt match with expectation",
//				"businessName from API after save matched with expectation");
//		assertEquals(savedBusinessTradingAddress, expectedBusinessTradingAddress,
//				"Business trading address from API after save doesnt match with expectation",
//				"Business trading address from API after save matched with expectation");
//	}
//
//	@Test
//	public void OnBoarding_05_Verify_update_residential_address_successfully() throws Exception {
//		String addressInforForSearch = "50 Margaret St, ASHFIELD";
//		String expectedFirstMatchTitle = "50 Margaret St";
//		String expectedFirstMatchInfor = "ASHFIELD WA 6054";
//		String expectedResidentialAddress = "50 Margaret St, ASHFIELD, WA, 6054";
//
//		// go to residential address screen
//		HtmlReporter.label("Go to Residential Address screen");
//		businessDetailPage = selectBusinessCardPage.clickOnCard("500");
//		residentialAddressPage = businessDetailPage.clickNext();
//		assertTrue(residentialAddressPage.isActive(), "Residential address screen is not displayed",
//				"Residential address screen is displayed");
//
//		// go to search address and
//		HtmlReporter.label("Go to find address screen and search");
//		findAddressPage = residentialAddressPage.clickSearchAddress();
//		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
//				"Find Address screen is displayed");
//		findAddressPage.inputSearchAddress(addressInforForSearch);
//		findAddressPage.verifyFirstMatch(expectedFirstMatchTitle, expectedFirstMatchInfor);
//
//		// select first matched result and verify field updated
//		HtmlReporter.label("Select first and verify screen change");
//		findAddressPage.clickOnFirstMatched();
//		assertTrue(residentialAddressPage.isActive(),
//				"Residential Address screen is not  displayed after select matched resulf",
//				"Residential Address screen is displayed after select matched resulf");
//		residentialAddressPage.verifyResidentialAddress(expectedResidentialAddress);
//
//		// click next and verify
//		HtmlReporter.label("Click Next and verify screen change and data with API");
//		photoIDPage = residentialAddressPage.clickNext();
//		assertTrue(photoIDPage.isActive(), "PhotoID screen is not displayed", "PhotoID screen is displayed");
//		String savedResidentialAddress = onboardingAPI.getResidentialAddress();
//		assertEquals(savedResidentialAddress, expectedResidentialAddress,
//				"Residential address from API after save doesnt match with expectation",
//				"Residential address from API after save matched with expectation");
//
//	}
//
//	@DataProvider(name = "onboarding_DrivingLicense")
//	public Object[][] setupDrivingLicense() {
//		return new Object[][] { { "Male", "Phong", "Trinh", "Van", "ACT", "01/01/2021", "0123456789", "20/07/2025" } };
//	}
//
//	@Test(dataProvider = "onboarding_DrivingLicense")
//	public void OnBoarding_07_Verify_update_driving_license_successfully(String genderName, String firstNameString,
//			String lastNameString, String middleNameString, String stateName, String dobString,
//			String licenseNumberString, String expireDateString) throws Exception {
//		// go to driving license
//		HtmlReporter.label("Go to Driving License screen");
//		businessDetailPage = selectBusinessCardPage.clickOnCard("500");
//		assertTrue(businessDetailPage.isActive(), "Business Detail screen is not  displayed after click on next",
//				"Business Detail screen is displayed after click on next");
//
//		residentialAddressPage = businessDetailPage.clickNext();
//		assertTrue(residentialAddressPage.isActive(),
//				"Residential Address screen is not  displayed after click on next",
//				"Residential Address screen is displayed after click on next");
//
//		photoIDPage = residentialAddressPage.clickNext();
//		assertTrue(photoIDPage.isActive(), "PhotoID screen is not  displayed after click on next",
//				"PhotoID screen is displayed after click on next");
//
//		drivingLisencePage = photoIDPage.clickNext();
//		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed after click on next",
//				"Driver License screen is displayed after click on next");
//
//		HtmlReporter.label("Input driving license data");
//		// input data
//		drivingLisencePage.inputDriverLicenseInfor(genderName, firstNameString, lastNameString, middleNameString,
//				stateName, dobString, licenseNumberString, expireDateString);
//
//		// click next and verify data saved
//		HtmlReporter.label("Click Next and verify screen change and data with API");
//		postalAddressPage = drivingLisencePage.clickNext();
//		driver.wait(10);
//		assertTrue(postalAddressPage.isActive(), "Postal address of card screen is not displayed",
//				"Postal address of card screen is displayed");
//
//		onboardingAPI.recoveryData().then().verifyResponseCode(200)
//				.verifyJsonNodeEqual("drivingLicence.firstName", firstNameString)
//				.verifyJsonNodeEqual("drivingLicence.surname", lastNameString)
//				.verifyJsonNodeEqual("drivingLicence.middleName", middleNameString)
//				.verifyJsonNodeEqual("drivingLicence.gender", genderName.equalsIgnoreCase("Male") ? "M" : "F")
//				// .verifyJsonNodeEqual("drivingLicence.dateOfBirth", dobString)
//				.verifyJsonNodeEqual("drivingLicence.licenceNumber", licenseNumberString)
//				.verifyJsonNodeEqual("drivingLicence.state", stateName).flush();
//		// .verifyJsonNodeEqual("drivingLicence.validTo", expireDateString).flush();
//	}
//
//	@DataProvider(name = "onboarding_Medicare")
//	public Object[][] setupMedicare() {
//		return new Object[][] {
//				{ "Phong", "Van", "Trinh", "Female", "27/02/1983", "Green", "2684483925", "1", "03/2020" } };
//	}
//
//	@Test(dataProvider = "onboarding_Medicare")
//	public void OnBoarding_08_Verify_update_medicare_successfully(String firstNameString, String middleNameString,
//			String lastNameString, String genderName, String dobString, String cardColor, String medicareNumberString,
//			String referenceNumberString, String expireDateString) throws Exception {
//		// go to Medicare screen
//		HtmlReporter.label("Go to Medicare screen");
//		businessDetailPage = selectBusinessCardPage.clickOnCard("500");
//		assertTrue(businessDetailPage.isActive(), "Business Detail screen is not  displayed after click on next",
//				"Business Detail screen is displayed after click on next");
//
//		residentialAddressPage = businessDetailPage.clickNext();
//		assertTrue(residentialAddressPage.isActive(),
//				"Residential Address screen is not  displayed after click on next",
//				"Residential Address screen is displayed after click on next");
//
//		photoIDPage = residentialAddressPage.clickNext();
//		assertTrue(photoIDPage.isActive(), "PhotoID screen is not  displayed after click on next",
//				"PhotoID screen is displayed after click on next");
//
//		drivingLisencePage = photoIDPage.clickNext();
//		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed after click on next",
//				"Driver License screen is displayed after click on next");
//
//		medicarePage = drivingLisencePage.clickMedicare();
//		assertTrue(medicarePage.isActive(), "Medicare screen is not  displayed ", "Medicare screen is displayed");
//
//		// input data
//		HtmlReporter.label("Input Medicare data");
//		medicarePage.inputMedicareInfor(firstNameString, middleNameString, lastNameString, genderName, dobString,
//				cardColor, medicareNumberString, referenceNumberString, expireDateString);
//
//		// click next and verify data saved
//		HtmlReporter.label("Click Next and verify screen change and data with API");
//		medicarePage.clickNext();
//		driver.wait(10);
//		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed after click on next",
//				"Driver License screen is displayed after click on next");
//
//		onboardingAPI.recoveryData().then().verifyResponseCode(200)
//				.verifyJsonNodeEqual("medicareCard.cardNumber", medicareNumberString)
//				.verifyJsonNodeEqual("medicareCard.cardNumberRef", referenceNumberString)
//				.verifyJsonNodeEqual("medicareCard.firstName", firstNameString)
//				.verifyJsonNodeEqual("medicareCard.gender", genderName.equalsIgnoreCase("Male") ? "M" : "F")
//				.verifyJsonNodeEqual("medicareCard.identificationId", "null")
//				.verifyJsonNodeEqual("medicareCard.middleInitial", middleNameString)
//				.verifyJsonNodeEqual("medicareCard.surname", lastNameString)
//				// .verifyJsonNodeEqual("medicareCard.dateOfBirth", dobString)
//				// .verifyJsonNodeEqual("medicareCard.validTo", "2020-03-01")
//				.flush();
//	}
//
//	@Test
//	public void OnBoarding_09_Verify_select_postal_address_successfully() throws Exception {
//		// go to postal address
//		HtmlReporter.label("Go to Postal Address screen");
//		businessDetailPage = selectBusinessCardPage.clickOnCard("500");
//		assertTrue(businessDetailPage.isActive(), "Business Detail screen is not  displayed after click on next",
//				"Business Detail screen is displayed after click on next");
//
//		residentialAddressPage = businessDetailPage.clickNext();
//		assertTrue(residentialAddressPage.isActive(),
//				"Residential Address screen is not  displayed after click on next",
//				"Residential Address screen is displayed after click on next");
//
//		photoIDPage = residentialAddressPage.clickNext();
//		assertTrue(photoIDPage.isActive(), "PhotoID screen is not  displayed after click on next",
//				"PhotoID screen is displayed after click on next");
//
//		drivingLisencePage = photoIDPage.clickNext();
//		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed after click on next",
//				"Driver License screen is displayed after click on next");
//
//		postalAddressPage = drivingLisencePage.clickNext();
//		assertTrue(postalAddressPage.isActive(), "Postal Address Of Card screen is not displayed",
//				"Postal Address Of Card screen is displayed");
//
//		// select business trading address
//		HtmlReporter.label("Select Business Trading Address");
//		postalAddressPage.selectBusinessTradingAddress();
//
//		// click next verify
//		HtmlReporter.label("Click Next and verify screen change and data with API");
//		postalAddressPage.clickNext();
//		completeAgreementPage = new CompleteAgreementPage(driver);
//		assertTrue(completeAgreementPage.isActive(), "Complete Agreement Screen is not displayed",
//				"Complete Agreement Screen is displayed");
//
//		onboardingAPI.getPostalAddressInfor();
//		assertEquals(Common.getTestVariable("postalAddress", true),
//				Common.getTestVariable("businessTradingAddress", true),
//				"Postal Address is not matched with Busienss Trading Address",
//				"Postal Address is matched with Business Trading Address");
//	}

	@Test
	public void OnBoarding_Verify_submit_bank_statement_successfully() throws Exception {
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
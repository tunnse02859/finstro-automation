package com.finstro.automation.test.regression;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.common.CommonFunction;
import com.finstro.automation.excelhelper.ExcelHelper;
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
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.FilePaths;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;
import java.util.HashMap;

public class OnBoarding500Test extends MobileTestSetup {

	private FinstroAPI finstroAPI;
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
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		selectBusinessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		findBusinessPage = new FindBusinessPage(driver);
		findAddressPage = new FindAddressPage(driver);
		residentialAddressPage = new ResidentialAddressPage(driver);
		photoIDPage = new PhotoIDPage(driver);
		drivingLisencePage = new DriverLicensePage(driver);
		medicarePage = new MedicarePage(driver);
		postalAddressPage = new PostalAddressPage(driver);
		completeAgreementPage = new CompleteAgreementPage(driver);
		// check if register page is default page
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Do login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}

	@Test
	public void OnBoarding_01_Verify_select_business_card_amount_successfully() throws Exception {

		// select 500$ and verify business detail page displayed
		selectBusinessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");

		// call API to get business detailed information
		String businessTradingAddress = finstroAPI.getBusinessDetailInfor();

		// verify screen displayed correct value!
		String abn_acn = Common.getTestVariable("abn", true).equalsIgnoreCase("") ? Common.getTestVariable("acn", true)
				: Common.getTestVariable("abn", true);
		businessDetailPage.verifyBusinessData(abn_acn, Common.getTestVariable("entityName", true),
				Common.getTestVariable("businessName", true));
		businessDetailPage.verifyBusinessTradingAddress(businessTradingAddress);
	}

	@Test
	public void OnBoarding_02_03_04_Verify_update_business_detail_successfully() throws Exception {

		// get business data from excel
		HashMap<String, String> businessDataForTest;
		Object[][] businessDataSource = ExcelHelper.getTableToHashMap(
				FilePaths.getResourcePath("/dataprovider/business_detail/BusinessDetail.xlsx"), "Sheet1");

		// select 500$ and verify business detail page displayed
		selectBusinessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");

		// check current configured business data to select business for test
		if (businessDetailPage.getCurrentABN().equals("44004584552")) {
			businessDataForTest = CommonFunction.getBusinessDataByType("SoleTrader", businessDataSource);
		} else {
			businessDataForTest = CommonFunction.getBusinessDataByType("Company", businessDataSource);
		}

		// call API to get business detailed information
		// String businessTradingAddress = finstroAPI.getBusinessDetailInfor();

		// verify screen displayed correct value!
		// String abn_acn = Common.getTestVariable("abn",true).equalsIgnoreCase("") ?
		// Common.getTestVariable("acn",true) : Common.getTestVariable("abn",true);
		// businessDetailPage.verifyBusinessData(abn_acn,
		// Common.getTestVariable("entityName",true),
		// Common.getTestVariable("businessName",true));
		// businessDetailPage.verifyBusinessTradingAddress(businessTradingAddress);

		// ----- START find business and select -----
		// go to search business and search, verify first matched is correct
		businessDetailPage.clickFindBusiness();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");

		findBusinessPage.inputSearchBusiness(businessDataForTest.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessDataForTest.get("Entity name"), businessDataForTest.get("ABN"));

		// select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessDataForTest.get("ABN"), businessDataForTest.get("Entity name"),
				businessDataForTest.get("Business Name"));
		// ---- END find business -----

		// ----- START find business trading address and select -----
		String addressInforForSearch = "50 Margaret St, ASHFIELD";
		String expectedFirstMatchTitle = "50 Margaret St";
		String expectedFirstMatchInfor = "ASHFIELD WA 6054";
		String expectedBusinessTradingAddress = "50 Margaret St ASHFIELD WA 6054";

		// go to search address and
		businessDetailPage.clickBusinessTradingAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
				"Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInforForSearch);
		findAddressPage.verifyFirstMatch(expectedFirstMatchTitle, expectedFirstMatchInfor);

		// select first matched result and verify field updated
		findAddressPage.clickOnFirstMatched();
		assertTrue(businessDetailPage.isActive(),
				"Business Detail screen is not  displayed after select matched resulf",
				"Business Detail screen is displayed after select matched resulf");
		businessDetailPage.verifyBusinessTradingAddress(expectedBusinessTradingAddress);
		// ----- END find business trading address and select -----

		// click NEXT and verify saved data
		businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(),
				"Residential address screen is not displayed after save Business Detail",
				"Residential address screen is displayed after save Business Detail");

		String savedBusinessTradingAddress = finstroAPI.getBusinessDetailInfor();

		// verify saved value correctly!
		assertEquals(Common.getTestVariable("abn", true), businessDataForTest.get("ABN"),
				"ABN from API after save doesnt match with expectation",
				"ABN from API after save matched with expectation");
		assertEquals(Common.getTestVariable("entityName", true), businessDataForTest.get("Entity name"),
				"entityName from API after save doesnt match with expectation",
				"entityName from API after save matched with expectation");
		assertEquals(Common.getTestVariable("businessName", true), businessDataForTest.get("Business Name"),
				"businessName from API after save doesnt match with expectation",
				"businessName from API after save matched with expectation");
		assertEquals(savedBusinessTradingAddress, expectedBusinessTradingAddress,
				"Business trading address from API after save doesnt match with expectation",
				"Business trading address from API after save matched with expectation");
	}

	@Test
	public void OnBoarding_05_Verify_update_residential_address_successfully() throws Exception {
		String addressInfor = "60 Margaret St, SYDNEY";
		String expectedFirstMatchTitle = "60 Margaret St";
		String expectedFirstMatchInfor = "SYDNEY NSW 2000";
		String expectedResidentialAddress = "60 Margaret St SYDNEY NSW 2000";

		// go to residential address screen
		selectBusinessCardPage.clickOnCard("500");
		businessDetailPage.clickNext();
		// Thread.sleep(10000);
		assertTrue(residentialAddressPage.isActive(), "Residential address screen is not displayed",
				"Residential address screen is displayed");
		String savedResidentialAddress = finstroAPI.getResidentialAddress();
		residentialAddressPage.verifyResidentialAddress(savedResidentialAddress);

		// go to search address and
		residentialAddressPage.clickSearchAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
				"Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInfor);
		findAddressPage.verifyFirstMatch(expectedFirstMatchTitle, expectedFirstMatchInfor);

		// select first matched result and verify field updated
		findAddressPage.clickOnFirstMatched();
		assertTrue(residentialAddressPage.isActive(),
				"Residential Address screen is not  displayed after select matched resulf",
				"Residential Address screen is displayed after select matched resulf");
		residentialAddressPage.verifyResidentialAddress(expectedResidentialAddress);

		// click next and verify
		residentialAddressPage.clickNext();
		assertTrue(photoIDPage.isActive(), "PhotoID screen is not displayed after save residential address",
				"PhotoID screen is displayed after save residential address");
		savedResidentialAddress = finstroAPI.getResidentialAddress();
		assertEquals(savedResidentialAddress, expectedResidentialAddress,
				"Residential address from API after save doesnt match with expectation",
				"Residential address from API after save matched with expectation");

	}

	@Test
	public void OnBoarding_07_Verify_update_driving_license_successfully() throws Exception {
		// go to driving license
		selectBusinessCardPage.clickOnCard("500");
		businessDetailPage.clickNext();
		residentialAddressPage.clickNext();
		Thread.sleep(60000);
		photoIDPage.clickNext();
		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed after click on next",
				"Driver License screen is displayed after click on next");

		// verify data displayed on screen with API
		finstroAPI.getDrivingLicenceInfor();
		drivingLisencePage.verifyDriverLicenseInfor(
				Common.getTestVariable("gender", true).equalsIgnoreCase("M") ? "Male" : "Female",
				Common.getTestVariable("firstName", true), Common.getTestVariable("surname", true),
				Common.getTestVariable("middleName", true), Common.getTestVariable("state", true),
				// Common.getTestVariable("dateOfBirth",true),
				"01/01/2021", Common.getTestVariable("licenceNumber", true), Common.getTestVariable("validTo", true));

		// input data
		drivingLisencePage.inputDriverLicenseInfor("Male", "Phong", "Trinh", "Van", "ACT", "01/01/2021", "0123456789",
				"23/08/2020");

		// click next and verify data saved
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(), "Postal address of card screen is not displayed",
				"Postal address of card screen is displayed");

		finstroAPI.recoveryData().then().verifyResponseCode(200).verifyJsonNodeEqual("drivingLicence.firstName", "Tu")
				.verifyJsonNodeEqual("drivingLicence.surname", "Nguyen")
				.verifyJsonNodeEqual("drivingLicence.middleName", "Ngoc")
				.verifyJsonNodeEqual("drivingLicence.gender", "M")
				.verifyJsonNodeEqual("drivingLicence.dateOfBirth", "2021/01/01")
				.verifyJsonNodeEqual("drivingLicence.licenceNumber", "0123456789")
				.verifyJsonNodeEqual("drivingLicence.state", "ACE")
				.verifyJsonNodeEqual("drivingLicence.validTo", "08/2020").flush();
	}

	@Test
	public void OnBoarding_08_Verify_update_medicare_successfully() throws Exception {
		// go to driving license
		selectBusinessCardPage.clickOnCard("500");
		businessDetailPage.clickNext();
		residentialAddressPage.clickNext();
		Thread.sleep(50000);
		photoIDPage.clickNext();
		drivingLisencePage.clickMedicare();
		assertTrue(medicarePage.isActive(), "Medicare screen is not  displayed ", "Medicare screen is displayed");

		// verify data displayed on screen with API
		finstroAPI.getMedicareInfor();
		medicarePage.verifyMedicareInfor(Common.getTestVariable("firstName",true), Common.getTestVariable("middleInitial",true),
				Common.getTestVariable("surname",true),
				Common.getTestVariable("gender",true).equalsIgnoreCase("M") ? "Male" : "Female",
				Common.getTestVariable("dateOfBirth",true), Common.getTestVariable("cardColor",true),
				Common.getTestVariable("cardNumber",true), Common.getTestVariable("cardNumberRef",true),
				//Common.getTestVariable("validTo",true));
				"03/2020");
		
		
		//input data
		medicarePage.inputMedicareInfor("Phong", "Van", "Trinh", "Male", "27/02/1983", "Green", "2684483925", "1", "03/2020");
		
		//click next and verify data saved
		medicarePage.clickNext();
		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed after click on next",
				"Driver License screen is displayed after click on next");
		
		finstroAPI.recoveryData().then().verifyResponseCode(200)
			.verifyJsonNodeEqual("medicareCard.cardNumber", "2684483925")
			.verifyJsonNodeEqual("medicareCard.cardNumberRef", "1")
			.verifyJsonNodeEqual("medicareCard.dateOfBirth", "1983-02-27")
			.verifyJsonNodeEqual("medicareCard.firstName", "Phong")
			.verifyJsonNodeEqual("medicareCard.gender", "M")
			.verifyJsonNodeEqual("medicareCard.identificationId", "null")
			.verifyJsonNodeEqual("medicareCard.middleInitial", "Van")
			.verifyJsonNodeEqual("medicareCard.surname", "Trinh")
			.verifyJsonNodeEqual("medicareCard.validTo", "2020-03-01")
		.flush();
	}
	
	@Test
	public void OnBoarding_09_Verify_select_postal_address_successfully() throws Exception {
		// go to postal address
		selectBusinessCardPage.clickOnCard("500");
		businessDetailPage.clickNext();
		residentialAddressPage.clickNext();
		Thread.sleep(50000);
		photoIDPage.clickNext();
		drivingLisencePage.clickNext();
		
		assertTrue(postalAddressPage.isActive(),
				"Postal Address Of Card screen is not displayed",
				"Postal Address Of Card screen is displayed");
		finstroAPI.getPostalAddressInfor();
		postalAddressPage.verifyData(Common.getTestVariable("businessTradingAddress", true),Common.getTestVariable("residentialAddress", true),Common.getTestVariable("postalAddress", true));
		
		//select address
		int selectResidential = 0;
		if(Common.getTestVariable("postalAddress", true).equals(Common.getTestVariable("businessTradingAddress", true))) {
			postalAddressPage.selectResidentialAddress();
			selectResidential = 1;
		}else if(Common.getTestVariable("postalAddress", true).equals(Common.getTestVariable("residentialAddress", true))) {
			postalAddressPage.selectBusinessTradingAddress();
			selectResidential = 0;
		}
		
		//click next verify
		postalAddressPage.clickNext();
		Thread.sleep(30000);
		assertTrue(completeAgreementPage.isActive(),
				"Complete Agreement Screen is not displayed",
				"Complete Agreement Screen is displayed");
		finstroAPI.getPostalAddressInfor();
		
		if(selectResidential == 1) {
			assertEquals(Common.getTestVariable("postalAddress", true), Common.getTestVariable("residentialAddress", true), "Postal Address is not matched with Residential Address", "Postal Address is matched with Residential Address");
		}else {
			assertEquals(Common.getTestVariable("postalAddress", true), Common.getTestVariable("businessTradingAddress", true), "Postal Address is not matched with Busienss Trading Address", "Postal Address is matched with Business Trading Address");
		}
	}
}

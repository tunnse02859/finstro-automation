package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.excelhelper.ExcelHelper;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.FindAddressPage;
import com.finstro.automation.pages.on_boarding.FindBusinessPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.finstro.automation.common.CommonFunction;
import com.finstro.automation.utility.FilePaths;

public class BusinessDetailTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private FindBusinessPage findBusinessPage;
	private SelectBusinessCardPage businessCardPage;
	private FindAddressPage findAddressPage;
	private ResidentialAddressPage residentialAddressPage;
	private FinstroAPI finstroAPI;
	private Object[][] businessDataSource;

	@BeforeClass
	public void setupTestData() throws Exception {
		businessDataSource = ExcelHelper.getTableToHashMap(
				FilePaths.getResourcePath("/dataprovider/business_detail/BusinessDetail.xlsx"), "Sheet1");
		finstroAPI = new FinstroAPI();
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		businessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		findBusinessPage = new FindBusinessPage(driver);
		findAddressPage = new FindAddressPage(driver);
		residentialAddressPage = new ResidentialAddressPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page in first installation");

		toBusinessDetailPage();
	}

	public void toBusinessDetailPage() throws Exception {
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		businessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
	}


	@Test
	public void FPC_1321_Verify_Redirect_to_FindBusiness_Successfully() throws Exception {
		businessDetailPage.clickFindBusiness();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");
	}

	@Test
	public void FPC_1322_Verify_add_Business_Detail_Sucessfully_WithType_Company() throws Exception {
		HashMap<String, String> businessDataOnTest = CommonFunction.getBusinessDataByType("Company",businessDataSource);

		// go to search business and search type = company, verify first matched is
		// correct
		businessDetailPage.clickFindBusiness();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");

		findBusinessPage.inputSearchBusiness(businessDataOnTest.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessDataOnTest.get("Entity name"), businessDataOnTest.get("ABN"));

		// select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessDataOnTest.get("ABN"), businessDataOnTest.get("Entity name"),
				businessDataOnTest.get("Business Name"));
		businessDetailPage.clickNext();
	}

	@Test
	public void FPC_1323_Verify_add_Business_Detail_Sucessfully_WithType_SoleTrader() throws Exception {
		HashMap<String, String> businessDataOnTest = CommonFunction.getBusinessDataByType("SoleTrader",businessDataSource);

		// go to search business and search type = company, verify first matched is
		// correct
		businessDetailPage.clickFindBusiness();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");

		findBusinessPage.inputSearchBusiness(businessDataOnTest.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessDataOnTest.get("Entity name"), businessDataOnTest.get("ABN"));

		// select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessDataOnTest.get("ABN"), businessDataOnTest.get("Entity name"),
				businessDataOnTest.get("Business Name"));
		businessDetailPage.clickNext();
	}

	@Test
	public void FPC_1325_Verify_add_Business_Detail_Sucessfully_WithType_CorporateTrustee() throws Exception {
		
		HashMap<String, String> businessDataOnTest = CommonFunction.getBusinessDataByType("Trust",businessDataSource);

		// go to search business and search type = company, verify first matched is
		// correct
		businessDetailPage.clickFindBusiness();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");

		findBusinessPage.inputSearchBusiness(businessDataOnTest.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessDataOnTest.get("Entity name"), businessDataOnTest.get("ABN"));

		// select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessDataOnTest.get("ABN"), businessDataOnTest.get("Entity name"),
				businessDataOnTest.get("Business Name"));
		businessDetailPage.clickNext();
	}

	@Test
	public void FPC_1326_Verify_add_Business_Detail_Sucessfully_WithType_Partnership() throws Exception {
		HashMap<String, String> businessDataOnTest = CommonFunction.getBusinessDataByType("Partnership",businessDataSource);

		// go to search business and search type = company, verify first matched is correct
		businessDetailPage.clickFindBusiness();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");

		findBusinessPage.inputSearchBusiness(businessDataOnTest.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessDataOnTest.get("Entity name"), businessDataOnTest.get("ABN"));

		// select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessDataOnTest.get("ABN"), businessDataOnTest.get("Entity name"),
				businessDataOnTest.get("Business Name"));
		businessDetailPage.clickNext();
	}

	@Test
	public void FPC_1328_Verify_Find_Business_No_Result_Matched() throws Exception {
		String invalidBusinessInfor = Common.randomAlphaNumeric(15);

		businessDetailPage.clickFindBusiness();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");
		findBusinessPage.inputSearchBusiness(invalidBusinessInfor);
		assertTrue(findBusinessPage.isNoResultMatched(), "Address not found should be displayed",
				"Address not found displayed");
	}

	@Test
	public void FPC_1329_VerifyUserUpdateTheBusinessTradingNamesSuccessful() throws Exception {
		HashMap<String, String> businessDataForTest;
		if(businessDetailPage.getCurrentABN().equals("44004584552")) {
			businessDataForTest = CommonFunction.getBusinessDataByType("SoleTrader",businessDataSource);
		}else {
			businessDataForTest = CommonFunction.getBusinessDataByType("Company",businessDataSource);
		}

		// go to search business and search type = company, verify first matched is
		// correct
		businessDetailPage.clickFindBusiness();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");

		findBusinessPage.inputSearchBusiness(businessDataForTest.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessDataForTest.get("Entity name"), businessDataForTest.get("ABN"));

		// select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessDataForTest.get("ABN"), businessDataForTest.get("Entity name"),
				businessDataForTest.get("Business Name"));
		
		businessDetailPage.selectBusinessName(businessDataForTest.get("Second Business Name"));
		Thread.sleep(1500);
		businessDetailPage.verifyBusinessName(businessDataForTest.get("Second Business Name"));
		businessDetailPage.clickNext();
		finstroAPI.recoveryData().then().verifyResponseCode(200)
				.extractJsonValue("businessName", "businessDetails.asicBusiness.companyName").flush();
		assertEquals(Common.getTestVariable("businessName", true), businessDataForTest.get("Second Business Name"), "Business Name is saved incorrectly", "Business Name is saved correctly");
	}

	@Test
	public void FPC_1332_1334_Verify_add_Business_Trading_Address_Successfully() throws Exception {
		String addressInfor = "50 Margaret St, ASHFIELD";
		String expectedFirstMatchTitle = "50 Margaret St";
		String expectedFirstMatchInfor = "ASHFIELD WA 6054";
		String expectedBusinessTradingAddress = "50 Margaret St ASHFIELD WA 6054";

		// go to search address and
		businessDetailPage.clickBusinessTradingAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
				"Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInfor);
		findAddressPage.verifyFirstMatch(expectedFirstMatchTitle, expectedFirstMatchInfor);

		// select first matched result and verify field updated
		findAddressPage.clickOnFirstMatched();
		assertTrue(businessDetailPage.isActive(),
				"Business Detail screen is not  displayed after select matched resulf",
				"Business Detail screen is displayed after select matched resulf");
		businessDetailPage.verifyBusinessTradingAddress(expectedBusinessTradingAddress);

		// click next and verify
		businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(),
				"Residential address screen is not displayed after save Business Detail",
				"Residential address screen is displayed after save Business Detail");

		String savedBusinessTradingAddress = finstroAPI.getBusinessTradingAddress();
		assertEquals(savedBusinessTradingAddress, expectedBusinessTradingAddress,
				"Business trading address from API after save doesnt match with expectation",
				"Business trading address from API after save matched with expectation");
	}

	@Test
	public void FPC_1333_Verify_Find_Address_with_No_Result_Matched() throws Exception {
		String addressInfor = Common.randomAlphaNumeric(10);

		businessDetailPage.clickBusinessTradingAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
				"Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInfor);
		assertTrue(findAddressPage.isNoResultMatched(), "Address not found should be displayed",
				"Address not found displayed");
	}
}
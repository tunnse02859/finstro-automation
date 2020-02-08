package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.excelhelper.ExcelHelper;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.setup_information.BusinessDetailPage;
import com.finstro.automation.pages.setup_information.FindBusinessPage;
import com.finstro.automation.pages.setup_information.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.FilePaths;

public class BusinessDetailTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private FindBusinessPage findBusinessPage;
	private SelectBusinessCardPage businessCardPage;
	private Object[][] businessData;

	@BeforeClass
	public void setupTestData() throws Exception {
		businessData = ExcelHelper.getTableToHashMap(
				FilePaths.getResourcePath("/dataprovider/business_detail/BusinessDetail.xlsx"), "Sheet1");
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		businessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		findBusinessPage = new FindBusinessPage(driver);
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

	public HashMap<String, String> getBusinessDataByType(String businessType) {
		HashMap<String, String> data = null;
		for (Object[] rowData : businessData) {
			HashMap<String, String> hashMap = (HashMap<String, String>) rowData[0];
			data = hashMap;
			if (data.get("Business Type").equals(businessType)) {
				return data;
			}
		}
		return data;
	}

	@Test
	public void FPC_1321_Verify_Redirect_to_FindBusiness_Successfully() throws Exception {
		businessDetailPage.redirectToTheFindYourBusinessScreen();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");
	}

	@Test
	public void FPC_1322_Verify_add_Business_Detail_Sucessfully_WithType_Company() throws Exception {
		HashMap<String,String> businessData = getBusinessDataByType("Company");
		
		//go to search business and search type = company, verify first matched is correct
		businessDetailPage.redirectToTheFindYourBusinessScreen();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");
		
		findBusinessPage.inputSearchBusiness(businessData.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessData.get("Entity name"), businessData.get("ABN"));
		
		//select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessData.get("ABN"), businessData.get("Entity name"), businessData.get("Business Name"));
	}

	@Test
	public void FPC_1323_Verify_add_Business_Detail_Sucessfully_WithType_SoleTrader() throws Exception {
		HashMap<String,String> businessData = getBusinessDataByType("SoleTrader");
		
		//go to search business and search type = company, verify first matched is correct
		businessDetailPage.redirectToTheFindYourBusinessScreen();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");
		
		findBusinessPage.inputSearchBusiness(businessData.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessData.get("Entity name"), businessData.get("ABN"));
		
		//select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessData.get("ABN"), businessData.get("Entity name"), businessData.get("Business Name"));
	}
	
	@Test
	public void FPC_1325_Verify_add_Business_Detail_Sucessfully_WithType_CorporateTrustee() throws Exception {
		HashMap<String,String> businessData = getBusinessDataByType("Trust");
		
		//go to search business and search type = company, verify first matched is correct
		businessDetailPage.redirectToTheFindYourBusinessScreen();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");
		
		findBusinessPage.inputSearchBusiness(businessData.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessData.get("Entity name"), businessData.get("ABN"));
		
		//select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessData.get("ABN"), businessData.get("Entity name"), businessData.get("Business Name"));
	}

	@Test
	public void FPC_1326_Verify_add_Business_Detail_Sucessfully_WithType_Partnership() throws Exception {
		HashMap<String,String> businessData = getBusinessDataByType("Partnership");
		
		//go to search business and search type = company, verify first matched is correct
		businessDetailPage.redirectToTheFindYourBusinessScreen();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");
		
		findBusinessPage.inputSearchBusiness(businessData.get("ABN"));
		findBusinessPage.verifyFirstMatch(businessData.get("Entity name"), businessData.get("ABN"));
		
		//select first match and verify filled data
		findBusinessPage.clickOnFirstMatched();
		businessDetailPage.verifyBusinessData(businessData.get("ABN"), businessData.get("Entity name"), businessData.get("Business Name"));
	}

	@Test
	public void FPC_1328_Verify_Find_Business_No_Result_Matched() throws Exception {
		String invalidBusinessInfor = Common.randomAlphaNumeric(15);

		businessDetailPage.redirectToTheFindYourBusinessScreen();
		assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
				"Find Your Business Page is displayed");
		findBusinessPage.inputSearchBusiness(invalidBusinessInfor);
		assertTrue(findBusinessPage.isNoResultMatched(), "Address not found should be displayed",
				"Address not found displayed");
	}

	@Test
	public void FPC_1329_VerifyUserUpdateTheBusinessTradingNamesSuccessful() throws Exception {
		//businessDetailPage.clickFindBusiness();
		//assertTrue(findBusinessPage.isActive(), "Find Your Business Page is not displayed",
		//		"Find Your Business Page is displayed");
		//findBusinessPage.addBusinessDetailSuccessfully("SoleTrader");
	}
	
	//finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	//String residentialAddress = finstroAPI.getResidentialAddress();
	//residentialAddressPage.verifyResidentialAddress(residentialAddress);
}
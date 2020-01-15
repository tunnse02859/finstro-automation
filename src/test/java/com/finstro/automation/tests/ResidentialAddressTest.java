package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.assertTrue;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.pages.BusinessDetailPage;
import com.finstro.automation.pages.FindAddressPage;
import com.finstro.automation.pages.FindYourBusinessPage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.pages.ResidentialAddressPage;
import com.finstro.automation.pages.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class ResidentialAddressTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage selectbusinessCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private FindAddressPage findAddressPage;


	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		selectbusinessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		residentialAddressPage = new ResidentialAddressPage(driver);
		findAddressPage = new FindAddressPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");
		
		toResidentialAddresslPage();
	}
	
	public void toResidentialAddresslPage() throws Exception {
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		selectbusinessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
		businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(), "Residential Address screen is not  displayed after click on card next",
				"Residential Address screen is displayed after click on next");
	}

	@Test
	public void FPC_1336_Verify_Find_Address_with_No_Result_Matched() throws Exception {
		String addressInfor = Common.randomAlphaNumeric(10);
		
		residentialAddressPage.clickSearchAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed", "Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInfor);
		assertTrue(findAddressPage.isNoResultMatched(),"Address not found should be displayed","Address not found displayed");
	}
	
	@Test
	public void FPC_1337_Verify_Update_Residential_Address_sucessfully() throws Exception {
		String addressInfor = "60 Margaret St, SYDNEY";
		String expectedFirstMatchTitle = "60 Margaret St";
		String expectedFirstMatchInfor = "SYDNEY NSW 2000";
		String expectedResidentialAddress = "60 Margaret St, SYDNEY, NSW 2000";
		
		residentialAddressPage.clickSearchAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed", "Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInfor);
		findAddressPage.verifyFirstMatch(expectedFirstMatchTitle, expectedFirstMatchInfor);
		
		findAddressPage.clickOnFirstMatched();
		assertTrue(residentialAddressPage.isActive(), "Residential Address screen is not  displayed after select matched resulf",
				"Residential Address screen is displayed after select matched resulf");
		residentialAddressPage.verifyResidentialAddress(expectedResidentialAddress);
	}
}
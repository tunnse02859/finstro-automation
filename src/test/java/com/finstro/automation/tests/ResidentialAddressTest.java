package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.setup_information.BusinessDetailPage;
import com.finstro.automation.pages.setup_information.FindAddressPage;
import com.finstro.automation.pages.setup_information.PhotoIDPage;
import com.finstro.automation.pages.setup_information.ResidentialAddressPage;
import com.finstro.automation.pages.setup_information.SelectBusinessCardPage;
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
	private PhotoIDPage photoIDPage;
	private FinstroAPI finstroAPI;

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		selectbusinessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		residentialAddressPage = new ResidentialAddressPage(driver);
		photoIDPage = new PhotoIDPage(driver);
		findAddressPage = new FindAddressPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page in first installation");

		toResidentialAddresslPage();
	}

	public void toResidentialAddresslPage() throws Exception {
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		selectbusinessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
		businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(),
				"Residential Address screen is not  displayed after click on next",
				"Residential Address screen is displayed after click on next");	
	}

	@Test
	public void FPC_1336_Verify_Find_Address_with_No_Result_Matched() throws Exception {
		String addressInfor = Common.randomAlphaNumeric(10);

		residentialAddressPage.clickSearchAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
				"Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInfor);
		assertTrue(findAddressPage.isNoResultMatched(), "Address not found should be displayed",
				"Address not found displayed");
	}

	@Test
	public void FPC_1337_FPC_1338_Verify_Update_Residential_Address_sucessfully() throws Exception {
		String addressInfor = "60 Margaret St, SYDNEY";
		String expectedFirstMatchTitle = "60 Margaret St";
		String expectedFirstMatchInfor = "SYDNEY NSW 2000";
		String expectedResidentialAddress = "60 Margaret St, SYDNEY, NSW 2000";

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
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		String savedResidentialAddress = finstroAPI.getResidentialAddress();
		assertEquals(savedResidentialAddress, expectedResidentialAddress,
				"Residential address from API after save doesnt match with expectation",
				"Residential address from API after save matched with expectation");
	}
}
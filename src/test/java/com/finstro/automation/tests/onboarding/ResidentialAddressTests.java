package com.finstro.automation.tests.onboarding;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.OnboardingAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.FindAddressPage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class ResidentialAddressTests extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage selectBusinessCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private FindAddressPage findAddressPage;
	private PhotoIDPage photoIDPage;
	private OnboardingAPI onboardingAPI;
	
	@BeforeClass
	public void setupAPI() throws Exception {
		onboardingAPI = new OnboardingAPI();
		onboardingAPI.loginForAccessToken(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		onboardingAPI.setupResidentialAddress();
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		selectBusinessCardPage = new SelectBusinessCardPage(driver);
		photoIDPage = new PhotoIDPage(driver);
		findAddressPage = new FindAddressPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page in first installation");
		
		toResidentialAddresslPage();
	}

	public void toResidentialAddresslPage() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		
		HtmlReporter.label("Go go residential address screen");
		businessDetailPage = selectBusinessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details screen is not displayed",
				"Business Details is displayed");
		residentialAddressPage = businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(),
				"Residential Address screen is not displayed",
				"Residential Address screen is displayed");	
	}
	
	@Test
	public void FPC_1336_Verify_Find_Address_with_No_Result_Matched() throws Exception {
		String addressInfor = "ahhsd";
		
		HtmlReporter.label("Go go Search address screen and input invalid address");
		residentialAddressPage.clickSearchAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
				"Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInfor);
		HtmlReporter.label("Verify no resulf found");
		assertTrue(findAddressPage.isNoResultMatched(), "Address not found should be displayed",
				"Address not found displayed");
	}

	@Test
	public void FPC_1337_FPC_1338_Verify_Update_Residential_Address_sucessfully() throws Exception {
		String addressInforForSearch = "50 Margaret St, ASHFIELD";
		String expectedFirstMatchTitle = "50 Margaret St";
		String expectedFirstMatchInfor = "ASHFIELD WA 6054";
		String expectedResidentialAddress = "50 Margaret St, ASHFIELD WA 6054";

		assertTrue(residentialAddressPage.isActive(), "Residential address screen is not displayed",
				"Residential address screen is displayed");

		// go to search address and
		HtmlReporter.label("Go go Search address screen and search for address");
		findAddressPage = residentialAddressPage.clickSearchAddress();
		assertTrue(findAddressPage.isActive(), "Find Address screen is not displayed",
				"Find Address screen is displayed");
		findAddressPage.inputSearchAddress(addressInforForSearch);
		findAddressPage.verifyFirstMatch(expectedFirstMatchTitle, expectedFirstMatchInfor);

		// select first matched result and verify field updated
		HtmlReporter.label("Select first matched address and verify screen change");
		findAddressPage.clickOnFirstMatched();
		assertTrue(residentialAddressPage.isActive(),
				"Residential Address screen is not  displayed after select matched resulf",
				"Residential Address screen is displayed after select matched resulf");
		residentialAddressPage.verifyResidentialAddress(expectedResidentialAddress);

		// click next and verify
		HtmlReporter.label("Click next and verify data changed on API");
		photoIDPage = residentialAddressPage.clickNext();
		assertTrue(photoIDPage.isActive(), "PhotoID screen is not displayed", "PhotoID screen is displayed");
		String savedResidentialAddress = onboardingAPI.getResidentialAddress();
		assertContains(savedResidentialAddress, expectedResidentialAddress,
				"Residential address from API after save doesnt match with expectation",
				"Residential address from API after save matched with expectation");
	}
}
package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.assertTrue;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.pages.BackStatementPage;
import com.finstro.automation.pages.BusinessDetailPage;
import com.finstro.automation.pages.CongratulationsPage;
import com.finstro.automation.pages.DriverLicensePage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.PhotoIDPage;
import com.finstro.automation.pages.PostalAddressPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.pages.ResidentialAddressPage;
import com.finstro.automation.pages.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

public class PostalAddressOfCardTest extends MobileTestSetup {
	
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage businessCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
	private BackStatementPage backStatementPage;
	private CongratulationsPage congratulationsPage;
	

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		businessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		residentialAddressPage = new ResidentialAddressPage(driver);
		photoIDPage = new PhotoIDPage(driver);
		drivingLisencePage = new DriverLicensePage(driver);
		postalAddressPage = new PostalAddressPage(driver);
		backStatementPage = new BackStatementPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");
		
		toPostalAddressOfCardPage();
	}


	private void toPostalAddressOfCardPage() throws Exception {
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		businessCardPage.clickOnCard("500");
		Thread.sleep(10000);
		businessDetailPage.clickNext();
		residentialAddressPage.clickNext();
		Thread.sleep(10000);
		photoIDPage.clickNext();
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),
				"Postal Address Of Card screen is not  displayed after click on next",
				"Postal Address Of Card screen is displayed after click on next");
		
	}
	
	@Test
	public void FPC_1388_VerifyUserNavigateToTheNextScreen() throws Exception {
		postalAddressPage.clickNext();
		Thread.sleep(10000);
		assertTrue(congratulationsPage.isActive(),
				"Congratulations Page screen is not  displayed after click on next",
				"Congratulations Page screen is displayed after click on next");
		
	
	}

}

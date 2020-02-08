package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.assertTrue;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BankStatementPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
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
	private BankStatementPage backStatementPage;
	private CompleteAgreementPage completeAgreementPage;
	

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
		backStatementPage = new BankStatementPage(driver);
		completeAgreementPage = new CompleteAgreementPage(driver);
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
		assertTrue(completeAgreementPage.isActive(),
				"Congratulations Page screen is not  displayed after click on next",
				"Congratulations Page screen is displayed after click on next");
		
	
	}

}

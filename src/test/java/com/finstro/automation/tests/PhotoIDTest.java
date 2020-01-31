package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.setup_information.BusinessDetailPage;
import com.finstro.automation.pages.setup_information.PhotoIDPage;
import com.finstro.automation.pages.setup_information.ResidentialAddressPage;
import com.finstro.automation.pages.setup_information.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

public class PhotoIDTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage businessCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		businessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		residentialAddressPage = new ResidentialAddressPage(driver);
		photoIDPage = new PhotoIDPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");
		
		toPhotoIDPage();
	}
	
	public void toPhotoIDPage() throws Exception {
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		businessCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");
		businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(),
				"Residential Address screen is not  displayed after click on next",
				"Residential Address screen is displayed after click on next");
		residentialAddressPage.clickNext();
		assertTrue(photoIDPage.isActive(),
				"PhotoID screen is not  displayed after click on next",
				"PhotoID screen is displayed after click on next");
	}

	@Test
	public void FPC_1338_Verify_User_RedirectTo_PhotoID_Screen_Successfully() throws Exception {
	}
}
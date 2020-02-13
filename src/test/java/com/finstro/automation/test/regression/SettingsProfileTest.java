package com.finstro.automation.test.regression;

import static com.finstro.automation.utility.Assertion.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.home.HomePage;
import com.finstro.automation.pages.home.MainNavigator;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.pages.settings.SettingProfilePage;
import com.finstro.automation.pages.settings.SettingsPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

public class SettingsProfileTest extends MobileTestSetup {
	
	private FinstroAPI finstroAPI;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private SelectBusinessCardPage selectBusinessCardPage;
	private BusinessDetailPage businessDetailPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
	private CompleteAgreementPage completeAgreementPage;
	private HomePage homePage;
	private MainNavigator navigator;
	private SettingsPage settingPage;
	private SettingProfilePage settingProfilePage;
	
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
		residentialAddressPage = new ResidentialAddressPage(driver);
		photoIDPage = new PhotoIDPage(driver);
		drivingLisencePage = new DriverLicensePage(driver);
		postalAddressPage = new PostalAddressPage(driver);
		completeAgreementPage = new CompleteAgreementPage(driver);
		homePage = new HomePage(driver);
		settingPage = new SettingsPage(driver);
		// check if register page is default page
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Do login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		
		toSettingProfilePage();
	}
	
	private void toSettingProfilePage() throws Exception {
		selectBusinessCardPage.clickOnCard("500");
		Thread.sleep(10000);
		businessDetailPage.clickNext();
		residentialAddressPage.clickNext();
		photoIDPage.clickNext();
		drivingLisencePage.clickNext();
		postalAddressPage.clickNext();
		completeAgreementPage.confirmAgreement();
		navigator.gotoSettingsPage();
		settingPage.goToProfileDetailsPage();
		
		assertTrue(settingProfilePage.isActive(), "Setting page didnt showed as default page in first installation",
				"Setting page showed as default page");
		
	
	}
	
	@Test
	public void SettingProfile_01_VerifyUserCanEditTheProfileInfomation() throws Exception {
		settingProfilePage.editAFieldOnProfileDetailPage("VavretchekTest","lastName");
		settingProfilePage.verifAFieldOnProfileDetailPageSuccesfully("VavretchekTest","lastName");
	}
	
	@Test
	public void SettingProfile_02_VerifyUserCanEditTheDrivingLicenceInformation() throws Exception {
		settingProfilePage.swipeLeftPage();
		settingProfilePage.editAFieldDrivingLicenceInfor("test", "middleName");
		settingProfilePage.verifyEditDrivingLicenceInforSuccesfully("test", "middleName");
	}
	
	@Test
	public void SettingProfile_03_VerifyUserCanEditTheMedicareInformation () throws Exception {
		settingProfilePage.swipeLeftPage();
		settingProfilePage.editAFieldMedicareInfor("test", "middleName");
		settingProfilePage.verifyEditMedicareInforSuccesfully("test", "middleName");
	}

}
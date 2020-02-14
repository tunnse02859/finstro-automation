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
import com.finstro.automation.pages.settings.SettingProfile_DrivingLicensePage;
import com.finstro.automation.pages.settings.SettingProfile_MedicarePage;
import com.finstro.automation.pages.settings.SettingProfile_ProfileDetailPage;
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
	private SettingProfile_ProfileDetailPage settingProfileDetailPage;
	private SettingProfile_DrivingLicensePage settingProfileDrivingLicensePage;
	private SettingProfile_MedicarePage settingProfileMedicarePage;
	
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
		navigator = new MainNavigator(driver);
		homePage = new HomePage(driver);
		settingPage = new SettingsPage(driver);
		settingProfileDetailPage = new SettingProfile_ProfileDetailPage(driver);
		// check if register page is default page
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Do login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		
	}
	
	private void toSettingProfilePage() throws Exception {
		selectBusinessCardPage.clickOnCard("500");
		Thread.sleep(10000);
		businessDetailPage.clickNext();
		residentialAddressPage.clickNext();
		Thread.sleep(10000);
		photoIDPage.clickNext();
		drivingLisencePage.clickNext();
		postalAddressPage.clickNext();
		completeAgreementPage.confirmAgreement();
		navigator.gotoSettingsPage();
		settingPage.goToProfileDetailsPage();
		
		assertTrue(settingProfileDetailPage.isActive(), "Setting Profile Page is not displayed",
				"Setting Profile Page is displayed");

	}
	
	@Test
	public void SettingProfile_01_VerifyUserCanEditTheProfileInfomation() throws Exception {
		//go to setting profile page
		toSettingProfilePage();
		
		// call recover api and get profile data here
		// verify data from API with ui
		
		
		
		// change data and click save
		settingProfileDetailPage.editAFieldOnProfileDetailPage("VavretchekTest","lastName");
		settingProfileDetailPage.clickSaveSetting();
		
		// call API again and verify data from API is match with inputed, not UI
		//settingProfileDetailPage.verifAFieldOnProfileDetailPageSuccesfully("VavretchekTest","lastName");
	}
	
	@Test
	public void SettingProfile_02_VerifyUserCanEditTheDrivingLicenceInformation() throws Exception {
		//go to setting profile driving license page
		toSettingProfilePage();
		settingProfileDrivingLicensePage = settingProfileDetailPage.toSettingDrivingLicensePage();
		
		// call recover api and get profile data here
		// verify data from API with ui
		
		// change data and click save
		settingProfileDrivingLicensePage.editAFieldDrivingLicenceInfor("test", "middleName");
		settingProfileDrivingLicensePage.clickSaveSetting();
		
		// call API again and verify data from API is match with inputed, not UI
		//settingProfileDrivingLicensePage.verifyEditDrivingLicenceInforSuccesfully("test", "middleName");
	}
	
	@Test
	public void SettingProfile_03_VerifyUserCanEditTheMedicareInformation () throws Exception {
		//go to setting profile medicare page
		toSettingProfilePage();
		settingProfileDrivingLicensePage = settingProfileDetailPage.toSettingDrivingLicensePage();
		settingProfileMedicarePage = settingProfileDrivingLicensePage.toSettingMedicarePage();
		
		// call recover api and get profile data here
		// verify data from API with ui
		
		
		// change data and click save
		settingProfileMedicarePage.editAFieldMedicareInfor("test", "middleName");
		settingProfileMedicarePage.clickSaveSetting();
		
		// call API again and verify data from API is match with inputed, not UI
		//settingProfileMedicarePage.verifyEditMedicareInforSuccesfully("test", "middleName");
	}
}
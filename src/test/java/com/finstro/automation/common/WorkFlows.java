package com.finstro.automation.common;

import static com.finstro.automation.utility.Assertion.assertTrue;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.home.HomePage;
import com.finstro.automation.pages.home.MainNavigator;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.pages.settings.SettingsPage;
import com.finstro.automation.pages.settings.approval.SettingsApprovalBankUploadPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCardsPage;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;

public class WorkFlows {

	public static MainNavigator goToTheMainPage(AppiumBaseDriver driver) throws Exception {

		// goto Business Details page
		SelectBusinessCardPage selectBusinessCardPage = new SelectBusinessCardPage(driver);
		selectBusinessCardPage.clickOnCard("500");
		BusinessDetailPage businessDetailPage = new BusinessDetailPage(driver);
		assertTrue(businessDetailPage.isActive(), "Business Details is not  displayed after click on card 500",
				"Business Details is displayed after click on card 500");

		// goto Residential Address page
		businessDetailPage.clickNext();
		ResidentialAddressPage residentialAddressPage = new ResidentialAddressPage(driver);
		assertTrue(residentialAddressPage.isActive(), "You're not on the Business Details page",
				"You're on the Business Details page");

		// goto Photo ID page
		residentialAddressPage.clickNext();
		PhotoIDPage photoIDPage = new PhotoIDPage(driver);
		assertTrue(photoIDPage.isActive(), "You're not on the Photo ID page", "You're on the Photo ID page");

		// goto Driving License page
		photoIDPage.clickNext();
		DriverLicensePage drivingLisencePage = new DriverLicensePage(driver);
		assertTrue(drivingLisencePage.isActive(), "You're not on the Driving Licence page",
				"You're on the Driving Licence page");

		// goto Postal Address page
		drivingLisencePage.clickNext();
		PostalAddressPage postalAddressPage = new PostalAddressPage(driver);
		assertTrue(postalAddressPage.isActive(), "You're not on the Postal Address page",
				"You're on the Postal Address page");

		// Agreement
		postalAddressPage.clickNext();
		CompleteAgreementPage completeAgreementPage = new CompleteAgreementPage(driver);
		assertTrue(completeAgreementPage.isActive(), "You're not on the Agreement page",
				"You're on the Agreement page");

		// Confirm and goto main page
		completeAgreementPage.confirmAgreement();
		MainNavigator navigator = new MainNavigator(driver);
		assertTrue(navigator.isActive(), "You're not on the Navigator", "You're on the Navigator");

		return navigator;
	}

	public static HomePage goToHomePage(AppiumBaseDriver driver) throws Exception {
		if (driver.isAndroidDriver()) {
			 goToTheMainPage(driver);
		}
		return new HomePage(driver);
	}

	public static SettingsPage goToTheSettingsPage(AppiumBaseDriver driver) throws Exception {
		MainNavigator navigator;
		if (driver.isAndroidDriver()) {
			navigator = goToTheMainPage(driver);
		} else {
			navigator = new MainNavigator(driver);
		}
		SettingsPage settingsPage = navigator.gotoSettingsPage();
		assertTrue(settingsPage.isActive(), "You're not on the Settings page", "You're on the Settings page");
		return settingsPage;
	}

	public static SettingProfile_ProfileDetailPage goToTheSettingProfilePage(AppiumBaseDriver driver) throws Exception {

		SettingsPage settingsPage = goToTheSettingsPage(driver);

		// goto Settings Business Details page
		SettingProfile_ProfileDetailPage settingProfileDetail = settingsPage.goToProfileDetailsPage();
		assertTrue(settingProfileDetail.isActive(), "You're not on the Settings Profile Details Page",
				"You're on the Settings Profile Details Page");

		return settingProfileDetail;
	}

	public static SettingsBusinessDetailsFirstPage goToTheSettingBusinessDetailsPage(AppiumBaseDriver driver)
			throws Exception {

		SettingsPage settingsPage = goToTheSettingsPage(driver);

		// goto Settings Business Details page
		SettingsBusinessDetailsFirstPage settingBusinessDetailsFirstPage = settingsPage
				.gotoSettingsBusinessDetailsPage();
		assertTrue(settingBusinessDetailsFirstPage.isActive(), "You're not on the Settings Business Details Page",
				"You're on the Settings Business Details Page");

		return settingBusinessDetailsFirstPage;
	}

	public static SettingsApprovalBankUploadPage goToApprovalBankUploadPage(AppiumBaseDriver driver) throws Exception {

		SettingsPage settingsPage = goToTheSettingsPage(driver);

		// goto ApprovalBankUpload page
		SettingsApprovalBankUploadPage settingsApprovalBankUploadPage = settingsPage.gotoApprovalBankUploadPage();
		assertTrue(settingsApprovalBankUploadPage.isActive(), "You're not on the Approval/Bank Upload Page",
				"You're on the Approval/Bank Upload Page");

		return settingsApprovalBankUploadPage;
	}

	public static DebtCreditCardsPage goToDebtCreditCardsPage(AppiumBaseDriver driver) throws Exception {

		SettingsPage settingsPage = goToTheSettingsPage(driver);

		// goto DebtCreditCards page
		DebtCreditCardsPage debtCreditCardsPage = settingsPage.gotoDebtCreditCardsPage();
		assertTrue(debtCreditCardsPage.isActive(), "You're not on the Debt/ Credit Cards Page",
				"You're on the Debt/ Credit Cards Page");

		return debtCreditCardsPage;
	}

}

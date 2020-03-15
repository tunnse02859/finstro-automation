package com.finstro.automation.pages.settings;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;
import com.finstro.automation.pages.settings.approval.SettingsApprovalBankUploadPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCardsPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/settings_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'SETTINGS'")
	private WebElement textTitle;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Business Details\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Business Details'")
	private WebElement nvgBusinessDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Profile Details\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Profile Details'")
	private WebElement nvgProfileDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Approval/Bank Upload\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Approval/Bank Upload\'")
	private WebElement nvgApproveBankUpload;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Bank Account Details\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Bank Account Details'")
	private WebElement nvgBankAccountDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Credit/Debt Card Details\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Debit Card Details'")
	private WebElement nvgCardDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Global Payment Date\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Global Payment Date'")
	private WebElement nvgGlobalPaymentDate;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Contact Finstro\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Contact Finstro'")
	private WebElement nvgContactFinstro;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Finstro Card - PIN\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Finstro Card - PIN'")
	private WebElement nvgFinstroCardPin;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Touch ID\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Touch ID'")
	private WebElement nvgTouchId;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Privacy Policy\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Privacy Policy'")
	private WebElement nvgPrivacyPolicy;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Terms & Conditions\")")
	@iOSXCUITFindBy(iOSNsPredicate = "label == 'Terms & Conditions'")
	private WebElement nvgTermCondition;
	
	
	
	public SettingsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle);
	}
	
	public SettingsBusinessDetailsFirstPage gotoSettingsBusinessDetailsPage() throws Exception {
		driver.clickByPosition(nvgBusinessDetails,"middle");
		return new SettingsBusinessDetailsFirstPage(driver);
	}
  
  public SettingsApprovalBankUploadPage gotoApprovalBankUploadPage() throws Exception {
		driver.clickByPosition(nvgApproveBankUpload,"middle");
		return new SettingsApprovalBankUploadPage(driver);
	}
	
	public DebtCreditCardsPage gotoDebtCreditCardsPage() throws Exception {
		driver.clickByPosition(nvgCardDetails,"middle");
		return new DebtCreditCardsPage(driver);
	}
  
    public SettingProfile_ProfileDetailPage goToProfileDetailsPage() throws Exception{
    	driver.clickByPosition(nvgProfileDetails,"middle");
    	return new SettingProfile_ProfileDetailPage(driver);
    }
}

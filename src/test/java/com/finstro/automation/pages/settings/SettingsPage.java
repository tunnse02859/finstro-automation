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
	@iOSXCUITFindBy(accessibility = "settings_business")
	private WebElement nvgBusinessDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Profile Details\")")
	@iOSXCUITFindBy(accessibility = "settings_profile")
	private WebElement nvgProfileDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Approval/Bank Upload\")")
	@iOSXCUITFindBy(accessibility = "settings_approved")
	private WebElement nvgApproveBankUpload;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Bank Account Details\")")
	@iOSXCUITFindBy(accessibility = "settings_bank_account")
	private WebElement nvgBankAccountDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Credit/Debt Card Details\")")
	@iOSXCUITFindBy(accessibility = "settings_credit_debit")
	private WebElement nvgCardDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Global Payment Date\")")
	@iOSXCUITFindBy(accessibility = "settings_global_pay_date")
	private WebElement nvgGlobalPaymentDate;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Contact Finstro\")")
	@iOSXCUITFindBy(accessibility = "settings_contact")
	private WebElement nvgContactFinstro;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Finstro Card - PIN\")")
	@iOSXCUITFindBy(accessibility = "settings_finstro_pay_card")
	private WebElement nvgFinstroCardPin;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Touch ID\")")
	@iOSXCUITFindBy(accessibility = "settings_touch_id")
	private WebElement nvgTouchId;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Privacy Policy\")")
	@iOSXCUITFindBy(accessibility = "settings_bank_upload")
	private WebElement nvgPrivacyPolicy;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Terms & Conditions\")")
	@iOSXCUITFindBy(accessibility = "setting_term_condition")
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

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
	private WebElement textTitle;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Business Details\")")
	private WebElement nvgBusinessDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Profile Details\")")
	private WebElement nvgProfileDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Approval/Bank Upload\")")
	private WebElement nvgApproveBankUpload;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Bank Account Details\")")
	private WebElement nvgBankAccountDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Credit/Debt Card Details\")")
	private WebElement nvgCardDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Global Payment Date\")")
	private WebElement nvgGlobalPaymentDate;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Contact Finstro\")")
	private WebElement nvgContactFinstro;
	
	
	
	public SettingsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle) && textTitle.getText().equalsIgnoreCase("SETTINGS");
	}
	
	public SettingsBusinessDetailsFirstPage gotoSettingsBusinessDetailsPage() throws Exception {
		driver.click(nvgBusinessDetails);
		return new SettingsBusinessDetailsFirstPage(driver);
	}
  
  public SettingsApprovalBankUploadPage gotoApprovalBankUploadPage() throws Exception {
		driver.click(nvgApproveBankUpload);
		return new SettingsApprovalBankUploadPage(driver);
	}
	
	public DebtCreditCardsPage gotoDebtCreditCardsPage() throws Exception {
		driver.click(nvgCardDetails);
		return new DebtCreditCardsPage(driver);
	}
  
    public SettingProfile_ProfileDetailPage goToProfileDetailsPage() throws Exception{
    	driver.click(nvgProfileDetails);
    	return new SettingProfile_ProfileDetailPage(driver);
    }
}

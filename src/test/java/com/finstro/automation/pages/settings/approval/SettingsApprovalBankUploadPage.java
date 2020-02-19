package com.finstro.automation.pages.settings.approval;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsApprovalBankUploadPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Approval\")")
	private WebElement approveTitle;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"ID Check\"))")
	private WebElement nvgIDCheck;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Bank Accounts Connected\"))")
	private WebElement nvgBankAccountsConnected;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Direct Debt Authority\"))")
	private WebElement nvgDirectDebtAuthority;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Credit Assessment\"))")
	private WebElement nvgCreditAssessment;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Facility Agreement\"))")
	private WebElement nvgFacilityAgreement;
	
	
	public SettingsApprovalBankUploadPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(approveTitle);
	}
	
	public SettingsApprovalIDCheck_ProfileDetailsPage gotoIDCheckPage() throws Exception {
		driver.click(nvgIDCheck);
		return new SettingsApprovalIDCheck_ProfileDetailsPage(driver);
	}
	
}

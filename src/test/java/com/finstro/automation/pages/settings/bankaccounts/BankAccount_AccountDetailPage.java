package com.finstro.automation.pages.settings.bankaccounts;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.settings.approval.SettingsApprovalBankUploadPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BankAccount_AccountDetailPage {

	private AppiumBaseDriver driver;
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'BANK ACCOUNT'")
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/name_edt")
	private WebElement textTitle;
	
	@iOSXCUITFindBy(accessibility = "name on account")
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/name_edt")
	private WebElement txtCardName;
	
	@iOSXCUITFindBy(accessibility = "bsb")
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/bsb_edt")
	private WebElement txtBSB;
	
	@iOSXCUITFindBy(accessibility = "account number")
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/number_edt")
	private WebElement txtAccountNumber;
	
	@iOSXCUITFindBy(accessibility = "default")
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/default_text")
	private WebElement btnDefault;
	
	@iOSXCUITFindBy(accessibility = "delete")
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/delete_text")
	private WebElement btnDelete;
	
	@iOSXCUITFindBy(accessibility = "Yes")
	@AndroidFindBy(id = "android:id/button1")
	private WebElement btnOk;
	
	@iOSXCUITFindBy(accessibility = "No")
	@AndroidFindBy(id = "android:id/button2")
	private WebElement btnCancel;
	
	@iOSXCUITFindBy(accessibility = "BANK ACCOUNTS")
	private WebElement btnBack;
	
	// Alert
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	private WebElement popupMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'Error' || name contains 'Success'")
	private WebElement statusAlert;
	
	
	public BankAccount_AccountDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle);
	}
	
	
	public BankAccountPage setDefaultBankAccount() throws Exception {
		driver.click(btnDefault);
		return new BankAccountPage(driver);
	}
	
	public BankAccountPage deleteBankAccount() throws Exception {
		driver.click(btnDelete);
		driver.click(btnOk);
		return new BankAccountPage(driver);
	}
	
	public String getSaveStatus() throws Exception {
		driver.waitForElementDisplayed(statusAlert, 30);
		return driver.getText(statusAlert);
	}
	
}

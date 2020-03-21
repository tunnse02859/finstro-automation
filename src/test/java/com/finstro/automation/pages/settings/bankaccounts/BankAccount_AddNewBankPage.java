package com.finstro.automation.pages.settings.bankaccounts;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BankAccount_AddNewBankPage {

	private AppiumBaseDriver driver;
	
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'BANK ACCOUNT'")
	private WebElement textTitle;
	
	@iOSXCUITFindBy(accessibility = "name on account")
	private WebElement txtAccountName;
	
	@iOSXCUITFindBy(accessibility = "bsb")
	private WebElement txtBSB;
	
	@iOSXCUITFindBy(accessibility = "account number")
	private WebElement txtAccountNumber;
	
	@iOSXCUITFindBy(accessibility = "saveSetting")
	private WebElement btnSaveSettings;
	
	@iOSXCUITFindBy(accessibility = "BANK ACCOUNTS")
	private WebElement btnBack;
	
	// Alert
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	private WebElement popupMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'Error' || name contains 'Success'")
	private WebElement statusAlert;
	
	public BankAccount_AddNewBankPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle) && driver.isElementDisplayed(btnSaveSettings);
	}
	
	public void setAccountName(String strNameOnAccount) throws Exception {
		driver.inputTextWithClear(txtAccountName, strNameOnAccount);
	}
	
	public void setBsb(String strBsb) throws Exception {
		driver.inputTextWithClear(txtBSB, strBsb);
	}
	
	public void setAccountNumber(String strAccountNumber) throws Exception {
		driver.inputTextWithClear(txtAccountNumber, strAccountNumber);
	}
	
	public BankAccountPage saveChanges() throws Exception {
		driver.click(btnSaveSettings);
		return new BankAccountPage(driver);
	}
	
	public String getSaveStatus() throws Exception {
		return driver.getText(statusAlert);
	}
	
}

package com.finstro.automation.pages.settings.bankaccounts;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BankAccount_AddNewBankPage {

	private AppiumBaseDriver driver;
	
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'BANK ACCOUNT'")
	private WebElement textTitle;
	
	@iOSXCUITFindBy(accessibility = "name on account")
	private WebElement txtCardName;
	
	@iOSXCUITFindBy(accessibility = "bsb")
	private WebElement txtBSB;
	
	@iOSXCUITFindBy(accessibility = "account number")
	private WebElement txtAccountNumber;
	
	@iOSXCUITFindBy(accessibility = "saveSetting")
	private WebElement btnSaveSettings;
	
	@iOSXCUITFindBy(accessibility = "BANK ACCOUNTS")
	private WebElement btnBack;
	
	public BankAccount_AddNewBankPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle);
	}
	
	public void setCardName(String strNameOnCard) throws Exception {
		driver.inputTextWithClear(txtCardName, strNameOnCard);
	}
	
	public void setCardNumber(String strCardNumber) throws Exception {
		driver.inputTextWithClear(txtBSB, strCardNumber);
	}
	
	public void setCardExpiry(String strExpiry) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.inputTextWithClear(txtAccountNumber, strExpiry);
		}else {
			driver.selectPickerWheel(txtAccountNumber, "February");
		}
	}
	
	public BankAccountPage saveChanges() throws Exception {
		driver.click(btnSaveSettings);
		driver.wait(10);
		return new BankAccountPage(driver);
	}
	
}

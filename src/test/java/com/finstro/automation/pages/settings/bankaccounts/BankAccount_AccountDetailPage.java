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
	private WebElement textTitle;
	
	@iOSXCUITFindBy(accessibility = "name on account")
	private WebElement txtCardName;
	
	@iOSXCUITFindBy(accessibility = "bsb")
	private WebElement txtBSB;
	
	@iOSXCUITFindBy(accessibility = "account number")
	private WebElement txtAccountNumber;
	
	@iOSXCUITFindBy(accessibility = "default")
	private WebElement btnDefault;
	
	@iOSXCUITFindBy(accessibility = "delete")
	private WebElement btnDelete;
	
	@iOSXCUITFindBy(accessibility = "Yes")
	private WebElement btnOk;
	
	@iOSXCUITFindBy(accessibility = "No")
	private WebElement btnCancel;
	
	@iOSXCUITFindBy(accessibility = "BANK ACCOUNTS")
	private WebElement btnBack;
	
	// Alert
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	private WebElement popupMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement popupType;
	
	
	public BankAccount_AccountDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle);
	}
	
	public void changeCardName(String strNameOnCard) throws Exception {
		driver.inputTextWithClear(txtCardName, strNameOnCard);
	}
	
	public void changeCardNumber(String strCardNumber) throws Exception {
		driver.inputTextWithClear(txtBSB, strCardNumber);
	}
	
	public void changeCardExpiry(String strExpiry) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.inputTextWithClear(txtAccountNumber, strExpiry);
		}else {
			driver.selectPickerWheel(txtAccountNumber, "February");
		}
	}
	
	public BankAccountPage setDefaultCard() throws Exception {
		driver.click(btnDefault);
		driver.wait(10);
		return new BankAccountPage(driver);
	}
	
	public BankAccountPage deleteCard() throws Exception {
		driver.click(btnDelete);
		driver.click(btnOk);
		driver.wait(10);
		return new BankAccountPage(driver);
	}
	
}

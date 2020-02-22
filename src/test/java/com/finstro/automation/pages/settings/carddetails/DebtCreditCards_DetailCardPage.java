package com.finstro.automation.pages.settings.carddetails;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.settings.approval.SettingsApprovalBankUploadPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DebtCreditCards_DetailCardPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"DEBT/CREDIT CARDS\")")
	private WebElement textTitle;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/name_edt")
	private WebElement txtCardName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/number_edt")
	private WebElement txtCardNumber;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/expiry_edt")
	private WebElement txtExpiry;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/default_text")
	private WebElement btnDefault;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/delete_text")
	private WebElement btnDelete;
	
	@AndroidFindBy(id = "android:id/button1")
	private WebElement btnOk;
	
	@AndroidFindBy(id = "android:id/button2")
	private WebElement btnCancel;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_icon")
	private WebElement btnBack;
	
	// Alert
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	private WebElement popupMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement popupType;
	
	
	public DebtCreditCards_DetailCardPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(txtCardName) && 
				driver.isElementDisplayed(txtCardNumber) && 
				driver.isElementDisplayed(txtExpiry) &&
				driver.isElementDisplayed(btnDefault) &&
				driver.isElementDisplayed(btnDelete);
	}
	
	public void changeCardName(String strNameOnCard) throws Exception {
		driver.inputTextWithClear(txtCardName, strNameOnCard);
	}
	
	public void changeCardNumber(String strCardNumber) throws Exception {
		driver.inputTextWithClear(txtCardNumber, strCardNumber);
	}
	
	public void changeCardExpiry(String strExpiry) throws Exception {
		driver.inputTextWithClear(txtExpiry, strExpiry);
	}
	
	public DebtCreditCardsPage setDefaultCard() throws Exception {
		driver.click(btnDefault);
		return new DebtCreditCardsPage(driver);
	}
	
	public DebtCreditCardsPage deleteCard() throws Exception {
		driver.click(btnDelete);
		driver.click(btnOk);
		return new DebtCreditCardsPage(driver);
	}
	
}

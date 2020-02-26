package com.finstro.automation.pages.settings.carddetails;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DebtCreditCards_DetailCardPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"DEBT/CREDIT CARDS\")")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'DEBIT/CREDIT CARDS'")
	private WebElement textTitle;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/name_edt")
	@iOSXCUITFindBy(accessibility = "name on card")
	private WebElement txtCardName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/number_edt")
	@iOSXCUITFindBy(accessibility = "card number")
	private WebElement txtCardNumber;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/expiry_edt")
	@iOSXCUITFindBy(accessibility = "expiry")
	private WebElement txtExpiry;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/default_text")
	@iOSXCUITFindBy(accessibility = "default")
	private WebElement btnDefault;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/delete_text")
	@iOSXCUITFindBy(accessibility = "delete")
	private WebElement btnDelete;
	
	@AndroidFindBy(id = "android:id/button1")
	@iOSXCUITFindBy(accessibility = "Yes")
	private WebElement btnOk;
	
	@AndroidFindBy(id = "android:id/button2")
	@iOSXCUITFindBy(accessibility = "No")
	private WebElement btnCancel;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_icon")
	@iOSXCUITFindBy(accessibility = "DEBIT/CREDIT CARDS")
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
		return driver.isElementDisplayed(btnDefault);
	}
	
	public void changeCardName(String strNameOnCard) throws Exception {
		driver.inputTextWithClear(txtCardName, strNameOnCard);
	}
	
	public void changeCardNumber(String strCardNumber) throws Exception {
		driver.inputTextWithClear(txtCardNumber, strCardNumber);
	}
	
	public void changeCardExpiry(String strExpiry) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.inputTextWithClear(txtExpiry, strExpiry);
		}else {
			driver.selectPickerWheel(txtExpiry, "February");
		}
	}
	
	public DebtCreditCardsPage setDefaultCard() throws Exception {
		driver.click(btnDefault);
		driver.wait(20);
		return new DebtCreditCardsPage(driver);
	}
	
	public DebtCreditCardsPage deleteCard() throws Exception {
		driver.click(btnDelete);
		driver.click(btnOk);
		driver.wait(20);
		return new DebtCreditCardsPage(driver);
	}
	
}

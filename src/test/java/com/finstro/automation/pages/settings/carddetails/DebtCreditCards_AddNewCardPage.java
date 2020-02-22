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

public class DebtCreditCards_AddNewCardPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"DEBT/CREDIT CARDS\")")
	private WebElement textTitle;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_name_card")
	private WebElement txtCardName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_card_number")
	private WebElement txtCardNumber;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_expiry")
	private WebElement txtExpiry;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnScanCard")
	private WebElement btnScanCard;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement btnSaveSettings;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	private WebElement btnBack;
	
	
	public DebtCreditCards_AddNewCardPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(txtCardName) && 
				driver.isElementDisplayed(btnScanCard) && 
				driver.isElementDisplayed(btnBack);
	}
	
	public void setCardName(String strNameOnCard) throws Exception {
		driver.inputTextWithClear(txtCardName, strNameOnCard);
	}
	
	public void setCardNumber(String strCardNumber) throws Exception {
		driver.inputTextWithClear(txtCardNumber, strCardNumber);
	}
	
	public void setCardExpiry(String strExpiry) throws Exception {
		driver.inputTextWithClear(txtExpiry, strExpiry);
	}
	
	public DebtCreditCardsPage saveChanges() throws Exception {
		driver.click(btnSaveSettings);
		driver.wait(10);
		return new DebtCreditCardsPage(driver);
	}
	
}

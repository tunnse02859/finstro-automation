package com.finstro.automation.pages.settings.carddetails;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DebtCreditCards_AddNewCardPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"DEBT/CREDIT CARDS\")")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'DEBIT/CREDIT CARDS'")
	private WebElement textTitle;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_name_card")
	@iOSXCUITFindBy(accessibility = "name on card")
	private WebElement txtCardName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_card_number")
	@iOSXCUITFindBy(accessibility = "card number")
	private WebElement txtCardNumber;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_expiry")
	@iOSXCUITFindBy(accessibility = "expiry")
	private WebElement txtExpiry;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnScanCard")
	@iOSXCUITFindBy(accessibility = "scancard")
	private WebElement btnScanCard;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	@iOSXCUITFindBy(accessibility = "saveSettings")
	private WebElement btnSaveSettings;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(accessibility = "DEBIT/CREDIT CARDS")
	private WebElement btnBack;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'Error' || name contains 'Success'")
	private WebElement statusAlert;
	
	public DebtCreditCards_AddNewCardPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnScanCard);
	}
	
	public void setCardName(String strNameOnCard) throws Exception {
		driver.inputTextWithClear(txtCardName, strNameOnCard);
	}
	
	public void setCardNumber(String strCardNumber) throws Exception {
		driver.inputTextWithClear(txtCardNumber, strCardNumber);
	}
	
	public void setCardExpiry(String strExpiry) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.inputTextWithClear(txtExpiry, strExpiry);
		}else {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
			Date date = simpleDateFormat.parse(strExpiry);
			String month = new SimpleDateFormat("MMMM").format(date);
			String year = new SimpleDateFormat("YYYY").format(date);
			String[] strdate = {month, year};
			driver.click(txtExpiry);
			driver.selectPickerWheels(strdate);
		}
	}
	
	public DebtCreditCardsPage saveChanges() throws Exception {
		driver.click(btnSaveSettings);
		return new DebtCreditCardsPage(driver);
	}
	
	public String getSaveStatus() throws Exception {
		return driver.waitForTextElementPresent(statusAlert, 30);
	}
	
}

package com.finstro.automation.pages.on_boarding;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCardsPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class RepaymentPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(accessibility = "Back")
	private WebElement back;


	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Repayments'")
	private WebElement title;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Next'")
	private WebElement next;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_name_card")
	@iOSXCUITFindBy(accessibility = "name on card")
	private WebElement txtCardName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_card_number")
	@iOSXCUITFindBy(accessibility = "card number")
	private WebElement txtCardNumber;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/repayment_expiry")
	@iOSXCUITFindBy(accessibility = "expiry")
	private WebElement txtExpiry;
	
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'Error' || name contains 'Success'")
	private WebElement statusAlert;

	public RepaymentPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
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
	
	public void saveChanges() throws Exception {
		driver.click(next);
	}
	
	public String getSaveStatus() throws Exception {
		return driver.waitForTextElementPresent(statusAlert, 30);
	}


	
}

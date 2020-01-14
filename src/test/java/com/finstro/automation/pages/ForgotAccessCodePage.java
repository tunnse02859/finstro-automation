package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.assertEquals;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ForgotAccessCodePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id="au.com.finstro.finstropay:id/forgot_access_title")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='Reset Access']")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	private WebElement cancelButton;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/forgot_access_mobile_edt")
	private WebElement mobileNumbers;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/forgot_access_email_edt")
	private WebElement emailAdress;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement submitButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	private WebElement popupMessage;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement popupType;

	public ForgotAccessCodePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.waitForElementDisplayed(title, 10);
	}

	
	public void verifyPopupMessage(String expectedMessage) throws Exception {
		String actualMessage = driver.getText(popupType) + " - " + driver.getText(popupMessage);
		assertEquals(actualMessage,expectedMessage,"Error message isnt correct",
				"Error message displayed correctly");
	}

	public void clickCancel() throws Exception {
		driver.click(cancelButton);
	}
	
	public void inputMobileNumber(String mobileNumber) throws Exception {
		driver.inputText(mobileNumbers, mobileNumber);
	}
	
	public void inputEmailAdress(String emailAddress) throws Exception {
		driver.inputText(emailAdress, emailAddress);
	}

	public void submit() throws Exception {
		driver.click(submitButton);
	}
}

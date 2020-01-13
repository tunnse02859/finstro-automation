package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ForgotAccessCodePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id="au.com.finstro.finstropay:id/forgot_access_title")
  @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='Reset Access']")
  private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	private WebElement cancelButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement submitButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_holder")
	private WebElement errorPopup;

	public ForgotAccessCodePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.waitForElementDisplayed(title, 10);
	}

	public boolean isErrorPopupDisplayed() throws Exception {
		return driver.waitForElementDisplayed(errorPopup, 10);

	}

	public void cancelForgetAccesCode() throws Exception {
		driver.click(cancelButton);
	}

	public void submitResetAccessFormWithAllBlankFields() throws Exception {
		driver.click(submitButton);
		assertTrue(isErrorPopupDisplayed(),
				"The error popup is not displayed after Users submit reset access form without input");
	}
}

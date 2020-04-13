package com.finstro.automation.pages.on_boarding;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PhotoIDPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(accessibility = "Back")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'Error'")
	private WebElement errorMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/photo_upload_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Photo ID'")
	private WebElement title;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	@iOSXCUITFindBy(accessibility = "next")
	private WebElement next;

	@AndroidFindBy(uiAutomator = "au.com.finstro.finstropay:id/front_progress")
	private WebElement frontLoadingProgress;

	@AndroidFindBy(uiAutomator = "au.com.finstro.finstropay:id/back_progress")
	private WebElement backLoadingProgress;

	@AndroidFindBy(uiAutomator = "au.com.finstro.finstropay:id/selfie_progress")
	private WebElement selfieLoadingProgress;

	public PhotoIDPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public DriverLicensePage clickNext() throws Exception {
		int count = 0;
		do {
			driver.click(next);
			if (!driver.waitForElementDisplayed(errorMessage, 3)) {
				return new DriverLicensePage(driver);
			}
			driver.wait(5);
			count++;
		} while (count < 7);
		throw new Exception("Cannot bypass through PhotoID screen after try for several times");
	}
}

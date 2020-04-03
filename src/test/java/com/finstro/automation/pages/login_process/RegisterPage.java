package com.finstro.automation.pages.login_process;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.report.HtmlReporter;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class RegisterPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/have_account_link\"))")
	@iOSXCUITFindBy(accessibility = "Already have an Account? Login")
	private WebElement loginPageLink;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/tvAgreement")
	@iOSXCUITFindBy(accessibility = "agreement")
	private WebElement agreement;

	@iOSXCUITFindBy(accessibility = "Don’t Allow")
	private WebElement notify_dontallow;

	@iOSXCUITFindBy(accessibility = "Allow")
	private WebElement notify_allow;

	public RegisterPage(AppiumBaseDriver driver) throws Exception {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public void toLoginPage() throws Exception {
		driver.clickByPosition(loginPageLink, "right");
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(agreement);
	}

	public void allowNotification() throws Exception {
		if (driver.isElementDisplayed(notify_allow) && driver.isIOSDriver()) {
			driver.click(notify_allow);
			HtmlReporter.pass("Allow Notification on IOS successfully");
		}
	}

	public void dontAllowNotification() throws Exception {
		try {
			driver.click(notify_dontallow);
		}catch(Exception e) {		
		}
	}
}

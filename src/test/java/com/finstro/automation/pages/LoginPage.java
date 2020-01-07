package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/login_email_edt")
	private WebElement emailAddress;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/login_access_code_edt")
	private WebElement accessCode;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	private WebElement submit;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/sign_up_link\"))")
	private WebElement registerPageLink;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/forgot_access_code\"))")
	private WebElement forgotAccessCodePageLink;

	public LoginPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.waitForElementDisplayed(emailAddress, 10);
	}


	public ForgotAccessCodePage toForgotAccessCodePage() throws Exception {
		driver.clickByPosition(forgotAccessCodePageLink, "right");
		return new ForgotAccessCodePage(driver);
	}

	public RegisterPage toRegisterPage() throws Exception {
		driver.clickByPosition(registerPageLink, "right");
		return new RegisterPage(driver);
	}

	public void doLogin(String email, String code) throws Exception {
		new RegisterPage(driver).toLoginPage();
		assertTrue(this.isActive(), "Login screen didnt showed after tap on login");
		driver.inputText(emailAddress, email);
		driver.inputText(accessCode, code);
		driver.click(submit);
		assertTrue(isActive(), "Home screen didnt showed after login");
	}
}

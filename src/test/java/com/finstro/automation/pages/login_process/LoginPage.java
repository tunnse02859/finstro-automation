package com.finstro.automation.pages.login_process;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.report.HtmlReporter;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'ERROR'")
	private WebElement errorMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/login_email_edt")
	@iOSXCUITFindBy(accessibility = "email")
	private WebElement emailAddress;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/login_access_code_edt")
	@iOSXCUITFindBy(accessibility = "accessCode")
	private WebElement accessCode;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	@iOSXCUITFindBy(accessibility = "submit")
	private WebElement submit;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/sign_up_link\"))")
	@iOSXCUITFindBy(accessibility = "signup")
	private WebElement registerPageLink;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/forgot_access_code\"))")
	@iOSXCUITFindBy(accessibility = "forgotAccesscode")
	private WebElement forgotAccessCodePageLink;

	@iOSXCUITFindBy(accessibility = "Maybe Later")
	private WebElement touchID_DontSave;

	@iOSXCUITFindBy(accessibility = "Yes")
	private WebElement touchID_Yes;

	@iOSXCUITFindBy(accessibility = "Not Now")
	private WebElement savePass_NotNow;

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

	public void toRegisterPage() throws Exception {
		driver.clickByPosition(registerPageLink, "right");
	}

	public void login(String email, String code) throws Exception {
		driver.inputTextWithClear(emailAddress, email);
		driver.inputTextWithClear(accessCode, code);
		driver.click(submit);
		driver.dismissAlert();
	}

	public void doSuccessLogin(String email, String code) throws Exception {
		HtmlReporter.label("Login to the app");
		new RegisterPage(driver).toLoginPage();
		assertTrue(this.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after tap on login");
		login(email, code);
		if (driver.isIOSDriver()) {
			if(driver.waitForElementDisplayed(savePass_NotNow, 15)) {
				clickNotNowiOS();
			}
			if(driver.waitForElementDisplayed(touchID_DontSave, 15)) {
				clickMaybeLateriOS();
			}
		}
	}
	
	public void clickNotNowiOS() {
		try {
			driver.click(savePass_NotNow);
		}catch(Exception e) {
		}
	}
	
	public void clickMaybeLateriOS() {
		try {
			driver.click(touchID_DontSave);
		}catch(Exception e) {		
		}
	}

	public String getErrorMessage() throws Exception {
		return driver.waitForTextElementPresent(errorMessage, 30);
	}
}

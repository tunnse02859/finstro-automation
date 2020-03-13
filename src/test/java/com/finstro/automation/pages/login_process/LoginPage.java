package com.finstro.automation.pages.login_process;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.home.HomePage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	@iOSXCUITFindBy(iOSClassChain = "name BEGINSWITH 'ERROR'")
	private WebElement errorMessage;
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement errorType;

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
	
	@iOSXCUITFindBy(accessibility = "Save Password")
	private WebElement savePassword;

	public LoginPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.waitForElementDisplayed(emailAddress, 10);
	}

	public void toForgotAccessCodePage() throws Exception {
		driver.clickByPosition(forgotAccessCodePageLink, "right");
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
		new RegisterPage(driver).toLoginPage();
		assertTrue(this.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after tap on login");
		login(email, code);
		if (driver.isIOSDriver()) {
			if (driver.isElementDisplayed(savePassword)) {
				driver.click(savePassword);
			}
			if (driver.isElementDisplayed(touchID_DontSave)) {
				driver.click(touchID_DontSave);
			}
		}
		if (driver.isAndroidDriver()) {
			assertTrue(new SelectBusinessCardPage(driver).isActive(),
					"Login unsuccessfully, Select card screen is not displayed",
					"Login successfully, Select card screen is displayed");
		} else {
			assertTrue(new HomePage(driver).isActive(), "Login unsuccessfully, Home screen is not displayed",
					"Login successfully, Home screen is displayed");
		}
		// return new SelectBusinessCardPage(driver);
	}

	public void verifyErrorMessage(String expectedMessage) throws Exception {
		String actualMessage = "";
		if (driver.isAndroidDriver()) {
			actualMessage = driver.getText(errorType) + ", " + driver.getText(errorMessage);
		} else {
			actualMessage = driver.getText(errorMessage);
		}
		assertEquals(actualMessage, expectedMessage, "Error message is displayed incorrectly",
				"Error message is displayed correctly");
	}
}

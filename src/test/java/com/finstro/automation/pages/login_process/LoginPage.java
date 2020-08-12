package com.finstro.automation.pages.login_process;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.report.HtmlReporter;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.*;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'ERROR' || name contains 'Error'")
	private WebElement errorMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/login_email_edt")
	@iOSXCUITFindBy(accessibility = "email")
	private WebElement emailAddress;

	// @AndroidFindBy(id = "au.com.finstro.finstropay:id/login_access_code_edt")
	// @iOSXCUITFindBy(accessibility = "accessCode")
	// private WebElement accessCode;

	@AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\")")
	@iOSXCUITFindBy(className = "XCUIElementTypeSecureTextField")
	private List<WebElement> accessCode;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	@iOSXCUITFindBy(accessibility = "submit")
	private WebElement submit;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/sign_up_link\"))")
	@iOSXCUITFindBy(accessibility = "signup")
	private WebElement btnSignUp;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/forgot_access_code\"))")
	@iOSXCUITFindBy(accessibility = "Access Code?")
	private WebElement btnForgotAccessCode;

	@iOSXCUITFindBy(accessibility = "Maybe Later")
	private WebElement touchID_DontSave;

	@iOSXCUITFindBy(accessibility = "Yes")
	private WebElement touchID_Yes;

	@iOSXCUITFindBy(accessibility = "Not Now")
	private WebElement savePass_NotNow;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/login_email")
	@iOSXCUITFindBy(id = "notyou")
	private WebElement loggedEmail;

	public LoginPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.waitForElementDisplayed(emailAddress, 10);
	}

	public ForgotAccessCodePage toForgotAccessCodePage() throws Exception {
		driver.click(btnForgotAccessCode);
		return new ForgotAccessCodePage(driver);
	}

	public void toRegisterPage() throws Exception {
		driver.click(btnSignUp);
	}

	public void login(String email, String code) throws Exception {
		driver.inputTextWithClear(emailAddress, email);
		inputAccessCode(code);
		driver.click(submit);
		// driver.dismissAlert();
	}

	public void login(String code) throws Exception {
		inputAccessCode(code);
		driver.click(submit);
		// driver.dismissAlert();
	}

	public void inputAccessCode(String code) throws Exception {
		if (code.length() > 0) {
			for (int i = 0; i < code.length(); i++) {
				driver.click(accessCode.get(i));
				driver.inputText(accessCode.get(i), "" + code.charAt(i));
			}
		}
	}

	public void clickSubmit() throws Exception {
		driver.click(submit);
	}

	public void doSuccessLogin(String email, String code) throws Exception {
		HtmlReporter.label("Login to the app");
		new RegisterPage(driver).toLoginPage();
		assertTrue(this.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after tap on login");
		login(email, code);
		boolean isLoginPageDisappear = driver.waitUntilElementNotPresent(emailAddress, 50);
		assertTrue(isLoginPageDisappear, "Login unsuccessfully, login page still displayed",
				"Login successfully, Login page disappeared");
		if (driver.isIOSDriver()) {
			// if(driver.waitForElementDisplayed(savePass_NotNow, 5)) {
			// clickNotNowiOS();
			// }
			if (driver.waitForElementDisplayed(touchID_DontSave, 5)) {
				clickMaybeLateriOS();
			}
		}
	}

	public void clickNotNowiOS() {
		try {
			driver.click(savePass_NotNow);
		} catch (Exception e) {
		}
	}

	public void clickMaybeLateriOS() {
		try {
			driver.click(touchID_DontSave);
		} catch (Exception e) {
		}
	}

	public String getErrorMessage() throws Exception {
		return driver.waitForTextElementPresent(errorMessage, 30);
	}

	public String getLoggedEmail() throws Exception {
		return driver.getText(loggedEmail);
	}

	public void clickOnLoggedEmail() {
		loggedEmail.click();
	}
}

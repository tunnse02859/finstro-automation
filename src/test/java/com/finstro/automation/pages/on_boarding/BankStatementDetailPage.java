package com.finstro.automation.pages.on_boarding;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BankStatementDetailPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/toolbar_left_text\"))")
	@iOSXCUITFindBy(accessibility = "back")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/bankstatement_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Please enter your Bank of Statements online banking details'")
	private WebElement title;
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name=\"BankStatements - Send Your Bank Statements Now\"`]/XCUIElementTypeOther[2]/*/XCUIElementTypeTextField")
	private WebElement bankUsername;
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name=\"BankStatements - Send Your Bank Statements Now\"`]/XCUIElementTypeOther[3]/*/XCUIElementTypeSecureTextField")
	private WebElement bankPassword;
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeSwitch")
	private WebElement acceptTerm;
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name=\"BankStatements - Send Your Bank Statements Now\"`]/*/XCUIElementTypeButton")
	private WebElement submit;
	
	
	public BankStatementDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}
	
	public void inputUsername(String username) throws Exception {
		driver.inputTextWithClear(bankUsername, username);
	}
	
	public void inputPassword(String password) throws Exception {
		driver.inputTextWithClear(bankPassword, password);
	}
	
	public void acceptTern() throws Exception {
		driver.click(acceptTerm);
	}
	
	public BankStatementRetrieveAccountlPage clickSubmit() throws Exception {
		driver.click(submit);
		return new BankStatementRetrieveAccountlPage(driver);
	}

}

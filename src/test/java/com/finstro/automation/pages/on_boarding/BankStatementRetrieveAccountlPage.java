package com.finstro.automation.pages.on_boarding;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import static com.finstro.automation.utility.Assertion.*;

public class BankStatementRetrieveAccountlPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/toolbar_left_text\"))")
	@iOSXCUITFindBy(accessibility = "back")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/bankstatement_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Please select the accounts you would like to retrieve?'")
	private WebElement title;
	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name=\"BankStatements - Send Your Bank Statements Now\"`]/*/XCUIElementTypeButton")
	private WebElement submit;
	
	@iOSXCUITFindBy(accessibility = "All Done")
	private WebElement doneBankStatement;
	
	@iOSXCUITFindBy(iOSNsPredicate = "value = 'Bank Statements Connected'")
	private WebElement popupBankStatementConnectionTitle;
	
	@iOSXCUITFindBy(accessibility = "OK")
	private WebElement closePopup;
	
	public BankStatementRetrieveAccountlPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}
	
	public boolean isDone() throws Exception {
		return driver.isElementDisplayed(doneBankStatement);
	}
	
	public void verifyPopupBankStatementConnected() throws Exception {
		assertTrue(driver.isElementDisplayed(popupBankStatementConnectionTitle),"Popup Bank Statements Connected is not displayed","Popup Bank Statements Connected is displayed correctly");
		driver.click(closePopup);
	}
	
	public void clickSubmit() throws Exception {
		driver.click(submit);
	}
}

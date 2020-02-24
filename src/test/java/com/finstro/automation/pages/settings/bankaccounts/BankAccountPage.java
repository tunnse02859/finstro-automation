package com.finstro.automation.pages.settings.bankaccounts;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BankAccountPage {

	private AppiumBaseDriver driver;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'BANK ACCOUNTS'")
	private WebElement textTitle;

	@iOSXCUITFindBy(accessibility = "addBankAccount")
	private WebElement btnAddNewCard;

	
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> cardList;

	private By getCardNameElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/name_edt")
				: MobileBy.iOSNsPredicateString(("name = 'name on account'"));
	}

	private By getCardNumberElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/number_edt") : null;
	}

	private By getBtnNextElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/btnNext") : null;
	}


	public BankAccountPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);

	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle);
	}

	public void scrollUntillViewCard(String strNameOnCard) {
		if (driver.isAndroidDriver()) {
			String locator = String.format(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/name_edt\").text(\"%s\"))",
					strNameOnCard);
			By cardName = MobileBy.AndroidUIAutomator(locator);
			driver.getDriver().findElement(cardName);
		}
		// Need to implement for IOS
		else {

		}
	}

	public WebElement findCard(String strNameOnCard) throws Exception {

		scrollUntillViewCard(strNameOnCard);
		WebElement element = cardList.stream()
				.filter(e -> e.findElement(getCardNameElement()).getText().contains(strNameOnCard)).findAny()
				.orElse(null);
		return element;
	}

	public BankAccount_AccountDetailPage selectCardDetailsByName(String strNameOnCard) throws Exception {
		WebElement card = findCard(strNameOnCard);
		driver.clickByPosition(card, "middle");
		return new BankAccount_AccountDetailPage(driver);
	}

	public BankAccount_AddNewBankPage addNewCard() throws Exception {
		driver.click(btnAddNewCard);
		return new BankAccount_AddNewBankPage(driver);
	}

	public boolean isCardExisting(String strNameOnCard) throws Exception {
		try {
			if (findCard(strNameOnCard) != null) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}

}

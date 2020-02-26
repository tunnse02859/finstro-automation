package com.finstro.automation.pages.settings.carddetails;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DebtCreditCardsPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"DEBT/CREDIT CARDS\")")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'DEBIT/CREDIT CARDS'")
	private WebElement textTitle;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/add_bank_account")
	@iOSXCUITFindBy(accessibility = "+ Add New Card")
	private WebElement btnAddNewCard;

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='au.com.finstro.finstropay:id/bank_accounts_list']/android.widget.RelativeLayout")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> cardList;

	private By getCardNameElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/name_edt")
				: MobileBy.iOSNsPredicateString(("name = 'name on card'"));
	}

	private By getCardNumberElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/number_edt") : null;
	}

	private By getBtnNextElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/btnNext") : null;
	}

	private By getGreenTickDefaultCardElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/green_tick")
				: MobileBy.iOSNsPredicateString("name='check_mark_14x14'");
	}

	public DebtCreditCardsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);

	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(btnAddNewCard);
	}


	public WebElement findCard(String strNameOnCard) throws Exception {

		driver.scrollUntillViewText(strNameOnCard);
		WebElement element = cardList.stream()
				.filter(e -> e.findElement(getCardNameElement()).getText().contains(strNameOnCard)).findAny()
				.orElse(null);
		return element;
	}

	public DebtCreditCards_DetailCardPage selectCardDetailsByName(String strNameOnCard) throws Exception {
		WebElement card = findCard(strNameOnCard);
		driver.clickByPosition(card, "middle");
		return new DebtCreditCards_DetailCardPage(driver);
	}

	public DebtCreditCards_AddNewCardPage addNewCard() throws Exception {
		driver.click(btnAddNewCard);
		return new DebtCreditCards_AddNewCardPage(driver);
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

	public boolean isDefaultCard(String strNameOnCard) throws Exception {
		try {
			return findCard(strNameOnCard).findElement(getGreenTickDefaultCardElement()) != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
}

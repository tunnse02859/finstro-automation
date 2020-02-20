package com.finstro.automation.pages.settings.carddetails;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;
import com.finstro.automation.pages.settings.approval.SettingsApprovalBankUploadPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;
import com.finstro.automation.report.Log;

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
	private WebElement textTitle;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/add_bank_account")
	private WebElement btnAddNewCard;

//	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='au.com.finstro.finstropay:id/bank_accounts_list']/android.widget.RelativeLayout")
//	private List<WebElement> cardList;

	private By listCardLocator(AppiumBaseDriver driver) {
		return driver.isAndroidDriver() ? By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='au.com.finstro.finstropay:id/bank_accounts_list']/android.widget.RelativeLayout") : null;
	}
	
	private By getCardNameElement(AppiumBaseDriver driver) {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/name_edt") : null;
	}

	private By getCardNumberElement(AppiumBaseDriver driver) {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/number_edt") : null;
	}

	private By getBtnNextElement(AppiumBaseDriver driver) {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/btnNext") : null;
	}

	private By getGreenTickDefaultCardElement(AppiumBaseDriver driver) {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/green_tick") : null;
	}

	public DebtCreditCardsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);

	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle) && textTitle.getText().equalsIgnoreCase("DEBT/CREDIT CARDS")
				&& driver.isElementDisplayed(btnAddNewCard);
	}
	
	public List<WebElement> findListCards() {
		return driver.findElements(listCardLocator(driver));
	}

	public WebElement findCard(String strNameOnCard) throws Exception {


		int attemps = 0;
		do {
			
			List<WebElement> cardList = findListCards();
			if (cardList.isEmpty()) {
				throw new Exception("You don't have any credit card!!!");
			}
			Log.info("========= " + cardList.size());
//			WebElement card = cardList.stream()
//					.filter(e -> e.findElement(getCardNameElement(driver)).getText().contains(strNameOnCard)).findAny()
//					.orElse(null);
//
//			if (card != null) {
//				Log.info("=========== found card");
//				return card;
//			}
			
			for(int j = 0; j <  cardList.size(); j ++) {
				String text = cardList.get(j).findElement(getCardNameElement(driver)).getText();
				Log.info("------------" + text);
				if(text.equals(strNameOnCard)) {
					return cardList.get(j);
				}
			}
			driver.swipe(DIRECTION.UP);
			driver.wait(3);
			attemps ++;

		} while (attemps < 3);

		throw new Exception("Your card [" + strNameOnCard + "] not exists");

	}

	public DebtCreditCards_DetailCardPage selectCardDetailsByName(String strNameOnCard) throws Exception {

		WebElement card = findCard(strNameOnCard);

		card.findElement(getBtnNextElement(driver)).click();

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

		} catch (Exception ex) {
			throw ex;
			//return false;
		}

		return false;
	}

	public boolean isDefaultCard(String strNameOnCard) throws Exception {

		try {
			return findCard(strNameOnCard).findElement(getGreenTickDefaultCardElement(driver)).isDisplayed();
		} catch (Exception ex) {
			return false;
		}

	}
}

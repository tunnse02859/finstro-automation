package com.finstro.automation.pages.settings.carddetails;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
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
	
	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='au.com.finstro.finstropay:id/bank_accounts_list']/	android.widget.RelativeLayout")
	private List<WebElement> cardList;
	
	private By getCardNameElement (AppiumBaseDriver driver) {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/name_edt") : null;
	}
	
	private By getCardNumberElement (AppiumBaseDriver driver) {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/number_edt") : null;
	}
	
	private By getBtnNextElement (AppiumBaseDriver driver) {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/btnNext") : null;
	}

	public DebtCreditCardsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
		
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle) && textTitle.getText().equalsIgnoreCase("DEBT/CREDIT CARDS")
				&& driver.isElementDisplayed(btnAddNewCard);
	}
	
	public DebtCreditCards_AddNewCardPage selectCardDetailsByName(String strNameOnCard) throws InterruptedException {
		
		WebElement card = cardList.stream().filter(e -> e.findElement(getCardNameElement(driver)).getText().contains(strNameOnCard)).findAny().orElse(null);
		card.findElement(getBtnNextElement(driver)).click();

		return new DebtCreditCards_AddNewCardPage(driver);
	}
	
	public DebtCreditCards_AddNewCardPage addNewCard() throws Exception {
		
		driver.click(btnAddNewCard);

		return new DebtCreditCards_AddNewCardPage(driver);
	}
	
	public boolean isCardExisting(String strNameOnCard) throws Exception {
		
		WebElement card = cardList.stream().filter(e -> e.findElement(getCardNameElement(driver)).getText().contains(strNameOnCard)).findAny().orElse(null);
		
		if(card != null) {
			return true;
		}
		
		return false;
	}
}

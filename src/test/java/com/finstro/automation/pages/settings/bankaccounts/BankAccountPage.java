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
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/add_bank_account")
	private WebElement textTitle;

	@iOSXCUITFindBy(accessibility = "addBankAccount")
	@AndroidFindBy(id="	au.com.finstro.finstropay:id/add_bank_account")
	private WebElement btnAddNewBankAccount;

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id='au.com.finstro.finstropay:id/bank_accounts_list']/android.widget.RelativeLayout")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/XCUIElementTypeCell")
	private List<WebElement> bankAccountList;

	private By getBankAccountNameElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/name_edt")
				: MobileBy.iOSNsPredicateString(("name = 'name on account'"));
	}
	
	private By getBankAccountBSBElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/au.com.finstro.finstropay:id/bsb_edt") : null;
	}

	private By getBankAccountNumberElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/number_edt") : null;
	}

	private By getBtnNextElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/btnNext") : null;
	}
	
	private By getGreenTickDefaultBankAccountElement() {
		return driver.isAndroidDriver() ? By.id("au.com.finstro.finstropay:id/green_tick")
				: MobileBy.iOSNsPredicateString("name='check_mark_14x14'");
	}


	public BankAccountPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);

	}

	public boolean isActive() throws Exception {
//		return driver.findElement(btnAddNewBankAccount).isDisplayed();
		return true;
	}

	public WebElement findBankAccount(String strNameOnBank) throws Exception {

		driver.scrollUntillViewText(strNameOnBank);
		WebElement element = bankAccountList.stream()
				.filter(e -> e.findElement(getBankAccountNameElement()).getText().contains(strNameOnBank)).findAny()
				.orElse(null);
		return element;
	}

	public BankAccount_AccountDetailPage selectBankAccountDetailsByName(String strNameOnBank) throws Exception {
		WebElement account = findBankAccount(strNameOnBank);
		driver.clickByPosition(account, "middle");
		return new BankAccount_AccountDetailPage(driver);
	}

	public BankAccount_AddNewBankPage addNewBankAccount() throws Exception {
		driver.click(btnAddNewBankAccount);
		return new BankAccount_AddNewBankPage(driver);
	}

	public boolean isBankAccountExisting(String strNameOnBank) throws Exception {
		try {
			if (findBankAccount(strNameOnBank) != null) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean isDefaultBankAccount(String strNameOnBank) throws Exception {
		try {
			return findBankAccount(strNameOnBank).findElement(getGreenTickDefaultBankAccountElement()) != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

}

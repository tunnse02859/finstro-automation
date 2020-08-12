package com.finstro.automation.pages.settings.approval;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.on_boarding.SelectBankStatementPage;
import com.finstro.automation.report.HtmlReporter;

import static com.finstro.automation.utility.Assertion.*;

import java.util.List;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsApproval_BankAccountConnectedPage {

	private AppiumBaseDriver driver;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'BANK ACCOUNTS'")
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"BANK ACCOUNTS\")")
	private WebElement title;

	@AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id='au.com.finstro.finstropay:id/bank_info_holder']/android.widget.RelativeLayout")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell")
	private List<WebElement> bankAccountList;
	
	private By getAccountNameElement() {
		return driver.isAndroidDriver() ? By.xpath("//android.widget.TextView[1]")
				: MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[1]");
	}
	
	private By getAccountDetailElement() {
		return driver.isAndroidDriver() ? By.xpath("//android.widget.TextView[3]")
				: MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[2]");
	}
	
	private By getAccountBalanceElemen() {
		return driver.isAndroidDriver() ? By.xpath("//android.widget.TextView[2]")
				: MobileBy.iOSClassChain("**/XCUIElementTypeStaticText[3]");
	}

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Re-Submit Bank Accounts'")
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement submitBankAccount;

	public SettingsApproval_BankAccountConnectedPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public SelectBankStatementPage clickSubmitBankAccount() throws Exception {
		driver.click(submitBankAccount);
		return new SelectBankStatementPage(driver);
	}

	public void verifyBankAccountDisplayed(JSONArray bankAccounts) throws Exception {
		int numberOfAccount = bankAccounts.length();
		int numberOfDisplayedBankAccount = bankAccountList.size();
		if (numberOfAccount > 0) {
			if (driver.isAndroidDriver()) {
				assertEquals(numberOfDisplayedBankAccount - 1, numberOfAccount,
						"Number of displayed bank account is incorrectly",
						"Number of displayed bank account is correct");
			}else {
				assertEquals(numberOfDisplayedBankAccount, numberOfAccount,
						"Number of displayed bank account is incorrectly",
						"Number of displayed bank account is correct");
			}
		}

		for (int i = 0; i < bankAccounts.length(); i++) {
			HtmlReporter.info("Verify account number: " + (i+1));
			
			JSONObject account = bankAccounts.getJSONObject(i);
			String accountName = account.getString("accountName");
			String accountNumber = account.getString("accountNumber");
			String bsb = account.getString("bsb");
			String availableBalance = "$" + account.getString("availableBalance");
			String expectedAccountNumberAndBSB = String.format("(Acc: %s BSB: %s)", accountNumber, bsb);
			
			String displayedAccountName = "";
			String displayedAccountDetail = "";
			String displayedAccountAmount = "";
			if(driver.isAndroidDriver()) {
				displayedAccountName = driver.getText(bankAccountList.get(i+1).findElement(getAccountNameElement()));
				displayedAccountDetail = driver.getText(bankAccountList.get(i+1).findElement(getAccountDetailElement()));
				displayedAccountAmount = driver.getText(bankAccountList.get(i+1).findElement(getAccountBalanceElemen()));
			}else {
				displayedAccountName = driver.getText(bankAccountList.get(i).findElement(getAccountNameElement()));
				displayedAccountDetail = driver.getText(bankAccountList.get(i).findElement(getAccountDetailElement()));
				displayedAccountAmount = driver.getText(bankAccountList.get(i).findElement(getAccountBalanceElemen()));
			}
			
			assertEquals(displayedAccountName.replace(" ", ""), accountName.replace(" ", ""), "Account Name [" + (i + 1) + "] is displayed incorrectly",
					"Account Name [" + (i + 1) + "] is displayed correctly");
			assertEquals(displayedAccountDetail.replace(" ", ""), expectedAccountNumberAndBSB.replace(" ", ""),
					"Account detail [" + (i + 1) + "] is displayed incorrectly",
					"Account detail [" + (i + 1) + "] is displayed correctly");
			assertEquals(displayedAccountAmount.replace(",", "").replace(" ", ""), availableBalance.replace(",", "").replace(" ", ""),
					"Account Available Balance [" + (i + 1) + "] is displayed incorrectly",
					"Account Available Balance [" + (i + 1) + "] is displayed correctly");
		}
	}

}

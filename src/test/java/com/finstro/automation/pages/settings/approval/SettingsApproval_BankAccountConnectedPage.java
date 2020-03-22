package com.finstro.automation.pages.settings.approval;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.on_boarding.SelectBankStatementPage;
import static com.finstro.automation.utility.Assertion.*;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsApproval_BankAccountConnectedPage {

	private AppiumBaseDriver driver;
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'BANK ACCOUNTS'")
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"BANK ACCOUNTS\")")
	private WebElement title;
	
	String accountNamePath = "**/XCUIElementTypeCell[%s]/XCUIElementTypeStaticText[1]";
	String accountNumber_BSBPath = "**/XCUIElementTypeCell[%s]/XCUIElementTypeStaticText[2]";
	String currentBalancePath = "**/XCUIElementTypeCell[%s]/XCUIElementTypeStaticText[3]";
	
	@iOSXCUITFindBy(accessibility = "Submit Bank Accounts")
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
		for(int i = 0; i < bankAccounts.length(); i++) {
			JSONObject account = bankAccounts.getJSONObject(i);
			String accountName = account.getString("accountName");
			String accountNumber = account.getString("accountNumber");
			String bsb = account.getString("bsb");
			String availableBalance = "$" + account.getString("availableBalance");
			String expectedAccountNumberAndBSB = String.format("(Acc: %s BSB: %s)",accountNumber,bsb); 
			
			String displayedAccountName = driver.getText(driver.findElementIgnoreError(MobileBy.iOSClassChain(String.format(accountNamePath,i+1))));
			String displayedAccountNumberAndBSB = driver.getText(driver.findElementIgnoreError(MobileBy.iOSClassChain(String.format(accountNumber_BSBPath,i+1))));
			String displayedAccountavailableBalance = driver.getText(driver.findElementIgnoreError(MobileBy.iOSClassChain(String.format(currentBalancePath,i+1))));
			
			assertEquals(displayedAccountName, accountName, "Account Name [" + (i+1) + "] is displayed incorrectly", "Account Name [" + (i+1) + "] is displayed correctly");
			assertEquals(displayedAccountNumberAndBSB, expectedAccountNumberAndBSB, "Account Number and BSB [" + (i+1) + "] is displayed incorrectly", "Account Number and BSB [" + (i+1) + "] is displayed correctly");
			assertEquals(displayedAccountavailableBalance.replace(",", ""), availableBalance.replace(",", ""), "Account Available Balance [" + (i+1) + "] is displayed incorrectly", "Account Available Balance [" + (i+1) + "] is displayed correctly");
		}
	}
	
	
}

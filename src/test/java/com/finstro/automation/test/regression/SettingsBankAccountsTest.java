package com.finstro.automation.test.regression;

import com.finstro.automation.api.BankAccountDetailtAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.bankaccounts.BankAccountPage;
import com.finstro.automation.pages.settings.bankaccounts.BankAccount_AccountDetailPage;
import com.finstro.automation.pages.settings.bankaccounts.BankAccount_AddNewBankPage;
import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

public class SettingsBankAccountsTest extends MobileTestSetup {

	private BankAccountDetailtAPI bankAccountDetailAPI;
	private BankAccountPage accountPage;
	private BankAccount_AddNewBankPage addBankAccountPage;
	private BankAccount_AccountDetailPage detailAccountPage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		bankAccountDetailAPI = new BankAccountDetailtAPI();
		bankAccountDetailAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		RegisterPage registerPage = new RegisterPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		// Login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		// Go to the debtCreditAccountsPage page

	}

	@DataProvider(name = "SettingAccountDetail_01")
	public Object[][] SettingAccountDetail_01() {
		return new Object[][] { { "AddAccountTest ", "762734", "123456788" } };
	}

	@Test(dataProvider = "SettingAccountDetail_01")
	public void SettingAccountDetail_01_AddNewAccount(String name, String bsb, String accountNumber) throws Exception {
		accountPage = WorkFlows.goToSettingBankAccountPage(driver);
		try {
			name = name + System.currentTimeMillis();

			// Is on Add new account page
			addBankAccountPage = accountPage.addNewBankAccount();
			assertTrue(addBankAccountPage.isActive(), "You aren't on the Add new account page",
					"You are on the Add new account page");

			// Add new account
			addBankAccountPage.setAccountName(name);
			addBankAccountPage.setAccountNumber(accountNumber);
			addBankAccountPage.setBsb(bsb);
			addBankAccountPage.saveChanges();

			// Get alert
			String status = addBankAccountPage.getSaveStatus();
			Assert.assertTrue(status.contains("New bank account successfully added."), status);

			// Verify new bank account on UI
			Assert.assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after save");
			Assert.assertTrue(accountPage.isBankAccountExisting(name), "Bank account is not displayed!!");

			// Verify new bank account by API
			Assert.assertTrue(bankAccountDetailAPI.getBankAccountInfoByName(name) != null,
					"Checking new card by API: ADD FAILED");

		} finally {
			// Remove the bank account after testing
			if (bankAccountDetailAPI.getBankAccountInfoByName(name) != null) {
				bankAccountDetailAPI.removeBankAccountByName(name);
			}
		}
	}

	@DataProvider(name = "SettingBankAccountDetail_02")
	public Object[][] SettingBankAccountDetail_02() {
		return new Object[][] { { "DefaultBankAccount", "762734", "123456789" } };

	}

	@Test(dataProvider = "SettingBankAccountDetail_02")
	public void SettingBankAccountDetail_02_SetDefaultBankAccount(String name, String bsb, String accountNumber)
			throws Exception {
		accountPage = WorkFlows.goToSettingBankAccountPage(driver);

		/********* Save the original default bank account *********************/
		JSONObject originalDefaultAccount = bankAccountDetailAPI.getDefaultAccountInfo();

		try {

			/********* Add a new bank account as a precondition *********************/
			name = name + System.currentTimeMillis();

			// Is on Add new account page
			addBankAccountPage = accountPage.addNewBankAccount();
			assertTrue(addBankAccountPage.isActive(), "You aren't on the Add new account page",
					"You are on the Add new account page");

			// Add new account
			addBankAccountPage.setAccountName(name);
			addBankAccountPage.setAccountNumber(accountNumber);
			addBankAccountPage.setBsb(bsb);
			addBankAccountPage.saveChanges();

			// Get alert
			String status = addBankAccountPage.getSaveStatus();
			Assert.assertTrue(status.contains("New bank account successfully added."), status);

			// Verify new bank account on UI
			Assert.assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after save");
			Assert.assertTrue(accountPage.isBankAccountExisting(name), "Bank account is not displayed!!");

			// Verify new bank account by API
			Assert.assertTrue(bankAccountDetailAPI.getBankAccountInfoByName(name) != null,
					"Checking new card by API: ADD FAILED");

			/********* Set the bank account as default *********************/
			// Is on Detail Bank Account page
			detailAccountPage = accountPage.selectBankAccountDetailsByName(name);
			Assert.assertTrue(detailAccountPage.isActive(), "You aren't on the Bank Account Detail page");

			// Set default Bank Account
			detailAccountPage.setDefaultBankAccount();

			// Get alert
			status = detailAccountPage.getSaveStatus();
			Assert.assertTrue(status.contains("Bank Account set as default"), status);

			// Verify new bank account on UI
			Assert.assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after set default");
			Assert.assertTrue(accountPage.isDefaultBankAccount(name), "The greentick doesn't appear!");

			// Verify default bank account by API
			Assert.assertTrue(bankAccountDetailAPI.isDefaultBankAccount(name),
					"Checking default bank account by API: FAILED");
		} finally {
			if (bankAccountDetailAPI.getBankAccountInfoByName(name) != null) {
				Log.info("---- delete the added card ----");
				// Set the default card to the original one
				bankAccountDetailAPI.saveBankAccount(originalDefaultAccount);
				// Remove card after testing
				bankAccountDetailAPI.removeBankAccountByName(name);
			}
		}
	}

	@DataProvider(name = "SettingBankAccountDetail_03")
	public Object[][] SettingBankAccountDetail_03() {
		return new Object[][] { { "DeleteBankAccount", "762734", "123456787" } };

	}

	@Test(dataProvider = "SettingBankAccountDetail_03")
	public void SettingBankAccountDetail_02_DeleteBankAccount(String name, String bsb, String accountNumber)
			throws Exception {

		accountPage = WorkFlows.goToSettingBankAccountPage(driver);

		try {
			/********* Add a new bank account as a precondition *********************/
			name = name + System.currentTimeMillis();

			// Is on Add new account page
			addBankAccountPage = accountPage.addNewBankAccount();
			assertTrue(addBankAccountPage.isActive(), "You aren't on the Add new account page",
					"You are on the Add new account page");

			// Add new account
			addBankAccountPage.setAccountName(name);
			addBankAccountPage.setAccountNumber(accountNumber);
			addBankAccountPage.setBsb(bsb);
			addBankAccountPage.saveChanges();

			// Get alert
			String status = addBankAccountPage.getSaveStatus();
			Assert.assertTrue(status.contains("New bank account successfully added."), status);

			// Verify new bank account on UI
			Assert.assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after save");
			Assert.assertTrue(accountPage.isBankAccountExisting(name), "Bank account is not displayed!!");

			// Verify new bank account by API
			Assert.assertTrue(bankAccountDetailAPI.getBankAccountInfoByName(name) != null,
					"Checking new card by API: ADD FAILED");

			/********* Set the card as default *********************/
			// Is on Detail Bank Account page
			detailAccountPage = accountPage.selectBankAccountDetailsByName(name);
			Assert.assertTrue(detailAccountPage.isActive(), "You aren't on the Bank Account Detail page");

			// Set default Bank Account
			detailAccountPage.deleteBankAccount();

			// Get alert
			status = detailAccountPage.getSaveStatus();
			Assert.assertTrue(status.contains("Bank account successfully deleted."), status);

			// Verify new bank account on UI
			Assert.assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after delete card");
			Assert.assertFalse(accountPage.isBankAccountExisting(name), "The bank account still displays on UI");

			// Verify the deleted bank account by API
			Assert.assertTrue(bankAccountDetailAPI.getBankAccountInfoByName(name) == null,
					"Verify the deleted bank account by API: FAILED");

		} finally {
			// Remove the bank account after testing
			if (bankAccountDetailAPI.getBankAccountInfoByName(name) != null) {
				bankAccountDetailAPI.removeBankAccountByName(name);
			}
		}

	}

}

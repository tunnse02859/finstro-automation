package com.finstro.automation.test.regression;

import com.finstro.automation.api.BankAccountDetailtAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.bankaccounts.BankAccountPage;
import com.finstro.automation.pages.settings.bankaccounts.BankAccount_AccountDetailPage;
import com.finstro.automation.pages.settings.bankaccounts.BankAccount_AddNewBankPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.DataGenerator;
import com.finstro.automation.setup.MobileTestSetup;

import org.json.JSONObject;
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
		bankAccountDetailAPI.loginForAccessToken(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS,
				Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		RegisterPage registerPage = new RegisterPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		// Login
		loginPage.doSuccessLogin(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS,
				Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);
		// Go to the debtCreditAccountsPage page

	}

	@DataProvider(name = "SettingAccountDetail_01")
	public Object[][] SettingAccountDetail_01() {
		return new Object[][] { { "AddAccountTest ", "762734", "123456788" } };
	}

	@Test
	public void SettingAccountDetail_01_AddNewAccount() throws Exception {

		// Generate test data
		DataGenerator data = new DataGenerator();
		String name = data.generateStringByDateTime("AddAccountTest");
		String bsb = data.generateBSBNumber();
		String accountNumber = data.generateBankAccountNumber();

		accountPage = WorkFlows.goToSettingBankAccountPage(driver);
		try {

			// Is on Add new account page
			HtmlReporter.label("Go to add new bank account screen");
			addBankAccountPage = accountPage.addNewBankAccount();
			assertTrue(addBankAccountPage.isActive(), "You aren't on the Add new account page",
					"You aren on the Add new account page");

			// Add new account
			HtmlReporter.label("Do add new account and verify");
			addBankAccountPage.setAccountName(name);
			addBankAccountPage.setAccountNumber(accountNumber);
			addBankAccountPage.setBsb(bsb);
			addBankAccountPage.saveChanges();

			// Get alert
			String status = addBankAccountPage.getSaveStatus();
			assertContains(status, "New bank account successfully added.", status, status);

			// Verify new bank account on UI
			assertTrue(accountPage.isActive(), "Bank Account Page is not displayed after save",
					"BankAccountPage is displayed after save");
			assertTrue(accountPage.isBankAccountExisting(name), "New Bank account is not displayed!!",
					"New Bank account is displayed!!");

			// Verify new bank account by API
			assertTrue(bankAccountDetailAPI.getBankAccountInfoByName(name) != null,
					"Checking new card by API: ADD FAILED", "Checking new card by API: ADD PASSED");

		} finally {
			// Remove the bank account after testing
			if (bankAccountDetailAPI.getBankAccountInfoByName(name) != null) {
				HtmlReporter.label("Remove added account");
				bankAccountDetailAPI.removeBankAccountByName(name);
			}
		}
	}

	@Test
	public void SettingBankAccountDetail_02_SetDefaultBankAccount() throws Exception {

		// Generate test data
		DataGenerator data = new DataGenerator();
		String name = data.generateStringByDateTime("DefaultAccountTest");
		String bsb = data.generateBSBNumber();
		String accountNumber = data.generateBankAccountNumber();

		accountPage = WorkFlows.goToSettingBankAccountPage(driver);

		/********* Save the original default bank account *********************/
		HtmlReporter.label("Get original default account");
		JSONObject originalDefaultAccount = bankAccountDetailAPI.getDefaultAccountInfo();

		try {

			/********* Add a new bank account as a precondition *********************/
			name = name + System.currentTimeMillis();

			// Is on Add new account page
			HtmlReporter.label("Add new account (for set default)");
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
			assertContains(status, "New bank account successfully added.", status, status);

			// Verify new bank account on UI
			assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after save",
					"BankAccountPage is displayed after save");
			assertTrue(accountPage.isBankAccountExisting(name), "New Bank account is not displayed!!",
					"New Bank account is displayed!!");

			// Verify new bank account by API
			assertTrue(bankAccountDetailAPI.getBankAccountInfoByName(name) != null,
					"Checking new card by API: ADD FAILED", "Checking new card by API: ADD PASSED");

			/********* Set the bank account as default *********************/
			HtmlReporter.label("Set new account as default and verify");
			// Is on Detail Bank Account page
			detailAccountPage = accountPage.selectBankAccountDetailsByName(name);
			assertTrue(detailAccountPage.isActive(), "You aren't on the Bank Account Detail page",
					"You are on the Bank Account Detail page");

			// Set default Bank Account
			detailAccountPage.setDefaultBankAccount();

			// Get alert
			status = detailAccountPage.getSaveStatus();
			assertContains(status, "Bank Account set as default", status, status);

			// Verify the default bank account on UI
			assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after set default",
					"BankAccountPage is displayed after set default");
			assertTrue(accountPage.isDefaultBankAccount(name), "The greentick doesn't appear!",
					"The greentick appeared!");

			// Verify default bank account by API
			assertTrue(bankAccountDetailAPI.isDefaultBankAccount(name), "Checking default bank account by API: FAILED",
					"Checking default bank account by API: PASSED");
		} finally {
			if (bankAccountDetailAPI.getBankAccountInfoByName(name) != null) {
				HtmlReporter.label("Delete created account and reset origin default account");
				// Set the default card to the original one
				if (originalDefaultAccount != null) {
					bankAccountDetailAPI.saveBankAccount(originalDefaultAccount);
				}
				// Remove card after testing
				bankAccountDetailAPI.removeBankAccountByName(name);
			}
		}
	}

	@Test
	public void SettingBankAccountDetail_03_DeleteBankAccount() throws Exception {

		// Generate test data
		DataGenerator data = new DataGenerator();
		String name = data.generateStringByDateTime("DeleteAccountTest");
		String bsb = data.generateBSBNumber();
		String accountNumber = data.generateBankAccountNumber();

		accountPage = WorkFlows.goToSettingBankAccountPage(driver);

		try {
			/********* Add a new bank account as a precondition *********************/
			name = name + System.currentTimeMillis();

			// Is on Add new account page
			addBankAccountPage = accountPage.addNewBankAccount();
			assertTrue(addBankAccountPage.isActive(), "You aren't on the Add new account page",
					"You are on the Add new account page");
			HtmlReporter.label("Add new account for delete");
			// Add new account
			addBankAccountPage.setAccountName(name);
			addBankAccountPage.setAccountNumber(accountNumber);
			addBankAccountPage.setBsb(bsb);
			addBankAccountPage.saveChanges();

			// Get alert
			String status = addBankAccountPage.getSaveStatus();
			assertContains(status, "New bank account successfully added.", status, status);

			// Verify new bank account on UI
			assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after save",
					"BankAccountPage is displayed after save");
			assertTrue(accountPage.isBankAccountExisting(name), "Bank account is not displayed!!",
					"Bank account is displayed!!");

			// Verify new bank account by API
			assertTrue(bankAccountDetailAPI.getBankAccountInfoByName(name) != null,
					"Checking new card by API: ADD FAILED", "Checking new card by API: ADD PASSED");

			/********* Delete the bank account *********************/
			HtmlReporter.label("Do Delete created account and verify");
			// Is on Detail Bank Account page
			detailAccountPage = accountPage.selectBankAccountDetailsByName(name);
			assertTrue(detailAccountPage.isActive(), "You aren't on the Bank Account Detail page",
					"You aren on the Bank Account Detail page");

			// Set default Bank Account
			detailAccountPage.deleteBankAccount();

			// Get alert
			status = detailAccountPage.getSaveStatus();
			assertContains(status, "Bank account successfully deleted.", status, status);

			// Verify new bank account on UI
			assertTrue(accountPage.isActive(), "BankAccountPage is not displayed after delete card",
					"BankAccountPage is displayed after delete card");
			assertFalse(accountPage.isBankAccountExisting(name), "The bank account still displays on UI",
					"The bank account is disappeared on screen");

			// Verify the deleted bank account by API
			assertTrue(bankAccountDetailAPI.getBankAccountInfoByName(name) == null,
					"Verify the deleted bank account by API: FAILED", "Verify the deleted bank account by API: PASSED");

		} finally {
			// Remove the bank account after testing
			if (bankAccountDetailAPI.getBankAccountInfoByName(name) != null) {
				HtmlReporter.label("Delete Failed -> delete created account by API");
				bankAccountDetailAPI.removeBankAccountByName(name);
			}
		}

	}

}

package com.finstro.automation.test.regression;

import com.finstro.automation.api.BankAccountDetailtAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.bankaccounts.BankAccountPage;
import com.finstro.automation.pages.settings.bankaccounts.BankAccount_AccountDetailPage;
import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
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
	private BankAccountPage addAccountPage;
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
		return new Object[][] { { "Add Account Test ", "815", "123456788" } };
	}

	@Test(dataProvider = "SettingAccountDetail_01")
	public void SettingAccountDetail_01_AddNewAccount(String name, String bsb, String accountNumber) throws Exception {
	}

	@DataProvider(name = "SettingAccountDetail_02")
	public Object[][] SettingAccountDetail_02() {
		return new Object[][] { { "Default Account Test", "5203950336889332", "01/2021" } };

	}

	@Test(dataProvider = "SettingAccountDetail_02")
	public void SettingAccountDetail_02_SetDefaultAccount(String name, String bsb, String accountNumber) throws Exception {
	}

	@DataProvider(name = "SettingAccountDetail_03")
	public Object[][] SettingAccountDetail_03() {
		return new Object[][] { { "Delete Account Test", "5203950336889332", "01/2021" } };

	}

	@Test(dataProvider = "SettingAccountDetail_03")
	public void SettingAccountDetail_03_DeleteAccount(String name, String bsb, String accountNumber) throws Exception {
	}

}

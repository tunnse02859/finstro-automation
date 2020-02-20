package com.finstro.automation.test.regression;

import com.finstro.automation.api.CreditCardAPI;
import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.common.CommonFunction;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.excelhelper.ExcelHelper;
import com.finstro.automation.pages.home.MainNavigator;
import com.finstro.automation.pages.login_process.ForgotAccessCodePage;
import com.finstro.automation.pages.login_process.LoginPINPage;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.CompleteAgreementPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.FindAddressPage;
import com.finstro.automation.pages.on_boarding.FindBusinessPage;
import com.finstro.automation.pages.on_boarding.MedicarePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.pages.settings.SettingsPage;
import com.finstro.automation.pages.settings.approval.SettingsApprovalBankUploadPage;
import com.finstro.automation.pages.settings.approval.SettingsApprovalIDCheck_ProfileDetailsPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsSecondPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCardsPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCards_AddNewCardPage;
import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.FilePaths;
import com.finstro.automation.utility.PropertiesLoader;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;
import java.util.HashMap;

public class SettingsDebtCreditCardsTests extends MobileTestSetup {

	private CreditCardAPI creditCardAPI;
	private DebtCreditCardsPage debtCreditCardsPage;
	private DebtCreditCards_AddNewCardPage addCardPage;

	@BeforeClass
	public void setupAccessTosken() throws Exception {
		creditCardAPI = new CreditCardAPI();
		creditCardAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		RegisterPage registerPage = new RegisterPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		// Login
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		// Go to the debtCreditCardsPage page
		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);

	}

	@DataProvider(name = "SettingCardDetail_01")
	public Object[][] SettingCardDetail_01() {
		return new Object[][] { { "Phong Trinh", "5203950336889332", "01/2021"} };

	}

	@Test(dataProvider = "SettingCardDetail_01")
	public void SettingCardDetail_01_AddNewCard(String name, String cardNumber, String expiry) throws Exception {
		
		try {
			// Is on Add new card page
			addCardPage = debtCreditCardsPage.addNewCard();
			assertTrue(addCardPage.isActive(), "You aren't on the Add new card page", "You are on the Add new card page");
			
			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			debtCreditCardsPage = addCardPage.saveChanges();
			
			// Verify new card on UI
			debtCreditCardsPage.isActive();
			assertTrue(debtCreditCardsPage.isCardExisting(name), "Add new card failed", "New card is added");
			
			// Verify new card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null, "Checking new card by API: ADD FAILED", "Checking new card by API: ADDED");
		}
		finally {
			// Remove card after testing
			creditCardAPI.removeCardByName(name);
		}
		
		
	}

}
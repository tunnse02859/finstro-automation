package com.finstro.automation.test.regression;

import com.finstro.automation.api.CreditCardAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCardsPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCards_AddNewCardPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCards_DetailCardPage;
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

public class SettingsDebtCreditCardsTests extends MobileTestSetup {

	private CreditCardAPI creditCardAPI;
	private DebtCreditCardsPage debtCreditCardsPage;
	private DebtCreditCards_AddNewCardPage addCardPage;
	private DebtCreditCards_DetailCardPage detailCardPage;

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

	}

	@DataProvider(name = "SettingCardDetail_01")
	public Object[][] SettingCardDetail_01() {
		return new Object[][] { { "Add Card Test", "5203950336889332", "01/2021" } };
	}

	@Test(dataProvider = "SettingCardDetail_01")
	public void SettingCardDetail_01_AddNewCard(String name, String cardNumber, String expiry) throws Exception {
		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		try {
			name = name + System.currentTimeMillis();
			// Is on Add new card page
			addCardPage = debtCreditCardsPage.addNewCard();
			assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
					"You are on the Add new card page");

			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			debtCreditCardsPage = addCardPage.saveChanges();

			// Verify new card on UI
			assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after save",
					"Debit/credit card is displayed after save");
			assertTrue(debtCreditCardsPage.isCardExisting(name), "Add new card failed", "New card is added");

			// Verify new card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null, "Checking new card by API: ADD FAILED",
					"Checking new card by API: ADDED");
		} finally {
			// Remove card after testing
			if (creditCardAPI.getCreditCardInfoByName(name) != null) {
				creditCardAPI.removeCardByName(name);
			}
		}
	}

	@DataProvider(name = "SettingCardDetail_02")
	public Object[][] SettingCardDetail_02() {
		return new Object[][] { { "Default Card Test", "5203950336889332", "01/2021" } };

	}

	@Test(dataProvider = "SettingCardDetail_02")
	public void SettingCardDetail_02_SetDefaultCard(String name, String cardNumber, String expiry) throws Exception {
		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		/********* Save the original default card *********************/
		JSONObject originalDefaultCard = creditCardAPI.getDefaultCreditCardInfo();

		try {

			/********* Add a new card as a precondition *********************/
			name = name + System.currentTimeMillis();
			addCardPage = debtCreditCardsPage.addNewCard();
			assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
					"You are on the Add new card page");

			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			debtCreditCardsPage = addCardPage.saveChanges();

			// Verify new card on UI
			debtCreditCardsPage.isActive();
			assertTrue(debtCreditCardsPage.isCardExisting(name), "Add new card failed", "New card is added");

			// Verify new card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null, "Checking new card by API: ADD FAILED",
					"Checking new card by API: ADDED");

			/********* Set the card as default *********************/
			// Is on Detail Card page
			detailCardPage = debtCreditCardsPage.selectCardDetailsByName(name);
			assertTrue(detailCardPage.isActive(), "You aren't on the Card Detail page",
					"You are on the Card Detail page");

			// Set default card
			debtCreditCardsPage = detailCardPage.setDefaultCard();
			// Verify default card on UI
			assertTrue(debtCreditCardsPage.isDefaultCard(name), "Set default card failed", "Set default card ok");

			// Verify default card by API
			assertTrue(creditCardAPI.isDefaultCard(name), "Checking default card by API: FAILED",
					"Checking default card by API: PASSED");
		} finally {
			if (creditCardAPI.getCreditCardInfoByName(name) != null) {
				// Set the default card to original one
				creditCardAPI.saveCard(originalDefaultCard);
				// Remove card after testing
				creditCardAPI.removeCardByName(name);
			}
		}
	}

	@DataProvider(name = "SettingCardDetail_03")
	public Object[][] SettingCardDetail_03() {
		return new Object[][] { { "Delete Card Test", "5203950336889332", "01/2021" } };

	}

	@Test(dataProvider = "SettingCardDetail_03")
	public void SettingCardDetail_03_DeleteCard(String name, String cardNumber, String expiry) throws Exception {
		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		try {
			/********* Add a new card as a precondition *********************/
			name = name + System.currentTimeMillis();
			addCardPage = debtCreditCardsPage.addNewCard();
			assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
					"You are on the Add new card page");

			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			debtCreditCardsPage = addCardPage.saveChanges();

			// Verify new card on UI
			debtCreditCardsPage.isActive();
			assertTrue(debtCreditCardsPage.isCardExisting(name), "Add new card failed", "New card is added");

			// Verify new card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null, "Checking new card by API: ADD FAILED",
					"Checking new card by API: ADDED");

			/********* Delete the card *********************/
			// Is on Detail Card page
			detailCardPage = debtCreditCardsPage.selectCardDetailsByName(name);
			assertTrue(detailCardPage.isActive(), "You aren't on the Card Detail page",
					"You are on the Card Detail page");

			// Delete card
			debtCreditCardsPage = detailCardPage.deleteCard();
			// Verify deleted card on UI
			assertTrue(!debtCreditCardsPage.isCardExisting(name), "Card isn't deleted", "Card is deleted");

			// Verify the deleted card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) == null, "Verify the deleted card by API: FAILED",
					"Verify the deleted card by API: PASSED");
		} finally {
			// Remove card after testing
			if (creditCardAPI.getCreditCardInfoByName(name) != null) {
				creditCardAPI.removeCardByName(name);
			}
		}

	}

}

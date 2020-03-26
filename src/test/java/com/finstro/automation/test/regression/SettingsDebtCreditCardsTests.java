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
import com.finstro.automation.setup.DataGenerator;
import com.finstro.automation.setup.MobileTestSetup;

import org.json.JSONObject;
import org.testng.Assert;
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
		creditCardAPI.loginForAccessToken(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS,
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
		// Go to the debtCreditCardsPage page

	}

	@Test
	public void SettingCardDetail_01_AddNewCard() throws Exception {
		// Generate test data
		DataGenerator data = new DataGenerator();
		String name = data.generateStringByDateTime("AddNewCard");
		String cardNumber = data.generateDebitCardNumber();
		String expiry = "01/2022";

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		try {
			name = name + System.currentTimeMillis();
			// Is on Add new card page
			addCardPage = debtCreditCardsPage.addNewCard();
			Assert.assertTrue(addCardPage.isActive(), "You aren't on the Add new card page");

			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			addCardPage.saveChanges();

			// Get alert
			String status = addCardPage.getSaveStatus();
			Assert.assertTrue(status.contains("New card successfully added."), status);

			// Verify new card on UI
			Assert.assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after adding");
			Assert.assertTrue(debtCreditCardsPage.isCardExisting(name), "New card doesn't display");

			// Verify new card by API
			Assert.assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null,
					"Checking new card by API: ADD FAILED");
		} finally {
			// Remove card after testing
			if (creditCardAPI.getCreditCardInfoByName(name) != null) {
				creditCardAPI.removeCardByName(name);
			}
		}
	}


	@Test
	public void SettingCardDetail_02_SetDefaultCard() throws Exception {
		
		// Generate test data
		DataGenerator data = new DataGenerator();
		String name = data.generateStringByDateTime("DefaultCard");
		String cardNumber = data.generateDebitCardNumber();
		String expiry = "01/2022";
		
		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		/********* Save the original default card *********************/
		JSONObject originalDefaultCard = creditCardAPI.getDefaultCreditCardInfo();

		try {

			/********* Add a new card as a precondition *********************/
			name = name + System.currentTimeMillis();
			// Is on Add new card page
			addCardPage = debtCreditCardsPage.addNewCard();
			Assert.assertTrue(addCardPage.isActive(), "You aren't on the Add new card page");

			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			addCardPage.saveChanges();

			// Get alert
			String status = addCardPage.getSaveStatus();
			Assert.assertTrue(status.contains("New card successfully added."), status);

			// Verify new card on UI
			Assert.assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after adding");
			Assert.assertTrue(debtCreditCardsPage.isCardExisting(name), "New card doesn't display");

			// Verify new card by API
			Assert.assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null,
					"Checking new card by API: ADD FAILED");

			/********* Set the card as default *********************/
			// Is on Detail Card page
			detailCardPage = debtCreditCardsPage.selectCardDetailsByName(name);
			Assert.assertTrue(detailCardPage.isActive(), "You aren't on the Card Detail page");

			// Set default card
			debtCreditCardsPage = detailCardPage.setDefaultCard();

			// Get alert
			status = detailCardPage.getSaveStatus();
			Assert.assertTrue(status.contains("Card set as default"), status);

			// Verify default card on UI
			Assert.assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after set default");
			Assert.assertTrue(debtCreditCardsPage.isDefaultCard(name), "The greentick doesn't appear!");

			// Verify default card by API
			Assert.assertTrue(creditCardAPI.isDefaultCard(name), "Checking default card by API: FAILED");
		} finally {
			if (creditCardAPI.getCreditCardInfoByName(name) != null) {
				// Set the default card to original one
				creditCardAPI.saveCard(originalDefaultCard);
				// Remove card after testing
				creditCardAPI.removeCardByName(name);
			}
		}
	}


	@Test
	public void SettingCardDetail_03_DeleteCard() throws Exception {
		// Generate test data
		DataGenerator data = new DataGenerator();
		String name = data.generateStringByDateTime("DeleteCard");
		String cardNumber = data.generateDebitCardNumber();
		String expiry = "01/2022";
		
		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		try {
			/********* Add a new card as a precondition *********************/
			name = name + System.currentTimeMillis();
			// Is on Add new card page
			addCardPage = debtCreditCardsPage.addNewCard();
			Assert.assertTrue(addCardPage.isActive(), "You aren't on the Add new card page");

			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			addCardPage.saveChanges();

			// Get alert
			String status = addCardPage.getSaveStatus();
			Assert.assertTrue(status.contains("New card successfully added."), status);

			// Verify new card on UI
			Assert.assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after adding");
			Assert.assertTrue(debtCreditCardsPage.isCardExisting(name), "New card doesn't display");

			// Verify new card by API
			Assert.assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null,
					"Checking new card by API: ADD FAILED");

			/********* Delete the card *********************/
			// Is on Detail Card page
			detailCardPage = debtCreditCardsPage.selectCardDetailsByName(name);
			Assert.assertTrue(detailCardPage.isActive(), "You aren't on the Card Detail page");

			// Delete card
			debtCreditCardsPage = detailCardPage.deleteCard();

			// Get alert
			status = detailCardPage.getSaveStatus();
			Assert.assertTrue(status.contains("Card successfully deleted."), status);

			// Verify deleted card on UI
			Assert.assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after delete card");
			Assert.assertFalse(debtCreditCardsPage.isCardExisting(name), "The card still displays on UI");

			// Verify the deleted card by API
			Assert.assertTrue(creditCardAPI.getCreditCardInfoByName(name) == null,
					"Verify the deleted card by API: FAILED");
		} finally {
			// Remove card after testing
			if (creditCardAPI.getCreditCardInfoByName(name) != null) {
				creditCardAPI.removeCardByName(name);
			}
		}

	}

}

package com.finstro.automation.test.regression;

import com.finstro.automation.api.CreditCardAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCardsPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCards_AddNewCardPage;
import com.finstro.automation.pages.settings.carddetails.DebtCreditCards_DetailCardPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.DataGenerator;
import com.finstro.automation.setup.MobileTestSetup;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
			HtmlReporter.label("Go to add new card screen");
			addCardPage = debtCreditCardsPage.addNewCard();
			assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
					"You are on the Add new card page");

			// Add new card
			HtmlReporter.label("Add new card and save");
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			addCardPage.saveChanges();

			// Get alert
			HtmlReporter.label("Verify after save");
			String status = addCardPage.getSaveStatus();
			assertContains(status, "New card successfully added.", status, status);

			// Verify new card on UI
			assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after adding",
					"Debit/credit card is displayed after adding");
			assertTrue(debtCreditCardsPage.isCardExisting(name), "New card doesn't display", "New card displayed");

			// Verify new card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null, "Checking new card by API: ADD FAILED",
					"Checking new card by API: ADD SUCCESSFULLY");
		} finally {
			HtmlReporter.label("Delete card after add sucess");
			creditCardAPI.removeCardByName(name);
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
			HtmlReporter.label("Add new card for set default");
			addCardPage = debtCreditCardsPage.addNewCard();
			assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
					"You are on the Add new card page");

			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			addCardPage.saveChanges();

			// Get alert
			String status = addCardPage.getSaveStatus();
			assertContains(status, "New card successfully added.", status, status);

			// Verify new card on UI
			assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after adding",
					"Debit/credit card is displayed after adding");
			assertTrue(debtCreditCardsPage.isCardExisting(name), "New card doesn't display", "New card displayed");

			// Verify new card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null, "Checking new card by API: ADD FAILED",
					"Checking new card by API: ADD SUCCESSFULLY");

			/********* Set the card as default *********************/
			HtmlReporter.label("Set new card as default and verify");
			// Is on Detail Card page
			detailCardPage = debtCreditCardsPage.selectCardDetailsByName(name);
			assertTrue(detailCardPage.isActive(), "You aren't on the Card Detail page",
					"You are on the Card Detail page");

			// Set default card
			debtCreditCardsPage = detailCardPage.setDefaultCard();

			// Get alert
			status = detailCardPage.getSaveStatus();
			assertContains(status, "Card set as default", status, status);
			// Verify default card on UI
			assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after set default",
					"Debit/credit card is displayed after set default");
			assertTrue(debtCreditCardsPage.isDefaultCard(name), "The greentick doesn't appear!",
					"The greentick appeared!");

			// Verify default card by API
			assertTrue(creditCardAPI.isDefaultCard(name), "Checking default card by API: FAILED",
					"Checking default card by API: PASSED");
		} finally {
			HtmlReporter.label("Delete created card and reset origin default card");
			// Set the default card to original one
			if (originalDefaultCard != null) {
				creditCardAPI.saveCard(originalDefaultCard);
			}
			// Remove card after testing
			creditCardAPI.removeCardByName(name);

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
			HtmlReporter.label("Add new card for set delete");
			addCardPage = debtCreditCardsPage.addNewCard();
			assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
					"You are on the Add new card page");

			// Add new card
			addCardPage.setCardName(name);
			addCardPage.setCardNumber(cardNumber);
			addCardPage.setCardExpiry(expiry);
			addCardPage.saveChanges();

			// Get alert
			String status = addCardPage.getSaveStatus();
			assertContains(status, "New card successfully added.", status, status);

			// Verify new card on UI
			assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after adding",
					"Debit/credit card is displayed after adding");
			assertTrue(debtCreditCardsPage.isCardExisting(name), "New card doesn't display", "New card displayed");

			// Verify new card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) != null, "Checking new card by API: ADD FAILED",
					"Checking new card by API: ADD SUCCESSFULLY");

			/********* Delete the card *********************/
			HtmlReporter.label("Delete card and verify");
			// Is on Detail Card page
			detailCardPage = debtCreditCardsPage.selectCardDetailsByName(name);
			assertTrue(detailCardPage.isActive(), "You aren't on the Card Detail page",
					"You are on the Card Detail page");

			// Delete card
			debtCreditCardsPage = detailCardPage.deleteCard();

			// Get alert
			status = detailCardPage.getSaveStatus();
			assertContains(status, "Card successfully deleted.", status, status);
			// Verify deleted card on UI
			assertTrue(debtCreditCardsPage.isActive(), "Debit/credit card is not displayed after delete card",
					"Debit/credit card is displayed after delete card");
			assertFalse(debtCreditCardsPage.isCardExisting(name), "The card still displays on UI",
					"The card is disappeared");

			// Verify the deleted card by API
			assertTrue(creditCardAPI.getCreditCardInfoByName(name) == null, "Verify the deleted card by API: FAILED",
					"Verify the deleted card by API: PASSED");
		} finally {
			// Remove card after testing
			HtmlReporter.label("Delete card failed, remove card by API");
			creditCardAPI.removeCardByName(name);
		}

	}

}

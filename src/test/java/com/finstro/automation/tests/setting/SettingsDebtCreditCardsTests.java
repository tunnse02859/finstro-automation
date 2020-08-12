package com.finstro.automation.tests.setting;

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

import org.json.JSONArray;
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
	public void FPC_3395_01_Setting_CardDetail_Verify_all_saved_card_displayed_correctly() throws Exception {
		// Generate test data

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		JSONArray creditCardsDetails = creditCardAPI.getCreditCardsInfo();
		for(int i = 0; i < creditCardsDetails.length(); i++) {
			String cardName = creditCardsDetails.getJSONObject(i).getString("name");
			assertTrue(debtCreditCardsPage.isCardExisting(cardName), "Card ["+ cardName +"] is not displayed", "Card ["+ cardName +"] is displayed correctly");
		}
		//assertTrue(debtCreditCardsPage.isCardExisting(name), "New card doesn't display", "New card displayed");
	}

	@Test
	public void FPC_3395_02_Setting_CardDetail_Verify_User_can_add_new_card() throws Exception {
		// Generate test data
		
		String name = DataGenerator.generateStringByDateTime("AddNewCard");
		String cardNumber = DataGenerator.generateDebitCardNumber();
		String expiry = "01/2022";

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		try {
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
	public void FPC_3396_01_Setting_CardDetail_Verify_user_can_set_default_card() throws Exception {

		// Generate test data
		
		String name = DataGenerator.generateStringByDateTime("DefaultCard");
		String cardNumber = DataGenerator.generateDebitCardNumber();
		String expiry = "01/2022";

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		/********* Save the original default card *********************/
		JSONObject originalDefaultCard = creditCardAPI.getDefaultCreditCardInfo();

		try {

			/********* Add a new card as a precondition *********************/

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
	public void FPC_3396_01_Setting_CardDetail_Verify_user_can_delete_card() throws Exception {
		// Generate test data
		String name = DataGenerator.generateStringByDateTime("DeleteCard");
		String cardNumber = DataGenerator.generateDebitCardNumber();
		String expiry = "01/2022";

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		try {
			/********* Add a new card as a precondition *********************/
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
	
	@Test
	public void FPC_2763_Setting_CardDetail_AddCard_All_Field_BLank() throws Exception {

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		HtmlReporter.label("Go to add new card screen and save without input data");
		addCardPage = debtCreditCardsPage.addNewCard();
		assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
				"You are on the Add new card page");
		
		addCardPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = addCardPage.getSaveStatus();
		assertContains(status, "Please enter all details on your card", "Error message displayed incorrectly", "Error message displayed correctly");
	}
	
	@Test
	public void FPC_2764_Setting_CardDetail_AddCard_Name_Blank() throws Exception {

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		HtmlReporter.label("Go to add new card screen and save without input Name");
		addCardPage = debtCreditCardsPage.addNewCard();
		assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
				"You are on the Add new card page");
		
		String cardNumber = DataGenerator.generateDebitCardNumber();
		String expiry = "01/2022";
		
		addCardPage.setCardNumber(cardNumber);
		addCardPage.setCardExpiry(expiry);
		addCardPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = addCardPage.getSaveStatus();
		assertContains(status, "Please enter all details on your card", "Error message displayed incorrectly", "Error message displayed correctly");
	}
	
	@Test
	public void FPC_2765_Setting_CardDetail_AddCard_CardNumber_Blank() throws Exception {

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		HtmlReporter.label("Go to add new card screen and save without input Card Number");
		addCardPage = debtCreditCardsPage.addNewCard();
		assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
				"You are on the Add new card page");
		
		String nameOnCard = DataGenerator.generateStringByDateTime("CardNumberBlank");
		String expiry = "01/2022";
		
		addCardPage.setCardName(nameOnCard);
		addCardPage.setCardExpiry(expiry);
		addCardPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = addCardPage.getSaveStatus();
		assertContains(status, "Please enter all details on your card", "Error message displayed incorrectly", "Error message displayed correctly");
	}
	
	@Test
	public void FPC_2766_Setting_CardDetail_AddCard_ExpireDate_Blank() throws Exception {

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		HtmlReporter.label("Go to add new card screen and save without input Expire Date");
		addCardPage = debtCreditCardsPage.addNewCard();
		assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
				"You are on the Add new card page");
		
		String nameOnCard = DataGenerator.generateStringByDateTime("ExpireDateBlank");
		String cardNumber = DataGenerator.generateDebitCardNumber();
		
		addCardPage.setCardName(nameOnCard);
		addCardPage.setCardNumber(cardNumber);
		addCardPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = addCardPage.getSaveStatus();
		assertContains(status, "Please enter all details on your card", "Error message displayed incorrectly", "Error message displayed correctly");
	}
	
	@Test
	public void FPC_2767_Setting_CardDetail_AddCard_CardNumber_Invalid() throws Exception {

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		HtmlReporter.label("Go to add new card screen and save with invalid Card Number");
		addCardPage = debtCreditCardsPage.addNewCard();
		assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
				"You are on the Add new card page");
		
		String nameOnCard = DataGenerator.generateStringByDateTime("InvalidCardNumber");
		String cardNumber = "123456";
		String expireDate = "01/2022";
		
		addCardPage.setCardName(nameOnCard);
		addCardPage.setCardNumber(cardNumber);
		addCardPage.setCardExpiry(expireDate);
		addCardPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = addCardPage.getSaveStatus();
		assertContains(status, "Invalid Card Number", "Error message displayed incorrectly", "Error message displayed correctly");
	}
	
	@Test
	public void FPC_3399_Setting_CardDetail_AddCard_unsuccessfully_for_pre_paid_card() throws Exception {

		debtCreditCardsPage = WorkFlows.goToDebtCreditCardsPage(driver);
		HtmlReporter.label("Go to add new card screen and save with invalid Card Number");
		addCardPage = debtCreditCardsPage.addNewCard();
		assertTrue(addCardPage.isActive(), "You aren't on the Add new card page",
				"You are on the Add new card page");
		
		String nameOnCard = DataGenerator.generateStringByDateTime("InvalidCardNumber");
		String cardNumber = "4262370314214521";
		String expireDate = "01/2022";
		
		addCardPage.setCardName(nameOnCard);
		addCardPage.setCardNumber(cardNumber);
		addCardPage.setCardExpiry(expireDate);
		addCardPage.saveChanges();

		// Get alert
		HtmlReporter.label("Verify error message");
		String status = addCardPage.getSaveStatus();
		assertContains(status, "Pre-paid cards not accepted", "Error message displayed incorrectly", "Error message displayed correctly");
	}

}

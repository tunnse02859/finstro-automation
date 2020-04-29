package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.OnboardingAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.MedicarePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class MedicareTests extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage selectCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private MedicarePage medicarePage;
	private OnboardingAPI onboardingAPI;
	
	@BeforeClass
	public void setupAPI() throws Exception {
		onboardingAPI = new OnboardingAPI();
		onboardingAPI.loginForAccessToken(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		onboardingAPI.setupMedicare();
	}

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		selectCardPage = new SelectBusinessCardPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page in first installation");

	}

	public void toMedicarePage() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS,
				Constant.ONBOARDING_ACCESS_CODE);

		HtmlReporter.label("Go to Medicare page");
		selectCardPage = new SelectBusinessCardPage(driver);

		businessDetailPage = selectCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(), "Business Detail screen is not  displayed",
				"Business Detail screen is displayed");

		residentialAddressPage = businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(), "Residential Address screen is not  displayed",
				"Residential Address screen is displayed");

		photoIDPage = residentialAddressPage.clickNext();
		assertTrue(photoIDPage.isActive(), "PhotoID screen is not  displayed", "PhotoID screen is displayed");

		drivingLisencePage = photoIDPage.clickNext();
		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not  displayed",
				"Driver License screen is displayed");
		
		medicarePage = drivingLisencePage.clickMedicare();
		assertTrue(medicarePage.isActive(), "Medicare screen is not  displayed ", "Medicare screen is displayed");
	}

	@Test
	public void FPC_1374_LastName_Cannot_Be_Blank() throws Exception {
		String firstName = Common.randomAlphaNumeric(5);
		String middleName = Common.randomAlphaNumeric(5);
		String gender = "Female";
		String dob = Common.generateLastDateFromNow("dd/MM/YYYY", 18);
		String colourOfCard = "Green";
		String referenceNmuber = Common.randomNumeric(2);
		String expiryDate = Common.generateLastDateFromNow("MM/YYYY", 0);
		String medicareNumber = "3501803151";

		HtmlReporter.label("Navigate the Medicare screen");
		toMedicarePage();

		HtmlReporter.label("Leave Blank at the Last Name");
		medicarePage.inputLastName("");

		HtmlReporter.label("Input valid data for remaining fields");
		medicarePage.inputFirstName(firstName);
		medicarePage.inputMiddleName(middleName);
		medicarePage.selectGender(gender);
		medicarePage.selectCardColor(colourOfCard);
		medicarePage.inputDoB(dob);
		medicarePage.inputMedicareNumber(medicareNumber);
		medicarePage.inputReferenceNumber(referenceNmuber);
		medicarePage.inputExpireDate(expiryDate);

		HtmlReporter.label("Click on Submit");
		medicarePage.clickNext();

		HtmlReporter.label("The validation message should display as : Last name must have value");
		String errorMess = medicarePage.getSubmitStatus();
		assertTrue(errorMess.equals("Please complete all fields")| errorMess.equals("Surname is mandatory!"), "Error message displayed incorrectly", "Error message displayed correctly");
	}

//	@Test
//	public void FPC_1376_DateOfBirth_Cannot_Be_Blank() throws Exception {
//		medicarePage.inputDoB("");
//		medicarePage.clickNext();
//		String errorMess = medicarePage.getSubmitStatus();
//		assertContains(errorMess, "Please complete all fields", "Error message displayed incorrectly", "Error message displayed correctly");
//	}

	@Test
	public void FPC_1378_MedicareNumber_Cannot_Be_Blank() throws Exception {
		String firstName = Common.randomAlphaNumeric(5);
		String middleName = Common.randomAlphaNumeric(5);
		String gender = "Female";
		String dob = Common.generateLastDateFromNow("dd/MM/YYYY", 18);
		String colourOfCard = "Green";
		String referenceNmuber = Common.randomNumeric(2);
		String expiryDate = Common.generateLastDateFromNow("MM/YYYY", 0);

		HtmlReporter.label("Navigate the Medicare screen");
		toMedicarePage();

		HtmlReporter.label("Leave Blank at the Medicare Number");
		medicarePage.inputMedicareNumber("");

		HtmlReporter.label("Input valid data for remaining fields");
		medicarePage.inputFirstName(firstName);
		medicarePage.inputLastName(firstName);
		medicarePage.inputMiddleName(middleName);
		medicarePage.selectGender(gender);
		medicarePage.selectCardColor(colourOfCard);
		medicarePage.inputDoB(dob);
		medicarePage.inputReferenceNumber(referenceNmuber);
		medicarePage.inputExpireDate(expiryDate);
		
		HtmlReporter.label("Click on Submit");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertTrue(errorMess.equals("Please complete all fields")| errorMess.equals("Medicare number is mandatory!"), "Error message displayed incorrectly", "Error message displayed correctly");
	}

	@Test
	public void FPC_1379_ReferenceNumber_Cannot_Be_Blank() throws Exception {
		String firstName = Common.randomAlphaNumeric(5);
		String middleName = Common.randomAlphaNumeric(5);
		String gender = "Female";
		String dob = Common.generateLastDateFromNow("dd/MM/YYYY", 18);
		String colourOfCard = "Green";
		String expiryDate = Common.generateLastDateFromNow("MM/YYYY", 0);
		String medicareNumber = "3501803151";

		HtmlReporter.label("Navigate the Medicare screen");
		toMedicarePage();

		HtmlReporter.label("Leave Blank at the ReferenceNumber");
		medicarePage.inputReferenceNumber("");

		HtmlReporter.label("Input valid data for remaining fields");
		medicarePage.inputFirstName(firstName);
		medicarePage.inputLastName(firstName);
		medicarePage.inputMiddleName(middleName);
		medicarePage.selectGender(gender);
		medicarePage.selectCardColor(colourOfCard);
		medicarePage.inputDoB(dob);
		medicarePage.inputMedicareNumber(medicareNumber);
		medicarePage.inputExpireDate(expiryDate);
		
		HtmlReporter.label("Click on Submit");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertTrue(errorMess.equals("Please complete all fields")| errorMess.equals("Reference number is mandatory!"), "Error message displayed incorrectly", "Error message displayed correctly");
	}

//	@Test
//	public void FPC_1380_ExpireDate_Cannot_Be_Blank() throws Exception {
//		medicarePage.inputExpireDate("");
//		medicarePage.clickNext();
//		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
//	}

//	@Test
//	public void FPC_1383_MiddleInitial_Must_be_valid() throws Exception {
//		String firstName = Common.randomAlphaNumeric(5);
//		String lastName = Common.randomAlphaNumeric(5);
//		String gender = "Female";
//		String dob = Common.generateLastDateFromNow("dd/MM/YYYY", 18);
//		String colourOfCard = "Green";
//		String referenceNmuber = Common.randomNumeric(2);
//		String expiryDate = Common.generateLastDateFromNow("MM/YYYY", 0);
//		String medicareNumber = "3501803151";
//
//		HtmlReporter.label("Navigate the Medicare screen");
//		toMedicarePage();
//
//		HtmlReporter.label("Input invalid middle name");
//		medicarePage.inputMiddleName("");
//
//		HtmlReporter.label("Input valid data for remaining fields");
//		medicarePage.inputFirstName(firstName);
//		medicarePage.inputMiddleName(lastName);
//		medicarePage.selectGender(gender);
//		medicarePage.selectCardColor(colourOfCard);
//		medicarePage.inputDoB(dob);
//		medicarePage.inputMedicareNumber(medicareNumber);
//		medicarePage.inputReferenceNumber(referenceNmuber);
//		medicarePage.inputExpireDate(expiryDate);
//
//		HtmlReporter.label("Click on Submit");
//		medicarePage.clickNext();
//		
//		String errorMess = medicarePage.getSubmitStatus();
//		assertTrue(errorMess.equals("Please complete all fields")| errorMess.equals("Reference number is mandatory!"), "Error message displayed incorrectly", "Error message displayed correctly");
//	}

	@Test
	public void FPC_1384_DateOfBirth_Must_be_in_YYYY_MM_DD() throws Exception {
		HtmlReporter.label("Navigate the Medicare screen");
		toMedicarePage();
		
		HtmlReporter.label("Verify format of Date of Birth");
		String dob = medicarePage.getDob();
		assertDateTimeFormat(dob,"dd/MM/yyyy","Date of birth value is not in correct format","Date of birth value is in correct format");
	}
	
//	@Test
//	public void Verify_ExpireDate_Must_be_in_YYYY_MM() throws Exception {
//		toMedicarePage();
//		HtmlReporter.label("Verify format of Date of Birth");
//		String expireDate = medicarePage.getExpireDate();
//		assertDateTimeFormat(expireDate,"YYYY/MM","Expire Date value is not in correct format","Expire Date value is in correct format");
//	}

	@Test
	public void FPC_1385_MedicareNumber_Must_be_valid() throws Exception {
		String firstName = Common.randomAlphaNumeric(5);
		String middleName = Common.randomAlphaNumeric(5);
		String gender = "Female";
		String dob = Common.generateLastDateFromNow("dd/MM/YYYY", 18);
		String colourOfCard = "Green";
		String referenceNmuber = Common.randomNumeric(2);
		String expiryDate = Common.generateLastDateFromNow("MM/YYYY", 0);
		String imvalidMedicareNumber = Common.randomNumeric(12);

		HtmlReporter.label("Navigate the Medicare screen");
		toMedicarePage();

		HtmlReporter.label("Input Invalid Medicare Number");
		medicarePage.inputMedicareNumber(imvalidMedicareNumber);

		HtmlReporter.label("Input valid data for remaining fields");
		medicarePage.inputFirstName(firstName);
		medicarePage.inputLastName(firstName);
		medicarePage.inputMiddleName(middleName);
		medicarePage.selectGender(gender);
		medicarePage.selectCardColor(colourOfCard);
		medicarePage.inputDoB(dob);
		medicarePage.inputReferenceNumber(referenceNmuber);
		medicarePage.inputExpireDate(expiryDate);
		
		HtmlReporter.label("Click on Submit");
		medicarePage.clickNext();
		
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Invalid Medicare Number.", "Error message displayed incorrectly", "Error message displayed correctly");
	}

	@Test
	public void FPC_1386_ReferenceNumber_Must_be_valid() throws Exception {
		toMedicarePage();
		String invalidReferenceNumber = Common.randomNumeric(2);
		medicarePage.inputReferenceNumber(invalidReferenceNumber);
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Invalid Medicare Reference.", "Error message displayed incorrectly", "Error message displayed correctly");
	}

	@Test
	public void FPC_1387_Cancel_and_Verify_no_data_saved() throws Exception {
		HtmlReporter.label("Navigate the Medicare screen");
		toMedicarePage();
		onboardingAPI.getMedicareInfor();
		medicarePage.inputFirstName("abc");
		medicarePage.inputLastName("def");
		medicarePage.inputMiddleName("ghj");

		HtmlReporter.label("Click on Cancel Button");
		medicarePage.clickCancel();
		onboardingAPI.recoveryData().then().verifyResponseCode(200)
				.verifyJsonNodeEqual("medicareCard.cardNumber", Common.getTestVariable("cardNumber", false))
				.verifyJsonNodeEqual("medicareCard.cardNumberRef", Common.getTestVariable("cardNumberRef", false))
				.verifyJsonNodeEqual("medicareCard.dateOfBirth", Common.getTestVariable("dateOfBirth", false))
				.verifyJsonNodeEqual("medicareCard.firstName", Common.getTestVariable("firstName", false))
				.verifyJsonNodeEqual("medicareCard.gender", Common.getTestVariable("gender", false))
				.verifyJsonNodeEqual("medicareCard.identificationId", Common.getTestVariable("identificationId", false))
				.verifyJsonNodeEqual("medicareCard.middleInitial", Common.getTestVariable("middleInitial", false))
				.verifyJsonNodeEqual("medicareCard.surname", Common.getTestVariable("surname", false))
				.verifyJsonNodeEqual("medicareCard.validTo", Common.getTestVariable("validTo", false))
			.flush();
	}
}
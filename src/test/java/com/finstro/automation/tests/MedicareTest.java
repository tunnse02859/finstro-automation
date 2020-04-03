package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
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

public class MedicareTest extends MobileTestSetup {
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

		toMedicarePage();
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
		medicarePage.inputLastName("");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Surname is mandatory!", "Error message displayed incorrectly", "Error message displayed correctly");
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
		medicarePage.inputMedicareNumber("");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Medicare number is mandatory!", "Error message displayed incorrectly", "Error message displayed correctly");
	}

	@Test
	public void FPC_1379_ReferenceNumber_Cannot_Be_Blank() throws Exception {
		medicarePage.inputReferenceNumber("");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Reference number is mandatory!", "Error message displayed incorrectly", "Error message displayed correctly");
	}

//	@Test
//	public void FPC_1380_ExpireDate_Cannot_Be_Blank() throws Exception {
//		medicarePage.inputExpireDate("");
//		medicarePage.clickNext();
//		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
//	}

	@Test
	public void FPC_1383_MiddleInitial_Must_be_valid() throws Exception {
		medicarePage.inputMiddleName("AB");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Please complete all fields", "Error message displayed incorrectly", "Error message displayed correctly");
	}

//	@Test
//	public void FPC_1384_DateOfBirth_Must_be_in_YYYY_MM_DD() throws Exception {
//		medicarePage.inputDoB("01/13/2020");
//		medicarePage.clickNext();
//		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
//	}

	@Test
	public void FPC_1385_MedicareNumber_Must_be_valid() throws Exception {
		String imvalidMedicareNumber = Common.randomNumeric(12);
		medicarePage.inputMedicareNumber(imvalidMedicareNumber);
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Invalid Medicare Number.", "Error message displayed incorrectly", "Error message displayed correctly");
	}

	@Test
	public void FPC_1386_Number_Must_be_valid() throws Exception {
		String invalidReferenceNumber = Common.randomNumeric(2);
		medicarePage.inputReferenceNumber(invalidReferenceNumber);
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Invalid Medicare Reference.", "Error message displayed incorrectly", "Error message displayed correctly");
	}

	@Test
	public void FPC_1387_Cancel_and_Verify_no_data_saved() throws Exception {
		onboardingAPI.getMedicareInfor();
		medicarePage.inputFirstName("abc");
		medicarePage.inputLastName("def");
		medicarePage.inputMiddleName("ghj");

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
package com.finstro.automation.tests.onboarding;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
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

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

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
		onboardingAPI.setupBusinessDetail("500");
		onboardingAPI.setupResidentialAddress();
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
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);

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

//	@Test
//	public void FPC_Pending_Gender_is_correct_DropDownList() throws Exception {
//		toMedicarePage();
//		medicarePage.selectGender("Male");
//		medicarePage.selectGender("Female");
//	}
//	
//	@Test
//	public void FPC_Pending_ColorOfCard_is_correct_DropDownList() throws Exception {
//		toMedicarePage();
//		medicarePage.selectCardColor("Green");
//		medicarePage.selectCardColor("Blue");
//		medicarePage.selectCardColor("Yellow");
//	}

	@Test
	public void FPC_1374_LastName_Cannot_Be_Blank() throws Exception {
		toMedicarePage();
		medicarePage.inputLastName("");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Surname is mandatory!", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}

	@Test
	public void FPC_1378_MedicareNumber_Cannot_Be_Blank() throws Exception {
		toMedicarePage();
		medicarePage.inputMedicareNumber("");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Medicare number is mandatory!", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}

	@Test
	public void FPC_1379_ReferenceNumber_Cannot_Be_Blank() throws Exception {
		toMedicarePage();
		medicarePage.inputReferenceNumber("");
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Reference number is mandatory!", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}


	// @Test
	// public void FPC_1380_ExpireDate_Cannot_Be_Blank() throws Exception {
	// medicarePage.inputExpireDate("");
	// medicarePage.clickNext();
	// medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	// }

	@Test
	public void FPC_1383_Verify_MiddleInitial_MaxLength_is_1() throws Exception {
		toMedicarePage();
		medicarePage.inputMiddleName("AB");
		String inputedMiddleName = medicarePage.getMiddleName();
		assertEquals(inputedMiddleName, "A", "Value of Middle Initial is incorrectly", "Value of Middle Initial is correctly");
	}

	// @Test
	// public void FPC_1384_DateOfBirth_Must_be_in_YYYY_MM_DD() throws Exception {
	// toMedicarePage();
	// HtmlReporter.label("Verify format of Date of Birth");
	// String dob = medicarePage.getDob();
	// assertDateTimeFormat(dob,"YYYY/MM/DD","Date of birth value is not in correct
	// format","Date of birth value is in correct format");
	// }

	// @Test
	// public void FPC_2840_Verify_ExpireDate_Must_be_in_YYYY_MM() throws Exception
	// {
	// toMedicarePage();
	// HtmlReporter.label("Verify format of Date of Birth");
	// String expireDate = medicarePage.getExpireDate();
	// assertDateTimeFormat(expireDate,"YYYY/MM","Expire Date value is not in
	// correct format","Expire Date value is in correct format");
	// }

	@Test
	public void FPC_1385_Verify_ErrorMessage_With_Invalid_MedicareNumber() throws Exception {
		toMedicarePage();
		String imvalidMedicareNumber = Common.randomNumeric(12);
		medicarePage.inputMedicareNumber(imvalidMedicareNumber);
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Invalid Medicare Number.", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}

	@Test
	public void FPC_1386_Verify_ErrorMessage_With_Invalid_ReferenceNumber() throws Exception {
		toMedicarePage();
		String invalidReferenceNumber = Common.randomNumeric(2);
		medicarePage.inputReferenceNumber(invalidReferenceNumber);
		medicarePage.clickNext();
		String errorMess = medicarePage.getSubmitStatus();
		assertContains(errorMess, "Invalid Medicare Reference.", "Error message displayed incorrectly",
				"Error message displayed correctly");
	}

	@Test
	public void FPC_1387_Cancel_and_Verify_no_data_saved() throws Exception {
		toMedicarePage();
		onboardingAPI.getMedicareInfor();
		medicarePage.inputFirstName("abc");
		medicarePage.inputLastName("def");
		medicarePage.inputMiddleName("ghj");

		drivingLisencePage = medicarePage.clickCancel();
		assertTrue(drivingLisencePage.isActive(), "Driver License screen is not displayed after back from Medicare",
				"Driver License screen is displayed after back from medicare");
	}
}
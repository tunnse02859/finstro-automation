package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.api.OnboardingAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.on_boarding.BusinessDetailPage;
import com.finstro.automation.pages.on_boarding.DriverLicensePage;
import com.finstro.automation.pages.on_boarding.PhotoIDPage;
import com.finstro.automation.pages.on_boarding.PostalAddressPage;
import com.finstro.automation.pages.on_boarding.ResidentialAddressPage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.FilePaths;

public class DriverLicenseTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage selectCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
	private OnboardingAPI onboardingAPI;
	
	@BeforeClass
	public void setupAPI() throws Exception {
		onboardingAPI = new OnboardingAPI();
		onboardingAPI.loginForAccessToken(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		onboardingAPI.setupDrivingLicense();
	}
	

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page in first installation");
	}
	
	public void toDriverLicensePage() throws Exception {
		loginPage.doSuccessLogin(Constant.ONBOARDING_EMAIL_ADDRESS, Constant.ONBOARDING_ACCESS_CODE);
		
		HtmlReporter.label("Go to driving license page");
		selectCardPage = new SelectBusinessCardPage(driver);
		
		businessDetailPage = selectCardPage.clickOnCard("500");
		assertTrue(businessDetailPage.isActive(),
				"Business Detail screen is not  displayed",
				"Business Detail screen is displayed");
		
		residentialAddressPage = businessDetailPage.clickNext();
		assertTrue(residentialAddressPage.isActive(),
				"Residential Address screen is not  displayed",
				"Residential Address screen is displayed");
		
		photoIDPage = residentialAddressPage.clickNext();
		assertTrue(photoIDPage.isActive(),
				"PhotoID screen is not  displayed",
				"PhotoID screen is displayed");
		
		drivingLisencePage = photoIDPage.clickNext();
		assertTrue(drivingLisencePage.isActive(),
				"Driver License screen is not  displayed",
				"Driver License screen is displayed");
	}

	
	@Test
	public void FPC_1350_Verify_State_ACT_License_MustBe_NumericUpto10() throws Exception {
		String validLicenseNumber = Common.randomNumeric(10);
		toDriverLicensePage();
		HtmlReporter.label("Input state = ACT and license number = " + validLicenseNumber);
		drivingLisencePage.inputState("ACT");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber);
		
		HtmlReporter.label("Verify user can submit and directed to postal address screen");
		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1351_Verify_State_NT_License_MustBe_NumericUpto10() throws Exception {
		String validLicenseNumber = Common.randomNumeric(10);
		toDriverLicensePage();
		HtmlReporter.label("Input state = NT and license number = " + validLicenseNumber);
		drivingLisencePage.inputState("NT");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber);
		
		HtmlReporter.label("Verify user can submit and directed to postal address screen");
		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1352_Verify_State_QLD_License_MustBe_Numeric8or9() throws Exception {
		String validLicenseNumber7num = Common.randomNumeric(8);
		toDriverLicensePage();
		HtmlReporter.label("Input state = QLD and license number = " + validLicenseNumber7num);
		drivingLisencePage.inputState("QLD");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber7num);
		
		HtmlReporter.label("Verify user can submit and directed to postal address screen");
		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1353_Verify_State_NSW_License_MustBe_Alphanumeric6to8() throws Exception {
		String validLicenseNumber5num = Common.randomNumeric(6);
		toDriverLicensePage();
		HtmlReporter.label("Input state = NSW and license number = " + validLicenseNumber5num);
		drivingLisencePage.inputState("NSW");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber5num);
		
		HtmlReporter.label("Verify user can submit and directed to postal address screen");
		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1354_Verify_State_SA_License_MustBe_Alphanumeric6() throws Exception {
		String validLicenseNumber5num = Common.randomNumeric(6);
		toDriverLicensePage();
		HtmlReporter.label("Input state = SA and license number = " + validLicenseNumber5num);
		drivingLisencePage.inputState("SA");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber5num);
		
		HtmlReporter.label("Verify user can submit and directed to postal address screen");
		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1355_Verify_State_TAS_License_MustBe_Alphanumeric6to8() throws Exception {
		String validLicenseNumber5num = Common.randomNumeric(6);
		toDriverLicensePage();
		HtmlReporter.label("Input state = TAS and license number = " + validLicenseNumber5num);
		drivingLisencePage.inputState("TAS");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber5num);
		
		HtmlReporter.label("Verify user can submit and directed to postal address screen");
		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1356_Verify_State_VIC_License_MustBe_NumericUpto10() throws Exception {
		String validLicenseNumber = Common.randomNumeric(10);
		toDriverLicensePage();
		HtmlReporter.label("Input state = VIC and license number = " + validLicenseNumber);
		drivingLisencePage.inputState("VIC");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber);
		
		HtmlReporter.label("Verify user can submit and directed to postal address screen");
		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1357_Verify_State_WA_License_MustBe_Numeric7() throws Exception {
		String validLicenseNumber7num = Common.randomNumeric(7);
		toDriverLicensePage();
		HtmlReporter.label("Input state = WA and license number = " + validLicenseNumber7num);
		drivingLisencePage.inputState("WA");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber7num);
		
		HtmlReporter.label("Verify user can submit and directed to postal address screen");
		postalAddressPage = drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1359_FirstName_Cannot_Be_Blank() throws Exception {
		toDriverLicensePage();
		HtmlReporter.label("Submit driving license with blank First Name and verify error");
		drivingLisencePage.inputFirstName("");
		postalAddressPage = drivingLisencePage.clickNext();
		String errorMess = drivingLisencePage.getSubmitStatus();
		assertContains(errorMess, "Please complete all fields or enter Medicare details", "Error message displayed incorrectly", "Error message displayed correctly");
	}
	
	@Test
	public void FPC_1360_LastName_Cannot_Be_Blank() throws Exception {
		toDriverLicensePage();
		HtmlReporter.label("Submit driving license with blank Last Name and verify error");
		drivingLisencePage.inputLastName("");
		postalAddressPage = drivingLisencePage.clickNext();
		String errorMess = drivingLisencePage.getSubmitStatus();
		assertContains(errorMess, "Please complete all fields or enter Medicare details", "Error message displayed incorrectly", "Error message displayed correctly");
	}
	
//	@Test
//	public void FPC_1361_State_Cannot_Be_Blank() throws Exception {
//		toDriverLicensePage();
//		HtmlReporter.label("Submit driving license with blank State and verify error");
//		drivingLisencePage.inputState("");
//		postalAddressPage = drivingLisencePage.clickNext();
//		String errorMess = drivingLisencePage.getSubmitStatus();
//		assertContains(errorMess, "Please complete all fields or enter Medicare details", "Error message displayed incorrectly", "Error message displayed correctly");
//	}
	
//	@Test
//	public void FPC_1362_DateOfBirth_Cannot_Be_Blank() throws Exception {
//		toDriverLicensePage();
//		HtmlReporter.label("Submit driving license with blank Date of birth and verify error");
//		drivingLisencePage.inputDoB("");
//		postalAddressPage = drivingLisencePage.clickNext();
//		String errorMess = drivingLisencePage.getSubmitStatus();
//		assertContains(errorMess, "Please complete all fields or enter Medicare details", "Error message displayed incorrectly", "Error message displayed correctly");
//	}
	
	@Test
	public void FPC_1363_DrivingLicenseNumber_Cannot_Be_Blank() throws Exception {
		toDriverLicensePage();
		HtmlReporter.label("Submit driving license with blank Driving License and verify error");
		drivingLisencePage.inputLicenseNumber("");
		postalAddressPage = drivingLisencePage.clickNext();
		String errorMess = drivingLisencePage.getSubmitStatus();
		assertContains(errorMess, "Please complete all fields or enter Medicare details", "Error message displayed incorrectly", "Error message displayed correctly");
	}
	
//	@Test
//	public void FPC_1364_ExpireDate_Cannot_Be_Blank() throws Exception {
//		toDriverLicensePage();
//		HtmlReporter.label("Submit driving license with blank Expire date and verify error");
//		drivingLisencePage.inputExpireDate("");
//		postalAddressPage = drivingLisencePage.clickNext();
//		String errorMess = drivingLisencePage.getSubmitStatus();
//		assertContains(errorMess, "Please complete all fields or enter Medicare details", "Error message displayed incorrectly", "Error message displayed correctly");
//	}
	
//	@Test
//	public void FPC_1369_DateOfBirth_Must_be_in_YYYY_MM_DD() throws Exception {
//		toDriverLicensePage();
//		HtmlReporter.label("Submit driving license with invalid format DoB and verify error");
//		drivingLisencePage.inputDoB("01/13/2020");
//		postalAddressPage = drivingLisencePage.clickNext();
//		String errorMess = drivingLisencePage.getSubmitStatus();
//		assertContains(errorMess, "Please complete all fields or enter Medicare details", "Error message displayed incorrectly", "Error message displayed correctly");
//	}
	
	@DataProvider(name="invalid_drivingLicense")
	public Object[][] getInvalidDrivingLicense() throws Exception{
		Object[][] data = getTestProvider(FilePaths.getResourcePath("/dataprovider/invalid_driving_license/invalid_driving_license.xlsx"), "Sheet1");
		return data;
	}
	
	
	@Test(dataProvider = "invalid_drivingLicense")
	public void FPC_1370_DrivingLicense_Must_be_valid(String state, String invalidDrivingLicense) throws Exception {
		toDriverLicensePage();
		HtmlReporter.label("Input state = " + state + " with invalid driving license = " + invalidDrivingLicense);
		drivingLisencePage.inputState(state);
		drivingLisencePage.inputLicenseNumber(invalidDrivingLicense);
		
		HtmlReporter.label("Submit and veriry error");
		postalAddressPage = drivingLisencePage.clickNext();
		String errorMess = drivingLisencePage.getSubmitStatus();
		assertContains(errorMess, "Invalid Driving Licence number.", "Error message displayed incorrectly", "Error message displayed correctly");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
}
package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.BusinessDetailPage;
import com.finstro.automation.pages.DriverLicensePage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.PhotoIDPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.pages.ResidentialAddressPage;
import com.finstro.automation.pages.SelectBusinessCardPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class DriverLicenseTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage businessCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private FinstroAPI finstroAPI;
	

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		businessCardPage = new SelectBusinessCardPage(driver);
		businessDetailPage = new BusinessDetailPage(driver);
		residentialAddressPage = new ResidentialAddressPage(driver);
		photoIDPage = new PhotoIDPage(driver);
		drivingLisencePage = new DriverLicensePage(driver);
		finstroAPI = new FinstroAPI();
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");
		
		toDriverLicensePage();
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
	}
	
	public void toDriverLicensePage() throws Exception {
		loginPage.doSuccessLogin(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		businessCardPage.clickOnCard("500");
		businessDetailPage.clickNext();
		residentialAddressPage.clickNext();
		Thread.sleep(10000);
		photoIDPage.clickNext();
		assertTrue(drivingLisencePage.isActive(),
				"Driver License screen is not  displayed after click on next",
				"Driver License screen is displayed after click on next");
	}

	@Test
	public void FPC_1348_Verify_User_RedirectTo_DriverLicense_Screen_Successfully() throws Exception {
		finstroAPI.getDrivingLicenceInfor();
		drivingLisencePage.verifyDriverLicenseInfor(
				Common.getTestVariable("gender").equalsIgnoreCase("M") ? "Male" : "Female",
				Common.getTestVariable("firstName"),
				Common.getTestVariable("surname"),
				Common.getTestVariable("middleName"),
				Common.getTestVariable("state"),
				Common.getTestVariable("dateOfBirth"),
				Common.getTestVariable("licenceNumber"),
				Common.getTestVariable("validTo"));
	}
	
	@Test
	public void FPC_1350_Verify_State_ACT_License_MustBe_NumericUpto10() throws Exception {
		String invalidLicenseNumber = Common.randomNumeric(11);
		
		//check validation
		drivingLisencePage.inputState("ACT");
		HtmlReporter.info("Input license Number with numeric length = 11");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
	
	@Test
	public void FPC_1351_Verify_State_NT_License_MustBe_NumericUpto10() throws Exception {
		String invalidLicenseNumber = Common.randomNumeric(11);
		
		//check validation
		drivingLisencePage.inputState("NT");
		HtmlReporter.info("Input license Number with numeric length = 11");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
	
	@Test
	public void FPC_1352_Verify_State_QLD_License_MustBe_Numeric8or9() throws Exception {
		String invalidLicenseNumber7num = Common.randomNumeric(7);
		String invalidLicenseNumber10num = Common.randomNumeric(10);
		//check validation
		drivingLisencePage.inputState("QLD");
		HtmlReporter.info("Input license Number with Alphanumeric length = 7");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber7num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
		
		HtmlReporter.info("Input license Number with Alphanumeric length = 10");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber10num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");
	}
	
	@Test
	public void FPC_1353_Verify_State_NSW_License_MustBe_Alphanumeric6to8() throws Exception {
		String invalidLicenseNumber5num = Common.randomAlphaNumeric(5);
		String invalidLicenseNumber9num = Common.randomAlphaNumeric(9);
		
		//check validation
		drivingLisencePage.inputState("NSW");
		HtmlReporter.info("Input license Number with Alphanumeric length = 5");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber5num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");
		
		HtmlReporter.info("Input license Number with Alphanumeric length = 9");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber9num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
	
	@Test
	public void FPC_1354_Verify_State_SA_License_MustBe_Alphanumeric6() throws Exception {
		String invalidLicenseNumber5num = Common.randomAlphaNumeric(5);
		String invalidLicenseNumber7num = Common.randomAlphaNumeric(7);
		
		//check validation
		drivingLisencePage.inputState("SA");
		
		HtmlReporter.info("Input license Number with Alphanumeric length = 5");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber5num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");
		
		HtmlReporter.info("Input license Number with Alphanumeric length = 7");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber7num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
	
	@Test
	public void FPC_1355_Verify_State_TAS_License_MustBe_Alphanumeric6to8() throws Exception {
		String invalidLicenseNumber5num = Common.randomAlphaNumeric(5);
		String invalidLicenseNumber9num = Common.randomAlphaNumeric(9);
		
		//check validation
		drivingLisencePage.inputState("TAS");
		HtmlReporter.info("Input license Number with Alphanumeric length = 5");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber5num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");
		
		HtmlReporter.info("Input license Number with Alphanumeric length = 9");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber9num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
	
	@Test
	public void FPC_1356_Verify_State_VIC_License_MustBe_NumericUpto10() throws Exception {
		String invalidLicenseNumber = Common.randomNumeric(11);
		
		//check validation
		drivingLisencePage.inputState("VIC");
		HtmlReporter.info("Input license Number with numeric length = 10");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
	
	@Test
	public void FPC_1357_Verify_State_WA_License_MustBe_Numeric7() throws Exception {
		String invalidLicenseNumber6num = Common.randomNumeric(6);
		String invalidLicenseNumber8num = Common.randomNumeric(8);
		
		//check validation
		drivingLisencePage.inputState("WA");
		HtmlReporter.info("Input license Number with numeric length = 6");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber6num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
		
		HtmlReporter.info("Input license Number with numeric length = 8");
		drivingLisencePage.inputLicenseNumber(invalidLicenseNumber8num);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
	
	@Test
	public void FPC_1359_FirstName_Cannot_Be_Blank() throws Exception {
		drivingLisencePage.inputFirstName("");
		drivingLisencePage.clickNext();
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
	}
	
	@Test
	public void FPC_1360_LastName_Cannot_Be_Blank() throws Exception {
		drivingLisencePage.inputLastName("");
		drivingLisencePage.clickNext();
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
	}
	
	@Test
	public void FPC_1361_State_Cannot_Be_Blank() throws Exception {
		drivingLisencePage.inputState("");
		drivingLisencePage.clickNext();
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
	}
	
	@Test
	public void FPC_1362_DateOfBirth_Cannot_Be_Blank() throws Exception {
		drivingLisencePage.inputDoB("");
		drivingLisencePage.clickNext();
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
	}
	
	@Test
	public void FPC_1363_DrivingLicenseNumber_Cannot_Be_Blank() throws Exception {
		drivingLisencePage.inputLicenseNumber("");
		drivingLisencePage.clickNext();
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
	}
	
	@Test
	public void FPC_1364_ExpireDate_Cannot_Be_Blank() throws Exception {
		drivingLisencePage.inputExpireDate("");
		drivingLisencePage.clickNext();
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
	}
}
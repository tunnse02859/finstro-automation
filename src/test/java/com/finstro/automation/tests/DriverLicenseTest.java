package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.BusinessDetailPage;
import com.finstro.automation.pages.DriverLicensePage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.PhotoIDPage;
import com.finstro.automation.pages.PostalAddressPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.pages.ResidentialAddressPage;
import com.finstro.automation.pages.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.FilePaths;

public class DriverLicenseTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage businessCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private PostalAddressPage postalAddressPage;
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
		postalAddressPage = new PostalAddressPage(driver);
		finstroAPI = new FinstroAPI();
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation");
		
		toDriverLicensePage();
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
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
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
		String validLicenseNumber = Common.randomNumeric(10);
		drivingLisencePage.inputState("ACT");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber);
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1351_Verify_State_NT_License_MustBe_NumericUpto10() throws Exception {
		String validLicenseNumber = Common.randomNumeric(11);
		drivingLisencePage.inputState("NT");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber);
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1352_Verify_State_QLD_License_MustBe_Numeric8or9() throws Exception {
		String validLicenseNumber7num = Common.randomNumeric(7);
		drivingLisencePage.inputState("QLD");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber7num);
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1353_Verify_State_NSW_License_MustBe_Alphanumeric6to8() throws Exception {
		String validLicenseNumber5num = Common.randomAlphaNumeric(5);
		drivingLisencePage.inputState("NSW");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber5num);
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1354_Verify_State_SA_License_MustBe_Alphanumeric6() throws Exception {
		String validLicenseNumber5num = Common.randomAlphaNumeric(5);
		drivingLisencePage.inputState("SA");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber5num);
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1355_Verify_State_TAS_License_MustBe_Alphanumeric6to8() throws Exception {
		String validLicenseNumber5num = Common.randomAlphaNumeric(5);
		drivingLisencePage.inputState("TAS");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber5num);
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1356_Verify_State_VIC_License_MustBe_NumericUpto10() throws Exception {
		String validLicenseNumber = Common.randomNumeric(11);
		drivingLisencePage.inputState("VIC");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber);
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
	}
	
	@Test
	public void FPC_1357_Verify_State_WA_License_MustBe_Numeric7() throws Exception {
		String validLicenseNumber6num = Common.randomNumeric(6);
		drivingLisencePage.inputState("WA");
		drivingLisencePage.inputLicenseNumber(validLicenseNumber6num);
		drivingLisencePage.clickNext();
		assertTrue(postalAddressPage.isActive(),"Postal address of card screen is not displayed","Postal address of card screen is displayed");
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
	
	@Test
	public void FPC_1369_DateOfBirth_Must_be_in_YYYY_MM_DD() throws Exception {
		drivingLisencePage.inputDoB("01/13/2020");
		drivingLisencePage.clickNext();
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
	}
	
	@DataProvider(name="invalid_drivingLicense")
	public Object[][] getInvalidDrivingLicense() throws Exception{
		Object[][] data = getTestProvider(FilePaths.getResourcePath("/dataprovider/invalid_driving_license/invalid_driving_license.xlsx"), "Sheet1");
		return data;
	}
	
	
	@Test(dataProvider = "invalid_drivingLicense")
	public void FPC_1370_DrivingLicense_Must_be_valid(String state, String invalidDrivingLicense) throws Exception {
		drivingLisencePage.inputState(state);
		drivingLisencePage.inputLicenseNumber(invalidDrivingLicense);
		drivingLisencePage.clickNext();
		
		drivingLisencePage.verifyErrorMessage("ERROR, Please complete all fields or enter Medicare details");
		drivingLisencePage.verifyDrivingLicenseError("Invalid Driving Licence Number");	
	}
}
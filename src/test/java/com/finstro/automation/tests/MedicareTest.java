package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.setup_information.BusinessDetailPage;
import com.finstro.automation.pages.setup_information.DriverLicensePage;
import com.finstro.automation.pages.setup_information.MedicarePage;
import com.finstro.automation.pages.setup_information.PhotoIDPage;
import com.finstro.automation.pages.setup_information.ResidentialAddressPage;
import com.finstro.automation.pages.setup_information.SelectBusinessCardPage;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class MedicareTest extends MobileTestSetup {
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private BusinessDetailPage businessDetailPage;
	private SelectBusinessCardPage businessCardPage;
	private ResidentialAddressPage residentialAddressPage;
	private PhotoIDPage photoIDPage;
	private DriverLicensePage drivingLisencePage;
	private MedicarePage medicarePage;
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
		medicarePage = new MedicarePage(driver);
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
		drivingLisencePage.clickMedicare();
		assertTrue(medicarePage.isActive(),
				"Medicare screen is not  displayed ",
				"Medicare screen is displayed");
		
	}

	@Test
	public void FPC_1371_Verify_User_RedirectTo_Medicare_Screen_Successfully() throws Exception {
		finstroAPI.loginForAccessToken(Constant.LOGIN_EMAIL_ADDRESS, Constant.LOGIN_ACCESS_CODE);
		finstroAPI.getMedicareInfor();
		medicarePage.verifyMedicareInfor(
				Common.getTestVariable("firstName"),
				Common.getTestVariable("middleInitial"),
				Common.getTestVariable("surname"),
				Common.getTestVariable("gender").equalsIgnoreCase("M") ? "Male" : "Female",
				Common.getTestVariable("dateOfBirth"),
				Common.getTestVariable("cardColor"),
				Common.getTestVariable("cardNumber"),
				Common.getTestVariable("cardNumberRef"),
				Common.getTestVariable("validTo"));
	}
	
	@Test
	public void FPC_1374_LastName_Cannot_Be_Blank() throws Exception {
		medicarePage.inputLastName("");
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}
	
	@Test
	public void FPC_1376_DateOfBirth_Cannot_Be_Blank() throws Exception {
		medicarePage.inputDoB("");
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}
	
	@Test
	public void FPC_1378_MedicareNumber_Cannot_Be_Blank() throws Exception {
		medicarePage.inputMedicareNumber("");
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}
	
	@Test
	public void FPC_1379_ReferenceNumber_Cannot_Be_Blank() throws Exception {
		medicarePage.inputReferenceNumber("");
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}

	@Test
	public void FPC_1380_ExpireDate_Cannot_Be_Blank() throws Exception {
		medicarePage.inputExpireDate("");
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}
	
	@Test
	public void FPC_1383_MiddleInitial_Must_be_valid() throws Exception {
		medicarePage.inputMiddleName("AB");
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}
	
	@Test
	public void FPC_1384_DateOfBirth_Must_be_in_YYYY_MM_DD() throws Exception {
		medicarePage.inputDoB("01/13/2020");
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}
	
	@Test
	public void FPC_1385_MedicareNumber_Must_be_valid() throws Exception {
		String imvalidMedicareNumber = Common.randomNumeric(12);
		medicarePage.inputMedicareNumber(imvalidMedicareNumber);
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}
	
	@Test
	public void FPC_1386_Number_Must_be_valid() throws Exception {
		String invalidReferenceNumber = Common.randomNumeric(2);
		medicarePage.inputReferenceNumber(invalidReferenceNumber);
		medicarePage.clickNext();
		medicarePage.verifyErrorMessage("ERROR, Please complete all fields");
	}
	//2684483925
}
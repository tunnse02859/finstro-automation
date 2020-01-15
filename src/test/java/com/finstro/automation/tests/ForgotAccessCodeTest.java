package com.finstro.automation.tests;

import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.finstro.automation.pages.ForgotAccessCodePage;
import com.finstro.automation.pages.LoginPage;
import com.finstro.automation.pages.RegisterPage;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class ForgotAccessCodeTest extends MobileTestSetup {
	private ForgotAccessCodePage forgotAccessCodePage;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);
		forgotAccessCodePage = new ForgotAccessCodePage(driver);
		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");
		
		toForgotAccessCodePage();
	}

	public void toForgotAccessCodePage() throws Exception {
		registerPage.toLoginPage();
		assertTrue(loginPage.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after tap on login");
		loginPage.toForgotAccessCodePage();
		assertTrue(forgotAccessCodePage.isActive(),
				"Forgot access code screen didnt showed after tap on forget access code",
				"Forgot access code screen showed after click on forget access code");
	}

	@Test
	public void FPC_1312_Verify_User_Go_To_The_Login_Screen_Successfully() throws Exception {
		forgotAccessCodePage.clickCancel();
		assertTrue(loginPage.isActive(), "Login screen didnt showed after tap on login",
				"Login screen showed after tap on login");
	}

	@Test
	public void FPC_1311_Verify_Submit_Unsuccessul_If_Leave_Blank_All_Field() throws Exception {
		String expectedMessage = "ERROR - User not found.";
		
		forgotAccessCodePage.submit();
		forgotAccessCodePage.verifyPopupMessage(expectedMessage);
	}

	@Test
	public void FPC_1310_Verify_Submit_Unsuccessul_If_Email_Doesnot_Exist() throws Exception {
		String invalidEmail = Common.randomAlphaNumeric(15) + "@gmail.com";
		String expectedMessage = "ERROR - User not found.";
		
		forgotAccessCodePage.inputEmailAdress(invalidEmail);
		forgotAccessCodePage.submit();
		forgotAccessCodePage.verifyPopupMessage(expectedMessage);
	}

	@Test
	public void FPC_1309_Verify_Submit_Unsuccessul_If_Email_Invalid() throws Exception {
		String invalidEmail = Common.randomAlphaNumeric(15);
		String expectedMessage = "ERROR - User not found.";
		
		forgotAccessCodePage.inputEmailAdress(invalidEmail);
		forgotAccessCodePage.submit();
		forgotAccessCodePage.verifyPopupMessage(expectedMessage);
	}

	@Test
	public void FPC_1308_Verify_Submit_Unsuccessul_If_Mobile_Invalid() throws Exception {
		String invalidMobileNumber = Common.randomNumeric(15);
		String expectedMessage = "ERROR - Invalid phone number";
		
		forgotAccessCodePage.inputMobileNumber(invalidMobileNumber);
		forgotAccessCodePage.submit();
		forgotAccessCodePage.verifyPopupMessage(expectedMessage);
	}

	@Test
	public void FPC_1307_Verify_Submit_Unsuccessul_If_Email_and_MobileNumber_Are_Mismatched() throws Exception {
		String expectedMessage = "ERROR - Invalid phone number";
		
		String invalidMobileNumber = Common.randomNumeric(15);
		String invalidEmail = Common.randomAlphaNumeric(15) + "@" + Common.randomLowerAlpha(5) + ".com";
		
		// input valid Email and mismatch mobile number;
		forgotAccessCodePage.inputEmailAdress(Constant.LOGIN_EMAIL_ADDRESS);
		forgotAccessCodePage.inputMobileNumber(invalidMobileNumber);
		forgotAccessCodePage.submit();
		forgotAccessCodePage.verifyPopupMessage(expectedMessage);
		
		// input valid mobileNumber and mismatch Email address;
//		forgotAccessCodePage.inputEmailAdress(validEmail);
//		forgotAccessCodePage.inputMobileNumber(invalidMobileNumber);
//		forgotAccessCodePage.submit();
//		actualMessage = forgotAccessCodePage.getErrorMessage();
//		EXPECTED_MESSAGE = "ERROR - Invalid phone number";
//		assertEquals(EXPECTED_MESSAGE, actualMessage, "Error message isnt correct",
//				"Error message displayed correctly");

	}
	
//	@Test
//	public void FPC_1306_Verify_Submit_Sucessfully_With_Correct_Email() throws Exception {
//		
//		forgotAccessCodePage.inputEmailAdress(VALID_EMAIL);
//		forgotAccessCodePage.submit();
//
//		String actualMessage = forgotAccessCodePage.getPopupMessage();
//		String EXPECTED_MESSAGE = "SUCCESS - Access code successfully sent.";
//		assertEquals(EXPECTED_MESSAGE, actualMessage, "Success message isnt correct",
//				"Success message displayed correctly");
//		
//	}
	
//	@Test
//	public void FPC_1305_Verify_Submit_Sucessfully_With_Correct_MobileNumber() throws Exception {
//		forgotAccessCodePage.inputMobileNumber(VALID_MOBILE_NUMBER);
//		forgotAccessCodePage.submit();
//		
//		String actualMessage = forgotAccessCodePage.getPopupMessage();
//		String EXPECTED_MESSAGE = "SUCCESS - Access code successfully sent.";
//		assertEquals(EXPECTED_MESSAGE, actualMessage, "Success message isnt correct",
//				"Success message displayed correctly");
//	}
	
//	@Test
//	public void FPC_1304_Verify_Submit_Sucessfully_With_Correct_Email_and_MobileNumber() throws Exception {
//		forgotAccessCodePage.inputEmailAdress(VALID_EMAIL);
//		forgotAccessCodePage.inputMobileNumber(VALID_MOBILE_NUMBER);
//		forgotAccessCodePage.submit();
//		
//		String actualMessage = forgotAccessCodePage.getPopupMessage();
//		String EXPECTED_MESSAGE = "SUCCESS - Access code successfully sent.";
//		assertEquals(EXPECTED_MESSAGE, actualMessage, "Success message isnt correct",
//				"Success message displayed correctly");
//	}
}

package com.finstro.automation.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import static com.finstro.automation.utility.Assertion.*;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.finstro.automation.api.FinstroAPI;
import com.finstro.automation.api.ProfileInforAPI;
import com.finstro.automation.common.WorkFlows;
import com.finstro.automation.pages.contactus.ContactUsPage;
import com.finstro.automation.pages.login_process.LoginPage;
import com.finstro.automation.pages.login_process.RegisterPage;
import com.finstro.automation.pages.settings.profile.SettingProfile_DrivingLicensePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_MedicarePage;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;
import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.setup.DataGenerator;
import com.finstro.automation.setup.MobileTestSetup;
import com.finstro.automation.utility.Common;

public class ContactFinstroTests extends MobileTestSetup {

	private LoginPage loginPage;
	private RegisterPage registerPage;
	private ContactUsPage contactUsPage;

	@BeforeMethod
	public void setupPage(Method method) throws Exception {
		registerPage = new RegisterPage(driver);
		loginPage = new LoginPage(driver);

		assertTrue(registerPage.isActive(), "Register page didnt showed as default page in first installation",
				"Register page showed as default page");

		// Login
		loginPage.doSuccessLogin(Constant.NON_ONBOARDING_LOGIN_EMAIL_ADDRESS,
				Constant.NON_ONBOARDING_LOGIN_ACCESS_CODE);

	}

	@DataProvider(name = "ContactUs_SendMessageSuccessfully")
	public Object[][] getContactReason() {
		return new Object[][] { { "Finstro Card Problem" }, { "Incorrect Transaction" }, { "Login Problems" } };

	}

	@Test(dataProvider = "ContactUs_SendMessageSuccessfully")
	public void ContactUs_SendMessageSuccessfully(String reason) throws Exception {

		DataGenerator data = new DataGenerator();
		String description = data.generateRandomString(10);
		contactUsPage = WorkFlows.goToTheContactUsPage(driver);

		contactUsPage.selectContactReason(reason);
		contactUsPage.setDescription(description);

		// The word count displays
		int wordCount = contactUsPage.getCharacterCount();
		assertEquals(wordCount, description.length(), "The word count label displays wrong",
				"The word count label displays correctly : " + wordCount + " characters");

		// Send
		contactUsPage.submit();
		String status = contactUsPage.getSubmitStatus();
		assertTrue(status.contains("Message successfully sent."), "Send message failed!", "Send message successfully!");
		Log.info(status);

	}
	
	@Test
	public void ContactUs_MaxCharacterCheck() throws Exception {

		DataGenerator data = new DataGenerator();
		String description = data.generateRandomString(501);
		contactUsPage = WorkFlows.goToTheContactUsPage(driver);

		contactUsPage.selectContactReason("Login Problems");
		contactUsPage.setDescription(description);

		String status = contactUsPage.getSubmitStatus();
		assertTrue(status.contains("Max length is 500 characters"), "Max lenght not working as expected", "Max lenght working as expected");
		Log.info(status);

	}
	
	@Test
	public void ContactUs_CheckMandatoryFields() throws Exception {

		String reason = "Login Problems";
		contactUsPage = WorkFlows.goToTheContactUsPage(driver);

		/****** Check the Contact Reason field  ********/
		contactUsPage.submit();
		String status = contactUsPage.getSubmitStatus();
		assertTrue(status.contains("Please select a reason."), "The reason field is not mandatory", "The reason field is a mandatory field as expected");
		
		/****** Check the Description field  ********/
		contactUsPage.selectContactReason(reason);
		contactUsPage.submit();

		status = contactUsPage.getSubmitStatus();
		assertTrue(status.contains("Please enter description"), "The description field is not mandatory field", "The description field is a mandatory field as expected");

	}

}
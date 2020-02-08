package com.finstro.automation.pages.on_boarding;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DriverLicensePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(id="")
	private WebElement back;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	@iOSXCUITFindBy(iOSClassChain = "name BEGINSWITH 'ERROR'")
	private WebElement errorMessage;
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement errorType;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/verification_title")
	private WebElement title;
	
	@AndroidFindBy(id = "android:id/text1")
	private WebElement gender;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	private WebElement firstName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	private WebElement lastName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/middle_name_edt")
	private WebElement middleName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/state_edt")
	private WebElement state;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	private WebElement dob;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/driving_number_edt")
	private WebElement driverLicenseNumber;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/textinput_error")
	private WebElement driverLicenseError;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/expiry_edt")
	private WebElement expireDate;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	private WebElement next;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnMedicare\"))")
	private WebElement medicare;
	
	

	public DriverLicensePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void clickNext() throws Exception {
		driver.click(next);
	}
	
	public void clickMedicare() throws Exception {
		driver.click(medicare);
	}
	
	public void selectGender(String genderName) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.click(gender);
			By genderSelectorBy = MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.CheckedTextView\").text(\"" + genderName + "\")");
			WebElement genderSelector = driver.isElementPresented(genderSelectorBy, 5);
			assertNotNull(genderSelector,"selector gender = [" + genderName + "] is not displayed for select","selector gender = [" + genderName + "] is displayed for select");
			driver.click(genderSelector);
		}
	}
	
	public void inputFirstName(String firstNameString) throws Exception {
		driver.inputTextWithClear(firstName, firstNameString);
	}
	
	public void inputLastName(String lastNameString) throws Exception {
		driver.inputTextWithClear(lastName, lastNameString);
	}
	
	public void inputMiddleName(String middleNameString) throws Exception {
		driver.inputTextWithClear(middleName, middleNameString);
	}
	
	public void inputState(String stateName) throws Exception {
		driver.inputTextWithClear(state, stateName);
	}
	
	public void inputDoB(String dobString) throws Exception {
		//data must be dd/MM/YYYY
		driver.inputTextWithClear(dob, dobString);
	}
	
	public void inputLicenseNumber(String licenseNumberString) throws Exception {
		driver.inputTextWithClear(driverLicenseNumber, licenseNumberString);
	}
	
	public void inputExpireDate(String expireDateString) throws Exception {
		driver.inputTextWithClear(expireDate, expireDateString);
	}
	
	public void inputDriverLicenseInfor(String genderName, String firstNameString, String lastNameString, String middleNameString, String stateName, String dobString, String licenseNumberString, String expireDateString) throws Exception {
		selectGender(genderName);
		inputFirstName(firstNameString);
		inputLastName(lastNameString);
		inputMiddleName(middleNameString);
		inputState(stateName);
		inputDoB(dobString);
		inputLicenseNumber(licenseNumberString);
		inputExpireDate(expireDateString);
	}
	
	public void verifyDriverLicenseInfor(String genderName, String firstNameString, String lastNameString, String middleNameString, String stateName, String dobString, String licenseNumberString, String expireDateString) throws Exception {
		assertEquals(driver.getText(gender), genderName, "Gender is displayed incorrectly", "Gender is displayed correctly");
		assertEquals(driver.getText(firstName), firstNameString, "First Name is displayed incorrectly", "First Name is displayed correctly");
		assertEquals(driver.getText(lastName), lastNameString, "Last Name is displayed incorrectly", "Last Name is displayed correctly");
		assertEquals(driver.getText(middleName), middleNameString, "Middle Name is displayed incorrectly", "Middle Name is displayed correctly");
		assertEquals(driver.getText(state), stateName, "State is displayed incorrectly", "State is displayed correctly");
		assertEquals(driver.getText(dob), dobString, "Date of birth is displayed incorrectly", "Date of birth is displayed correctly");
		assertEquals(driver.getText(driverLicenseNumber), licenseNumberString, "License number is displayed incorrectly", "License number is displayed correctly");
		assertEquals(driver.getText(expireDate), expireDateString, "expire date is displayed incorrectly", "expire date is displayed correctly");
	}
	
	public void verifyErrorMessage(String expectedMessage) throws Exception {
		String actualMessage = "";
		if (driver.isAndroidDriver()) {
			actualMessage = driver.getText(errorType) + ", " + driver.getText(errorMessage);
		} else {
			actualMessage = driver.getText(errorMessage);
		}
		assertEquals(actualMessage, expectedMessage, "Error message is displayed incorrectly", "Error message is displayed correctly");
	}
	public void verifyDrivingLicenseError(String expectedMessage) throws Exception {
		assertEquals(driver.getText(driverLicenseError), expectedMessage, "License Number error message is displayed incorrectly", "License Number error message is displayed correctly");
	}
}

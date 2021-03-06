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
	@iOSXCUITFindBy(accessibility = "back")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'Error' || name contains 'Success'")
	private WebElement errorMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement errorType;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/verification_title")
	@iOSXCUITFindBy(iOSNsPredicate = "value = 'Driving Licence'")
	private WebElement title;

	@AndroidFindBy(id = "android:id/text1")
	@iOSXCUITFindBy(accessibility = "gender")
	private WebElement gender;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	@iOSXCUITFindBy(accessibility = "firstname")
	private WebElement firstName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	@iOSXCUITFindBy(accessibility = "lastname")
	private WebElement lastName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/middle_name_edt")
	@iOSXCUITFindBy(accessibility = "middlename")
	private WebElement middleName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/state_edt")
	@iOSXCUITFindBy(accessibility = "state")
	private WebElement state;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	@iOSXCUITFindBy(accessibility = "dob")
	private WebElement dob;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/driving_number_edt")
	@iOSXCUITFindBy(accessibility = "driving license number")
	private WebElement driverLicenseNumber;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/textinput_error")
	private WebElement driverLicenseError;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/expiry_edt")
	@iOSXCUITFindBy(accessibility = "expiry date")
	private WebElement expireDate;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	@iOSXCUITFindBy(accessibility = "next")
	private WebElement next;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnMedicare\"))")
	@iOSXCUITFindBy(accessibility = "medicare")
	private WebElement medicare;

	public DriverLicensePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public PostalAddressPage clickNext() throws Exception {
		driver.click(next);
		return new PostalAddressPage(driver);
	}

	public MedicarePage clickMedicare() throws Exception {
		driver.click(medicare);
		return new MedicarePage(driver);
	}

	public void selectGender(String genderName) throws Exception {
		if (driver.isAndroidDriver()) {
			driver.click(gender);
			By genderSelectorBy = MobileBy.AndroidUIAutomator(
					"new UiSelector().className(\"android.widget.CheckedTextView\").text(\"" + genderName + "\")");
			WebElement genderSelector = driver.isElementPresented(genderSelectorBy, 5);
			assertNotNull(genderSelector, "selector gender = [" + genderName + "] is not displayed for select",
					"selector gender = [" + genderName + "] is displayed for select");
			driver.click(genderSelector);
		} else {
			driver.click(gender);
			driver.selectPickerWheel(null, genderName,true);
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
		if (stateName.equalsIgnoreCase("ACT")) {
			stateName = "Australian Capital Territory";
		} else if (stateName.equalsIgnoreCase("NT")) {
			stateName = "Northern Territory";
		} else if (stateName.equalsIgnoreCase("NSW")) {
			stateName = "New South Wales";
		} else if (stateName.equalsIgnoreCase("QLD")) {
			stateName = "Queensland";
		} else if (stateName.equalsIgnoreCase("SA")) {
			stateName = "South Australia";
		} else if (stateName.equalsIgnoreCase("TAS")) {
			stateName = "Tasmania";
		} else if (stateName.equalsIgnoreCase("VIC")) {
			stateName = "Victoria";
		} else if (stateName.equalsIgnoreCase("WA")) {
			stateName = "Western Australia";
		}
		if (driver.isAndroidDriver()) {
			driver.selectItemFromSpinner(state, stateName);
		}else {
			driver.click(state);
			driver.selectPickerWheel(null, stateName,true);
		}
	}

	public void inputDoB(String dobString) throws Exception {
		// data must be dd/MM/YYYY
		driver.inputTextWithClear(dob, dobString);
	}

	public void inputLicenseNumber(String licenseNumberString) throws Exception {
		driver.inputTextWithClear(driverLicenseNumber, licenseNumberString);
	}

	public void inputExpireDate(String expireDateString) throws Exception {
		driver.inputTextWithClear(expireDate, expireDateString);
	}

	public void inputDriverLicenseInfor(String genderName, String firstNameString, String lastNameString,
			String middleNameString, String stateName, String dobString, String licenseNumberString,
			String expireDateString) throws Exception {
		selectGender(genderName);
		inputFirstName(firstNameString);
		inputLastName(lastNameString);
		inputMiddleName(middleNameString);
		inputState(stateName);
		// inputDoB(dobString);
		inputLicenseNumber(licenseNumberString);
		// inputExpireDate(expireDateString);
	}

	public void verifyDriverLicenseInfor(String genderName, String firstNameString, String lastNameString,
			String middleNameString, String stateName, String dobString, String licenseNumberString,
			String expireDateString) throws Exception {
		if (driver.isAndroidDriver()) {
			assertEquals(driver.getText(gender), genderName, "Gender is displayed incorrectly",
					"Gender is displayed correctly");
			assertEquals(driver.getText(state), stateName, "State is displayed incorrectly",
					"State is displayed correctly");
		} else if (driver.isIOSDriver()) {
			assertEquals(driver.getTextSelected(gender), genderName, "Gender is displayed incorrectly",
					"Gender is displayed correctly");
			assertEquals(driver.getTextSelected(state), stateName, "State is displayed incorrectly",
					"State is displayed correctly");
		}

		assertEquals(driver.getText(firstName), firstNameString, "First Name is displayed incorrectly",
				"First Name is displayed correctly");
		assertEquals(driver.getText(lastName), lastNameString, "Last Name is displayed incorrectly",
				"Last Name is displayed correctly");
		assertEquals(driver.getText(middleName), middleNameString, "Middle Name is displayed incorrectly",
				"Middle Name is displayed correctly");
		// assertEquals(driver.getText(dob), dobString, "Date of birth is displayed
		// incorrectly",
		// "Date of birth is displayed correctly");
		assertEquals(driver.getText(driverLicenseNumber), licenseNumberString,
				"License number is displayed incorrectly", "License number is displayed correctly");
		// assertEquals(driver.getText(expireDate), expireDateString, "expire date is
		// displayed incorrectly",
		// "expire date is displayed correctly");
	}

	public String getSubmitStatus() throws Exception {
		return driver.waitForTextElementPresent(errorMessage, 30);
	}

	public void verifyDrivingLicenseError(String expectedMessage) throws Exception {
		if (driver.isAndroidDriver())
			assertEquals(driver.getText(driverLicenseError), expectedMessage,
					"License Number error message is displayed incorrectly",
					"License Number error message is displayed correctly");
	}
	
	public String getDoB() throws Exception {
		return driver.getText(dob);
	}
	
	public String getExpireDate() throws Exception {
		return driver.getText(expireDate);
	}
}

package com.finstro.automation.pages.settings.profile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static com.finstro.automation.utility.Assertion.*;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingProfile_DrivingLicensePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"DRIVING LICENCE\"]")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'DRIVING LICENCE'")
	private WebElement title;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
	@iOSXCUITFindBy(accessibility = "saveSettings")
	private WebElement saveButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_icon")
	private WebElement backButton;

	@AndroidFindBy(id = "android:id/text1")
	@iOSXCUITFindBy(accessibility = "gender")
	private WebElement gender;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	@iOSXCUITFindBy(accessibility = "first name")
	private WebElement firstName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	@iOSXCUITFindBy(accessibility = "surname")
	private WebElement lastName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/middle_name_edt")
	@iOSXCUITFindBy(accessibility = "middle name")
	private WebElement middleName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/state_edt")
	@iOSXCUITFindBy(accessibility = "state")
	private WebElement state;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	@iOSXCUITFindBy(accessibility = "dob")
	private WebElement dob;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/driving_number_edt")
	@iOSXCUITFindBy(accessibility = "driver license number")
	private WebElement driverLicenseNumber;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/expiry_edt\"))")
	@iOSXCUITFindBy(accessibility = "expiry date")
	private WebElement expireDate;

	public SettingProfile_DrivingLicensePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void clickSaveSetting() throws Exception {
		driver.click(saveButton);
	}

	public SettingProfile_MedicarePage toSettingMedicarePage() throws Exception {
		// driver.swipe(DIRECTION.LEFT);
		driver.swipe(0.9, 0.5, 0.1, 0.5);
		driver.wait(2);
		return new SettingProfile_MedicarePage(driver);
	}

	public void inputDriverLicenseInfor(String genderName, String firstNameString, String lastNameString,
			String middleNameString, String stateName, String dobString, String licenseNumberString,
			String expireDateString) throws Exception {
		setGender(genderName);
		setFirstName(firstNameString);
		setLastName(lastNameString);
		setMiddleName(middleNameString);
		setState(stateName);
		setDriverLicenseNumber(licenseNumberString);
		// setDoB(dobString);
		// setExpireDate(expireDateString);
	}

	public void verifyDriverLicenseInfor(String genderName, String firstNameString, String lastNameString,
			String middleNameString, String stateName, String dobString, String licenseNumberString,
			String expireDateString) throws Exception {
		assertEquals(driver.getText(gender), genderName, "Gender is displayed incorrectly",
				"Gender is displayed correctly");
		assertEquals(driver.getText(state), stateName, "State is displayed incorrectly",
				"State is displayed correctly");
		// assertEquals(driver.getText(expireDate), expireDateString, "expire date is
		// displayed incorrectly",
		// "expire date is displayed correctly");
		// assertEquals(driver.getText(dob), dobString, "Date of birth is displayed
		// incorrectly",
		// "Date of birth is displayed correctly");
		assertEquals(driver.getText(firstName), firstNameString, "First Name is displayed incorrectly",
				"First Name is displayed correctly");
		assertEquals(driver.getText(lastName), lastNameString, "Last Name is displayed incorrectly",
				"Last Name is displayed correctly");
		assertEquals(driver.getText(middleName), middleNameString, "Middle Name is displayed incorrectly",
				"Middle Name is displayed correctly");
		assertEquals(driver.getText(driverLicenseNumber), licenseNumberString,
				"License number is displayed incorrectly", "License number is displayed correctly");
	}

	public String getGender() throws Exception {
		return driver.getText(gender);
	}

	public void setGender(String genderValue) throws Exception {
		if (driver.isAndroidDriver()) {
			driver.selectItemFromSpinner(gender, genderValue);
		} else {
			driver.click(gender);
			driver.selectPickerWheel(null, genderValue);
		}
	}

	public String getFirstName() throws Exception {
		return driver.getText(firstName);
	}

	public void setFirstName(String firstNameValue) throws Exception {
		driver.inputTextWithClear(firstName, firstNameValue);
	}

	public String getLastName() throws Exception {
		return driver.getText(lastName);
	}

	public void setLastName(String lastNameValue) throws Exception {
		driver.inputTextWithClear(lastName, lastNameValue);
	}

	public String getMiddleName() throws Exception {
		return driver.getText(middleName);
	}

	public void setMiddleName(String middleNameValue) throws Exception {
		driver.inputTextWithClear(middleName, middleNameValue);
		;
	}

	public String getState() throws Exception {
		return driver.getText(state);
	}

	public void setState(String stateValue) throws Exception {
		if (stateValue.equalsIgnoreCase("ACT")) {
			stateValue = "Australian Capital Territory";
		} else if (stateValue.equalsIgnoreCase("NT")) {
			stateValue = "Northern Terriories";
		} else if (stateValue.equalsIgnoreCase("NSW")) {
			stateValue = "New South Wales";
		} else if (stateValue.equalsIgnoreCase("QLD")) {
			stateValue = "Queensland";
		} else if (stateValue.equalsIgnoreCase("SA")) {
			stateValue = "South Australia";
		} else if (stateValue.equalsIgnoreCase("TAS")) {
			stateValue = "Tasmania";
		} else if (stateValue.equalsIgnoreCase("VIC")) {
			stateValue = "Victoria";
		} else if (stateValue.equalsIgnoreCase("WA")) {
			stateValue = "Western Australia";
		}
		if (driver.isAndroidDriver()) {
			driver.selectItemFromSpinner(state, stateValue);
		} else {
			driver.selectPickerWheel(state, stateValue);
		}
	}

	public void setDoB(String dobString) throws Exception {
		// data must be dd/MM/YYYY
		if (driver.isAndroidDriver()) {
			driver.inputTextWithClear(dob, dobString);
		}
	}

	public String getDob() throws Exception {
		return driver.getText(dob);
	}

	public String getDriverLicenseNumber() throws Exception {
		return driver.getText(driverLicenseNumber);
	}

	public void setDriverLicenseNumber(String driverLicenseNumberValue) throws Exception {
		driver.inputTextWithClear(driverLicenseNumber, driverLicenseNumberValue);
	}

	public String getExpireDate() throws Exception {
		return driver.getText(expireDate);
	}

	public void setExpireDate(String expireDateValue) throws Exception {
		driver.inputTextWithClear(expireDate, expireDateValue);
	}

}
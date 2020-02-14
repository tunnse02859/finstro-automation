package com.finstro.automation.pages.settings;

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

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PROFILE DETAILS\"]")
	@iOSXCUITFindBy(iOSNsPredicate="name = 'DRIVING LICENSE'")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
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


	@AndroidFindBy(id = "au.com.finstro.finstropay:id/expiry_edt")
	@iOSXCUITFindBy(accessibility = "expiry date")
	private WebElement expireDate;

	public SettingProfile_DrivingLicensePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}


	public void editAFieldDrivingLicenceInfor(String expectedText, String elementName) throws Exception {
		switch (elementName) {
		case "middleName":
			driver.inputTextWithClear(middleName, expectedText);
			break;
		default:
			break;
		}
	}
	
	public void clickSaveSetting() throws Exception {
		driver.click(saveButton);
	}

	public void verifyEditDrivingLicenceInforSuccesfully(String expectedText, String elementName) throws Exception {
		String actualValue = null;
		switch (elementName) {
		case "middleName":
			actualValue = driver.getText(middleName);
			break;
		default:
			break;
		}

		assertEquals(actualValue, expectedText, "elementName is not edited successfully",
				"elementName is edited successfully");
	}
	
	public SettingProfile_MedicarePage toSettingMedicarePage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		return new SettingProfile_MedicarePage(driver);
	}

	public String getGender() throws Exception {
		return driver.getText(gender);
	}

	public void setGender(String genderValue) throws Exception {
		driver.selectItemFromSpinner(gender, genderValue);
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
		driver.inputTextWithClear(middleName, middleNameValue);;
	}

	public String getState() throws Exception {
		return driver.getText(state);
	}

	public void setState(String stateValue) throws Exception {
		driver.selectItemFromSpinner(state, stateValue);
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
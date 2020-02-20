package com.finstro.automation.pages.settings.profile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static com.finstro.automation.utility.Assertion.*;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingProfile_ProfileDetailPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PROFILE DETAILS\"]")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'PROFILE DETAILS'")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	@iOSXCUITFindBy(accessibility = "first name")
	private WebElement firstName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	@iOSXCUITFindBy(accessibility = "last name")
	private WebElement lastName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/mobile_edt")
	@iOSXCUITFindBy(accessibility = "mobile number")
	private WebElement mobileNumber;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	@iOSXCUITFindBy(accessibility = "saveSettings")
	private WebElement saveButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_icon")
	private WebElement backButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/email_edt")
	@iOSXCUITFindBy(accessibility = "email address")
	private WebElement emailAddress;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	private WebElement dob;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_trading_address_spinner")
	private WebElement residential;

	public SettingProfile_ProfileDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void clickSaveSetting() throws Exception {
		driver.click(saveButton);
	}

	public void verifyProfileInfor(String firstNameString, String lastNameString, String emailAddressString,
			String mobileNumberString) throws Exception {
		assertEquals(driver.getText(firstName), firstNameString, "First Name is displayed incorrectly",
				"First Name is displayed correctly");
		assertEquals(driver.getText(lastName), lastNameString, "Last Name is displayed incorrectly",
				"Last Name is displayed correctly");
		assertEquals(driver.getText(emailAddress), emailAddressString, "Email address is displayed incorrectly",
				"Email address is displayed correctly");
		assertEquals(driver.getText(mobileNumber), mobileNumberString,
				"Mobile number is displayed incorrectly", "Mobile number is displayed correctly");
	}
	
	public void inputProfileInfor(String firstNameString, String lastNameString) throws Exception {
		setFirstName(firstNameString);
		setLastName(lastNameString);
	}

	public SettingProfile_DrivingLicensePage toSettingDrivingLicensePage() throws Exception {
		driver.swipe(DIRECTION.LEFT);

		Thread.sleep(1000);
		return new SettingProfile_DrivingLicensePage(driver);
	}

	public String getEmail() throws Exception {
		return driver.getText(emailAddress);
	}

	public String getPhoneNumber() throws Exception {
		return driver.getText(mobileNumber);
	}

	public String getFirstName() throws Exception {
		return driver.getText(firstName);
	}

	public String getLastName() throws Exception {
		return driver.getText(lastName);
	}

	public String getDOB() throws Exception {
		return driver.getText(dob);
	}

	public String getResidential() throws Exception {
		return driver.getText(residential);
	}

	public void setEmail(String email) throws Exception {
		driver.inputTextWithClear(emailAddress, email);
	}

	public void setPhoneNumber(String phoneValue) throws Exception {
		driver.inputTextWithClear(mobileNumber, phoneValue);
	}

	public void setFirstName(String firstname) throws Exception {
		driver.inputTextWithClear(firstName, firstname);
	}

	public void setLastName(String lastname) throws Exception {
		driver.inputTextWithClear(lastName, lastname);
	}

	public void setResidential(String residentialValue) throws Exception {
		driver.selectItemFromSpinner(residential, residentialValue);
	}

	public void saveChanges() throws Exception {
		driver.click(saveButton);
	}

	public SettingProfile_MedicarePage gotoSettingProfileMedicarePage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		driver.swipe(DIRECTION.LEFT);
		return new SettingProfile_MedicarePage(driver);
	}

}


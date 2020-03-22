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

	public SettingProfile_MedicarePage toSettingMedicarePage() throws Exception {
		// driver.swipe(DIRECTION.LEFT);
		driver.swipe(0.9, 0.5, 0.1, 0.5);
		driver.wait(2);
		return new SettingProfile_MedicarePage(driver);
	}

	public String getGender() throws Exception {
		String strGender = driver.getText(gender);
		if(strGender.equalsIgnoreCase("Male")) {
			return "M";
		}
		else if(strGender.equalsIgnoreCase("Female")) {
			return "F";
		}
		else {
			return "";
		}
	}


	public String getFirstName() throws Exception {
		return driver.getText(firstName);
	}


	public String getLastName() throws Exception {
		return driver.getText(lastName);
	}


	public String getMiddleName() throws Exception {
		return driver.getText(middleName);
	}


	public String getState() throws Exception {
		return driver.getText(state);
	}

	public String getDob() throws Exception {
		return driver.getText(dob).replace("/", "-");
	}

	public String getDriverLicenseNumber() throws Exception {
		return driver.getText(driverLicenseNumber);
	}

	public String getExpireDate() throws Exception {
		return driver.getText(expireDate).replace("/", "-");
	}


}
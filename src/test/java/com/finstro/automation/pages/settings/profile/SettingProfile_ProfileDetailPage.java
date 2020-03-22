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

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/email_edt")
	@iOSXCUITFindBy(accessibility = "email address")
	private WebElement emailAddress;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_trading_address_spinner")
	@iOSXCUITFindBy(accessibility = "residental address")
	private WebElement residential;

	public SettingProfile_ProfileDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
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

	public String getResidential() throws Exception {
		return driver.getText(residential).trim();
	}

	public SettingProfile_MedicarePage gotoSettingProfileMedicarePage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		driver.swipe(DIRECTION.LEFT);
		return new SettingProfile_MedicarePage(driver);
	}
	
	public SettingProfile_DrivingLicensePage toSettingDrivingLicensePage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		driver.wait(2);
		return new SettingProfile_DrivingLicensePage(driver);
	}

}


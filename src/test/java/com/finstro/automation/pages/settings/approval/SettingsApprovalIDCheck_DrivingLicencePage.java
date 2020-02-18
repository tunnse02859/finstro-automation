package com.finstro.automation.pages.settings.approval;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsApprovalIDCheck_DrivingLicencePage {

	private AppiumBaseDriver driver;
	
	// Main title
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/settings_title")
	private WebElement textTitle;
	
	// Gender
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/driver_licence_gender_spinner")
	private WebElement spinnerGender;
	
	// First name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	private WebElement txtFistName;
	
	// Middle name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/middle_name_edt")
	private WebElement txtMiddleName;
	
	// Last name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	private WebElement txtLastName;
	
	// Date of birth
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	private WebElement txtDOB;
	
	// State
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/state_edt")
	private WebElement txtState;
	
	// Driving licence number
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/driving_number_edt")
	private WebElement txtDrivingLicense;
	
	// Expiry date
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/expiry_edt")
	private WebElement txtExpirydate;
	
	// Save button
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement btnSave;
	
	
	public SettingsApprovalIDCheck_DrivingLicencePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle) && textTitle.getText().equalsIgnoreCase("DRIVING LICENCE");
	}
	
	public SettingsApprovalIDCheck_MedicarePage gotoMedicarePage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		return new SettingsApprovalIDCheck_MedicarePage(driver);
	}
	
	public SettingsApprovalIDCheck_ProfileDetailsPage gotoProfileDetailsPage() throws Exception {
		driver.swipe(DIRECTION.RIGHT);
		return new SettingsApprovalIDCheck_ProfileDetailsPage(driver);
	}
}

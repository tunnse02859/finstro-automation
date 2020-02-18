package com.finstro.automation.pages.settings.approval;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsApprovalIDCheck_MedicarePage {

	private AppiumBaseDriver driver;
	
	// Main title
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/settings_title")
	private WebElement textTitle;
	
	// First Name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	private WebElement txtFistName;
	
	// Middle Name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/middle_name_edt")
	private WebElement txtMiddleName;
	
	// Last Name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	private WebElement txtLastName;
	
	// Date of birth
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	private WebElement txtDOB;
	
	// Gender
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/driver_licence_gender_spinner")
	private WebElement spinnerGender;
	
	// Colour of card
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/medicare_card_color")
	private WebElement spinnerCardColour;
	
	// Medicare Number
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/medicare_number_edt")
	private WebElement txtMedicareNumber;
	
	// Reference Number
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/medicare_reference_edt")
	private WebElement txtReferenceNumber;
	
	// Expiry Date
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/expiry_edt")
	private WebElement txtExpiryDate;
	
	// Save button
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement btnSave;
	
	
	public SettingsApprovalIDCheck_MedicarePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle) && textTitle.getText().equalsIgnoreCase("MEDICARE");
	}
	
	public SettingsApprovalIDCheck_DrivingLicencePage gotoDrivingLicencePage() throws Exception {
		driver.swipe(DIRECTION.RIGHT);
		return new SettingsApprovalIDCheck_DrivingLicencePage(driver);
	}
	
}

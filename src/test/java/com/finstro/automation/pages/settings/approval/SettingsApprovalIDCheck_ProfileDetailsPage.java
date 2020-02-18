package com.finstro.automation.pages.settings.approval;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsApprovalIDCheck_ProfileDetailsPage {

	private AppiumBaseDriver driver;
	
	// Main title
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/settings_title")
	private WebElement textTitle;
	
	// Email
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/email_edt")
	private WebElement txtEmailAddress;
	
	// Mobile number
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/mobile_edt")
	private WebElement txtMobile;
	
	// First name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	private WebElement txtFistName;
	
	// Last name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	private WebElement txtLastName;
	
	// Date of birth
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	private WebElement txtDOB;
	
	// Residential Address
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_trading_address_spinner")
	private WebElement spinnerResidential;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/text1")
	private WebElement txtResidentialAddr;
	
	// Save button
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement btnSave;
	
	
	public SettingsApprovalIDCheck_ProfileDetailsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle) && textTitle.getText().equalsIgnoreCase("PROFILE DETAILS");
	}
	
	public SettingsApprovalIDCheck_DrivingLicencePage gotoDrivingLicencePage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		return new SettingsApprovalIDCheck_DrivingLicencePage(driver);
	}
	
	public void setEmailAddress(String email) throws Exception {
		driver.inputTextWithClear(txtEmailAddress, email);
	}
	
	public String getEmailAddress() throws Exception {
		return driver.getText(txtEmailAddress);
	}
	
	public void setMobileNumber(String mobile) throws Exception {
		driver.inputTextWithClear(txtMobile, mobile);
	}
	
	public String getMobileNumber() throws Exception {
		return driver.getText(txtMobile);
	}
	
	public void setFirstName(String firstname) throws Exception {
		driver.inputTextWithClear(txtFistName, firstname);
	}
	
	public String getFirstName() throws Exception {
		return driver.getText(txtFistName);
	}
	
	public void setLastName(String firstname) throws Exception {
		driver.inputTextWithClear(txtLastName, firstname);
	}
	
	public String getLastName() throws Exception {
		return driver.getText(txtLastName);
	}
	
	public void setDOB(String dob) throws Exception {
		driver.inputTextWithClear(txtDOB, dob);
	}
	
	public String getDOB() throws Exception {
		return driver.getText(txtDOB);
	}
	
	public String getResidentialAddress() throws Exception {
		return driver.getText(txtResidentialAddr);
	}
	
	public void saveChanges() throws Exception {
		driver.click(btnSave);
	}
	
}

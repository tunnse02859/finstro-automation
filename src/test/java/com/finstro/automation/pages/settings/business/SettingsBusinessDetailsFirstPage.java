package com.finstro.automation.pages.settings.business;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsBusinessDetailsFirstPage {

	private AppiumBaseDriver driver;

	// Main title
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/settings_title")
	private WebElement textTitle;

	// Category of Business
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/business_category\"))")
	private WebElement ddlCategory;

	@AndroidFindBy(id = "android:id/text1")
	private WebElement txtDisplayedCategory;

	// Email
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/email_edt\"))")
	private WebElement txtEmail;

	// Mobile Number
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/mobile_edt\"))")
	private WebElement txtMobile;

	// Website address
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/website_edt\"))")
	private WebElement txtWebsite;

	// Facebook
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/facebook_edt\"))")
	private WebElement txtFaceBook;

	// Twitter
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/twitter_edt\"))")
	private WebElement txtTwitter;

	// Instagram
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/instagram_edt\"))")
	private WebElement txtInstagram;

	// Skype
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/skype_edt\"))")
	private WebElement txtSkype;

	// LinkedIn
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/linkedin_edt\"))")
	private WebElement txtLinkedIn;

	// Other
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/other_edt\"))")
	private WebElement txtOther;

	// Submit button
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
	private WebElement btnSubmit;

	// Alert
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	private WebElement popupMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement popupType;

	public SettingsBusinessDetailsFirstPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(ddlCategory) && textTitle.getText().equalsIgnoreCase("BUSINESS DETAILS");
	}

	public void setCategoryOfBusiness(String category) throws Exception {
		driver.selectItemFromSpinner(ddlCategory, category);
	}

	public String getCategoryOfBusiness() throws Exception {
		return driver.getText(txtDisplayedCategory);
	}

	public void setEmail(String email) throws Exception {
		driver.inputTextWithClear(txtEmail, email);
	}

	public String getEmail() throws Exception {
		return driver.getText(txtEmail);
	}

	public void setMobileNumber(String mobile) throws Exception {
		driver.inputTextWithClear(txtMobile, mobile);
	}

	public String getMobileNumber() throws Exception {
		return driver.getText(txtMobile);
	}

	public void setWebsiteAddress(String website) throws Exception {
		driver.inputTextWithClear(txtWebsite, website);
	}

	public String getWebsiteAddress() throws Exception {
		return driver.getText(txtWebsite);
	}

	public void setFacebook(String facebook) throws Exception {
		driver.inputTextWithClear(txtFaceBook, facebook);
	}

	public String getFacebook() throws Exception {
		return driver.getText(txtFaceBook);
	}

	public void setTwitter(String twitter) throws Exception {
		driver.inputTextWithClear(txtTwitter, twitter);
	}

	public String getTwitter() throws Exception {
		return driver.getText(txtTwitter);
	}

	public void setInstagram(String instagram) throws Exception {
		driver.inputTextWithClear(txtInstagram, instagram);
	}

	public String getInstagram() throws Exception {
		return driver.getText(txtInstagram);
	}

	public void setSkype(String skype) throws Exception {
		driver.inputTextWithClear(txtSkype, skype);
	}

	public String getSkype() throws Exception {
		return driver.getText(txtSkype);
	}

	public void setLinkedin(String linkedin) throws Exception {
		driver.inputTextWithClear(txtLinkedIn, linkedin);
	}

	public String getLinkedin() throws Exception {
		return driver.getText(txtLinkedIn);
	}

	public void setOther(String other) throws Exception {
		driver.inputTextWithClear(txtOther, other);
	}

	public String getOther() throws Exception {
		return driver.getText(txtOther);
	}
	
	public void saveChanges() throws Exception {
		driver.click(btnSubmit);
	}

	public String getPopupMessage() throws Exception {
		return driver.getText(popupMessage);
	}
	
	public String getPopupActionType() throws Exception {
		return driver.getText(popupType);
	}
	
	public SettingsBusinessDetailsSecondPage gotoSettingsBusinessDetailsSecondPage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		return new SettingsBusinessDetailsSecondPage(driver);
	}
}

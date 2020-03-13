package com.finstro.automation.pages.settings.business;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.assertEquals;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsBusinessDetailsFirstPage {

	private AppiumBaseDriver driver;

	// Main title
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/settings_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'BUSINESS DETAILS'")
	private WebElement textTitle;

	// Category of Business
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/business_category\"))")
	@iOSXCUITFindBy(accessibility = "category of business")
	private WebElement ddlCategory;

	@AndroidFindBy(id = "android:id/text1")
	@iOSXCUITFindBy(accessibility = "category of business")
	private WebElement txtDisplayedCategory;

	// Email
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/email_edt\"))")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeScrollView[2]/**/XCUIElementTypeTextField[`name=\"email\"`]")
	private WebElement txtEmail;

	// Mobile Number
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/mobile_edt\"))")
	@iOSXCUITFindBy(accessibility = "mobile no")
	private WebElement txtMobile;

	// Website address
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/website_edt\"))")
	@iOSXCUITFindBy(accessibility = "website")
	private WebElement txtWebsite;

	// Facebook
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/facebook_edt\"))")
	@iOSXCUITFindBy(accessibility = "facebook")
	private WebElement txtFaceBook;

	// Twitter
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/twitter_edt\"))")
	@iOSXCUITFindBy(accessibility = "twitter")
	private WebElement txtTwitter;

	// Instagram
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/instagram_edt\"))")
	@iOSXCUITFindBy(accessibility = "instagram")
	private WebElement txtInstagram;

	// Skype
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/skype_edt\"))")
	@iOSXCUITFindBy(accessibility = "skype")
	private WebElement txtSkype;

	// LinkedIn
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/linkedin_edt\"))")
	@iOSXCUITFindBy(accessibility = "likedin")
	private WebElement txtLinkedIn;

	// Other
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/other_edt\"))")
	@iOSXCUITFindBy(accessibility = "other")
	private WebElement txtOther;

	// Submit button
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
	@iOSXCUITFindBy(accessibility = "save")
	private WebElement btnSubmit;

	// Alert
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	@iOSXCUITFindBy(accessibility = "save")
	private WebElement popupMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(accessibility = "email")
	private WebElement popupType;

	public SettingsBusinessDetailsFirstPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle);
	}

	public void verifyDisplayedData(String emailString, String mobileString, String websiteString,
			String facebookString, String twitterString, String instagramString, String skypeString,
			String linkedString, String otherString) throws Exception {

		// assertEquals(getEmail(), emailString, "Email is displayed incorrectly",
		// "Email is displayed correctly");
		assertEquals(getMobileNumber(), mobileString, "Mobile number is displayed incorrectly",
				"Mobile number is displayed correctly");
		assertEquals(getWebsiteAddress(), websiteString, "Website Address is displayed incorrectly",
				"Website Address is displayed correctly");
		assertEquals(getFacebook(), facebookString, "Facebook is displayed incorrectly",
				"Facebook is displayed correctly");
		assertEquals(getTwitter(), twitterString, "Twitter is displayed incorrectly", "Twitter is displayed correctly");
		assertEquals(getInstagram(), instagramString, "Instagram is displayed incorrectly",
				"Instagram is displayed correctly");
		assertEquals(getSkype(), skypeString, "Skype is displayed incorrectly", "Skype is displayed correctly");
		assertEquals(getLinkedin(), linkedString, "LinkedIn is displayed incorrectly",
				"LinkedIn is displayed correctly");
		assertEquals(getOther(), otherString, "Other is displayed incorrectly", "Other is displayed correctly");
	}

	public void setCategoryOfBusiness(String category) throws Exception {
		driver.scrollUntillViewText("CATEGORY OF BUSINESS");
		if (driver.isAndroidDriver()) {
			driver.selectItemFromSpinner(ddlCategory, category);
		}else {
			driver.click(ddlCategory);
			driver.selectPickerWheel(null, category);
		}
	}

	public String getCategoryOfBusiness() throws Exception {
		driver.scrollUntillViewText("CATEGORY OF BUSINESS");
		return driver.getText(txtDisplayedCategory);
	}

	public void setEmail(String email) throws Exception {
		driver.scrollUntillViewText("EMAIL");
		driver.inputTextWithClear(txtEmail, email);
	}

	public String getEmail() throws Exception {
		driver.scrollUntillViewText("EMAIL");
		return driver.getText(txtEmail);
	}

	public void setMobileNumber(String mobile) throws Exception {
		driver.scrollUntillViewText("MOBILE NO");
		driver.inputTextWithClear(txtMobile, mobile);
	}

	public String getMobileNumber() throws Exception {
		driver.scrollUntillViewText("MOBILE NO");
		return driver.getText(txtMobile);
	}

	public void setWebsiteAddress(String website) throws Exception {
		driver.scrollUntillViewText("WEBSITE ADDRESS");
		driver.inputTextWithClear(txtWebsite, website);
	}

	public String getWebsiteAddress() throws Exception {
		driver.scrollUntillViewText("WEBSITE ADDRESS");
		return driver.getText(txtWebsite);
	}

	public void setFacebook(String facebook) throws Exception {
		driver.scrollUntillViewText("FACEBOOK PAGE");
		driver.inputTextWithClear(txtFaceBook, facebook);
	}

	public String getFacebook() throws Exception {
		driver.scrollUntillViewText("FACEBOOK PAGE");
		return driver.getText(txtFaceBook);
	}

	public void setTwitter(String twitter) throws Exception {
		driver.scrollUntillViewText("TWITTER URL");
		driver.inputTextWithClear(txtTwitter, twitter);
	}

	public String getTwitter() throws Exception {
		driver.scrollUntillViewText("TWITTER URL");
		return driver.getText(txtTwitter);
	}

	public void setInstagram(String instagram) throws Exception {
		driver.scrollUntillViewText("INSTAGRAM URL");
		driver.inputTextWithClear(txtInstagram, instagram);
	}

	public String getInstagram() throws Exception {
		driver.scrollUntillViewText("INSTAGRAM URL");
		return driver.getText(txtInstagram);
	}

	public void setSkype(String skype) throws Exception {
		driver.scrollUntillViewText("SKYPE ADDRESS");
		driver.inputTextWithClear(txtSkype, skype);
	}

	public String getSkype() throws Exception {
		driver.scrollUntillViewText("SKYPE ADDRESS");
		return driver.getText(txtSkype);
	}

	public void setLinkedin(String linkedin) throws Exception {
		driver.scrollUntillViewText("LINKEDIN");
		driver.inputTextWithClear(txtLinkedIn, linkedin);
	}

	public String getLinkedin() throws Exception {
		driver.scrollUntillViewText("LINKEDIN");
		return driver.getText(txtLinkedIn);
	}

	public void setOther(String other) throws Exception {
		driver.scrollUntillViewText("OTHER");
		driver.inputTextWithClear(txtOther, other);
	}

	public String getOther() throws Exception {
		driver.scrollUntillViewText("OTHER");
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

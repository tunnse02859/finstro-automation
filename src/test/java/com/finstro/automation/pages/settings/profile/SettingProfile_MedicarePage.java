package com.finstro.automation.pages.settings.profile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static com.finstro.automation.utility.Assertion.*;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingProfile_MedicarePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"MEDICARE\"]")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'MEDICARE'")
	private WebElement title;


	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_icon")
	private WebElement backButton;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"au.com.finstro.finstropay:id/driver_licence_gender_spinner\").childSelector(new UiSelector().resourceId(\"android:id/text1\"))")
	@iOSXCUITFindBy(accessibility = "gender")
	private WebElement gender;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/first_name_edt\"))")
	@iOSXCUITFindBy(accessibility = "first name")
	private WebElement firstName;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/last_name_edt\"))")
	@iOSXCUITFindBy(accessibility = "surname")
	private WebElement lastName;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/middle_name_edt\"))")
	@iOSXCUITFindBy(accessibility = "middle initial")
	private WebElement middleName;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/dob_edt\"))")
	@iOSXCUITFindBy(accessibility = "dob")
	private WebElement dob;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_number_edt\"))")
	@iOSXCUITFindBy(accessibility = "medicare number")
	private WebElement medicareNumber;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_reference_edt\"))")
	@iOSXCUITFindBy(accessibility = "reference number")
	private WebElement referenceNumber;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_card_color\").childSelector(new UiSelector().resourceId(\"android:id/text1\")))")
	@iOSXCUITFindBy(accessibility = "colour of card")
	private WebElement cardColor;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/expiry_edt\"))")
	@iOSXCUITFindBy(accessibility = "expiry date")
	private WebElement expireDate;

	public SettingProfile_MedicarePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public String getFirstName() throws Exception {
		return driver.getText(firstName);
	}
	
	public String getMiddleName() throws Exception {
		return driver.getText(middleName);
	}
	
	public String getLastName() throws Exception {
		return driver.getText(lastName);
	}
	
	public String getGender() throws Exception {
		String strGender = driver.getText(gender);
		return strGender.equalsIgnoreCase("select")? "" : strGender;
	}
	
	public String getDOB() throws Exception {
		return driver.getText(dob).replace("/", "-");
	}
	
	public String getCardColor() throws Exception {
		String strColor = driver.getText(cardColor);
		return strColor.equalsIgnoreCase("select")? "" : strColor;
	}
	
	public String getMedicareNumber() throws Exception {
		return driver.getText(medicareNumber);
	}
	
	public String getReferenceNumber() throws Exception {
		return driver.getText(referenceNumber);
	}
	
	public String getExpiryDate() throws Exception {
		return driver.getText(expireDate).replace("/", "-");
	}
	

}
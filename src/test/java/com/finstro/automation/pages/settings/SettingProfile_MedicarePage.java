package com.finstro.automation.pages.settings;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static com.finstro.automation.utility.Assertion.*;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingProfile_MedicarePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PROFILE DETAILS\"]")
	@iOSXCUITFindBy(iOSNsPredicate="name = 'MEDICARE'")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	@iOSXCUITFindBy(accessibility = "saveSettings")
	private WebElement saveButton;

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
	
	public void editAFieldMedicareInfor(String expectedText, String elementName) throws Exception {
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

	public void verifyEditMedicareInforSuccesfully(String expectedText, String elementName) throws Exception {
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

}
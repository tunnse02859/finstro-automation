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
	@iOSXCUITFindBy(iOSNsPredicate="name = 'PROFILE DETAILS'")
	private WebElement title;
	
	//@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	@iOSXCUITFindBy(accessibility = "email address")
	private WebElement email;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	@iOSXCUITFindBy(accessibility = "first name")
	private WebElement firstName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	@iOSXCUITFindBy(accessibility = "last name")
	private WebElement lastName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	@iOSXCUITFindBy(accessibility = "mobile number")
	private WebElement mobileNumber;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	@iOSXCUITFindBy(accessibility = "saveSettings")
	private WebElement saveButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_icon")
	private WebElement backButton;


	public SettingProfile_ProfileDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void editAFieldOnProfileDetailPage(String expectedText, String elementName) throws Exception {
		switch (elementName) {
		case "lastName":
			driver.inputTextWithClear(lastName, expectedText);
			break;
		default:
			break;
		}
	}
	
	public void clickSaveSetting() throws Exception {
		driver.click(saveButton);
	}

	public void verifAFieldOnProfileDetailPageSuccesfully(String expectedText, String elementName) throws Exception {
		String actualValue = null;
		switch (elementName) {
		case "lastName":
			actualValue = driver.getText(lastName);
			break;
		default:
			break;
		}

		assertEquals(actualValue, expectedText, "lastName is not edited successfully",
				"lastName is edited successfully");
	}

	public SettingProfile_DrivingLicensePage toSettingDrivingLicensePage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
		return new SettingProfile_DrivingLicensePage(driver);
	}



}
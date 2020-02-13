package com.finstro.automation.pages.settings;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static com.finstro.automation.utility.Assertion.*;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SettingProfilePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PROFILE DETAILS\"]")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	private WebElement lastName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement saveButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_icon")
	private WebElement backButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/middle_name_edt")
	private WebElement middleName;

	public SettingProfilePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void editAFieldOnProfileDetailPage(String expectedText, String elementName) throws Exception {
		switch (elementName) {
		case "lastName":
			driver.inputText(lastName, expectedText);
			break;
		default:
			break;
		}

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

	public void swipeLeftPage() throws Exception {
		driver.swipe(DIRECTION.LEFT);
	}

	public void editAFieldDrivingLicenceInfor(String expectedText, String elementName) throws Exception {
		switch (elementName) {
		case "middleName":
			driver.inputText(middleName, expectedText);
			break;
		default:
			break;
		}
		driver.click(saveButton);
	}

	public void verifyEditDrivingLicenceInforSuccesfully(String expectedText, String elementName) throws Exception {
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

	
	public void editAFieldMedicareInfor(String expectedText, String elementName) throws Exception {
		switch (elementName) {
		case "middleName":
			driver.inputText(middleName, expectedText);
			break;
		default:
			break;
		}
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
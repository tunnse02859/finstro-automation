package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DriverLicensePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(id="")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/verification_title")
	private WebElement title;
	
	@AndroidFindBy(id = "android:id/text1")
	private WebElement gender;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	private WebElement firstName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	private WebElement lastName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/middle_name_edt")
	private WebElement middleName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/state_edt")
	private WebElement state;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	private WebElement dob;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/driving_number_edt")
	private WebElement driverLicenseNumber;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/expiry_edt")
	private WebElement expireDate;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	private WebElement next;
	
	

	public DriverLicensePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void clickNext() throws Exception {
		driver.click(next);
	}
	
	public void selectGender(String genderName) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.click(gender);
			By genderSelectorBy = MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.CheckedTextView\").text(\"" + genderName + "\")");
			WebElement genderSelector = driver.isElementPresented(genderSelectorBy, 5);
			assertNotNull(genderSelector,"selector gender = [" + genderName + "] is not displayed for select","selector gender = [" + genderName + "] is displayed for select");
			driver.click(genderSelector);
		}
	}
}

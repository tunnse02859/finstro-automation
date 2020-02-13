package com.finstro.automation.pages.settings;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/settings_title")
	private WebElement textTitle;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Business Details\")")
	private WebElement nvgBusinessDetails;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Profile Details\")")
	private WebElement nvgProfileDetails;
	
	public SettingsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle) && textTitle.getText().equalsIgnoreCase("SETTINGS");
	}
	
	public SettingsBusinessDetailsFirstPage gotoSettingsBusinessDetailsPage() throws Exception {
		driver.click(nvgBusinessDetails);
		return new SettingsBusinessDetailsFirstPage(driver);
	}
	
    public void goToProfileDetailsPage() throws Exception{
    	driver.click(nvgProfileDetails);
    }
}

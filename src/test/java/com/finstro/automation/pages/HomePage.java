package com.finstro.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage {
	
	private AppiumBaseDriver driver;
	
	@AndroidFindBy(id="au.com.finstro.finstropay:id/verification_title")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='We are here to help']")
    private WebElement title;
	
	@AndroidFindBy(id="au.com.finstro.finstropay:id/verification_subtitle")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='We are here to help']")
    private WebElement subTitle;
	
	public HomePage(AppiumBaseDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }
	
    public boolean isActive(){
        return (driver.waitForElementDisplayed(title, 20) && driver.waitForElementDisplayed(subTitle, 20));
    }
}

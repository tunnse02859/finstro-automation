package com.finstro.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage {
	
	private AppiumBaseDriver driver;
	
	@AndroidFindBy(id="au.com.finstro.finstropay:id/verification_title")
    private WebElement title;
	@AndroidFindBy(id="au.com.finstro.finstropay:id/verification_subtitle")
    private WebElement subTitle;
	
	public HomePage(AppiumBaseDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }
	
    public boolean isActive(){
        return (driver.waitForElementDisplayed(title, 10) && driver.waitForElementDisplayed(subTitle, 10));
    }
}

package com.finstro.automation.pages.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomeYearlyViewPage {
	private AppiumBaseDriver driver;

    @AndroidFindBy(id="au.com.finstro.finstropay:id/settings_title")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'Statements'")
    private WebElement title;

    public HomeYearlyViewPage(AppiumBaseDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public boolean isActive() throws Exception{
        return driver.isElementDisplayed(title);
    }
}

package com.finstro.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomeBalancePage {
	private AppiumBaseDriver driver;

    @AndroidFindBy(id="au.com.finstro.finstropay:id/settings_title")
    private WebElement title;

    public HomeBalancePage(AppiumBaseDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public boolean isActive() throws Exception{
        return driver.isElementDisplayed(title);
    }
}

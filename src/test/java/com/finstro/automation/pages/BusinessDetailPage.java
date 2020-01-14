package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BusinessDetailPage {

    private AppiumBaseDriver driver;

    @AndroidFindBy(id="au.com.finstro.finstropay:id/business_detail_title")
    @iOSXCUITFindBy(iOSNsPredicate="name = 'Business Details'")
    private WebElement title;

    public BusinessDetailPage(AppiumBaseDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public boolean isActive() throws Exception{
        return driver.isElementDisplayed(title);
    }
}

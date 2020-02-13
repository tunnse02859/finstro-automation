package com.finstro.automation.pages.home;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private AppiumBaseDriver driver;

    @AndroidFindBy(id="au.com.finstro.finstropay:id/business_detail_title")
    @iOSXCUITFindBy(iOSNsPredicate="name = 'Home'")
    private WebElement title;
    
    @AndroidFindBy(xpath="//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[1]")
    private WebElement yourNextBill;
    
    @AndroidFindBy(xpath="//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[2]")
    private WebElement balance;
    
    @AndroidFindBy(xpath="//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[3]")
    private WebElement yearlyView;
    
    public HomePage(AppiumBaseDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public boolean isActive() throws Exception{
        return driver.isElementDisplayed(title);
    }
    
    
    public void goToTheNextBillScreen() throws Exception{
    	driver.click(yourNextBill);
    }
    
    public void goToBalanceScreen() throws Exception{
    	driver.click(balance);
    }
    
    public void goToYearlyViewScreen() throws Exception{
    	driver.click(yearlyView);
    }
}

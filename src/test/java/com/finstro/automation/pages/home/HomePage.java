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
    @iOSXCUITFindBy(iOSNsPredicate="name = 'HOME'")
    private WebElement title;
    
    @AndroidFindBy(xpath="//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[1]")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name=\"fund\"`][1]/XCUIElementTypeStaticText[2]")
    private WebElement yourNextBill;
    
    @AndroidFindBy(xpath="//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[2]")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name=\"fund\"`][2]/XCUIElementTypeStaticText[2]")
    private WebElement balance;
    
    @AndroidFindBy(xpath="//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[3]")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name=\"fund\"`][3]/XCUIElementTypeStaticText[2]")
    private WebElement yearlyView;
    
    public HomePage(AppiumBaseDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public boolean isActive() throws Exception{
        return driver.isElementDisplayed(title);
    }
    
    
    public void goToTheNextBillScreen() throws Exception{
    	driver.clickByPosition(yourNextBill,"middle");
    }
    
    public void goToBalanceScreen() throws Exception{
    	driver.clickByPosition(balance,"middle");
    	driver.click(balance);
    }
    
    public void goToYearlyViewScreen() throws Exception{
    	driver.clickByPosition(yearlyView,"middle");
    }
}

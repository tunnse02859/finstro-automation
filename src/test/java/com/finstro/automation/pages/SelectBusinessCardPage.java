package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SelectBusinessCardPage {
	
    private AppiumBaseDriver driver;

    public SelectBusinessCardPage(AppiumBaseDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }
    
    @AndroidFindBy(id="au.com.finstro.finstropay:id/verification_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'We are here to help'")
    private WebElement title;
	
	@AndroidFindBy(id="au.com.finstro.finstropay:id/verification_subtitle")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Need a business card?'")
    private WebElement subTitle;

    
    @AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"$500\"))")
    @iOSXCUITFindBy(accessibility="$500")
    private WebElement card500;
    
    @AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"$1,000\"))")
    @iOSXCUITFindBy(accessibility="$1,000")
    private WebElement card1000;
    
    @AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"$5,000\"))")
    @iOSXCUITFindBy(accessibility="$5,000")
    private WebElement card5000;
    
    @AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"$10,000\"))")
    @iOSXCUITFindBy(accessibility="$10,000")
    private WebElement card10000;
    
    @AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"$25,000\"))")
    @iOSXCUITFindBy(accessibility="$25,000")
    private WebElement card25000;
    
    @AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"$50,000\"))")
    @iOSXCUITFindBy(accessibility="$50,000")
    private WebElement card50000;
    
    @AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"$500,000+\"))")
    @iOSXCUITFindBy(accessibility="$500,000+")
    private WebElement card500000;
    
    
    
    
    public boolean isActive() throws Exception{
        return (driver.isElementDisplayed(title) && driver.isElementDisplayed(subTitle));
    }
    
    

	public void clickOnCard(String money) throws Exception {
		switch (money) {
		case "500":
			driver.clickByPosition(card500, "middle");
			break;
		case "1000":
			driver.clickByPosition(card1000, "middle");
			break;
		case "5000":
			driver.clickByPosition(card5000, "middle");
			break;
		case "10000":
			driver.clickByPosition(card10000, "middle");
			break;
		case "25000":
			driver.clickByPosition(card25000, "middle");
			break;
		case "50000":
			driver.clickByPosition(card50000, "middle");
			break;
		case "500000":
			driver.clickByPosition(card500000, "middle");
			break;
		}
	}
}

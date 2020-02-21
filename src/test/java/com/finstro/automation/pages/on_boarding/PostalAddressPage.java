package com.finstro.automation.pages.on_boarding;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.report.HtmlReporter;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PostalAddressPage {

    private AppiumBaseDriver driver;
    
    @AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
    @iOSXCUITFindBy(accessibility = "back")
	private WebElement back;

    @AndroidFindBy(id="au.com.finstro.finstropay:id/postal_address_title")
    @iOSXCUITFindBy(iOSNsPredicate = "name = 'Postal Address'")
    private WebElement title;
    
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
    @iOSXCUITFindBy(accessibility = "next")
    private WebElement next;
    
    @AndroidFindBy(id="au.com.finstro.finstropay:id/residential_check_content")
    @iOSXCUITFindBy(accessibility = "residentialAddress")
    private WebElement residentialAddressContent;
    
    @AndroidFindBy(id="au.com.finstro.finstropay:id/residential_check")
    @iOSXCUITFindBy(accessibility = "residential address checkbox")
    private WebElement residentialAddressSelector;
    
    
    @AndroidFindBy(id="au.com.finstro.finstropay:id/business_check_content")
    @iOSXCUITFindBy(accessibility = "businessAddress")
    private WebElement businessAddressContent;
    
    @AndroidFindBy(id="au.com.finstro.finstropay:id/business_check")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage[`name=\"business address checkbox\"`][3]")
    private WebElement businessAddressSelector;
    
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage[`name=\"business address checkbox\"`][1]")
    private WebElement acceptTerm1;
    
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage[`name=\"business address checkbox\"`][2]")
    private WebElement acceptTerm2;
    
    
    @iOSXCUITFindBy(accessibility = "Don’t Allow")
	private WebElement address_dontallow;

    public PostalAddressPage(AppiumBaseDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public boolean isActive() throws Exception{
    	if (driver.isIOSDriver()) {
			if (driver.isElementDisplayed(address_dontallow)) {
				driver.click(address_dontallow);
			}
    	}
        return driver.isElementDisplayed(title);
    }
    
    public void acceptTerm() throws Exception {
    	if(driver.isIOSDriver()) {
    		driver.clickByPosition(acceptTerm1, "middle");
    		driver.clickByPosition(acceptTerm2, "middle");
    	}
    }
    
    public void verifyData(String businessTradingAddress, String residentialAddress,String postalAddress) throws Exception {
    	assertEquals(driver.getText(businessAddressContent).replace(",", "").trim(), businessTradingAddress, "Business Address is displayed incorrectly", "Business Address is displayed correctly");
    	assertEquals(driver.getText(residentialAddressContent).replace(",", "").trim(), residentialAddress, "Residential Address is displayed incorrectly", "Residential Address is displayed correctly");
    	
    	if(postalAddress.equals(businessTradingAddress)) {
    		assertTrue(driver.isElementEnabled(businessAddressSelector), "Business Address should be selected", "Business Address is selected correctly");
    	}else if(postalAddress.equals(residentialAddress)) {
    		assertTrue(driver.isElementEnabled(residentialAddressSelector), "Residential Address should be selected", "Residential Address is selected correctly");
    	}else {
    		assertTrue(false, "Postal Address does not matched with Business Address and Residential Address from API", "");
    	}
    }
    
    public void selectResidentialAddress() throws Exception {
    	driver.clickByPosition(residentialAddressSelector,"middle");
    }
    
    public void selectBusinessTradingAddress() throws Exception {
    	driver.clickByPosition(businessAddressSelector,"middle");
    }
    
	public void clickNext() throws Exception {
		acceptTerm();
		driver.click(next);
	}
	
	public void dontAllowNotification() throws Exception {
		if (driver.isElementDisplayed(address_dontallow)) {
			driver.click(address_dontallow);
			HtmlReporter.pass("Dont Allow Notification on IOS successfully");
		}
	}
}

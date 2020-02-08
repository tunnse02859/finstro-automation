package com.finstro.automation.pages.on_boarding;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PostalAddressPage {

    private AppiumBaseDriver driver;
    
    @AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	private WebElement back;

    @AndroidFindBy(id="au.com.finstro.finstropay:id/postal_address_title")
    private WebElement title;
    
    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
	private WebElement next;
    
    @AndroidFindBy(id="au.com.finstro.finstropay:id/residential_check_content")
    private WebElement residentialAddressContent;
    @AndroidFindBy(id="au.com.finstro.finstropay:id/residential_check")
    private WebElement residentialAddressSelector;
    
    @AndroidFindBy(id="au.com.finstro.finstropay:id/business_check_content")
    private WebElement businessAddressContent;
    @AndroidFindBy(id="au.com.finstro.finstropay:id/business_check")
    private WebElement businessAddressSelector;

    public PostalAddressPage(AppiumBaseDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }

    public boolean isActive() throws Exception{
        return driver.isElementDisplayed(title);
    }
    
    public void verifyData(String businessTradingAddress, String residentialAddress,String postalAddress) throws Exception {
    	assertEquals(driver.getText(businessAddressContent).replace(",", ""), businessTradingAddress, "Business Address is displayed incorrectly", "Business Address is displayed correctly");
    	assertEquals(driver.getText(residentialAddressContent).replace(",", ""), residentialAddress, "Residential Address is displayed incorrectly", "Residential Address is displayed correctly");
    	
    	if(postalAddress.equals(businessTradingAddress)) {
    		assertTrue(driver.isElementEnabled(businessAddressSelector), "Business Address should be selected", "Business Address is selected correctly");
    	}else if(postalAddress.equals(residentialAddress)) {
    		assertTrue(driver.isElementEnabled(residentialAddressSelector), "Residential Address should be selected", "Residential Address is selected correctly");
    	}else {
    		assertTrue(false, "Postal Address does not matched with Business Address and Residential Address from API", "");
    	}
    }
    
    public void selectResidentialAddress() throws Exception {
    	driver.click(residentialAddressSelector);
    }
    
    public void selectBusinessTradingAddress() throws Exception {
    	driver.click(businessAddressSelector);
    }
    
	public void clickNext() throws Exception {
		driver.click(next);
	}
}

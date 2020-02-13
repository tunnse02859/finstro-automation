package com.finstro.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;


public class SettingPage {

	   private AppiumBaseDriver driver;

	    @AndroidFindBy(id="au.com.finstro.finstropay:id/settings_title")
	    private WebElement title;
	    
	    @AndroidFindBy(xpath="//android.widget.TextView[@text=\"Profile Details\"]")
	    private WebElement profileDetail;
	    
	    @AndroidFindBy(id="au.com.finstro.finstropay:id/last_name_edt")
	    private WebElement lastName;

	    public SettingPage(AppiumBaseDriver driver){
	        this.driver = driver;
	        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	    }

		public boolean isActive() throws Exception{
	        return driver.isElementDisplayed(title);
	    }
	    
	    public void navigateToProfileDetailsPage() throws Exception{
	    	driver.click(profileDetail);
	    }
	    

}
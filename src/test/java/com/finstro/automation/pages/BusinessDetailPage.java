package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BusinessDetailPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id="au.com.finstro.finstropay:id/business_detail_title")
	@iOSXCUITFindBy(iOSNsPredicate="name = 'Business Details'")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnFindBusiness_text")
	private WebElement findYourBusinessButton;

	public BusinessDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception{
        	return driver.isElementDisplayed(title);
    	}

	public void redirectToTheFindYourBusinessScrees() throws Exception {
		driver.click(findYourBusinessButton);
	}
}

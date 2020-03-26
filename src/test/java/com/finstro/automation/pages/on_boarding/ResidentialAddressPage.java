package com.finstro.automation.pages.on_boarding;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.*;

public class ResidentialAddressPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(accessibility = "Back")
	private WebElement back;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_detail_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Residential Address'")
	private WebElement title;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnFindAddress_text")
	@iOSXCUITFindBy(accessibility = "residentialAdressSearch")
	private WebElement searchAddress;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/text1")
	@iOSXCUITFindBy(accessibility = "address")
	private WebElement residentialAddress;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	@iOSXCUITFindBy(accessibility = "next")
	private WebElement next;
	
	

	public ResidentialAddressPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return (driver.isElementDisplayed(title));
	}
	
	public FindAddressPage clickSearchAddress() throws Exception {
		driver.click(searchAddress);
		return new FindAddressPage(driver);
	}
	
	public String getResidentialAddress() throws Exception {
		return driver.getText(residentialAddress).replace(",", "").replace("  ", " ").trim();
	}
	
	public void verifyResidentialAddress(String expectedAddress) throws Exception {
		assertContains(getResidentialAddress(), expectedAddress, "Residential Address field's value doesnt match with expectation", "Residential Address field's value matched with expectation");
	}
	
	public PhotoIDPage clickNext() throws Exception {
		driver.click(next);
		return new PhotoIDPage(driver);
	}
	

}

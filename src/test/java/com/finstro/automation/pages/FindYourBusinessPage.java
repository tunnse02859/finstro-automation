package com.finstro.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FindYourBusinessPage {

	private AppiumBaseDriver driver;
	
	private String abnForCompanyType = "26 000 426 282";

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_title")
	private WebElement title;

	public FindYourBusinessPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.waitForElementDisplayed(title, 10);
	}
	

}
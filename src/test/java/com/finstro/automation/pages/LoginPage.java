package com.finstro.automation.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage {
	
	private AppiumBaseDriver driver;
	
	@AndroidFindBy(id="au.com.finstro.finstropay:id/first_name_edt")
	private WebElement firstName;

	public LoginPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}
	
	public  void setFirstName(String firstname) throws Exception {
		driver.inputText(firstName, firstname);
		driver.waitForTextElementPresent(firstName, 10);
		driver.waitForTextValueElementPresent(firstName, 10, firstname);
	}
}

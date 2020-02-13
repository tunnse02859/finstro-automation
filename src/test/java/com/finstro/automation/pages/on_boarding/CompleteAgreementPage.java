package com.finstro.automation.pages.on_boarding;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CompleteAgreementPage {
	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/forgot_access_title")
	private WebElement title;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/money_chart")
	private WebElement moneyChart;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/agreement_checkbox")
	private WebElement chkAgreement;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
	private WebElement btnSubmit;
	
	public CompleteAgreementPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(moneyChart);
	}
	
	public void confirmAgreement() throws Exception {
		this.driver.selectCheckBox(chkAgreement);
		this.driver.click(btnSubmit);
	}
}

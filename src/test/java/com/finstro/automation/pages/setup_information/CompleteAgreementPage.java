package com.finstro.automation.pages.setup_information;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CompleteAgreementPage {
	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/forgot_access_title")
	private WebElement title;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/forgot_access_subtitle")
	private WebElement subTitle;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/money_chart")
	private WebElement moneyChart;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/agreement_checkbox")
	private WebElement agreementCheckbox;
	
	@AndroidFindBy(xpath = "//android.widget.CheckBox[@resource-id=\"au.com.finstro.finstropay:id/agreement_checkbox\"]/following-sibling::android.widget.TextView")
	private WebElement agreementTextView;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnSubmit")
	private WebElement submitButton;
	
	@AndroidFindBy(xpath = "//android.widget.RelativeLayout[@resource-id=\"au.com.finstro.finstropay:id/btnSubmit\"]/preceding-sibling::android.widget.TextView")
	private WebElement discriptionTextView;
	
	
	
	public CompleteAgreementPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(moneyChart);
	}
	
	public boolean verifyCongratulationsPageLoadingDefault() throws Exception {
		boolean result = false;
		if (!driver.isElementDisplayed(title)) {
			result = false;
		} else {
			result = true;
		}
		
		if (!driver.isElementDisplayed(subTitle)) {
			result = false;
		} else {
			result = true;
		}
		
		if (!driver.isElementDisplayed(moneyChart)) {
			result = false;
		} else {
			result = true;
		}
		
		if (!driver.isElementDisplayed(agreementCheckbox)) {
			result = false;
		} else {
			result = true;
		}
		
		if (!driver.isElementDisplayed(agreementTextView)) {
			result = false;
		} else {
			result = true;
		}
		
		if (!driver.isElementDisplayed(discriptionTextView)) {
			result = false;
		} else {
			result = true;
		}
		
		if (!driver.isElementDisplayed(submitButton)) {
			result = false;
		} else {
			result = true;
		}
		
		return result;
	}
	
	public void confirmCongratulationPage() throws Exception {
		driver.click(agreementCheckbox);
		driver.isElementSelected(agreementCheckbox);
		driver.click(submitButton);
	}
	
}
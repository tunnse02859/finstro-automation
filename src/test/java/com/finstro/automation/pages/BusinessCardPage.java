package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BusinessCardPage {
	private AppiumBaseDriver driver;

	public BusinessCardPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/selected_amount_lv\"]//android.widget.TextView[@text=\"$500\"]")
	private WebElement card500;
	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/selected_amount_lv\"]//android.widget.TextView[@text=\"$1,000\"]")
	private WebElement card1000;
	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/selected_amount_lv\"]//android.widget.TextView[@text=\"$5,000\"]")
	private WebElement card5000;
	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/selected_amount_lv\"]//android.widget.TextView[@text=\"$10,000\"]")
	private WebElement card10000;
	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/selected_amount_lv\"]//android.widget.TextView[@text=\"$25,000\"]")
	private WebElement card25000;
	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/selected_amount_lv\"]//android.widget.TextView[@text=\"$50,000\"]")
	private WebElement card50000;
	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/selected_amount_lv\"]//android.widget.TextView[@text=\"$500,000+\"]")
	private WebElement card500000;

	public void ClickOnCard(String money) throws Exception {
		switch (money) {
		case "500":
			driver.click(card500);
			break;
		case "1000":
			driver.click(card1000);
			break;
		case "5000":
			driver.click(card5000);
			break;
		case "10000":
			driver.click(card10000);
			break;
		case "25000":
			driver.click(card25000);
			break;
		case "50000":
			driver.click(card50000);
			break;
		case "500000":
			driver.click(card500000);
			break;
		}

	}
}
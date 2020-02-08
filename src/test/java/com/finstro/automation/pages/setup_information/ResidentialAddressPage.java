package com.finstro.automation.pages.setup_information;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import static com.finstro.automation.utility.Assertion.*;

public class ResidentialAddressPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_detail_title")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnFindAddress_text")
	private WebElement searchAddress;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"au.com.finstro.finstropay:id/address_label\"]/following-sibling::android.widget.Spinner[@resource-id = \"au.com.finstro.finstropay:id/business_trading_address_spinner\"]//android.widget.TextView")
	private WebElement residentialAddress;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement next;

	public ResidentialAddressPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return (driver.isElementDisplayed(title) && driver.getText(title).equalsIgnoreCase("Residential Address"));
	}

	public void clickSearchAddress() throws Exception {
		driver.click(searchAddress);
	}

	public void verifyResidentialAddress(String expectedAddress) throws Exception {
		assertEquals(getCurrentResidentialAddress(), expectedAddress,
				"Residential Address field's value doesnt match with expectation",
				"Residential Address field's value matched with expectation");
	}

	public String getCurrentResidentialAddress() throws Exception {
		return driver.getText(residentialAddress);
	}

	public void clickNext() throws Exception {
		driver.click(next);
	}

}



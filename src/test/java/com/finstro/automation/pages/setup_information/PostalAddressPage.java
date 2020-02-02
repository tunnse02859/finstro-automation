package com.finstro.automation.pages.setup_information;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.assertEquals;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class PostalAddressPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/postal_address_title")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/postal_address_subtitle")
	private WebElement subTitle;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
	private WebElement next;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/residential_check_content")
	private WebElement residentialAddressContent;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/residential_check")
	private WebElement residentialAddressSelector;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/residential_check_title")
	private WebElement residentialAddressSelectorTitle;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_check_title")
	private WebElement businessAddressSelectorTitle;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_check_content")
	private WebElement businessAddressContent;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_check")
	private WebElement businessAddressSelector;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/postal_address_or")
	private WebElement orText;

	public PostalAddressPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void clickNext() throws Exception {
		driver.click(next);
	}

	public void verifyResidentialAddress(String expectedAddress) throws Exception {
		assertEquals(driver.getText(residentialAddressContent), expectedAddress,
				"Residential Address field's value doesnt match with expectation",
				"Residential Address field's value matched with expectation");
	}

	public void verifyBusinessAddress(String expectedAddress) throws Exception {
		assertEquals(driver.getText(businessAddressContent), expectedAddress,
				"Business Address field's value doesnt match with expectation",
				"Business Address field's value matched with expectation");
	}

	public boolean verifyAllElementsAreDisplayed() throws Exception {
		boolean result = false;
		if (!driver.isElementDisplayed(back)) {
			result = false;
		} else {
			result = true;
		}

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

		if (!driver.isElementDisplayed(next)) {
			result = false;
		} else {
			result = true;
		}

		if (!driver.isElementDisplayed(residentialAddressSelector)) {
			result = false;
		} else {
			result = true;
		}

		if (!driver.isElementDisplayed(residentialAddressContent)) {
			result = false;
		} else {
			result = true;
		}

		if (!driver.isElementDisplayed(businessAddressContent)) {
			result = false;
		} else {
			result = true;
		}

		if (!driver.isElementDisplayed(businessAddressSelector)) {
			result = false;
		} else {
			result = true;
		}

		if (!driver.isElementDisplayed(businessAddressSelectorTitle)) {
			result = false;
		} else {
			result = true;
		}

		if (!driver.isElementDisplayed(residentialAddressSelectorTitle)) {
			result = false;
		} else {
			result = true;
		}

		if (!driver.isElementDisplayed(orText)) {
			result = false;
		} else {
			result = true;
		}
		return result;

	}
}
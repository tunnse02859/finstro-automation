package com.finstro.automation.pages.on_boarding;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BusinessDetailPage {

	
	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_detail_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Business Details'")
	private WebElement title;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/abc_acn_value")
	@iOSXCUITFindBy(accessibility = "abnacn")
	private WebElement abn;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/legal_name_value")
	@iOSXCUITFindBy(accessibility = "legal name entity name")
	private WebElement entityName;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"au.com.finstro.finstropay:id/business_trading_name_spinner\").childSelector(new UiSelector().resourceId(\"android:id/text1\"))")
	@iOSXCUITFindBy(accessibility = "business trading name")
	private WebElement businessName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnFindBusiness_text")
	@iOSXCUITFindBy(accessibility = "findBusiness")
	private WebElement findYourBusinessButton;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
	@iOSXCUITFindBy(accessibility = "next")
	private WebElement next;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"au.com.finstro.finstropay:id/business_trading_address_spinner\").childSelector(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/text1\"))")
	@iOSXCUITFindBy(accessibility = "business trading address")
	private WebElement businessTradingAddress;

	public BusinessDetailPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void clickNext() throws Exception {
		driver.click(next);
	}
	
	public String getCurrentABN() throws Exception {
		return driver.getText(abn).replace(" ", "");
	}
	
	public void clickBusinessTradingAddress() throws Exception {
		driver.clickByPosition(businessTradingAddress,"middle");
	}
	
	public void selectBusinessName(String name) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.click(businessName);
			By genderSelectorBy = MobileBy.AndroidUIAutomator("new UiSelector().className(\"android.widget.CheckedTextView\").text(\"" + name + "\")");
			WebElement genderSelector = driver.isElementPresented(genderSelectorBy, 5);
			assertNotNull(genderSelector,"selector business name = [" + name + "] is not displayed for select","business name = [" + name + "] is displayed for select");
			driver.click(genderSelector);
		}
	}
	
	public void verifyBusinessName(String expectedName) throws Exception {
		assertEquals(driver.getText(businessName), expectedName, "Business Name field's value doesnt match with expectation", "Business Name field's value matched with expectation");
	}
	
	public void verifyBusinessTradingAddress(String expectedAddress) throws Exception {
		assertEquals(driver.getText(businessTradingAddress).replace(",", ""), expectedAddress, "Business Trading Address field's value doesnt match with expectation", "Business Trading Address field's value matched with expectation");
	}
	
	public void verifyBusinessData(String expectedABN, String expectedEntityName, String expectedBusinessName) throws Exception {
		assertEquals(driver.getText(abn).replace(" ", ""), expectedABN, "ABN/ACN is displayed incorrectly", "ABN/ACN is displayed correctly");
		assertEquals(driver.getText(entityName), expectedEntityName, "Entity name is displayed incorrectly", "Entity name is displayed correctly");
		assertEquals(driver.getText(businessName), expectedBusinessName, "Business name is displayed incorrectly", "Business name is displayed correctly");
	}

	public void clickFindBusiness() throws Exception {
		driver.click(findYourBusinessButton);
	}
}

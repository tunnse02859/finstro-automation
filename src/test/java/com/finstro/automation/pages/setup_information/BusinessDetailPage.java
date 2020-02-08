package com.finstro.automation.pages.setup_information;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BusinessDetailPage {

	
	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_detail_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Business Details'")
	private WebElement title;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/abc_acn_value")
	private WebElement abn;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/legal_name_value")
	private WebElement entityName;
	
	@AndroidFindBy(id = "android:id/text1")
	private WebElement businessName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnFindBusiness_text")
	private WebElement findYourBusinessButton;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/submit_text\"))")
	private WebElement next;

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
	
	public void verifyBusinessData(String expectedABN, String expectedEntityName, String expectedBusinessName) throws Exception {
		assertEquals(driver.getText(abn).replace(" ", ""), expectedABN, "ABN/ACN is displayed incorrectly", "ABN/ACN is displayed correctly");
		assertEquals(driver.getText(entityName), expectedEntityName, "Entity name is displayed incorrectly", "Entity name is displayed correctly");
		assertEquals(driver.getText(businessName), expectedBusinessName, "Business name is displayed incorrectly", "Business name is displayed correctly");
	}

	public void clickFindBusiness() throws Exception {
		driver.click(findYourBusinessButton);
	}
}

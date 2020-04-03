package com.finstro.automation.pages.on_boarding;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.*;

import java.util.List;

public class FindAddressPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(accessibility = "Back")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Search Address'")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_edit_text")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeSearchField")
	private WebElement searchAddress;

	@iOSXCUITFindBy(accessibility = "Search")
	private WebElement searchButton;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_name")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell/XCUIElementTypeStaticText[1]")
	private WebElement firstMatchTitle;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_abn_acn")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell/XCUIElementTypeStaticText[2]")
	private WebElement firstMatchInfo;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_no_result_title")
	@iOSXCUITFindBy(iOSNsPredicate = "value = 'Address not found'")
	private WebElement noResulthMatch;

	@iOSXCUITFindBy(className = "XCUIElementTypeCell")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"au.com.finstro.finstropay:id/address_result_list\").childSelector(new UiSelector().className(\"android.widget.RelativeLayout\"))")
	private List<MobileElement> searchedAddress;

	public FindAddressPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void inputSearchAddress(String addressInfor) throws Exception {
		driver.inputTextWithClear(searchAddress, addressInfor);
		if (driver.isIOSDriver()) {
			driver.click(searchButton);
		}
	}

	public void verifyFirstMatch(String expectedFirstMatchTitle, String expectedFirstMatchInfor) throws Exception {
		assertEquals(driver.getText(firstMatchTitle).trim(), expectedFirstMatchTitle,
				"First result title is displayed incorrectly", "First result title is displayed correctly");
		assertEquals(driver.getText(firstMatchInfo).trim(), expectedFirstMatchInfor,
				"First result infor is displayed incorrectly", "First result infor is displayed correctly");
	}

	public boolean isNoResultMatched() throws Exception {
		return driver.isElementDisplayed(noResulthMatch);
	}

	public void clickOnFirstMatched() throws Exception {
		if (searchedAddress.size() > 0) {
			searchedAddress.get(0).click();
			driver.wait(5);
			return;
		}
		throw new Exception("There is no results for the search criteria");
	}

}

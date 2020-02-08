package com.finstro.automation.pages.on_boarding;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import static com.finstro.automation.utility.Assertion.*;

public class FindAddressPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_title")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_edit_text")
	private WebElement searchAddress;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_name")
	private WebElement firstMatchTitle;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_abn_acn")
	private WebElement firstMatchInfo;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_no_result_title")
	private WebElement noResulthMatch;

	public FindAddressPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void inputSearchAddress(String addressInfor) throws Exception {
		driver.inputTextWithClear(searchAddress, addressInfor);
	}

	public void verifyFirstMatch(String expectedFirstMatchTitle, String expectedFirstMatchInfor) throws Exception {
		assertEquals(driver.getText(firstMatchTitle), expectedFirstMatchTitle,
				"First result title is displayed correctly", "First result title is displayed incorrectly");
		assertEquals(driver.getText(firstMatchInfo), expectedFirstMatchInfor,
				"First result infor is displayed correctly", "First result infor is displayed incorrectly");
	}

	public boolean isNoResultMatched() throws Exception {
		return driver.isElementDisplayed(noResulthMatch);
	}

	public void clickOnFirstMatched() throws Exception {
		driver.click(firstMatchTitle);
	}

}

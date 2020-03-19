package com.finstro.automation.pages.on_boarding;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.finstro.automation.appium.driver.AppiumBaseDriver;
import static com.finstro.automation.utility.Assertion.*;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class FindBusinessPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(accessibility = "Back")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Business Search'")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_edit_text")
	@iOSXCUITFindBy(accessibility = "Company Name or ABN/ACN")
	private WebElement searchBusiness;
	
	@iOSXCUITFindBy(accessibility = "Search")
	private WebElement searchButton;
	

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_name")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[1]")
	private WebElement firstMatchBusiniessName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_abn_acn")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[2]")
	private WebElement firstMatchBusinessABNACN;
	
	@iOSXCUITFindBy(className = "XCUIElementTypeCell")
	private List<MobileElement> searchedBusiness;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_no_result_title")
	private WebElement noResulthMatch;

	public FindBusinessPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void inputSearchBusiness(String addressInfor) throws Exception {
		driver.inputTextWithClear(searchBusiness, addressInfor);
		if(driver.isIOSDriver()) {
			driver.click(searchButton);
		}
	}

	public void verifyFirstMatch(String expectedFirstMatchTitle, String expectedFirstMatchInfor) throws Exception {
		assertEquals(driver.getText(firstMatchBusiniessName).trim(), expectedFirstMatchTitle,
				"First result title is displayed incorrectly", "First result title is displayed correctly");
		assertEquals(driver.getText(firstMatchBusinessABNACN).trim().replace(" ", ""), expectedFirstMatchInfor,
				"First result infor is displayed incorrectly", "First result infor is displayed correctly");
	}

	public boolean isNoResultMatched() throws Exception {
		if(driver.isAndroidDriver()){
			return driver.isElementDisplayed(noResulthMatch);
		}
		return true;
	}

	public void clickOnFirstMatched() throws Exception {
		if(searchedBusiness.size() > 0) {
			searchedBusiness.get(0).click();
			return;
		}
		
		throw new Exception("There is not results for the search criteria");
	}

}

package com.finstro.automation.pages.setup_information;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.finstro.automation.appium.driver.AppiumBaseDriver;
import static com.finstro.automation.utility.Assertion.*;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FindBusinessPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	private WebElement back;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_title")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_edit_text")
	private WebElement searchBusiness;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_name")
	private WebElement firstMatchBusiniessName;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_abn_acn")
	private WebElement firstMatchBusinessABNACN;

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
	}

	public void verifyFirstMatch(String expectedFirstMatchTitle, String expectedFirstMatchInfor) throws Exception {
		assertEquals(driver.getText(firstMatchBusiniessName), expectedFirstMatchTitle,
				"First result title is displayed imcorrectly", "First result title is displayed correctly");
		assertEquals(driver.getText(firstMatchBusinessABNACN), expectedFirstMatchInfor,
				"First result infor is displayed incorrectly", "First result infor is displayed correctly");
	}

	public boolean isNoResultMatched() throws Exception {
		return driver.isElementDisplayed(noResulthMatch);
	}

	public void clickOnFirstMatched() throws Exception {
		driver.click(firstMatchBusiniessName);
		Thread.sleep(3000);
	}

}

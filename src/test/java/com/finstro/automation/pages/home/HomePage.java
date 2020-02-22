package com.finstro.automation.pages.home;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import static com.finstro.automation.utility.Assertion.*;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/setting_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'HOME'")
	private WebElement title;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/navigation_cards")
	private WebElement cardTab;

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[1]")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name=\"fund\"`][1]/XCUIElementTypeStaticText[2]")
	private WebElement yourNextBill;

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[2]")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name=\"fund\"`][2]/XCUIElementTypeStaticText[2]")
	private WebElement balance;

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[3]")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[`name=\"fund\"`][3]/XCUIElementTypeStaticText[2]")
	private WebElement yearlyView;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/available_amount")
	private WebElement availableAmount;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/facility_limit")
	private WebElement limitAmount;

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[1]//android.widget.LinearLayout//android.widget.TextView[2]")
	private WebElement nextBillAmount;

	@AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@resource-id=\"au.com.finstro.finstropay:id/home_menu_lv\"]//android.widget.RelativeLayout[2]//android.widget.LinearLayout//android.widget.TextView[2]")
	private WebElement balanceAmount;

	public HomePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void goToTheNextBillScreen() throws Exception {
		driver.clickByPosition(yourNextBill, "middle");

	}

	public void goToBalanceScreen() throws Exception {
		driver.clickByPosition(balance, "middle");
		driver.click(balance);
	}

	public void goToYearlyViewScreen() throws Exception {
		driver.clickByPosition(yearlyView, "middle");
	}

	public String getAvailableNumber() throws Exception {
		return driver.getText(availableAmount).replace("$", "");

	}

	public String getLimitNumber() throws Exception {
		return driver.getText(limitAmount).replace("LIMIT = $", "");

	}

	public String getNextBillNumber() throws Exception {
		return driver.getText(nextBillAmount).replace("$", "");

	}

	public String getBalanceNumber() throws Exception {
		return driver.getText(balanceAmount).replace("$", "");

	}

	public void checkAvailableBalance() throws Exception {

		double actualAvailable = Double.parseDouble(getAvailableNumber());
		double expectedAvailable = Double.parseDouble(getLimitNumber()) - Double.parseDouble(getBalanceNumber());

		assertEquals(String.valueOf(actualAvailable), String.valueOf(expectedAvailable),
				"Available Balance # Account Limit - Account Balance",
				"Available Balance = Account Limit - Account Balance");

	}
	
	public void goToCardTab() throws Exception {
		driver.click(cardTab);
	}
}
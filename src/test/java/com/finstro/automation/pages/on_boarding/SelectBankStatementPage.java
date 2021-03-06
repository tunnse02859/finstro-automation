package com.finstro.automation.pages.on_boarding;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SelectBankStatementPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/toolbar_left_text\"))")
	@iOSXCUITFindBy(accessibility = "back")
	private WebElement back;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"bank-search\")")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name=\"BankStatements - Send Your Bank Statements Now\"`]/*/XCUIElementTypeTextField")
	private WebElement searchBox;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/bankstatement_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Banking Statement'")
	private WebElement title;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/androidx.viewpager.widget.ViewPager/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[1]/android.webkit.WebView/android.webkit.WebView/android.view.View[1]/android.view.View/android.view.View[2]/android.widget.ListView/android.view.View[5]/android.view.View")
	@iOSXCUITFindBy(accessibility = "Bank of Statements")
	private WebElement bankDemo;

	public SelectBankStatementPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void inputSearch(String text) throws Exception {
		driver.inputTextWithClear(searchBox, text);
		driver.wait(5);
	}

	public BankStatementDetailPage selectBankDemo() throws Exception {
		driver.clickByPosition(bankDemo, "middle");
		return new BankStatementDetailPage(driver);
	}
}

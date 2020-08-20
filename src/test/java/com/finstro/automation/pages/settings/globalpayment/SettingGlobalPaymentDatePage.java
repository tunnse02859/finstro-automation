package com.finstro.automation.pages.settings.globalpayment;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import static com.finstro.automation.utility.Assertion.*;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingGlobalPaymentDatePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PAYMENT DATE\"]")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'PAYMENT DATE'")
	private WebElement title;


	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_icon")
	private WebElement backButton;

	@iOSXCUITFindBy(accessibility = "weekly radio button")
	private WebElement btnWeekly;
	
	@iOSXCUITFindBy(accessibility = "fornightly radio button")
	private WebElement btnFortnightly;
	
	@iOSXCUITFindBy(accessibility = "monthy radio button")
	private WebElement btnMonthly;

	@iOSXCUITFindBy(accessibility = "set your payment day")
	private WebElement paymentDay;
	
	@iOSXCUITFindBy(accessibility = "save")
	private WebElement btnSave;

	public SettingGlobalPaymentDatePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void selectWeekly(boolean flag) throws Exception {
		driver.click(btnWeekly);
		System.out.println(btnWeekly.getAttribute("value"));
		System.out.println(btnFortnightly.getAttribute("value"));
		System.out.println(btnWeekly.isEnabled());
		System.out.println(btnFortnightly.isEnabled());
		System.out.println(btnWeekly.isSelected());
		System.out.println(btnFortnightly.isSelected());
	}
	

}
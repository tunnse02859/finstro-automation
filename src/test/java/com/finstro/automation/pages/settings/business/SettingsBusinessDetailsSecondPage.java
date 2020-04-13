package com.finstro.automation.pages.settings.business;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.appium.driver.AppiumBaseDriver.DIRECTION;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsBusinessDetailsSecondPage {

	private AppiumBaseDriver driver;

	// Main title
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/settings_title")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'BUSINESS DETAILS'")
	private WebElement textTitle;

	// Type of business
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_type_edt")
	@iOSXCUITFindBy(accessibility = "type of business")
	private WebElement txtBusinessType;

	// Trading business name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_name_edt")
	@iOSXCUITFindBy(accessibility = "trading business name")
	private WebElement txtBusinessName;

	// Trading legal name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/trading_name_edt")
	@iOSXCUITFindBy(accessibility = "trading legal name")
	private WebElement txtTradingName;

	// ABN
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/abn_edt")
	@iOSXCUITFindBy(accessibility = "abn")
	private WebElement txtABN;

	// ACN
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/acn_edt")
	@iOSXCUITFindBy(accessibility = "acn")
	private WebElement txtACN;
	
	// Business Address
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/business_address_edt\"))")
	@iOSXCUITFindBy(accessibility = "business address")
	private WebElement txtBusinessAddress;
	
	// Other name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/other_names_edt")
	@iOSXCUITFindBy(accessibility = "other names")
	private WebElement txtOtherName;
	
	// Incorporation
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_incorporation_edt")
	@iOSXCUITFindBy(accessibility = "incoporation")
	private WebElement txtIncorporation;
	
	// GST Date
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/gst_date_edt")
	@iOSXCUITFindBy(accessibility = "gst date")
	private WebElement txtGSTDate;
	
	// Time trading
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/time_trading_edt")
	@iOSXCUITFindBy(accessibility = "time trading")
	private WebElement txtTimeTrading;
	
	public SettingsBusinessDetailsSecondPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(textTitle);
	}


	public String getTypeOfBusiness() throws Exception {
		return driver.getText(txtBusinessType);
	}


	public String getTradingBusinessName() throws Exception {
		return driver.getText(txtBusinessName);
	}

	public String getTradingLegalName() throws Exception {
		return driver.getText(txtTradingName);
	}


	public String getABN() throws Exception {
		return driver.getText(txtABN);
	}


	public String getACN() throws Exception {
		return driver.getText(txtACN);
	}

	public String getBusinessAddress() throws Exception {
		return driver.getText(txtBusinessAddress).trim();
	}

	public String getOtherName() throws Exception {
		return driver.getText(txtOtherName);
	}

	public String getIncorporation() throws Exception {
		return driver.getText(txtIncorporation);
	}

	public String getGSTDate() throws Exception {
		return driver.getText(txtGSTDate);
	}

	public String getTimeTrading() throws Exception {
		return driver.getText(txtTimeTrading);
	}

	public SettingsBusinessDetailsFirstPage gotoSettingsBusinessDetailsFirstPage() throws Exception {
		driver.swipe(DIRECTION.RIGHT);
		return new SettingsBusinessDetailsFirstPage(driver);
	}
}

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
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_address_edt")
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
		return driver.getAttribute(txtBusinessType,"value");
	}


	public String getTradingBusinessName() throws Exception {
		return driver.getAttribute(txtBusinessName,"value");
	}

	public String getTradingLegalName() throws Exception {
		return driver.getAttribute(txtTradingName,"value");
	}


	public String getABN() throws Exception {
		return driver.getAttribute(txtABN,"value");
	}


	public String getACN() throws Exception {
		driver.getText(txtACN);
		return driver.getAttribute(txtACN,"value");
	}


	public String getBusinessAddress() throws Exception {
		return driver.getAttribute(txtBusinessAddress,"value");
	}

	public String getOtherName() throws Exception {
		return driver.getAttribute(txtOtherName,"value");
	}

	public String getIncorporation() throws Exception {
		return driver.getAttribute(txtIncorporation,"value");
	}

	public String getGSTDate() throws Exception {
		return driver.getAttribute(txtGSTDate,"value");
	}

	public String getTimeTrading() throws Exception {
		return driver.getAttribute(txtTimeTrading,"value");
	}

	public SettingsBusinessDetailsFirstPage gotoSettingsBusinessDetailsFirstPage() throws Exception {
		driver.swipe(DIRECTION.RIGHT);
		return new SettingsBusinessDetailsFirstPage(driver);
	}
}

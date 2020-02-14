package com.finstro.automation.pages.settings;

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
	private WebElement textTitle;

	// Type of business
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_type_edt")
	private WebElement txtBusinessType;

	// Trading business name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_name_edt")
	private WebElement txtBusinessName;

	// Trading legal name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/trading_name_edt")
	private WebElement txtTradingName;

	// ABN
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/abn_edt")
	private WebElement txtABN;

	// ACN
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/acn_edt")
	private WebElement txtACN;
	
	// Business Address
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_address_edt")
	private WebElement txtBusinessAddress;
	
	// Other name
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/other_names_edt")
	private WebElement txtOtherName;
	
	// Incorporation
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/business_incorporation_edt")
	private WebElement txtIncorporation;
	
	// GST Date
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/gst_date_edt")
	private WebElement txtGSTDate;
	
	// Time trading
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/time_trading_edt")
	private WebElement txtTimeTrading;
	
	public SettingsBusinessDetailsSecondPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(txtBusinessType) && textTitle.getText().equalsIgnoreCase("BUSINESS DETAILS");
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
		return driver.getText(txtBusinessAddress);
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

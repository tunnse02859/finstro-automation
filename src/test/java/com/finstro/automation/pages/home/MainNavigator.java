package com.finstro.automation.pages.home;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.cards.CardsPage;
import com.finstro.automation.pages.offers.OffersPage;
import com.finstro.automation.pages.settings.SettingsPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MainNavigator {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/navigation_home")
	private WebElement nvgHome;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/navigation_cards")
	private WebElement nvgCards;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/navigation_offers")
	private WebElement nvgOffers;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/navigation_settings")
	private WebElement nvgSettings;

	public MainNavigator(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}
	
	public boolean isActive() {
		return driver.isElementDisplayed(nvgHome) &&
				driver.isElementDisplayed(nvgCards) &&
				driver.isElementDisplayed(nvgOffers) &&
				driver.isElementDisplayed(nvgSettings);
	}

	public HomePage gotoHomePage() throws Exception {
		driver.click(nvgHome);
		return new HomePage(driver);
	}
	
	public CardsPage gotoCardsPage() throws Exception {
		driver.click(nvgHome);
		return new CardsPage(driver);
	}
	
	public OffersPage gotoOffersPage() throws Exception {
		driver.click(nvgHome);
		return new OffersPage(driver);
	}
	
	public SettingsPage gotoSettingsPage() throws Exception {
		driver.click(nvgSettings);
		return new SettingsPage(driver);
	}
}

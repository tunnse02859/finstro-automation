package com.finstro.automation.pages.cards;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class CardsPage {

	private AppiumBaseDriver driver;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/cards_title")
	private WebElement title;
	
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/activate_card")
	private WebElement inactiveCard;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/finstro_card_front")
	private WebElement activeCard;
	


	public CardsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}
	
	public void activeCard() throws Exception {
		driver.click(inactiveCard);
	}
	
	public boolean verifyCardActive() throws Exception {
		return driver.isElementDisplayed(activeCard);
	}
}

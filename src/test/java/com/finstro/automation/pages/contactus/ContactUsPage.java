package com.finstro.automation.pages.contactus;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.home.HomePage;
import com.finstro.automation.pages.on_boarding.SelectBusinessCardPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ContactUsPage {

	public AppiumBaseDriver driver;
	
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'CONTACT FINSTRO'")
	private WebElement textTitle;

	// Alert
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	private WebElement popupMessage;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'Error' || name contains 'Success' || name contains 'Warning'")
	private WebElement statusAlert;

	// Select reason for contact
	@iOSXCUITFindBy(accessibility = "reason for contact")
	private WebElement ddlContactReason;

	// Description label
	@iOSXCUITFindBy(iOSNsPredicate = "value = 'DESCRIPTION'")
	private WebElement lblDescription;
	
	// Description
	@iOSXCUITFindBy(accessibility = "description")
	private WebElement txtDescription;
	
	// Word count
	@iOSXCUITFindBy(xpath = "//*[@name='description']/following-sibling::XCUIElementTypeStaticText")
	private WebElement lblWordCount;
	
	// Max 500 characters
	@iOSXCUITFindBy(accessibility = "(Max 500 characters)")
	private WebElement lblMaxCharacters;
	
	// Submit button
	@iOSXCUITFindBy(xpath = "//*[@name='contact_icon']/following-sibling::*//*[@name='submit']")
	private WebElement btnSubmit;

	public ContactUsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() {
		return driver.isElementDisplayed(textTitle);
	}


	public void selectContactReason(String reason) throws Exception {
		if (driver.isAndroidDriver()) {
			driver.selectItemFromSpinner(ddlContactReason, reason);
		}else {
			driver.click(ddlContactReason);
			driver.clickByPosition(ddlContactReason, "middle");
			driver.selectPickerWheel(null, reason);
		}
	}
	
	public void setDescription(String description) throws Exception {
		driver.inputTextWithClear(txtDescription, description);
	}
	
	public void submit() throws Exception {
		driver.click(lblDescription);
		driver.wait(1);
		driver.click(btnSubmit);
	}
	
	public int getCharacterCount() throws Exception {
		Pattern p = Pattern.compile("\\d+");
        Matcher n = p.matcher(driver.getText(lblWordCount));
        if (n.find()) {
            return Integer.parseInt(n.group(0));
        }
		throw new Exception("Get word count failed!");
	}
	
	
	public String getSubmitStatus() throws Exception {
		driver.waitForElementDisplayed(statusAlert, 30);
		return driver.getText(statusAlert);
	}

}

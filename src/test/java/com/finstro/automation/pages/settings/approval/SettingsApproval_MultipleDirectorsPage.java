package com.finstro.automation.pages.settings.approval;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import java.util.List;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsApproval_MultipleDirectorsPage {

	private AppiumBaseDriver driver;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'MULTIPLE DIRECTORS'")
	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"MULTIPLE DIRECTORS\")")
	private WebElement title;

	@AndroidFindBy(xpath = "//android.widget.LinearLayout[@resource-id='au.com.finstro.finstropay:id/bank_info_holder']/android.widget.RelativeLayout")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell")
	private List<WebElement> bankAccountList;

	private By getDirectorCellElement(String directorNo) {
		return driver.isAndroidDriver() ? By.xpath("//android.widget.TextView[1]")
				: MobileBy
						.iOSClassChain("**/XCUIElementTypeCell[$type=='XCUIElementTypeStaticText' AND name BEGINSWITH \""
								+ directorNo + "\" AND name ENDSWITH \"DIRECTOR\"$]");
	}

	private By getDirectorDetailElement(String textValue) {
		return driver.isAndroidDriver() ? By.xpath("//android.widget.TextView[1]")
				: MobileBy.iOSClassChain("**/XCUIElementTypeTextField[`value == \"" + textValue + "\"`]");
	}

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Send'")
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/submit_text")
	private WebElement saveDirector;

	public SettingsApproval_MultipleDirectorsPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public SettingsApprovalBankUploadPage clickSubmitBankAccount() throws Exception {
		driver.click(saveDirector);
		return new SettingsApprovalBankUploadPage(driver);
	}

	public boolean isDirectorDisplayed(String directorNo, String directorName) {
		WebElement directorCell = driver.findElement(getDirectorCellElement(directorNo));
		WebElement directorDetail = driver.findChildElement(directorCell, getDirectorDetailElement(directorName));
		return true;
	}

}

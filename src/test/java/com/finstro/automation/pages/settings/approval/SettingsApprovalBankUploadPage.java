package com.finstro.automation.pages.settings.approval;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.pages.settings.business.SettingsBusinessDetailsFirstPage;
import com.finstro.automation.pages.settings.profile.SettingProfile_ProfileDetailPage;
import com.finstro.automation.utility.Assertion;
import static com.finstro.automation.utility.Assertion.*;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsApprovalBankUploadPage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Approval\")")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'APPROVAL'")
	private WebElement approveTitle;

	// ID CHECK
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"ID Check\"))")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[1]")
	private WebElement nvgIDCheck;

	public By getIdCheckError() {
		return driver.isIOSDriver()
				? MobileBy.iOSClassChain("**/XCUIElementTypeCell[1]/XCUIElementTypeImage[`name=\"badge_failed\"`]")
				: MobileBy.xpath(
						"//*[@resource-id='au.com.finstro.finstropay:id/check_result_lv']/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.ImageView[1]");
	}

	public By getIdCheckPass() {
		return driver.isIOSDriver()
				? MobileBy.iOSClassChain("**/XCUIElementTypeCell[1]/XCUIElementTypeImage[`name=\"badge_checkbox\"`]")
				: MobileBy.xpath(
						"//*[@resource-id='au.com.finstro.finstropay:id/check_result_lv']/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.ImageView[1]");
	}

	// BANK ACCOUNT
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Bank Accounts Connected\"))")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[2]")
	private WebElement nvgBankAccountsConnected;

	public By getBankAccountCheckError() {
		return driver.isIOSDriver()
				? MobileBy.iOSClassChain("**/XCUIElementTypeCell[2]/XCUIElementTypeImage[`name=\"badge_failed\"`]")
				: MobileBy.xpath(
						"//*[@resource-id='au.com.finstro.finstropay:id/check_result_lv']/android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.ImageView[1]");
	}

	public By getBankAccountCheckPass() {
		return driver.isIOSDriver()
				? MobileBy.iOSClassChain("**/XCUIElementTypeCell[2]/XCUIElementTypeImage[`name=\"badge_checkbox\"`]")
				: MobileBy.xpath(
						"//*[@resource-id='au.com.finstro.finstropay:id/check_result_lv']/android.widget.LinearLayout[2]/android.widget.RelativeLayout/android.widget.ImageView[1]");
	}

	// DIRECT DEBIT AUTHORITY
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Direct Debt Authority\"))")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[3]")
	private WebElement nvgDirectDebtAuthority;

	public By getDirectDebitCheckError() {
		return driver.isIOSDriver()
				? MobileBy.iOSClassChain("**/XCUIElementTypeCell[3]/XCUIElementTypeImage[`name=\"badge_failed\"`]")
				: MobileBy.xpath(
						"//*[@resource-id='au.com.finstro.finstropay:id/check_result_lv']/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.ImageView[1]");
	}

	public By getDirectDebitCheckPass() {
		return driver.isIOSDriver()
				? MobileBy.iOSClassChain("**/XCUIElementTypeCell[3]/XCUIElementTypeImage[`name=\"badge_checkbox\"`]")
				: MobileBy.xpath(
						"//*[@resource-id='au.com.finstro.finstropay:id/check_result_lv']/android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.ImageView[1]");
	}

	// CREDIT ASSESSMENT
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Credit Assessment\"))")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell[4]")
	private WebElement nvgCreditAssessment;

	public By getCreditAssessmentCheckError() {
		return driver.isIOSDriver()
				? MobileBy.iOSClassChain("**/XCUIElementTypeCell[4]/XCUIElementTypeImage[`name=\"badge_failed\"`]")
				: MobileBy.xpath(
						"//*[@resource-id='au.com.finstro.finstropay:id/check_result_lv']/android.widget.LinearLayout[4]/android.widget.RelativeLayout/android.widget.ImageView[1]");
	}

	public By getCreditAssessmentCheckPass() {
		return driver.isIOSDriver()
				? MobileBy.iOSClassChain("**/XCUIElementTypeCell[4]/XCUIElementTypeImage[`name=\"badge_checkbox\"`]")
				: MobileBy.xpath(
						"//*[@resource-id='au.com.finstro.finstropay:id/check_result_lv']/android.widget.LinearLayout[4]/android.widget.RelativeLayout/android.widget.ImageView[1]");
	}

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Facility Agreement\"))")
	private WebElement nvgFacilityAgreement;

	public SettingsApprovalBankUploadPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(approveTitle);
	}

	public SettingProfile_ProfileDetailPage gotoIDCheckPage() throws Exception {
		driver.clickByPosition(nvgIDCheck, "middle");
		return new SettingProfile_ProfileDetailPage(driver);
	}

	public SettingsApproval_BankAccountConnectedPage gotoBankAccountPage() throws Exception {
		driver.clickByPosition(nvgBankAccountsConnected, "middle");
		return new SettingsApproval_BankAccountConnectedPage(driver);
	}

	public void gotoDirectDebitPage() throws Exception {
		driver.clickByPosition(nvgDirectDebtAuthority, "middle");
	}

	public void verifyIDCheckStatusIsPass(String expectedStatusPass) throws Exception {
		if (!expectedStatusPass.equalsIgnoreCase("true")) {
			assertTrue(driver.findElementIgnoreError(getIdCheckError()) != null,
					"ID Check Status is displayed incorrectly, it shoud be pending",
					"ID Check Status is displayed correctly");
		} else {
			assertTrue(driver.findElementIgnoreError(getIdCheckPass()) != null,
					"ID Check Status is displayed incorrectly, it shoud be passed",
					"ID Check Status is displayed correctly");
		}
	}

	public void verifyBankAccountStatusIsPass(String expectedStatusPass) throws Exception {
		if (!expectedStatusPass.equalsIgnoreCase("true")) {
			assertTrue(driver.findElementIgnoreError(getBankAccountCheckError()) != null,
					"Bank Account Status is displayed incorrectly, it shoud be pending",
					"Bank Account Status is displayed correctly");
		} else {
			assertTrue(driver.findElementIgnoreError(getBankAccountCheckPass()) != null,
					"Bank Account Status is displayed incorrectly, it shoud be passed",
					"Bank Account Status is displayed correctly");
		}
	}

	public void verifyDirectDebitStatusIsPass(String expectedStatusPass) throws Exception {
		if (!expectedStatusPass.equalsIgnoreCase("true")) {
			assertTrue(driver.findElementIgnoreError(getDirectDebitCheckError()) != null,
					"Direct Debit Authority Status is displayed incorrectly, it shoud be pending",
					"Direct Debit Authority Status is displayed correctly");
		} else {
			assertTrue(driver.findElementIgnoreError(getDirectDebitCheckPass()) != null,
					"Direct Debit Authority Status is displayed incorrectly, it shoud be passed",
					"Direct Debit Authority Status is displayed correctly");
		}
	}

	public void verifyCreditAssessmentStatusIsPass(String expectedStatusPass) throws Exception {
		if (!expectedStatusPass.equalsIgnoreCase("true")) {
			assertTrue(driver.findElementIgnoreError(getCreditAssessmentCheckError()) != null,
					"Credit Assessment Status is displayed incorrectly, it shoud be pending",
					"Credit Assessment Status is displayed correctly");
		} else {
			assertTrue(driver.findElementIgnoreError(getCreditAssessmentCheckPass()) != null,
					"Credit Assessment Status is displayed incorrectly, it shoud be passed",
					"Credit Assessment Status is displayed correctly");
		}
	}

}

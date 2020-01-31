package com.finstro.automation.pages.setup_information;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import static com.finstro.automation.utility.Assertion.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MedicarePage {

	private AppiumBaseDriver driver;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/toolbar_left_text")
	@iOSXCUITFindBy(id="")
	private WebElement back;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	@iOSXCUITFindBy(iOSClassChain = "name BEGINSWITH 'ERROR'")
	private WebElement errorMessage;
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement errorType;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/verification_title")
	private WebElement title;
	
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"au.com.finstro.finstropay:id/driver_licence_gender_spinner\").childSelector(new UiSelector().resourceId(\"android:id/text1\"))")
	private WebElement gender;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/first_name_edt")
	private WebElement firstName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/last_name_edt")
	private WebElement lastName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/middle_name_edt")
	private WebElement middleName;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/dob_edt")
	private WebElement dob;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_number_edt\"))")
	private WebElement medicareNumber;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_reference_edt\"))")
	private WebElement referenceNumber;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_card_color\").childSelector(new UiSelector().resourceId(\"android:id/text1\")))")
	private WebElement cardColor;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/expiry_edt\"))")
	private WebElement expireDate;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	private WebElement next;
	
	

	public MedicarePage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void clickNext() throws Exception {
		driver.click(next);
	}
	
	public void selectGender(String genderName) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.click(gender);
			By genderSelectorBy = MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().className(\"android.widget.CheckedTextView\").text(\"" + genderName + "\"))");
			WebElement genderSelector = driver.isElementPresented(genderSelectorBy, 5);
			assertNotNull(genderSelector,"selector gender = [" + genderName + "] is not displayed for select","selector gender = [" + genderName + "] is displayed for select");
			driver.click(genderSelector);
		}
	}
	
	public void inputFirstName(String firstNameString) throws Exception {
		driver.inputTextWithClear(firstName, firstNameString);
	}
	
	public void inputLastName(String lastNameString) throws Exception {
		driver.inputTextWithClear(lastName, lastNameString);
	}
	
	public void inputMiddleName(String middleNameString) throws Exception {
		driver.inputTextWithClear(middleName, middleNameString);
	}
	
	public void selectCardColor(String colorName) throws Exception {
		if(driver.isAndroidDriver()) {
			driver.click(gender);
			By cardColorSelectorBy = MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().className(\"android.widget.CheckedTextView\").text(\"" + colorName + "\"))");
			WebElement cardColorSelector = driver.isElementPresented(cardColorSelectorBy, 5);
			assertNotNull(cardColorSelector,"selector color = [" + colorName + "] is not displayed for select","selector color = [" + colorName + "] is displayed for select");
			driver.click(cardColorSelector);
		}
	}
	
	public void inputDoB(String dobString) throws Exception {
		//data must be dd/MM/YYYY
		driver.inputTextWithClear(dob, dobString);
	}
	
	public void inputMedicareNumber(String medicareNumberString) throws Exception {
		driver.inputTextWithClear(medicareNumber, medicareNumberString);
	}
	
	public void inputReferenceNumber(String referenceNumberString) throws Exception {
		driver.inputTextWithClear(referenceNumber, referenceNumberString);
	}
	
	public void inputExpireDate(String expireDateString) throws Exception {
		driver.inputTextWithClear(expireDate, expireDateString);
	}
	
	public void inputMedicareInfor(String firstNameString, String middleNameString, String lastNameString, String genderName, String dobString, String cardColor, String medicareNumberString, String referenceNumberString, String expireDateString) throws Exception {	
		inputFirstName(firstNameString);
		inputMiddleName(middleNameString);
		inputLastName(lastNameString);
		selectGender(genderName);
		inputDoB(dobString);
		selectCardColor(cardColor);
		inputMedicareNumber(medicareNumberString);
		inputReferenceNumber(referenceNumberString);
		inputExpireDate(expireDateString);
	}
	
	public void verifyMedicareInfor(String firstNameString,String middleNameString, String lastNameString, String genderName, String dobString, String cardColorString, String medicareNumberString, String referenceNumberString, String expireDateString) throws Exception {
		assertEquals(driver.getText(firstName), firstNameString, "First Name is displayed incorrectly", "First Name is displayed correctly");
		assertEquals(driver.getText(middleName), middleNameString, "Middle Name is displayed incorrectly", "Middle Name is displayed correctly");
		assertEquals(driver.getText(lastName), lastNameString, "Last Name is displayed incorrectly", "Last Name is displayed correctly");
		assertEquals(driver.getText(gender), genderName, "Gender is displayed incorrectly", "Gender is displayed correctly");
		//assertEquals(driver.getText(dob), dobString, "Date of birth is displayed incorrectly", "Date of birth is displayed correctly");
		if(cardColorString.equalsIgnoreCase("G")) {
			cardColorString = "Green";
		}else if(cardColorString.equalsIgnoreCase("B")) {
			cardColorString = "Blue";
		}else if(cardColorString.equalsIgnoreCase("Y")) {
			cardColorString = "Yellow";
		}
		assertEquals(driver.getText(cardColor), cardColorString, "Card color is displayed incorrectly", "Card color is displayed correctly");
		assertEquals(driver.getText(medicareNumber), medicareNumberString, "Medicare number is displayed incorrectly", "Medicare number is displayed correctly");
		assertEquals(driver.getText(referenceNumber), referenceNumberString, "Reference number is displayed incorrectly", "Reference number is displayed correctly");
		assertEquals(driver.getText(expireDate), expireDateString, "expire date is displayed incorrectly", "expire date is displayed correctly");
	}
	
	public void verifyErrorMessage(String expectedMessage) throws Exception {
		String actualMessage = "";
		if (driver.isAndroidDriver()) {
			actualMessage = driver.getText(errorType) + ", " + driver.getText(errorMessage);
		} else {
			actualMessage = driver.getText(errorMessage);
		}
		assertEquals(actualMessage, expectedMessage, "Error message is displayed incorrectly", "Error message is displayed correctly");
	}
}

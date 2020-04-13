package com.finstro.automation.pages.on_boarding;

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

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/toolbar_left_text\"))")
	@iOSXCUITFindBy(accessibility = "Cancel")
	private WebElement cancel;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
	@iOSXCUITFindBy(iOSNsPredicate = "name contains 'Error' || name contains 'Success'")
	private WebElement errorMessage;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/verification_title\"))")
	@iOSXCUITFindBy(iOSNsPredicate = "value = 'Medicare'")
	private WebElement title;

	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"au.com.finstro.finstropay:id/driver_licence_gender_spinner\").childSelector(new UiSelector().resourceId(\"android:id/text1\"))")
	@iOSXCUITFindBy(accessibility = "gender")
	private WebElement gender;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/first_name_edt\"))")
	@iOSXCUITFindBy(accessibility = "firstname")
	private WebElement firstName;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/last_name_edt\"))")
	@iOSXCUITFindBy(accessibility = "lastname")
	private WebElement lastName;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/middle_name_edt\"))")
	@iOSXCUITFindBy(accessibility = "middle initial")
	private WebElement middleName;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/dob_edt\"))")
	@iOSXCUITFindBy(accessibility = "dob")
	private WebElement dob;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_number_edt\"))")
	@iOSXCUITFindBy(accessibility = "medicare number")
	private WebElement medicareNumber;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_reference_edt\"))")
	@iOSXCUITFindBy(accessibility = "reference number")
	private WebElement referenceNumber;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/medicare_card_color\").childSelector(new UiSelector().resourceId(\"android:id/text1\")))")
	@iOSXCUITFindBy(accessibility = "color of card")
	private WebElement cardColor;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/expiry_edt\"))")
	@iOSXCUITFindBy(accessibility = "expiry date")
	private WebElement expireDate;

	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
	@iOSXCUITFindBy(accessibility = "next")
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

	public void clickCancel() throws Exception {
		driver.click(cancel);
	}

	public void selectGender(String genderName) throws Exception {
		if (driver.isAndroidDriver()) {
			driver.click(gender);
			By genderSelectorBy = MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().className(\"android.widget.CheckedTextView\").text(\""
							+ genderName + "\"))");
			WebElement genderSelector = driver.isElementPresented(genderSelectorBy, 5);
			assertNotNull(genderSelector, "selector gender = [" + genderName + "] is not displayed for select",
					"selector gender = [" + genderName + "] is displayed for select");
			driver.click(genderSelector);
		} else {
			driver.click(gender);
			driver.selectPickerWheel(null, genderName, true);
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
		if (driver.isAndroidDriver()) {
			driver.click(cardColor);
			By cardColorSelectorBy = MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().className(\"android.widget.CheckedTextView\").text(\""
							+ colorName + "\"))");
			WebElement cardColorSelector = driver.isElementPresented(cardColorSelectorBy, 5);
			assertNotNull(cardColorSelector, "selector color = [" + colorName + "] is not displayed for select",
					"selector color = [" + colorName + "] is displayed for select");
			driver.click(cardColorSelector);
		} else {
			driver.click(cardColor);
			driver.selectPickerWheel(null, colorName, true);
		}
	}

	public void inputDoB(String dobString) throws Exception {
		// data must be dd/MM/YYYY
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

	public void inputMedicareInfor(String firstNameString, String middleNameString, String lastNameString,
			String genderName, String dobString, String cardColor, String medicareNumberString,
			String referenceNumberString, String expireDateString) throws Exception {
		inputFirstName(firstNameString);
		inputMiddleName(middleNameString);
		inputLastName(lastNameString);
		selectGender(genderName);
		// inputDoB(dobString);
		selectCardColor(cardColor);
		inputMedicareNumber(medicareNumberString);
		inputReferenceNumber(referenceNumberString);
		// inputExpireDate(expireDateString);
	}

	public void verifyMedicareInfor(String firstNameString, String middleNameString, String lastNameString,
			String genderName, String dobString, String cardColorString, String medicareNumberString,
			String referenceNumberString, String expireDateString) throws Exception {
		assertEquals(driver.getText(firstName), firstNameString, "First Name is displayed incorrectly",
				"First Name is displayed correctly");
		assertEquals(driver.getText(middleName), middleNameString, "Middle Name is displayed incorrectly",
				"Middle Name is displayed correctly");
		assertEquals(driver.getText(lastName), lastNameString, "Last Name is displayed incorrectly",
				"Last Name is displayed correctly");
		// assertEquals(driver.getText(dob), dobString, "Date of birth is displayed
		// incorrectly", "Date of birth is displayed correctly");
		if (cardColorString.equalsIgnoreCase("G")) {
			cardColorString = "Green";
		} else if (cardColorString.equalsIgnoreCase("B")) {
			cardColorString = "Blue";
		} else if (cardColorString.equalsIgnoreCase("Y")) {
			cardColorString = "Yellow";
		}
		if (driver.isAndroidDriver()) {
			assertEquals(driver.getText(gender), genderName, "Gender is displayed incorrectly",
					"Gender is displayed correctly");
			assertEquals(driver.getText(cardColor), cardColorString, "Card color is displayed incorrectly",
					"Card color is displayed correctly");
		} else if (driver.isIOSDriver()) {
			System.out.println(driver.getText(gender));
			System.out.println(driver.getAttribute(gender, "value"));
			System.out.println(driver.getAttribute(gender, "label"));
			assertEquals(driver.getTextSelected(gender), genderName, "Gender is displayed incorrectly",
					"Gender is displayed correctly");
			assertEquals(driver.getTextSelected(cardColor), cardColorString, "Card color is displayed incorrectly",
					"Card color is displayed correctly");
		}
		assertEquals(driver.getText(medicareNumber), medicareNumberString, "Medicare number is displayed incorrectly",
				"Medicare number is displayed correctly");
		assertEquals(driver.getText(referenceNumber), referenceNumberString,
				"Reference number is displayed incorrectly", "Reference number is displayed correctly");
		assertEquals(driver.getText(expireDate), expireDateString, "expire date is displayed incorrectly",
				"expire date is displayed correctly");
	}

	public String getSubmitStatus() throws Exception {
		return driver.getText(errorMessage);
	}
	
	public String getDob() throws Exception {
		return driver.getText(dob);
	}
	
	public String getExpireDate() throws Exception {
		return driver.getText(expireDate);
	}
}

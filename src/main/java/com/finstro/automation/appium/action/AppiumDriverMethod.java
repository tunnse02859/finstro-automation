package com.finstro.automation.appium.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import com.finstro.automation.ui.ImageCompare;
import com.finstro.automation.appium.driver.AppiumDriverFactory;
import com.finstro.automation.common.*;
import com.finstro.automation.logger.*;
import com.finstro.automation.extentreport.HtmlReporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AppiumDriverMethod {

	private AppiumDriver<WebElement> driver;
	Dimension size;

	public AppiumDriverMethod(HashMap<String, String> deviceInfo) throws Exception {
		this.driver = AppiumDriverFactory.getInstance().createDriver(deviceInfo);
	}

	public AppiumDriverMethod() throws Exception {
		this.driver = AppiumDriverFactory.getInstance().createDriver();
	}

	/**
	 * This method is used to close a webdriver
	 * 
	 * @author tunn6
	 * @param None
	 * @return None
	 * @throws Exception
	 */
	public void closeDriver() throws Exception {
		try {
			if (driver != null) {
				driver.quit();
				Log.info("The webdriver is closed!!!");
			}
		} catch (Exception e) {
			Log.error("The webdriver is not closed!!! " + e.getMessage());
			throw (e);
		}
	}

	/**
	 * This method is used to navigate the browser to the url
	 * 
	 * @author tunn6
	 * @param url
	 *            the url of website
	 * @return None
	 * @throws Exception
	 *             The exception is thrown if the driver can't navigate to the url
	 */
	public void openUrl(String url) throws Exception {
		try {
			driver.get(url);
			Log.info("Navigate to the url : " + url);
			HtmlReporter.pass("\"Navigate to the url : \" + url");
		} catch (Exception e) {
			Log.error("Can't navigate to the url : " + url);
			Log.error(e.getMessage());
			HtmlReporter.fail("Can't navigate to the url : " + url, e, takeScreenshot());
			throw (e);
		}
	}

	/**
	 * This method is used to send keys into a text box without cleaning before.
	 * 
	 * @author tunn6
	 * @param elementName
	 *            The name of text box
	 * @param byWebElementObject
	 *            The by object of text box element
	 * @param keysToSend
	 *            The keys are sent
	 * @throws Exception
	 *             The exception is throws if sending keys not success
	 */
	public void sendkeys(String elementName, By byWebElementObject, String keysToSend) throws Exception {
		try {
			findElement(byWebElementObject).sendKeys(keysToSend);
			Log.info("Keys are sent to the element: " + elementName);
			HtmlReporter.pass(
					"Keys [" + keysToSend + "] are sent to the element: " + elementName + " - " + byWebElementObject);
		} catch (Exception e) {
			Log.error("Keys aren't sent to the element: " + elementName);
			Log.error(e.toString());
			HtmlReporter.fail("Failed to sendKeys [" + keysToSend + "] to the element: " + elementName + " - "
					+ byWebElementObject, e, takeScreenshot());
			throw (e);
		}
	}

	/**
	 * This method is used to send keys into a text box.
	 * 
	 * @author tunn6
	 * @param elementName
	 *            The name of text box
	 * @param byWebElementObject
	 *            The by object of text box element
	 * @param keysToSend
	 *            The keys are sent
	 * @throws Exception
	 *             The exception is throws if input text not success
	 */
	public void inputText(String elementName, By byWebElementObject, String keysToSend) throws Exception {
		try {
			for (int time = 0; time < Integer
					.parseInt(Common.constants.getProperty("DEFAULT_WAITTIME_SECONDS")); time += Integer
							.parseInt(Common.constants.getProperty("TIMEOUT_STEPS_SECONDS"))) {
				try {
					WebElement txtElement = findElement(byWebElementObject);
					txtElement.click();
					txtElement.clear();
					txtElement.sendKeys(keysToSend);
					break;
				} catch (StaleElementReferenceException e) {
					Common.sleep(Integer.parseInt(Common.constants.getProperty("TIMEOUT_STEPS_SECONDS")));
				}
			}
			Log.info("Input text into the element: " + elementName);
			HtmlReporter.pass("Keys [" + keysToSend + "] are inputed to the element: " + elementName + " - "
					+ byWebElementObject);
		} catch (Exception e) {
			Log.error("Can't input text into the element: " + elementName);
			Log.error(e.getMessage());
			HtmlReporter.fail(
					"Failed to input [" + keysToSend + "] to the element: " + elementName + " - " + byWebElementObject,
					e, takeScreenshot());
			throw (e);
		}
	}

	/**
	 * Execute javascript. This method used to execute a javascript
	 * 
	 * @author tunn6
	 * @param jsFunction
	 *            the js function
	 * @throws Exception
	 *             The exception is thrown if can't execute java script
	 */
	public void executeJavascript(String jsFunction) throws Exception {
		try {

			((JavascriptExecutor) driver).executeScript(jsFunction);
			Log.info("Excecuting the java script: " + jsFunction);
			HtmlReporter.pass("Excecuting the java script: " + jsFunction);
		} catch (Exception e) {
			Log.error("Can't excecute the java script: " + jsFunction);
			Log.error(e.getMessage());
			HtmlReporter.fail("Failed to excecuting the java script: " + jsFunction, e, takeScreenshot());
			throw (e);
		}
	}

	/**
	 * This method is used to execute a java script function for an object argument.
	 * 
	 * @author tunn6
	 * @param jsFunction
	 *            The java script function
	 * @param object
	 *            The argument to execute script
	 * @throws Exception
	 *             The exception is thrown if object is invalid.
	 */
	public void executeJavascript(String jsFunction, Object object) throws Exception {
		try {
			((JavascriptExecutor) driver).executeScript(jsFunction, object);
			Log.info("Excecuting the java script: " + jsFunction);
			HtmlReporter.pass("Excecuting the java script: " + jsFunction + "for object: " + object);
		} catch (Exception e) {
			Log.error("Can't excecute the java script: " + jsFunction + " for the object: " + object);
			Log.error(e.getMessage());
			HtmlReporter.fail("Can't excecute the java script: " + jsFunction + " for the object: " + object, e,
					takeScreenshot());
			throw (e);

		}
	}

	/**
	 * Get the text of a web element
	 * 
	 * @param elementName
	 *            The name of web element
	 * @param byWebElementObject
	 *            The by object of web element
	 * @return The text of web element
	 * @throws Exception
	 *             The exception is thrown if can't get text successfully.
	 */
	public String getText(String elementName, By byWebElementObject) throws Exception {
		try {
			String text = findElement(byWebElementObject).getText();
			Log.info("Got the text of element : " + elementName + " : " + text);
			HtmlReporter.pass("Got the text of element : " + elementName + " : " + text);
			return text;
		} catch (Exception e) {
			Log.error("Can't get text of element: " + elementName);
			HtmlReporter.fail("Can't get text of element: " + elementName, e, takeScreenshot());
			return "";

		}
	}

	/**
	 * Get the attribute value of a web element
	 * 
	 * @param elementName
	 *            The name of element
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @param attribute
	 *            The attribute need to get value
	 * @return The attribute value as string
	 * @throws Exception
	 */
	public String getAttribute(String elementName, By byWebElementObject, String attribute) throws Exception {
		try {
			String attributeValue = findElement(byWebElementObject).getAttribute(attribute);
			return attributeValue;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Click on a web element
	 * 
	 * @param elementName
	 *            The name of element
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @throws Exception
	 */
	public void click(String elementName, By byWebElementObject) throws Exception {
		try {
			findElement(byWebElementObject).click();
			Log.info("Click to the element: " + elementName);
			HtmlReporter.pass("Click to the element: " + elementName + " - " + byWebElementObject);
		} catch (Exception e) {
			Log.error("Can't click to the element: " + elementName);
			HtmlReporter.fail("Cant click to the element: " + elementName + " - " + byWebElementObject, e,
					takeScreenshot());
			throw (e);
		}
	}

	/**
	 * Click on a web element using javascript
	 * 
	 * @param elementName
	 *            The name of web element
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @throws Exception
	 */
	public void clickByJS(String elementName, By byWebElementObject) throws Exception {
		try {
			executeJavascript("arguments[0].click();", findElement(byWebElementObject));
			Log.info("Click by Java Script on the element: " + elementName + " - " + byWebElementObject);
			HtmlReporter.pass("Click by Java Script on the element: " + elementName + " - " + byWebElementObject);
		} catch (Exception e) {
			Log.error("Can't click by Java Script on the element: " + elementName + " - " + byWebElementObject);
			HtmlReporter.fail("Can't click by Java Script on the element: " + elementName + " - " + byWebElementObject,
					e, takeScreenshot());
			throw (e);
		}
	}

	/**
	 * Select a radio button
	 * 
	 * @param elementName
	 *            The name of element
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @throws Exception
	 */
	public void selectRadioButton(String elementName, By byWebElementObject) throws Exception {
		try {
			WebElement rbElement = findElement(byWebElementObject);
			if (!rbElement.isSelected()) {
				rbElement.click();
			}
			Log.info("Radio button element: " + elementName + " is selected.");

		} catch (Exception e) {

			Log.error("Radio button element: " + elementName + " isn't selected.");

			throw (e);
		}

	}

	/**
	 * Select a check box
	 * 
	 * @param elementName
	 *            The name of element
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @throws Exception
	 */
	public void selectCheckBox(String elementName, By byWebElementObject) throws Exception {
		try {
			WebElement chkElement = findElement(byWebElementObject);
			if (!chkElement.isSelected()) {
				chkElement.click();
			}
			Log.info("Checkbox element: " + elementName + " is selected.");

		} catch (Exception e) {
			Log.error("Checkbox element: " + elementName + " isn't selected.");
			throw (e);
		}

	}

	/**
	 * De-select a check box
	 * 
	 * @param elementName
	 *            The name of element
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @throws Exception
	 */
	public void deselectCheckBox(String elementName, By byWebElementObject) throws Exception {
		try {
			WebElement chkElement = findElement(byWebElementObject);
			if (chkElement.isSelected()) {
				chkElement.click();
			}
			Log.info("Checkbox element: " + elementName + " is deselected.");
		} catch (Exception e) {
			Log.error("Checkbox element: " + elementName + " isn't deselected.");
			throw (e);
		}

	}

	/**
	 * Select an option in the Drop Down list
	 * 
	 * @param elementName
	 *            The element name
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @param chosenOption
	 *            The option is chosen
	 * @throws Exception
	 */
	public void selectDDL(String elementName, By byWebElementObject, String chosenOption) throws Exception {
		try {
			Select ddl = new Select(findElement(byWebElementObject));
			ddl.selectByVisibleText(chosenOption);
			Log.info("Select option: " + chosenOption + " from select box: " + elementName);
		} catch (Exception e) {
			Log.error("Can't select option: " + chosenOption + " from select box: " + elementName);
		}
	}

	/**
	 * Wait for a time until a web element located
	 * 
	 * @param by
	 *            The by locator object of element
	 * @param time
	 *            Time to wait in seconds
	 * @throws Exception
	 */
	public void waitForElementPresent(By by, int time) throws Exception {
		int i = 0;
		WebDriverWait wait = new WebDriverWait(driver,
				Integer.parseInt(Common.constants.getProperty("TIMEOUT_STEPS_SECONDS")));
		while (i < time) {
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(by));
				return;
			} catch (Exception e) {

			}
			i += Integer.parseInt(Common.constants.getProperty("TIMEOUT_STEPS_SECONDS"));
			Log.info("Wait for element " + by + " in " + i + " seconds");
		}
		Log.error("The element " + by + " not present ");
		throw new TimeoutException("The element " + by + " not present ");
	}

	/**
	 * Checking a web element is present or not
	 * 
	 * @param by
	 *            The By locator object of element
	 * @return True if the element is present, False if the element is not present
	 */
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Get a web element object
	 * 
	 * @param by
	 *            The By locator object of element
	 * @return The WebElement object
	 * @throws Exception
	 */
	public WebElement findElement(By by) throws Exception {
		WebElement element = null;
		try {
			waitForElementPresent(by, Integer.parseInt(Common.constants.getProperty("DEFAULT_WAITTIME_SECONDS")));
			element = driver.findElement(by);
			Log.info("The element : " + by + " is found.");
		} catch (Exception e) {
			Log.error("The element : " + by + " isn't found. : " + e);
			throw (e);
		}
		return element;
	}

	/**
	 * Check correction of element text
	 * 
	 * @param by
	 *            The By locator object of element
	 * @return The WebElement object
	 * @throws Exception
	 */
	public void verifyText(String elementName, By byWebElementObject, String compareText) throws Exception {
		try {
			String txt = getText(elementName, byWebElementObject);
			Assert.assertEquals(txt, compareText);
			Log.info(elementName + " is correct ");
		} catch (Exception e) {
			Log.error("The element: " + elementName + " is not correct: ");
			throw (e);
		}
	}

	/**
	 * Check correction of element text (Contain compare text)
	 * 
	 * @param by
	 *            The By locator object of element
	 * @return The WebElement object
	 * @throws Exception
	 */
	public void verifyNotEqualText(String elementName, By byWebElementObject, String compareText) throws Exception {
		try {
			String txt = getText(elementName, byWebElementObject);
			Assert.assertNotEquals(txt, compareText);
			Log.info(elementName + " is not equal to " + compareText);
		} catch (Exception e) {
			Log.error("The element: " + elementName + " is not equal to " + compareText);
			throw (e);
		}
	}

	/**
	 * Check correction of element text (not equal to compare text)
	 * 
	 * @param by
	 *            The By locator object of element
	 * @return The WebElement object
	 * @throws Exception
	 */
	public boolean verifyContainText(String elementName, By byWebElementObject, String containText) throws Exception {
		try {
			String txt = getText(elementName, byWebElementObject);

			if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(txt, containText)) {
				Log.info(elementName + " contains " + containText);
				return true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			Log.error("The element: " + elementName + " does not contain " + containText);
			throw (e);
		}
	}

	/**
	 * Upload file
	 * 
	 * @param elementName
	 *            The element name
	 * @param title
	 *            Title of window select box
	 * @param url
	 *            Url to file upload
	 * @return The WebElement object
	 * @throws Exception
	 */
	/*
	 * public void uploadfile(String elementName, By byWebElementObject, String
	 * title, String url) throws Exception { try { click("Open upload location",
	 * byWebElementObject); AutoItX autoit = new AutoItX(); autoit.winWait(title);
	 * autoit.controlFocus(title, "", "Edit1"); autoit.sleep(2000);
	 * autoit.ControlSetText(title, "", "Edit1", url); autoit.controlClick(title,
	 * "", "Button1"); Log.info("File is uploaded"); } catch (Exception e) {
	 * Log.error(elementName + " uploaded fail ");
	 * TestngLogger.writeResult(elementName + " uploaded fail ", false); throw (e);
	 * } }
	 */

	/**
	 * Open url in new tab
	 * 
	 * @param url
	 *            Url to of new tab *
	 * @throws Exception
	 */
	/*
	 * public void openNewTab(String url) throws Exception { try { // Open tab 2
	 * using CTRL + t keys. driver.findElement(By.cssSelector("body")).sendKeys(
	 * Keys.CONTROL + "t"); // Open URL In 2nd tab. driver.get(url); // Switch to
	 * current selected tab's content. driver.switchTo().defaultContent(); } catch
	 * (Exception e) {
	 * 
	 * Log.error("Open tab failed "); TestngLogger.writeResult("Open tab failed ",
	 * false); throw (e); } }
	 */

	/**
	 * Verify Status of check box/selection box
	 * 
	 * @param elementName
	 *            The name of check box/selection box
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @throws Exception
	 */
	public boolean verifyStatusCheckbox(String elementName, By byWebElementObject, boolean status) throws Exception {
		try {
			if (driver.findElement(byWebElementObject).isSelected() && status == true)
				Log.info("Checkbox related to: '" + elementName + "' is checked.");
			else if (driver.findElement(byWebElementObject).isSelected() == false && status == false)
				Log.info("Checkbox related to: '" + elementName + "' is unchecked.");
			else
				throw new Exception();
			return (driver.findElement(byWebElementObject).isSelected());
		} catch (Exception e) {
			Log.error("Error while Checking if the checkbox related to " + elementName + " is checked or not. -"
					+ e.getMessage());
			throw (e);
		}
	}

	/**
	 * Get the attribute value of a web element
	 * 
	 * @param elementName
	 *            The name of element
	 * @param byWebElementObject
	 *            The By locator object of element
	 * @param attribute
	 *            The attribute need to get value
	 * @param verifyAttribute
	 *            The attribute value used to compare
	 * @return The attribute value as string
	 * @throws Exception
	 */
	public boolean verifyAttribute(String elementName, By byWebElementObject, String attribute, String verifyAttribute)
			throws Exception {
		try {
			String attributeValue = getAttribute(elementName, byWebElementObject, attribute);
			Assert.assertEquals(attributeValue, verifyAttribute);
			Log.info(elementName + " is correct ");
			return true;
		} catch (Exception e) {
			Log.error("Verify that element \"" + elementName + "\" is PRESENT");
			return true;
		}
	}

	public boolean verifyCSS(String elementName, By byWebElementObject, String cssAttribute, String verifyAttribute)
			throws Exception {
		try {
			String attributeValue = findElement(byWebElementObject).getCssValue(cssAttribute);
			Assert.assertEquals(attributeValue, verifyAttribute);
			Log.info(elementName + " is correct ");
			return true;
		} catch (Exception e) {
			Log.error("Verify that element \"" + elementName + "\" is PRESENT");
			return true;
		}
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}

	public void checkAlert() throws Exception {
		try {
			// WebDriverWait wait = new WebDriverWait(driver,
			// Constant.DEFAULT_WAITTIME_SECONDS);
			// wait.until(ExpectedConditions.alertIsPresent());
			// Alert alert = driver.switchTo().alert();
			// alert.accept();
			if (isAlertPresent()) {
				driver.switchTo().alert().accept();
			}
		} catch (Exception e) {
			Log.error("Alert is not present");
			throw (e);
		}
	}

	public boolean displayedElement(By by) throws Exception {
		boolean check;
		try {
			waitForElementPresent(by, Integer.parseInt(Common.constants.getProperty("DEFAULT_WAITTIME_SECONDS")));
			check = driver.findElement(by).isDisplayed();
			Log.info("The element : " + by + " is found.");
		} catch (Exception e) {
			Log.error("The element : " + by + " isn't found. : " + e);
			throw (e);
		}
		return check;
	}

	// -----------------------------------------------------------------------------
	/**
	 * Verify the enabled status of a web element
	 * 
	 * @param byWebElementObject
	 *            The by object of text box element
	 * @param status
	 *            The expect enabled status of element
	 * @return True if verification is correct
	 * @throws Exception
	 */
	public boolean verifyElementIsEnabled(By byWebElementObject, boolean status) throws Exception {
		boolean check;
		try {
			waitForElementPresent(byWebElementObject,
					Integer.parseInt(Common.constants.getProperty("DEFAULT_WAITTIME_SECONDS")));
			check = driver.findElement(byWebElementObject).isEnabled();
			if (check == status) {
				Log.info("The enabled status of element : " + byWebElementObject + " is correct.");
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			Log.error("The enabled status of element : " + byWebElementObject + " is incorrect." + e);
			throw (e);
		}
		return check;
	}

	/**
	 * Swipe the android mobile from right to left
	 * 
	 * @throws Exception
	 */
	public void swipeLeft() throws Exception {

		try {

			Thread.sleep(5000);
			// Get the size of screen.
			size = driver.manage().window().getSize();
			// Find startx point which is at right side of screen.
			int startx = (int) (size.width * 0.96);
			// Find endx point which is at left side of screen.
			int endx = (int) (size.width * 0.50);
			// Find vertical point where you wants to swipe. It is in middle of
			// screen height.
			int starty = size.height / 2;
			// Swipe from Right to Left.
			new TouchAction<>(driver).press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(endx, starty))
					.release().perform();
			Log.info("Swipe left successfully");
		} catch (Exception e) {
			Log.error("Can't swipe left!!! : " + e);
			throw (e);
		}
	}

	/**
	 * Swipe the android mobile by location in screen
	 * 
	 * @param fromx
	 *            % vertical of screen for starting point
	 * 
	 * @param fromy
	 *            % horizontal of screen for starting point
	 * 
	 * @param tox
	 *            % vertical of screen for ending point
	 * 
	 * @param toy
	 *            % horizontal of screen for ending point
	 * 
	 * @throws Exception
	 */
	public void swipe(double fromx, double fromy, double tox, double toy) throws Exception {
		try {
			Thread.sleep(5000);
			// Get the size of screen.
			Dimension size = driver.manage().window().getSize();
			int startx = (int) (size.width * fromx);
			int endx = (int) (size.height * fromy);
			int starty = (int) (size.width * tox);
			int endy = (int) (size.height * toy);
			new TouchAction<>(driver).press(PointOption.point(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(endx, endy))
					.release().perform();
			// driver.swipe(startx, endx, starty, endy, 1000);
			Log.info("Swipe successfully");

		} catch (Exception e) {
			Log.error("Can't swipe! : " + e);
			throw (e);
		}
	}

	/**
	 * Verify that a text that available in screen
	 * 
	 * @param compareText
	 *            string that need to be verify
	 * 
	 * @return All string that available in screen
	 * 
	 * @throws Exception
	 */
	/*
	 * public void verifyToastMessage(String compareText) throws Exception { try {
	 * String imageClientCode = "ClientCodeEmptyImage";
	 * this.takeScreenshot(imageClientCode); String TessMessage =
	 * readToastMessage(imageClientCode);
	 * Assert.assertTrue(TessMessage.contains(compareText)); Log.info("String \"" +
	 * compareText + "\" is available in screen");
	 * 
	 * } catch (Exception e) { Log.error("String \"" + compareText +
	 * "\" is not available in screen"); throw (e); } }
	 */

	/**
	 * This method is used to capture a screenshot then write to the TestNG Logger
	 * 
	 * @author tunn6
	 * 
	 * @return A html tag that reference to the image, it's attached to the
	 *         report.html
	 * @throws Exception
	 */
	public String takeScreenshot() throws Exception {

		String failureImageFileName = new SimpleDateFormat(Common.constants.getProperty("TIME_STAMP_3"))
				.format(new GregorianCalendar().getTime()) + ".jpg";
		try {
			if (driver != null) {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String screenShotDirector = HtmlReporter.strScreenshotFolder;
				FileUtils.copyFile(scrFile, new File(screenShotDirector + failureImageFileName));
			}
		} catch (Exception e) {
			throw e;
		}
		return "";
	}

	/**
	 * This method is used to capture a screenshot
	 * 
	 * @author tunn6
	 * 
	 * @return A html tag that reference to the image, it's attached to the
	 *         report.html
	 * @throws Exception
	 */
	public String takeScreenshot(String filename) throws Exception {

		String screenShotDirector = HtmlReporter.strScreenshotFolder;
		String screenshotFile = Common.correctPath(screenShotDirector + filename);

		try {
			if (driver != null) {

				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

				FileUtils.copyFile(scrFile, new File(screenshotFile));

				return screenshotFile;

			} else {
				return "";
			}
		} catch (Exception e) {
			Log.error("Can't capture the screenshot");
			Log.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * This method is used to capture a screenshot with Ashot
	 * 
	 * @author tunn6
	 * @param filename
	 * @return The screenshot path
	 * @throws Exception
	 */
	public String takeScreenshotWithAshot(String fileDir) throws Exception {

		fileDir = Common.correctPath(fileDir);
		try {

			if (driver != null) {
				Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
						.takeScreenshot(driver);
				ImageIO.write(screenshot.getImage(), "jpg", new File(fileDir));
			} else {
				fileDir = "";
			}

		} catch (Exception e) {
			Log.error("Can't capture the screenshot");
			Log.error(e.getMessage());
			throw e;
		}
		return fileDir;
	}

	/**
	 * This method is used to capture an element's screenshot with Ashot
	 * 
	 * @author tunn6
	 * @param filename
	 * @return The screenshot path
	 * @throws Exception
	 */
	public String takeScreenshotWithAshot(String fileDir, By by) throws Exception {

		fileDir = Common.correctPath(fileDir);
		try {

			if (driver != null) {
				WebElement element = findElement(by);
				Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
						.takeScreenshot(driver, element);
				ImageIO.write(screenshot.getImage(), "jpg", new File(fileDir));
			}

		} catch (Exception e) {
			Log.error("Can't capture the screenshot");
			Log.error(e.getMessage());
			throw e;
		}
		return fileDir;
	}

	/**
	 * To compare the layout of a web page with baseline
	 * 
	 * @param filename
	 *            The name of screenshot
	 * @throws Exception
	 */

	public void compareScreenshot(String filename) throws Exception {
		String screenshotFileName = filename + ".jpg";
		String baseLineImage = HtmlReporter.strBaseLineScreenshotFolder + screenshotFileName;
		String actualImage = HtmlReporter.strActualScreenshotFolder + screenshotFileName;
		// String diffImage = HtmlReporter.strDiffScreenshotFolder + screenshotFileName;
		try {
			if (!Common.pathExist(baseLineImage)) {
				takeScreenshotWithAshot(baseLineImage);
			} else {
				takeScreenshotWithAshot(actualImage);
				ImageCompare imageComparitor = new ImageCompare();
				BufferedImage diffBuff = imageComparitor.diffImages(baseLineImage, actualImage, 30, 10);
				if (diffBuff == null) {
					Log.info("The actual screenshot of page [" + filename + "] matches with the baseline");
				} else {
					Log.error("The actual screenshot of page [" + filename + "] doesn't match with the baseline");
					ImageIO.write(diffBuff, "jpg", new File(HtmlReporter.strDiffScreenshotFolder, screenshotFileName));
					throw new Exception("The actual screenshot doesn't match with the baseline");
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * To compare the layout of a web element with baseline
	 * 
	 * @param filename
	 *            The name of screenshot
	 * @throws Exception
	 */

	public void compareScreenshot(String filename, By locator) throws Exception {
		String screenshotFileName = filename + ".jpg";
		String baseLineImage = HtmlReporter.strBaseLineScreenshotFolder + screenshotFileName;
		String actualImage = HtmlReporter.strActualScreenshotFolder + screenshotFileName;
		// String diffImage = HtmlReporter.strDiffScreenshotFolder + screenshotFileName;
		try {
			if (!Common.pathExist(baseLineImage)) {
				takeScreenshotWithAshot(baseLineImage, locator);
			} else {
				takeScreenshotWithAshot(actualImage, locator);
				ImageCompare imageComparitor = new ImageCompare();
				BufferedImage diffBuff = imageComparitor.diffImages(baseLineImage, actualImage, 30, 10);
				if (diffBuff == null) {
					Log.info("The actual screenshot of element [" + filename + "] matches with the baseline");
				} else {
					Log.error("The actual screenshot of element [" + filename + "] doesn't match with the baseline");
					throw new Exception(
							"The actual screenshot of element [" + filename + "] doesn't match with the baseline");
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

}

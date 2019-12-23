package com.finstro.automation.appium.driver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import com.finstro.automation.utility.FilePaths;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.report.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AppiumBaseDriver {

	protected AppiumDriver<WebElement> driver;

	private final int DEFAULT_WAITTIME_SECONDS = 30;

	public AppiumBaseDriver getDriver() {
		return this;
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
	 *             The exception is thrown if the driver can't navigate to the
	 *             url
	 */
	public void openUrl(String url) throws Exception {
		try {
			driver.get(url);
			Log.info("Navigate to the url : " + url);
			HtmlReporter.pass("\"Navigate to the url : \" + url");
		} catch (Exception e) {
			Log.error("Can't navigate to the url : " + url);
			Log.error(e.getMessage());
			HtmlReporter.fail("Can't navigate to the url : " + url);
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
	public void sendkeys(String elementName, By locator, String keysToSend) throws Exception {
		try {
			findElement(locator).sendKeys(keysToSend);
			Log.info("Keys are sent to the element: " + elementName);
			HtmlReporter.pass(
					"Keys [" + keysToSend + "] are sent to the element: " + elementName + " - " + locator);
		} catch (Exception e) {
			Log.error("Keys aren't sent to the element: " + elementName);
			Log.error(e.toString());
			HtmlReporter.fail("Failed to sendKeys [" + keysToSend + "] to the element: " + elementName + " - "
					+ locator);
			throw (e);
		}
	}

	/**
	 * This method is used to send keys into a text box.
	 * 
	 * @author tunn6
	 * @param elementName
	 *            The name of text box
	 * @param locator
	 *            The by object of text box element
	 * @param text
	 *            The keys are sent
	 * @throws Exception
	 *             The exception is throws if input text not success
	 */
	public void inputText(String elementName, By locator, String text) throws Exception {

		waitForElementPresent(locator, DEFAULT_WAITTIME_SECONDS);
		WebElement txtElement = findElement(locator);
		txtElement.click();
		txtElement.clear();
		txtElement.sendKeys(text);

		String mess = String.format("Input text [%s] to the element [%s]", text, elementName);
		Log.info(mess);
		HtmlReporter.pass(mess);

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
			HtmlReporter.fail("Failed to excecuting the java script: " + jsFunction);
			throw (e);
		}
	}

	/**
	 * This method is used to execute a java script function for an object
	 * argument.
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
			HtmlReporter.fail("Can't excecute the java script: " + jsFunction + " for the object: " + object);
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
	public String getText(String elementName, By locator) throws Exception {
		try {
			String text = findElement(locator).getText();
			Log.info("Got the text of element : " + elementName + " : " + text);
			HtmlReporter.pass("Got the text of element : " + elementName + " : " + text);
			return text;
		} catch (Exception e) {
			Log.error("Can't get text of element: " + elementName);
			HtmlReporter.fail("Can't get text of element: " + elementName);
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
	public String getAttribute(String elementName, By locator, String attribute) throws Exception {
		try {
			String attributeValue = findElement(locator).getAttribute(attribute);
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
	public void click(String elementName, By locator) throws Exception {
		try {
			findElement(locator).click();
			Log.info("Click to the element: " + elementName);
			HtmlReporter.pass("Click to the element: " + elementName + " - " + locator);
		} catch (Exception e) {
			Log.error("Can't click to the element: " + elementName);
			HtmlReporter.fail("Cant click to the element: " + elementName + " - " + locator);
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
	public void clickByJS(String elementName, By locator) throws Exception {
		try {
			executeJavascript("arguments[0].click();", findElement(locator));
			Log.info("Click by Java Script on the element: " + elementName + " - " + locator);
			HtmlReporter.pass("Click by Java Script on the element: " + elementName + " - " + locator);
		} catch (Exception e) {
			Log.error("Can't click by Java Script on the element: " + elementName + " - " + locator);
			HtmlReporter.fail("Can't click by Java Script on the element: " + elementName + " - " + locator);
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
	public void selectRadioButton(String elementName, By locator) throws Exception {
		try {
			WebElement rbElement = findElement(locator);
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
	public void selectCheckBox(String elementName, By locator) throws Exception {
		try {
			waitForElementPresent(locator, DEFAULT_WAITTIME_SECONDS);
			WebElement chkElement = findElement(locator);
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
	public void deselectCheckBox(String elementName, By locator) throws Exception {
		try {
			waitForElementPresent(locator, DEFAULT_WAITTIME_SECONDS);
			WebElement chkElement = findElement(locator);
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
	public void selectDDL(String elementName, By locator, String chosenOption) throws Exception {
		try {
			Select ddl = new Select(findElement(locator));
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
	public void waitForElementPresent(By locator, int time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}
	
	/**
	 * Wait for a time until a web element displayed
	 * 
	 * @param by
	 *            The by locator object of element
	 * @param time
	 *            Time to wait in seconds
	 * @throws Exception
	 */
	public void waitForElementDisplayed(By locator, int time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	/**
	 * Checking a web element is present or not
	 * 
	 * @param by
	 *            The By locator object of element
	 * @return True if the element is present, False if the element is not
	 *         present
	 */
	public boolean isElementPresent(By locator) {
		try {
			waitForElementPresent(locator, DEFAULT_WAITTIME_SECONDS);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isElementDisplayed(By locator) throws Exception {

		waitForElementPresent(locator, DEFAULT_WAITTIME_SECONDS);
		return driver.findElement(locator).isDisplayed();

	}

	/**
	 * Get a web element object
	 * 
	 * @param by
	 *            The By locator object of element
	 * @return The WebElement object
	 * @throws Exception
	 */
	public WebElement findElement(By locator) throws Exception {

		return driver.findElement(locator);

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
	 * byWebElementObject); AutoItX autoit = new AutoItX();
	 * autoit.winWait(title); autoit.controlFocus(title, "", "Edit1");
	 * autoit.sleep(2000); autoit.ControlSetText(title, "", "Edit1", url);
	 * autoit.controlClick(title, "", "Button1"); Log.info("File is uploaded");
	 * } catch (Exception e) { Log.error(elementName + " uploaded fail ");
	 * TestngLogger.writeResult(elementName + " uploaded fail ", false); throw
	 * (e); } }
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
	 * Keys.CONTROL + "t"); // Open URL In 2nd tab. driver.get(url); // Switch
	 * to current selected tab's content. driver.switchTo().defaultContent(); }
	 * catch (Exception e) {
	 * 
	 * Log.error("Open tab failed "); TestngLogger.writeResult(
	 * "Open tab failed ", false); throw (e); } }
	 */


	public boolean isAlertPresent() {

		try {

			driver.switchTo().alert();
			return true;

		} catch (Exception Ex) {

			return false;

		}
	}

	public void acceptAlert() {

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}

	}

	public void dismissAlert() {

		if (isAlertPresent()) {
			driver.switchTo().alert().dismiss();
		}

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
			Dimension size = driver.manage().window().getSize();
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
	 * public void verifyToastMessage(String compareText) throws Exception { try
	 * { String imageClientCode = "ClientCodeEmptyImage";
	 * this.takeScreenshot(imageClientCode); String TessMessage =
	 * readToastMessage(imageClientCode);
	 * Assert.assertTrue(TessMessage.contains(compareText)); Log.info(
	 * "String \"" + compareText + "\" is available in screen");
	 * 
	 * } catch (Exception e) { Log.error("String \"" + compareText +
	 * "\" is not available in screen"); throw (e); } }
	 */

	/**
	 * This method is used to capture a screenshot then write to the TestNG
	 * Logger
	 * 
	 * @author tunn6
	 * 
	 * @return A html tag that reference to the image, it's attached to the
	 *         report.html
	 * @throws Exception
	 */
	public String takeScreenshot() throws Exception {

		String failureImageFileName = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss.SSS")
				.format(new GregorianCalendar().getTime()) + ".jpg";
		try {
			if (driver != null) {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String screenShotDirector = FilePaths.getScreenshotFolder();
				FileUtils.copyFile(scrFile, new File(screenShotDirector + failureImageFileName));
				
				return screenShotDirector + failureImageFileName;
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

		String screenShotDirector = FilePaths.getScreenshotFolder();
		String screenshotFile = FilePaths.correctPath(screenShotDirector + filename);

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

		fileDir = FilePaths.correctPath(fileDir);
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

		fileDir = FilePaths.correctPath(fileDir);
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

}

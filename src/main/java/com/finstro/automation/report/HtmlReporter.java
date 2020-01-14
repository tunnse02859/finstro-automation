package com.finstro.automation.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class HtmlReporter {

	private static ExtentReports _report;

	private static HashMap<String, ExtentTest> extentTestMap = new HashMap<String, ExtentTest>();

	public static ExtentReports setReporter(String filename) throws UnknownHostException {

		if (_report == null)
			_report = createInstance(filename);

		// Tests view
		_report.setAnalysisStrategy(AnalysisStrategy.CLASS);
		return _report;
	}

	/**
	 * To create an reporter instance
	 * 
	 * @param fileName
	 *            The report's name
	 * @return
	 */
	public static ExtentReports createInstance(String fileName) {

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		// String configFile = Common.correctPath(Common.strWorkspacepath +
		// "/src/main/resource/config/extent-config.xml");
		// htmlReporter.loadXMLConfig(configFile);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);
		htmlReporter.setAppendExisting(false);

		ExtentReports report = new ExtentReports();
		report.attachReporter(htmlReporter);
		report.setSystemInfo("Application", "Finstro Pay Automation Tests");

		return report;
	}

	/**
	 * Write report
	 */
	public static void flush() {
		if (_report != null) {
			_report.flush();
		}
	}

	/**
	 * To Create an ExtentTest session
	 * 
	 * @param strTestMethodName
	 *            The method name
	 * @param strTestMethodDesc
	 *            The method description
	 * @return ExtentTest session
	 */
	public static synchronized ExtentTest createTest(String strTestMethodName, String strTestMethodDesc) {

		ExtentTest test = _report.createTest(strTestMethodName, strTestMethodDesc);
		extentTestMap.put("test_" + Thread.currentThread().getId(), test);
		return test;
	}

	/**
	 * To Create an ExtentTest session
	 * 
	 * @param strTestMethodName
	 *            The method name
	 * @param strTestMethodDesc
	 *            The method description
	 * @return ExtentTest session
	 */
	public static synchronized ExtentTest createTest(String strTestClassName) {

		ExtentTest test = _report.createTest(strTestClassName);
		extentTestMap.put("test_" + Thread.currentThread().getId(), test);
		return test;
	}

	/**
	 * To Create a node of ExtentTest session
	 * 
	 * @param strTestMethodName
	 *            The method name
	 * @param strTestMethodDesc
	 *            The method description
	 * @param strNodeName
	 *            The node name
	 * @return ExtentTest session
	 */
	public static synchronized ExtentTest createNode(String strClassName, String strTestMethodName,
			String strTestMethodDesc) {
		ExtentTest test = getParent();
		if (test == null) {
			test = createTest(strClassName);
		}

		ExtentTest node = test.createNode(strTestMethodName, strTestMethodDesc);
		extentTestMap.put("node_" + Thread.currentThread().getId(), node);
		return node;
	}

	/**
	 * To get the ExtentTest's parent session to write report
	 * 
	 * @return ExtentTest session
	 */

	public static synchronized ExtentTest getParent() {
		return extentTestMap.get("test_" + Thread.currentThread().getId());
	}

	/**
	 * To get the ExtentTest's node session to write report
	 * 
	 * @return ExtentTest session
	 */
	public static synchronized ExtentTest getNode() {
		return extentTestMap.get("node_" + Thread.currentThread().getId());
	}

	/**
	 * To get the ExtentTest session to write report
	 * 
	 * @return ExtentTest session
	 */
	public static synchronized ExtentTest getTest() {
		if (getNode() == null) {
			return getParent();
		} else {
			return getNode();
		}
	}

	/**
	 * To write a passed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 */
	public static void pass(String strDescription) {
		getTest().pass(strDescription);
		Log.info(strDescription);
	}
	
	/**
	 * To write a passed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 */
	public static void warning(String strDescription) {
		getTest().warning(strDescription);
		Log.warn(strDescription);
	}
	
	/**
	 * To write a passed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 */
	public static void info(String strDescription) {
		getTest().info(strDescription);
		Log.info(strDescription);
	}

	/**
	 * To write a passed step to report with screenshot
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void pass(String strDescription, String strScreenshotPath) throws Exception {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().pass(strDescription);
		} else {
			strScreenshotPath = "file:///" + strScreenshotPath;
			getTest().pass(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}

		Log.info(strDescription);

	}

	/**
	 * To write a failed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void fail(String strDescription)  {

		getTest().fail(strDescription);
		Log.error(strDescription);

	}

	/**
	 * To write a failed step to report with screenshot
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void fail(String strDescription, String strScreenshotPath) throws IOException  {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().fail(strDescription);
		} else {
			strScreenshotPath = "file:///" + strScreenshotPath;
			getTest().fail(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}

		Log.error(strDescription);

	}

	/**
	 * To write a failed step to report with screenshot and throwable stacktrace
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param e
	 *            Throwable object
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void fail(String strDescription, Throwable e, String strScreenshotPath) throws IOException  {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().fail(strDescription).fail(e);
		} else {
			strScreenshotPath = "file:///" + strScreenshotPath;
			getTest().fail(strDescription).fail(e).addScreenCaptureFromPath(strScreenshotPath);
		}
		Log.error(strDescription);
	}

	/**
	 * To write a skipped step to report with screenshot
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void skip(String strDescription, String strScreenshotPath) throws IOException  {

		if (strDescription.equalsIgnoreCase("")) {
			getTest().skip(strDescription);
		} else {
			strScreenshotPath = "file:///" + strScreenshotPath;
			getTest().skip(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}

		Log.info(strDescription);
	}

	/**
	 * To write a skipped step to report with screenshot and throwable
	 * stacktrace
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param e
	 *            Throwable object
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws IOException 
	 * @throws Exception
	 */
	public static void skip(String strDescription, Throwable e, String strScreenshotPath) throws IOException {

			if (strDescription.equalsIgnoreCase("")) {
				getTest().skip(strDescription).skip(e);
			} else {
				strScreenshotPath = "file:///" + strScreenshotPath;
				getTest().skip(strDescription).skip(e).addScreenCaptureFromPath(strScreenshotPath);
			}
		
		Log.info(strDescription);

	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception
	 */
	public static void label(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.BLUE));

	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception
	 */
	public static void labelFailed(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.RED));

	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception
	 */
	public static void labelSkiped(String strDescription) {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.AMBER));
			
	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception
	 */
	public static void labelWarning(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.ORANGE));

	}

	public static String throwableToString(Throwable e) throws Exception {
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);) {
			e.printStackTrace(pw);

			return sw.toString();
		}
	}

	/**
	 * To write a passed step to report
	 * 
	 * @throws Exception
	 */
	public static void pass(String[] data) throws Exception {
		if (data.length == 2) {
			pass(data[1]);
		} else if (data.length == 3) {
			pass(data[1], data[2]);
		}
	}

	/**
	 * To write a failed step to report
	 * 
	 * @throws Exception
	 */
	public static void fail(String[] data) throws Exception {
		if (data.length == 3) {
			HtmlReporter.fail(data[1], data[2]);
		} else if (data.length == 4) {
			HtmlReporter.fail(data[1], data[3]);
		}
	}

	/**
	 * To write a failed step to report
	 * 
	 * @throws Exception
	 */
	public static void description(String data) throws Exception {
		try {
			getTest().info(MarkupHelper.createCodeBlock(data));
		} catch (Exception ex) {
			Log.info("Can't write to htm report, initialize it first");
		}
	}

}

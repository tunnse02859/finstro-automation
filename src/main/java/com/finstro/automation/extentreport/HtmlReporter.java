package com.finstro.automation.extentreport;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.finstro.automation.common.Common;
import com.finstro.automation.logger.Log;

public class HtmlReporter {

	private static ExtentReports _report;
	public static String date;
	public static String strReportFolder;
	public static String strBaseLineScreenshotFolder;
	public static String strDailyReportFolder;
	public static String strScreenshotFolder;
	public static String strUITestScreenshotFolder;
	public static String strDiffScreenshotFolder;
	public static String strActualScreenshotFolder;

	static {
		try {
			initWebReportPath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void initWebReportPath() throws Exception {
		// dd-MM-yyyy
		date = new SimpleDateFormat(Common.constants.getProperty("TIME_STAMP_5")).format(new GregorianCalendar().getTime());
		/*********** Setting path for Web Report *******************/
		// Web Report Folder : ./reports/web/
		strReportFolder = Common.correctPath(Common.strWorkspacepath + Common.constants.getProperty("webReportFolder"));
		// Baseline Screenshot folder: ./reports/web/BaseLineScreenshot/
		strBaseLineScreenshotFolder = Common.correctPath(strReportFolder + "\\BaseLineScreenshot\\");
		// Daily Report Folder: ./reports/web/dd-MM-yyyy HHmmss
		strDailyReportFolder = Common.correctPath(strReportFolder + "\\" + date);
		// Daily Screenshot Folder: ./reports/web/dd-MM-yyyy HHmmss/Screenshot
		strScreenshotFolder = Common.correctPath(strDailyReportFolder + "\\Screenshots\\");
		// Daily UI Test Screenshot Folder: ./reports/web/dd-MM-yyyy HHmmss/UI
		strUITestScreenshotFolder = Common.correctPath(strDailyReportFolder + "\\UI\\");
		// Daily UI Test, Different Screenshot Folder: ./reports/web/dd-MM-yyyy
		// HHmmss/Screenshot/Different
		strDiffScreenshotFolder = Common.correctPath(strUITestScreenshotFolder + "\\Different\\");
		// Daily UI Test, Different Screenshot Folder: ./reports/web/dd-MM-yyyy
		// HHmmss/Screenshot/Actual
		strActualScreenshotFolder = Common.correctPath(strUITestScreenshotFolder + "\\Actual\\");
	}

	/*********************************************************************/

	public static void initWebReportFolder() throws Exception {
		try {
			// Create Reports folder for Web
			Common.createDirectory(strReportFolder);
			Common.createDirectory(strBaseLineScreenshotFolder);
			Common.createDirectory(strDailyReportFolder);
			Common.createDirectory(strScreenshotFolder);
			Common.createDirectory(strUITestScreenshotFolder);
			Common.createDirectory(strDiffScreenshotFolder);
			Common.createDirectory(strActualScreenshotFolder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static HashMap<String, ExtentTest> extentTestMap = new HashMap<String, ExtentTest>();

	public static ExtentReports getReporter(String filename) throws UnknownHostException {
		if (_report == null)
			_report = createInstance(filename);

		_report.setSystemInfo("Application", "Data Driven Framework");
		_report.setSystemInfo("Os Name", System.getProperty("os.name"));
		_report.setSystemInfo("Os Version", System.getProperty("os.version"));
		_report.setSystemInfo("Os Architecture", System.getProperty("os.arch"));
		_report.setSystemInfo("Host", InetAddress.getLocalHost().getHostName());
		_report.setSystemInfo("Username", System.getProperty("user.name"));

		// Tests view
		_report.setAnalysisStrategy(AnalysisStrategy.CLASS);
		return _report;
	}

	/**
	 * To create an reporter instance
	 * 
	 * @param fileName The report's name
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
	 * @param strTestMethodName The method name
	 * @param strTestMethodDesc The method description
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
	 * @param strTestMethodName The method name
	 * @param strTestMethodDesc The method description
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
	 * @param strTestMethodName The method name
	 * @param strTestMethodDesc The method description
	 * @param strNodeName       The node name
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
	 * @param strDescription The Step's description
	 * @throws Exception 
	 */
	public static void pass(String strDescription) throws Exception {

		try {
			getTest().pass(strDescription);
		} catch (Exception ex) {
			Log.info("Can't write to htm report, initialize it first");
			ex.printStackTrace();
		}

	}

	/**
	 * To write a passed step to report with screenshot
	 * 
	 * @param strDescription    The Step's description
	 * @param strScreenshotPath The screenshot's path
	 * @throws Exception 
	 * @throws IOException If the screenshot doesn't exist
	 */
	public static void pass(String strDescription, String strScreenshotPath) throws Exception {

		try {
			if (strScreenshotPath.equalsIgnoreCase("")) {
				getTest().pass(strDescription);
			} else {
				strScreenshotPath = "file:///" + strScreenshotPath;
				getTest().pass(strDescription).addScreenCaptureFromPath(strScreenshotPath);
			}
		} catch (Exception ex) {
			Log.info("Can't write to htm report, initialize it first");
			ex.printStackTrace();
		}
	}

	/**
	 * To write a failed step to report with screenshot
	 * 
	 * @param strDescription    The Step's description
	 * @param strScreenshotPath The screenshot's path
	 * @throws Exception 
	 * @throws IOException If the screenshot doesn't exist
	 */
	public static void fail(String strDescription, String strScreenshotPath) throws Exception {

		try {
			if (strScreenshotPath.equalsIgnoreCase("")) {
				getTest().fail(strDescription);
			} else {
				strScreenshotPath = "file:///" + strScreenshotPath;
				getTest().fail(strDescription).addScreenCaptureFromPath(strScreenshotPath);
			}
		} catch (Exception ex) {
			Log.info("Can't write to htm report, initialize it first");
		}

	}

	/**
	 * To write a failed step to report with screenshot and throwable stacktrace
	 * 
	 * @param strDescription    The Step's description
	 * @param e                 Throwable object
	 * @param strScreenshotPath The screenshot's path
	 * @throws Exception 
	 * @throws IOException If the screenshot doesn't exist
	 */
	public static void fail(String strDescription, Throwable e, String strScreenshotPath) throws Exception {

		try {
			if (strScreenshotPath.equalsIgnoreCase("")) {
				getTest().fail(strDescription).fail(e);
			} else {
				strScreenshotPath = "file:///" + strScreenshotPath;
				getTest().fail(strDescription).fail(e).addScreenCaptureFromPath(strScreenshotPath);
			}
		} catch (Exception ex) {
			Log.info("Can't write to htm report, initialize it first");
		}

	}

	/**
	 * To write a skipped step to report with screenshot
	 * 
	 * @param strDescription    The Step's description
	 * @param strScreenshotPath The screenshot's path
	 * @throws Exception 
	 * @throws IOException If the screenshot doesn't exist
	 */
	public static void skip(String strDescription, String strScreenshotPath) throws Exception {

		try {
			if (strDescription.equalsIgnoreCase("")) {
				getTest().skip(strDescription);
			} else {
				strScreenshotPath = "file:///" + strScreenshotPath;
				getTest().skip(strDescription).addScreenCaptureFromPath(strScreenshotPath);
			}
		} catch (Exception ex) {
			Log.info("Can't write to htm report, initialize it first");
		}

	}

	/**
	 * To write a skipped step to report with screenshot and throwable stacktrace
	 * 
	 * @param strDescription    The Step's description
	 * @param e                 Throwable object
	 * @param strScreenshotPath The screenshot's path
	 * @throws Exception 
	 */
	public static void skip(String strDescription, Throwable e, String strScreenshotPath) throws Exception {

		try {
			if (strDescription.equalsIgnoreCase("")) {
				getTest().skip(strDescription).skip(e);
			} else {
				strScreenshotPath = "file:///" + strScreenshotPath;
				getTest().skip(strDescription).skip(e).addScreenCaptureFromPath(strScreenshotPath);
			}
		} catch (Exception ex) {
			Log.info("Can't write to htm report, initialize it first");
		}

	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription The step's description
	 * @throws Exception 
	 */
	public static void label(String strDescription) throws Exception {

		try {
			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.BLUE));
		} catch (Exception ex) {
			Log.info("Can't write to htm report, initialize it first");
		}

	}
	
	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception 
	 */
	public static void labelFailed(String strDescription) throws Exception {

		try {
			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.RED));
		} catch (Exception ex) {
			Log.info("Can't write to html report, initialize it first");
		}

	}
	
	
	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception 
	 */
	public static void labelSkiped(String strDescription) throws Exception {

		try {
			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.AMBER));
		} catch (Exception ex) {
			Log.info("Can't write to html report, initialize it first");
		}
	}
	
	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception 
	 */
	public static void labelWarning(String strDescription) throws Exception {

		try {
			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.ORANGE));
		} catch (Exception ex) {
			Log.info("Can't write to html report, initialize it first");
		}

	}


	public static String throwableToString(Throwable e) throws Exception {
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);) {
			e.printStackTrace(pw);

			return sw.toString();
		}
	}
	
	/**
	 * To write a passed step to report
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

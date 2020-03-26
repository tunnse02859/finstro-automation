package com.finstro.automation.utility;

import org.testng.Assert;
import com.finstro.automation.report.HtmlReporter;

public class Assertion {
	public static void assertTrue(boolean expected, String errorMess, String successMess) throws Exception {
		try {
			Assert.assertTrue(expected, errorMess);
			HtmlReporter.pass(successMess);
		} catch (AssertionError e) {
			HtmlReporter.fail(errorMess, e, "");
			throw e;
		}
	}

	public static void assertFalse(boolean expected, String errorMess, String successMess) throws Exception {
		try {
			Assert.assertFalse(expected, errorMess);
			HtmlReporter.pass(successMess);
		} catch (AssertionError e) {
			HtmlReporter.fail(errorMess, e, "");
			throw e;
		}
	}

	public static void assertEquals(String actualValue, String expectedValue, String errorMess, String successMess)
			throws Exception {
		try {
			Assert.assertEquals(actualValue, expectedValue, errorMess);
			HtmlReporter.pass(successMess + " - [" + actualValue + "]");
		} catch (AssertionError e) {
			HtmlReporter.fail(errorMess + " - expected [" + expectedValue + "] but found [" + actualValue + "]", e, "");
			throw e;
		}
	}

	public static void assertEquals(int actualValue, int expectedValue, String errorMess, String successMess)
			throws Exception {
		try {
			Assert.assertEquals(actualValue, expectedValue, errorMess);
			HtmlReporter.pass(successMess + " - [" + actualValue + "]");
		} catch (AssertionError e) {
			HtmlReporter.fail(errorMess + " - expected [" + expectedValue + "] but found [" + actualValue + "]", e, "");
			throw e;
		}
	}

	public static void assertContains(String actualValue, String expectedContain, String errorMess, String successMess)
			throws Exception {
		if (actualValue.contains(expectedContain)) {
			HtmlReporter.pass(successMess + " - [" + actualValue + "]");
		} else {
			HtmlReporter.fail(
					errorMess + " - expected contains [" + expectedContain + "] but found [" + actualValue + "]",
					new Exception(errorMess + " - expected contains [" + expectedContain + "] but found [" + actualValue + "]"),
					"");
		}
	}

	public static void assertNotNull(Object object, String errorMess, String successMess) throws Exception {
		try {
			Assert.assertNotNull(object, errorMess);
			HtmlReporter.pass(successMess);
		} catch (AssertionError e) {
			HtmlReporter.fail(errorMess, e, "");
			throw e;
		}
	}
}

package com.finstro.automation.utility;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

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
					new Exception(errorMess + " - expected contains [" + expectedContain + "] but found [" + actualValue
							+ "]"),
					"");
			throw new Exception(
					errorMess + " - expected contains [" + expectedContain + "] but found [" + actualValue + "]");
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

	public static void assertDateTimeFormat(String datetimeValue, String format, String errorMess, String successMess) throws Exception {
		if (isValidFormat(format, datetimeValue)) {
			HtmlReporter.pass(successMess + " - [" + datetimeValue + "]");
		} else {
			HtmlReporter.fail(errorMess + " - expected format [" + format + "] but found [" + datetimeValue + "]",
					new Exception(errorMess + " - expected format [" + format + "] but found [" + datetimeValue + "]"),
					"");
			throw new Exception(
					errorMess + " - expected format [" + format + "] but found [" + datetimeValue + "]");
		}
	}

	public static boolean isValidFormat(String format, String value) {
		LocalDateTime ldt = null;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format);
		try {
			ldt = LocalDateTime.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} catch (DateTimeParseException e) {
			try {
				LocalDate ld = LocalDate.parse(value, fomatter);
				String result = ld.format(fomatter);
				return result.equals(value);
			} catch (DateTimeParseException exp) {
				try {
					LocalTime lt = LocalTime.parse(value, fomatter);
					String result = lt.format(fomatter);
					return result.equals(value);
				} catch (DateTimeParseException e2) {
					// Debugging purposes
					// e2.printStackTrace();
				}
			}
		}
		return false;
	}
}

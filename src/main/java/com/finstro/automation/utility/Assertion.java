package com.finstro.automation.utility;

import org.testng.Assert;
import com.finstro.automation.report.HtmlReporter;

public class Assertion {
	public static void assertTrue(boolean expected, String errorMess, String successMess) throws Exception {
		try {
			Assert.assertTrue(expected,errorMess);
			HtmlReporter.pass(successMess);
		}catch(AssertionError e) {
			HtmlReporter.fail(errorMess,e,"");
			throw e;
		}
	}
	
	public static void assertEquals(String actualValue, String expectedValue,String errorMess, String successMess) throws Exception {
		try {
			Assert.assertEquals(actualValue,expectedValue,errorMess);
			HtmlReporter.pass(successMess + " - [" + actualValue + "]");
		}catch(AssertionError e) {
			HtmlReporter.fail(errorMess + " - expected [" + expectedValue + "] but found [" + actualValue + "]",e,"");
			throw e;
		}
	}
	
	public static void assertNotNull(Object object,String errorMess, String successMess) throws Exception {
		try {
			Assert.assertNotNull(object,errorMess);
			HtmlReporter.pass(successMess);
		}catch(AssertionError e) {
			HtmlReporter.fail(errorMess,e,"");
			throw e;
		}
	}
}

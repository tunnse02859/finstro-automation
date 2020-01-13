package com.finstro.automation.utility;

import org.testng.Assert;
import com.finstro.automation.report.HtmlReporter;

public class Assertion {
	public static void assertTrue(boolean expected, String errorMess, String successMess) throws Exception {
		try {
			Assert.assertTrue(expected,errorMess);
			HtmlReporter.pass(successMess);
		}catch(Exception e) {
			HtmlReporter.fail(errorMess,e,"");
			throw e;
		}
	}
	
	public static void assertEquals(String expectedValue, String actualValue,String errorMess, String successMess) throws Exception {
		try {
			Assert.assertEquals(expectedValue,actualValue,errorMess);
			HtmlReporter.pass(successMess);
		}catch(Exception e) {
			HtmlReporter.fail(errorMess,e,"");
			throw e;
		}
	}
}

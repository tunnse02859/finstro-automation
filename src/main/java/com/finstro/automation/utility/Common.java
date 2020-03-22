package com.finstro.automation.utility;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.finstro.automation.report.Log;

public class Common {
	private static final String LOWER_ALPHA = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPER_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMERIC = "0123456789";
	public static String currentTest = "";
	
	public static String randomAlphaNumeric(int stringLength) {
		String base = LOWER_ALPHA + UPPER_ALPHA + NUMERIC;
		StringBuilder builder = new StringBuilder();
		while (stringLength-- != 0) {
			int character = (int) (Math.random() * base.length());
			builder.append(base.charAt(character));
		}
		return builder.toString();
	}
	
	public static String randomLowerAlpha(int stringLength) {
		StringBuilder builder = new StringBuilder();
		while (stringLength-- != 0) {
			int character = (int) (Math.random() * LOWER_ALPHA.length());
			builder.append(LOWER_ALPHA.charAt(character));
		}
		return builder.toString();
	}
	
	public static String randomNumeric(int stringLength) {
		StringBuilder builder = new StringBuilder();
		while (stringLength-- != 0) {
			int character = (int) (Math.random() * NUMERIC.length());
			builder.append(NUMERIC.charAt(character));
		}
		return builder.toString();
	}
	
	public static String getTestVariable(String key,boolean convertNullToBlank) throws Exception {
		String value = PropertiesLoader.getPropertiesLoader().test_variables.getProperty(key);
		if(value == null) {
			throw new Exception("Variable = ["+ key +"] does not exist. please check your code");
		}
		if(convertNullToBlank) {
			if(value.equals("null")) {
				value = "";
			}
		}
		return value;
	}
	
	public static String throwableToString(Throwable e){
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);) {
			e.printStackTrace(pw);
			return sw.toString();
		}catch(Exception exception) {
			Log.error("Cannot convert Throwable to String");
			e.printStackTrace();
		}
		return "";
	}
}

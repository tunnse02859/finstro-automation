package com.finstro.automation.utility;

public class Common {
	private static final String LOWER_ALPHA = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPER_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMERIC = "0123456789";
	
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
}

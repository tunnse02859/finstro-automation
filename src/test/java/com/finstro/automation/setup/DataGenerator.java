package com.finstro.automation.setup;

import java.util.Random;

public class DataGenerator {

	private String[] bsbList = {"762732"};

	private String[] bankAccountNumberList = { "123456780", "123456781", "123456782", "123456783", "123456784",
			"123456785", "123456786", "123456787", "123456788", "123456789", "123456790" };

	private String[] debitCardNumberList = { "5203950337464077", "5203950332746387", "5203950336889332",
			"5203950333578953", "5203950338218035", "5203950332980226", "5203950332061159", "5203950334068764" };


	public int randomNumber(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public String generateBSBNumber() {
		int index = randomNumber(0, bsbList.length - 1);
		return bsbList[index];
	}

	public String generateBankAccountNumber() {
		int index = randomNumber(0, bankAccountNumberList.length - 1);
		return bankAccountNumberList[index];
	}

	public String generateStringByDateTime(String prefix) {
		return prefix + System.currentTimeMillis();
	}

	public String generateDebitCardNumber() {
		int index = randomNumber(0, debitCardNumberList.length - 1);
		return debitCardNumberList[index];
	}

	public String generateRandomString(int length) {
		String temp = "abcdefghijklmnopqrstuvxyz";
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < length; i ++) {
			builder.append(temp.charAt(randomNumber(0, temp.length() - 1)));
		}
		
		return builder.toString();
	}

}

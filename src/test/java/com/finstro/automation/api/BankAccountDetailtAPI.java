package com.finstro.automation.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.finstro.automation.setup.Constant;
import com.finstro.automation.utility.Common;

public class BankAccountDetailtAPI extends FinstroAPI {

	private String removeAccountEndpoint = "/api/CreditApplication/RemoveBankDetails";
	private String saveAccountEndpoint = "/api/CreditApplication/SaveBankDetails";

	public JSONArray getBankDetailInfo() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("bankDetails", "bankDetails").flush();
		JSONArray bankDetails = new JSONArray(Common.getTestVariable("bankDetails", true));
		return bankDetails;
	}

	public JSONObject getCreditCardInfoByName(String strNameOnAccount) throws Exception {
		JSONArray bankDetails = getBankDetailInfo();
		if (bankDetails.isEmpty()) {
			return null;
		}
		for (int i = 0; i < bankDetails.length(); i++) {

			JSONObject card = (JSONObject) bankDetails.get(i);

			if (card.getString("name").equals(strNameOnAccount)) {
				return card;
			}
		}

		return null;
	}

	public JSONObject getDefaultAccountInfo() throws Exception {
		JSONArray bankDetails = getBankDetailInfo();
		if (bankDetails.isEmpty()) {
			return null;
		}
		for (int i = 0; i < bankDetails.length(); i++) {
			JSONObject card = (JSONObject) bankDetails.get(i);
			if (card.getBoolean("isDefault") == true) {
				return card;
			}
		}

		return null;
	}

	public boolean isDefaultCard(String strNameOnAccount) throws Exception {
		JSONObject defaultAccount = getDefaultAccountInfo();

		return defaultAccount.getString("name").equals(strNameOnAccount);
	}

	public void removeCardByName(String strNameOnAccount) throws Exception {

		JSONObject account = getCreditCardInfoByName(strNameOnAccount);
		Thread.sleep(5000);
		if (account != null) {
			new APIRequest().baseUrl(Constant.API_HOST).path(removeAccountEndpoint)
					.addHeader("Content-Type", "application/json").oauth2(accessToken).body(account.toString()).post()
					.then().verifyResponseCode(200).flush();
		}
	}

	public void saveCard(JSONObject card) throws Exception {
		Thread.sleep(5000);
		new APIRequest().baseUrl(Constant.API_HOST).path(saveAccountEndpoint)
				.addHeader("Content-Type", "application/json").oauth2(accessToken).body(card.toString()).post().then()
				.verifyResponseCode(200).flush();

	}

}

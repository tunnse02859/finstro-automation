package com.finstro.automation.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.PropertiesLoader;

public class CreditCardAPI extends FinstroAPI {

	private String removeCardEnpoint = "/api/CreditApplication/RemoveCreditCard";
	private String saveCardEnpoint = "/api/CreditApplication/SaveCreditCard";

	public JSONArray getCreditCardsInfo() throws Exception {
		Object recoveryData = recoveryData().then().verifyResponseCode(200).getResponseBodyJson();
		JSONArray creditCardsDetails = (JSONArray) ((JSONObject) recoveryData).get("creditCardDetails");
		return creditCardsDetails;
	}

	public JSONObject getCreditCardInfoByName(String strNameOnCard) throws Exception {
		JSONArray creditCardsDetails = getCreditCardsInfo();

		if (creditCardsDetails.isEmpty()) {
			return null;
		}

		for (int i = 0; i < creditCardsDetails.length(); i++) {

			JSONObject card = (JSONObject) creditCardsDetails.get(i);

			if (card.getString("name").equals(strNameOnCard)) {
				return card;
			}

		}

		return null;
	}

	public JSONObject getDefaultCreditCardInfo() throws Exception {
		JSONArray creditCardsDetails = getCreditCardsInfo();

		if (creditCardsDetails.isEmpty()) {
			return null;
		}

		for (int i = 0; i < creditCardsDetails.length(); i++) {

			JSONObject card = (JSONObject) creditCardsDetails.get(i);

			if (card.getBoolean("mainAccount") == true) {
				return card;
			}

		}

		return null;
	}

	public boolean isDefaultCard(String strNameOncard) throws Exception {
		JSONObject defaultCard = getDefaultCreditCardInfo();

		return defaultCard.getString("name").equals(strNameOncard);
	}

	public void removeCardByName(String strNameOncard) throws Exception {

		JSONObject card = getCreditCardInfoByName(strNameOncard);
		Thread.sleep(5000);
		if (card != null) {
			new APIRequest().baseUrl(Constant.API_HOST).path(removeCardEnpoint)
					.addHeader("Content-Type", "application/json").oauth2(accessToken).body(card.toString()).post();
		}
	}

	public void saveCard(JSONObject card) throws Exception {
		Thread.sleep(5000);
		new APIRequest().baseUrl(Constant.API_HOST).path(saveCardEnpoint)
				.addHeader("Content-Type", "application/json").oauth2(accessToken).body(card.toString()).post();

	}

}

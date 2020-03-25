package com.finstro.automation.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.finstro.automation.setup.Constant;
import com.finstro.automation.utility.Common;

public class CreditCardAPI extends FinstroAPI {

	private String removeCardEnpoint = "/api/CreditApplication/RemoveCreditCard";
	private String saveCardEnpoint = "/api/CreditApplication/SaveCreditCard";

	public JSONArray getCreditCardsInfo() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("creditCardDetails", "creditCardDetails")
				.flush();
		JSONArray creditCardsDetails = new JSONArray(Common.getTestVariable("creditCardDetails", true));
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
		if (card != null) {
			
			if(card.get("mainAccount").equals(true)) {
				card.put("mainAccount", false);
				saveCard(card);
			}
			
			new APIRequest().baseUrl(Constant.API_HOST).path(removeCardEnpoint)
					.addHeader("Content-Type", "application/json").oauth2(accessToken).body(card.toString()).post()
					.then().verifyResponseCode(200).flush();
		}
	}

	public void saveCard(JSONObject card) throws Exception {
		if (card != null) {

			new APIRequest().baseUrl(Constant.API_HOST).path(saveCardEnpoint)
					.addHeader("Content-Type", "application/json").oauth2(accessToken).body(card.toString()).post()
					.then().verifyResponseCode(200).flush();
		}

	}

}

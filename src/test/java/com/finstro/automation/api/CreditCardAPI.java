package com.finstro.automation.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.PropertiesLoader;

public class CreditCardAPI extends FinstroAPI{


	private String removeCardEnpoint = "/api/CreditApplication/RemoveCreditCard";
	
	public JSONArray getCreditCardsInfo() throws Exception {
		Object recoveryData =  recoveryData().then().verifyResponseCode(200)
				.getResponseBodyJson();
		JSONArray creditCardsDetails = (JSONArray) ((JSONObject) recoveryData).get("creditCardDetails");
		return creditCardsDetails;
	}
	
	public JSONObject getCreditCardInfoByName(String strNameOnCard) throws Exception {
		JSONArray creditCardsDetails = getCreditCardsInfo();
		
		if (creditCardsDetails.isEmpty()) {
			return null;
		}
		
		for(int i = 0 ; i < creditCardsDetails.length(); i ++ ) {
			
			JSONObject card = (JSONObject) creditCardsDetails.get(i);
			
			if(card.getString("name").equals(strNameOnCard)) {
				Log.info(card.toString());
				return card;
			}
			
		}
		
		return null;
	}
	
	public void removeCardByName(String strNameOncard) throws Exception {
		
		JSONObject card = getCreditCardInfoByName(strNameOncard);
		
		if(card != null) {
			APIRequest response = new APIRequest().baseUrl(Constant.API_HOST).path(removeCardEnpoint).addHeader("Content-Type", "application/json")
					.oauth2(accessToken).body(card.toString()).post();
			
			Assert.assertTrue(response.getStatusCode() == 200, "Remove card unsuccessfully");
		}
	}

}

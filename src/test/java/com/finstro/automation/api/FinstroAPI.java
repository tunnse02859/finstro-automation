package com.finstro.automation.api;

import org.json.JSONObject;

import com.finstro.automation.setup.Constant;
import com.finstro.automation.utility.Common;

public class FinstroAPI {
	
	
	private String accessToken;
	private String loginPath = "/api/Authentication/SignIn";
	private String recoveryDataPath = "/api/CreditApplication/AppRecovery";
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public APIRequest login(String username, String password) throws Exception {
		JSONObject loginBody = new JSONObject();
		loginBody.put("username", username);
		loginBody.put("password", password);
		
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST)
			.path(loginPath)
			.addHeader("Content-Type", "application/json")
			.body(loginBody.toString())
			.post();
		return request;
	}
	
	public APIRequest recoveryData() throws Exception {
		JSONObject requestBody = new JSONObject();
		requestBody.put("forceCardUpdate", true);
		
		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST)
			.path(recoveryDataPath)
			.addHeader("Content-Type", "application/json")
			.oauth2(accessToken)
			.body(requestBody.toString())
			.post();
		return request;
	}

}

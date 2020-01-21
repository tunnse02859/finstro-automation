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
		request.baseUrl(Constant.API_HOST).path(loginPath).addHeader("Content-Type", "application/json")
				.body(loginBody.toString()).post();
		return request;
	}

	public void loginForAccessToken(String username, String password) throws Exception {
		login(username, password).then().verifyResponseCode(200).extractJsonValue("accessToken").flush();
		setAccessToken(Common.getTestVariable("accessToken"));
	}

	public APIRequest recoveryData() throws Exception {
		JSONObject requestBody = new JSONObject();
		requestBody.put("forceCardUpdate", true);

		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(recoveryDataPath).addHeader("Content-Type", "application/json")
				.oauth2(accessToken).body(requestBody.toString()).post();
		return request;
	}

	public String getResidentialAddress() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("country", "residentialAddress.country")
				.extractJsonValue("postCode", "residentialAddress.postCode")
				.extractJsonValue("state", "residentialAddress.state")
				.extractJsonValue("streetName", "residentialAddress.streetName")
				.extractJsonValue("streetNumber", "residentialAddress.streetNumber")
				.extractJsonValue("streetType", "residentialAddress.streetType")
				.extractJsonValue("suburb", "residentialAddress.suburb").flush();
		String residentialAddress = String.format("%s %s %s, %s %s %s", Common.getTestVariable("streetNumber"),
				Common.getTestVariable("streetName"), Common.getTestVariable("streetType"),
				Common.getTestVariable("suburb"), Common.getTestVariable("state"), Common.getTestVariable("postCode"));
		return residentialAddress;
	}

	public void getDrivingLicenceInfor() throws Exception {
		recoveryData().then().verifyResponseCode(200)
				.extractJsonValue("firstName", "drivingLicence.firstName")
				.extractJsonValue("middleName", "drivingLicence.middleName")
				.extractJsonValue("surname", "drivingLicence.surname")
				.extractJsonValue("gender", "drivingLicence.gender")
				.extractJsonValue("dateOfBirth", "drivingLicence.dateOfBirth")
				.extractJsonValue("licenceNumber", "drivingLicence.licenceNumber")
				.extractJsonValue("state", "drivingLicence.state")
				.extractJsonValue("validTo", "drivingLicence.validTo")
			.flush();
		
	}

}

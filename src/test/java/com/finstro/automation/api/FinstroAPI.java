package com.finstro.automation.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.finstro.automation.report.Log;
import com.finstro.automation.setup.Constant;
import com.finstro.automation.utility.Common;
import com.finstro.automation.utility.PropertiesLoader;

public class FinstroAPI {

	public String accessToken;
	private String loginPath = "/api/Authentication/SignIn";
	private String recoveryDataPath = "/api/CreditApplication/AppRecovery";
	private String currentUserDetail = "/api/Authentication/CurrentUserDetails";

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
		setAccessToken(Common.getTestVariable("accessToken", true));
	}

	public APIRequest recoveryData() throws Exception {
		JSONObject requestBody = new JSONObject();
		requestBody.put("forceCardUpdate", false);

		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(recoveryDataPath).addHeader("Content-Type", "application/json")
				.oauth2(accessToken).body(requestBody.toString()).post();
		return request;
	}

	public APIRequest currentUserDetail() throws Exception {
		JSONObject requestBody = new JSONObject();
		requestBody.put("emailAddress", "");
		requestBody.put("mobilePhoneNumber", "");

		APIRequest request = new APIRequest();
		request.baseUrl(Constant.API_HOST).path(currentUserDetail).addHeader("Content-Type", "application/json")
				.oauth2(accessToken).body(requestBody.toString()).post();
		return request;
	}

	public String getBusinessDetailInfor() throws Exception {
		recoveryData().then().verifyResponseCode(200)
				// business infor
				.extractJsonValue("abn", "businessDetails.asicBusiness.abn")
				.extractJsonValue("acn", "businessDetails.asicBusiness.acn")
				.extractJsonValue("entityName", "businessDetails.asicBusiness.companyLegalName")
				.extractJsonValue("businessName", "businessDetails.asicBusiness.companyName")
				.extractJsonValue("type", "businessDetails.asicBusiness.type")
				.extractJsonValue("businessNameId", "businessDetails.asicBusiness.businessNameId")
				.extractJsonValue("parentCompany", "businessDetails.asicBusiness.parentCompany")
				.extractJsonValue("gstDate", "businessDetails.gstDate")
				.extractJsonValue("timeTrading", "businessDetails.timeTrading")

				// business trading address
				.extractJsonValue("country", "businessDetails.businessTradingAddress.country")
				.extractJsonValue("postCode", "businessDetails.businessTradingAddress.postCode")
				.extractJsonValue("state", "businessDetails.businessTradingAddress.state")
				.extractJsonValue("streetName", "businessDetails.businessTradingAddress.streetName")
				.extractJsonValue("streetNumber", "businessDetails.businessTradingAddress.streetNumber")
				.extractJsonValue("streetType", "businessDetails.businessTradingAddress.streetType")
				.extractJsonValue("suburb", "businessDetails.businessTradingAddress.suburb")
				.extractJsonValue("unitOrLevel", "businessDetails.businessTradingAddress.unitOrLevel")

				// other
				.extractJsonValue("email", "businessDetails.email")
				.extractJsonValue("phoneNumber", "businessDetails.phoneNumber")
				.extractJsonValue("website", "businessDetails.website")
				.extractJsonValue("facebook", "businessDetails.facebook")
				.extractJsonValue("twitter", "businessDetails.twitter")
				.extractJsonValue("instagram", "businessDetails.instagram")
				.extractJsonValue("skype", "businessDetails.skype")
				.extractJsonValue("linkedin", "businessDetails.linkedin")
				.extractJsonValue("other", "businessDetails.other")
				.extractJsonValue("incorporationDate", "businessDetails.incorporationDate")
				.extractJsonValue("phoneNumber", "businessDetails.phoneNumber")
				.extractJsonValue("gstDate", "businessDetails.gstDate")
				.extractJsonValue("timeTrading", "businessDetails.timeTrading").flush();

		String businessTradingAddress = String.format("%s, %s %s %s, %s %s %s", 
				Common.getTestVariable("unitOrLevel", true),
				Common.getTestVariable("streetNumber", true),
				Common.getTestVariable("streetName", true), 
				Common.getTestVariable("streetType", true),
				Common.getTestVariable("suburb", true),
				Common.getTestVariable("state", true),
				Common.getTestVariable("postCode", true));
		if(businessTradingAddress.charAt(0) == ',') {
			businessTradingAddress = businessTradingAddress.substring(1);
		}
		return businessTradingAddress.trim();
	}

	public void getPostalAddressInfor() throws Exception {
		recoveryData().then().verifyResponseCode(200)
				// business trading address
				.extractJsonValue("businessTradingAddress.country", "businessDetails.businessTradingAddress.country")
				.extractJsonValue("businessTradingAddress.postCode", "businessDetails.businessTradingAddress.postCode")
				.extractJsonValue("businessTradingAddress.state", "businessDetails.businessTradingAddress.state")
				.extractJsonValue("businessTradingAddress.streetName",
						"businessDetails.businessTradingAddress.streetName")
				.extractJsonValue("businessTradingAddress.streetNumber",
						"businessDetails.businessTradingAddress.streetNumber")
				.extractJsonValue("businessTradingAddress.streetType",
						"businessDetails.businessTradingAddress.streetType")
				.extractJsonValue("businessTradingAddress.suburb", "businessDetails.businessTradingAddress.suburb")
				.extractJsonValue("businessTradingAddress.unitOrLevel", "businessDetails.businessTradingAddress.unitOrLevel")
				// residential address
				.extractJsonValue("residentialAddress.country", "residentialAddress.country")
				.extractJsonValue("residentialAddress.postCode", "residentialAddress.postCode")
				.extractJsonValue("residentialAddress.state", "residentialAddress.state")
				.extractJsonValue("residentialAddress.streetName", "residentialAddress.streetName")
				.extractJsonValue("residentialAddress.streetNumber", "residentialAddress.streetNumber")
				.extractJsonValue("residentialAddress.streetType", "residentialAddress.streetType")
				.extractJsonValue("residentialAddress.suburb", "residentialAddress.suburb")
				.extractJsonValue("residentialAddress.unitOrLevel", "residentialAddress.unitOrLevel")
				// selected address for postal address
				.extractJsonValue("postalAddress.country", "finstroCards[0].postalAddress.country")
				.extractJsonValue("postalAddress.postCode", "finstroCards[0].postalAddress.postCode")
				.extractJsonValue("postalAddress.state", "finstroCards[0].postalAddress.state")
				.extractJsonValue("postalAddress.streetName", "finstroCards[0].postalAddress.streetName")
				.extractJsonValue("postalAddress.streetNumber", "finstroCards[0].postalAddress.streetNumber")
				.extractJsonValue("postalAddress.streetType", "finstroCards[0].postalAddress.streetType")
				.extractJsonValue("postalAddress.suburb", "finstroCards[0].postalAddress.suburb")
				.extractJsonValue("postalAddress.unitOrLevel", "finstroCards[0].postalAddress.unitOrLevel")
				.flush();
		String businessTradingAddress = String.format("%s, %s %s %s, %s %s %s", 
				Common.getTestVariable("businessTradingAddress.unitOrLevel", true),
				Common.getTestVariable("businessTradingAddress.streetNumber", true),
				Common.getTestVariable("businessTradingAddress.streetName", true), 
				Common.getTestVariable("businessTradingAddress.streetType", true),
				Common.getTestVariable("businessTradingAddress.suburb", true),
				Common.getTestVariable("businessTradingAddress.state", true),
				Common.getTestVariable("businessTradingAddress.postCode", true));
		if(businessTradingAddress.charAt(0) == ',') {
			businessTradingAddress = businessTradingAddress.substring(1);
		}
		PropertiesLoader.getPropertiesLoader().test_variables.setProperty("businessTradingAddress",
				businessTradingAddress.trim());

		String residentialAddress = String.format("%s, %s %s %s, %s %s %s", 
				Common.getTestVariable("postalAddress.unitOrLevel", true),
				Common.getTestVariable("postalAddress.streetNumber", true),
				Common.getTestVariable("postalAddress.streetName", true), 
				Common.getTestVariable("postalAddress.streetType", true),
				Common.getTestVariable("postalAddress.suburb", true),
				Common.getTestVariable("postalAddress.state", true),
				Common.getTestVariable("postalAddress.postCode", true));
		if(residentialAddress.charAt(0) == ',') {
			residentialAddress = residentialAddress.substring(1);
		}
		PropertiesLoader.getPropertiesLoader().test_variables.setProperty("residentialAddress", residentialAddress.trim());

		String postalAddress = String.format("%s, %s %s %s, %s %s %s",
				Common.getTestVariable("postalAddress.unitOrLevel", true),
				Common.getTestVariable("postalAddress.streetNumber", true),
				Common.getTestVariable("postalAddress.streetName", true),
				Common.getTestVariable("postalAddress.streetType", true),
				Common.getTestVariable("postalAddress.suburb", true),
				Common.getTestVariable("postalAddress.state", true),
				Common.getTestVariable("postalAddress.postCode", true));
		if(postalAddress.charAt(0) == ',') {
			postalAddress = postalAddress.substring(1);
		}
		PropertiesLoader.getPropertiesLoader().test_variables.setProperty("postalAddress", postalAddress.trim());
	}

	public String getResidentialAddress() throws Exception {
		recoveryData().then().verifyResponseCode(200)
				.extractJsonValue("country", "residentialAddress.country")
				.extractJsonValue("postCode", "residentialAddress.postCode")
				.extractJsonValue("state", "residentialAddress.state")
				.extractJsonValue("streetName", "residentialAddress.streetName")
				.extractJsonValue("streetNumber", "residentialAddress.streetNumber")
				.extractJsonValue("streetType", "residentialAddress.streetType")
				.extractJsonValue("suburb", "residentialAddress.suburb")
				.extractJsonValue("unitOrLevel", "residentialAddress.unitOrLevel")
			.flush();
		String residentialAddress = String.format("%s, %s %s %s, %s %s %s", 
				Common.getTestVariable("unitOrLevel", true),
				Common.getTestVariable("streetNumber", true),
				Common.getTestVariable("streetName", true), 
				Common.getTestVariable("streetType", true),
				Common.getTestVariable("suburb", true),
				Common.getTestVariable("state", true),
				Common.getTestVariable("postCode", true));
		if(residentialAddress.charAt(0) == ',') {
			residentialAddress = residentialAddress.substring(1);
		}
		return residentialAddress.trim();
	}

	public String getBusinessTradingAddress() throws Exception {
		recoveryData().then().verifyResponseCode(200)
				.extractJsonValue("country", "businessDetails.businessTradingAddress.country")
				.extractJsonValue("postCode", "businessDetails.businessTradingAddress.postCode")
				.extractJsonValue("state", "businessDetails.businessTradingAddress.state")
				.extractJsonValue("streetName", "businessDetails.businessTradingAddress.streetName")
				.extractJsonValue("streetNumber", "businessDetails.businessTradingAddress.streetNumber")
				.extractJsonValue("streetType", "businessDetails.businessTradingAddress.streetType")
				.extractJsonValue("suburb", "businessDetails.businessTradingAddress.suburb")
				.extractJsonValue("unitOrLevel", "businessDetails.businessTradingAddress.unitOrLevel")
				.flush();
		String businessTradingAddress = String.format("%s, %s %s %s, %s %s %s", 
				Common.getTestVariable("unitOrLevel", true),
				Common.getTestVariable("streetNumber", true),
				Common.getTestVariable("streetName", true), 
				Common.getTestVariable("streetType", true),
				Common.getTestVariable("suburb", true),
				Common.getTestVariable("state", true),
				Common.getTestVariable("postCode", true));
		if(businessTradingAddress.charAt(0) == ',') {
			businessTradingAddress = businessTradingAddress.substring(1);
		}
		return businessTradingAddress.trim();
	}

	public void getDrivingLicenceInfor() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("firstName", "drivingLicence.firstName")
				.extractJsonValue("middleName", "drivingLicence.middleName")
				.extractJsonValue("surname", "drivingLicence.surname")
				.extractJsonValue("gender", "drivingLicence.gender")
				.extractJsonValue("dateOfBirth", "drivingLicence.dateOfBirth")
				.extractJsonValue("licenceNumber", "drivingLicence.licenceNumber")
				.extractJsonValue("state", "drivingLicence.state").extractJsonValue("validTo", "drivingLicence.validTo")
				.extractJsonValue("driverLicenseJson", "drivingLicence").flush();
	}

	public void getMedicareInfor() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("cardColor", "medicareCard.cardColor")
				.extractJsonValue("cardNumber", "medicareCard.cardNumber")
				.extractJsonValue("cardNumberRef", "medicareCard.cardNumberRef")
				.extractJsonValue("dateOfBirth", "medicareCard.dateOfBirth")
				.extractJsonValue("firstName", "medicareCard.firstName")
				.extractJsonValue("gender", "medicareCard.gender")
				.extractJsonValue("identificationId", "medicareCard.identificationId")
				.extractJsonValue("middleInitial", "medicareCard.middleInitial")
				.extractJsonValue("surname", "medicareCard.surname").extractJsonValue("validTo", "medicareCard.validTo")
				.extractJsonValue("medicareJson", "medicareCard").flush();
	}

	public void getProfileDetailInfor() throws Exception {
		recoveryData().then().verifyResponseCode(200)
				.extractJsonValue("contacts.emailAddress", "contacts[0].emailAddress")
				.extractJsonValue("contacts.familyName", "contacts[0].familyName")
				.extractJsonValue("contacts.firstGivenName", "contacts[0].firstGivenName")
				.extractJsonValue("contacts.mobilePhoneNumber", "contacts[0].mobilePhoneNumber")
				.extractJsonValue("contacts.dob", "contacts[0].dob").flush();
	}

	public void getHomePageValues() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("availableBalance", "availableBalance")
				.extractJsonValue("balance", "balance").extractJsonValue("nextBillAmount", "nextBillAmount")
				.extractJsonValue("limit", "businessDetails.selectedCreditAmount").flush();
	}

	public void getApprovalStatus() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("idCheckStatus", "idCheckPassed")
				.extractJsonValue("bankStatementStatus", "bankStatementDone")
				.extractJsonValue("directDebitAuthorityStatus", "directDebitAuthority.done")
				.extractJsonValue("creditAssessmentStatus", "creditAssessmentStatus").flush();
	}

	public JSONArray getBankAccountsInfo() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("bankAccounts", "bankData.bankAccounts").flush();
		JSONArray bankAccounts = null;
		try {
			bankAccounts = new JSONArray(Common.getTestVariable("bankAccounts", false));
		} catch (Exception e) {
			Log.error("Bank accounts are not submitted");
			return null;
		}
		return bankAccounts;
	}
	
	public JSONArray getDirectors() throws Exception {
		recoveryData().then().verifyResponseCode(200).extractJsonValue("directors", "directors").flush();
		JSONArray bankAccounts = null;
		try {
			bankAccounts = new JSONArray(Common.getTestVariable("directors", false));
		} catch (Exception e) {
			Log.error("Cannot found any directors");
			return null;
		}
		return bankAccounts;
	}

}

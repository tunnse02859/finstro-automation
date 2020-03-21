package com.finstro.automation.api;


import com.finstro.automation.setup.Constant;

public class ProfileInforAPI extends FinstroAPI {

	private String saveDrivingLicense = "/api/CreditApplication/SaveDriversLicence";
	private String saveMedicare = "/api/CreditApplication/SaveMedicare";

	public void saveDrivingLicense(String drivingLicenseData) throws Exception {
		if (drivingLicenseData != null && !drivingLicenseData.equals("")) {
			new APIRequest().baseUrl(Constant.API_HOST).path(saveDrivingLicense)
					.addHeader("Content-Type", "application/json").oauth2(accessToken).body(drivingLicenseData).post()
					.then().verifyResponseCode(200).flush();
		}

	}

	public void saveMedicare(String medicareData) throws Exception {
		if (medicareData != null && !medicareData.equals("")) {
			new APIRequest().baseUrl(Constant.API_HOST).path(saveMedicare).addHeader("Content-Type", "application/json")
					.oauth2(accessToken).body(medicareData).post().then().verifyResponseCode(200).flush();
		}

	}

}

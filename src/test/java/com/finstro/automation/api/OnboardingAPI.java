package com.finstro.automation.api;

import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.setup.Constant;

public class OnboardingAPI extends FinstroAPI{
	
	private String saveBusinessDetailAPI = "/api/CreditApplication/SaveBusinessDetail";
	private String saveResidentialAddressAPI = "/api/CreditApplication/SaveResidentialAddress";
	private String saveDrirvingLicenseAPI = "/api/CreditApplication/SaveDriversLicence";
	private String saveMedicareAPI = "/api/CreditApplication/SaveMedicare";
	
	public void setupBusinessDetail(String selectedAmount) throws Exception {
		String data = "{" + 
				"	'asicBusiness': {" + 
				"		'acn': ''," + 
				"		'abn': '94330845794'," + 
				"		'type': 'SOLE_TRADER'," + 
				"		'companyName': 'TILLKOO'," + 
				"		'companyLegalName': 'RUSSELL, DAREN IAN'" + 
				"	}," + 
				"	'businessTradingAddress': {" + 
				"		'postCode': '6054'," + 
				"		'suburb': 'ASHFIELD'," + 
				"		'country': 'Australia'," + 
				"		'addressId': 0," + 
				"		'streetType': 'St'," + 
				"		'streetName': 'Margaret'," + 
				"		'unitOrLevel': ''," + 
				"		'streetNumber': '50'," + 
				"		'state': 'WA'" + 
				"	}," + 
				"	'selectedCreditAmount': "+selectedAmount+"" + 
				"}";
		HtmlReporter.label("Setup Business detail by API request");
		new APIRequest().baseUrl(Constant.API_HOST).path(saveBusinessDetailAPI)
				.addHeader("Content-Type", "application/json")
				.oauth2(accessToken)
				.body(data).post()
			.then()
				.verifyResponseCode(200)
				.flush();
	}
	
	public void setupResidentialAddress() throws Exception {
		String data = "{" + 
				"	'unitOrLevel': ''," + 
				"	'country': 'Australia'," + 
				"	'streetNumber': '60'," + 
				"	'state': 'NSW'," + 
				"	'streetType': 'St'," + 
				"	'suburb': 'SYDNEY'," + 
				"	'addressId': 0," + 
				"	'postCode': '2000'," + 
				"	'streetName': 'Margaret'" + 
				"}";
		
		new APIRequest().baseUrl(Constant.API_HOST).path(saveResidentialAddressAPI)
				.addHeader("Content-Type", "application/json")
				.oauth2(accessToken)
				.body(data).post()
			.then()
				.verifyResponseCode(200)
				.flush();
	}
	
	public void setupDrivingLicense() throws Exception {
		String data = "{" + 
				"	'licenceNumber': '012345'," + 
				"	'gender': 'F'," + 
				"	'surname': 'Nguyen'," + 
				"	'dateOfBirth': '1993-07-20'," + 
				"	'firstName': 'Tu'," + 
				"	'middleName': 'Ngoc'," + 
				"	'state': 'SA'," + 
				"	'validTo': '2025-12-31'" + 
				"}";
		
		new APIRequest().baseUrl(Constant.API_HOST).path(saveDrirvingLicenseAPI)
				.addHeader("Content-Type", "application/json")
				.oauth2(accessToken)
				.body(data).post()
			.then()
				.verifyResponseCode(200)
				.flush();
	}
	
	public void setupMedicare() throws Exception {
		String data = "{" + 
				"	'gender': 'F'," + 
				"	'middleInitial': 'Ngoc'," + 
				"	'validTo': '2025-12-01'," + 
				"	'cardColor': 'G'," + 
				"	'firstName': 'Tu'," + 
				"	'surname': 'Nguyen'," + 
				"	'cardNumber': '3501803151'," + 
				"	'dateOfBirth': '1993-07-20'," + 
				"	'cardNumberRef': 6" + 
				"}";
		
		new APIRequest().baseUrl(Constant.API_HOST).path(saveMedicareAPI)
				.addHeader("Content-Type", "application/json")
				.oauth2(accessToken)
				.body(data).post()
			.then()
				.verifyResponseCode(200)
				.flush();
	}

}
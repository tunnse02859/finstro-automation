package com.finstro.automation.common;

import java.util.HashMap;

public class CommonFunction {
	
	public static HashMap<String, String> getBusinessDataByType(String businessType,Object[][] businessData) {
		HashMap<String, String> data = null;
		for (Object[] rowData : businessData) {
			HashMap<String, String> hashMap = (HashMap<String, String>) rowData[0];
			data = hashMap;
			if (data.get("Business Type").equals(businessType)) {
				return data;
			}
		}
		return data;
	}
}

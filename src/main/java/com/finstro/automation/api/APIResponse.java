package com.finstro.automation.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.finstro.automation.report.markuphelper.MarkupHelper;
import com.finstro.automation.utility.PropertiesLoader;
import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.report.Log;

/**
 * @author ADT-Macbook
 *
 */
/**
 * @author ADT-Macbook
 *
 */
public class APIResponse {
	private HttpResponse response;
	private HttpUriRequest request;

	private List<String> assertionList;
	private List<String> extractedVariable;
	private Object responseBodyJson;
	private String responseBody;
	private Exception exception;

	public APIResponse(HttpUriRequest request, HttpResponse response) {
		this.request = request;
		this.response = response;
		getResponseBodyString();
		getResponseBodyJsonObject();
		assertionList = new ArrayList<String>();
		extractedVariable = new ArrayList<String>();
	}

	public Object getResponseBodyJsonObject() {
		try {
			if (responseBody == null || responseBody.equals("")) {
				responseBodyJson = null;
			} else if (responseBody.charAt(0) == '[') {
				responseBodyJson = new JSONArray(responseBody);
			} else {
				responseBodyJson = new JSONObject(responseBody);
			}
		} catch (JSONException e) {
			Log.warn("Cannot convert response body to json object/array - " + e.getMessage());
			responseBodyJson = null;
		}

		return responseBodyJson;
	}

	public int getStatusCode() {
		return this.response.getStatusLine().getStatusCode();
	}

	public String getResponseBodyString() {
		try {
			HttpEntity responseEntity = response.getEntity();
			responseBody = EntityUtils.toString(responseEntity);
		} catch (Exception e) {
			exception = e;
		}
		return responseBody;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public APIResponse verifyResponseCode(int expectedCode) throws Exception {
		int actualCode = this.response.getStatusLine().getStatusCode();
		if (expectedCode == actualCode) {
			assertionList.add("[PASSED] Response code matched with expectation: [" + expectedCode + "]");
			Log.info("[Assertion][PASSED] Response code matched with expectation: [" + expectedCode + "]");
		} else {
			assertionList.add("[FAILED] Response code is incorrect.  Expected [" + expectedCode + "] but got ["
					+ actualCode + "]");
			Log.error("[Assertion][FAILED] Response code is incorrect.  Expected [" + expectedCode + "] but got ["
					+ actualCode + "]");
		}
		return this;
	}

	public APIResponse verifyJsonNodeEqual(String regex, String expectedValue) {
		try {
			if (responseBodyJson != null) {
				JSONObject currentNode = (JSONObject) responseBodyJson;
				String[] keys = regex.split("\\.");
				try {
					for (int i = 0; i < keys.length; i++) {
						String currentExtractKey = keys[i];
						String key = "";
						String indexInArray = StringUtils.substringBetween(currentExtractKey, "[", "]");
						if (indexInArray != null) {
							key = currentExtractKey.substring(0, currentExtractKey.indexOf("["));
						} else {
							key = currentExtractKey;
						}
						if (currentNode.has(key)) {
							Object extractedNode = currentNode.get(key);
							if ((indexInArray != null) && (extractedNode instanceof JSONArray)) {
								extractedNode = ((JSONArray) extractedNode).get(Integer.parseInt(indexInArray));
							} else if ((indexInArray != null) && !(extractedNode instanceof JSONArray)) {
								assertionList.add("[FAILED] [" + key + "] in [" + regex + "] is not an json array");
								Log.info("[Extract][FAILED] [" + key + "] in [" + regex + "] is not an json array");
								return this;
							}
							if (i == keys.length - 1) {
								String extractedValue = extractedNode == null ? "" : extractedNode.toString();
								// if(extractedValue == null || extractedValue.equals("null")) {
								// extractedValue = "";
								// }
								if (expectedValue.equals(extractedValue)) {
									assertionList.add("[PASSED] value of [" + key + "] matched with expectation: ["
											+ expectedValue + "]");
									Log.info("[Assertion][PASSED] value of [" + key + "] matched with expectation: ["
											+ expectedValue + "]");
								} else {
									assertionList.add("[FAILED] value of [" + key + "] is incorrect.  Expected ["
											+ expectedValue + "] but got [" + extractedValue + "]");
									Log.error("[Assertion][FAILED] value of [" + key + "] is incorrect.  Expected ["
											+ expectedValue + "] but got [" + extractedValue + "]");
								}
							} else if (!(extractedNode instanceof JSONObject)
									&& !(extractedNode instanceof JSONArray)) {
								assertionList.add("[FAILED] key [" + key + "] in [" + regex + "]does not contain ["
										+ keys[i + 1] + "]");
								Log.error("[Assertion][FAILED] key [" + key + "] in [" + regex + "] does not contain ["
										+ keys[i + 1] + "]");
								return this;
							} else {
								currentNode = (JSONObject) extractedNode;
							}
						} else {
							assertionList.add("[FAILED] key [" + key + "] in [" + regex + "] does not exist");
							Log.error("[Assertion][FAILED] key [" + key + "] in [" + regex + "] does not exist");
							return this;
						}
					}
				} catch (Exception e) {
					assertionList.add("[FAILED] Cannot extract value with [" + regex + "]");
					Log.error("[Assertion][FAILED] Cannot extract value with [" + regex + "]");
					exception = e;
				}
			} else {
				extractedVariable.add("[FAILED] Response body is not JSON for extract [" + regex + "]");
				Log.error("[Assertion][FAILED] Response body is not JSON for extract [" + regex + "]");
			}
		} catch (Exception e) {
			assertionList.add("[FAILED] Cannot extract value with [" + regex + "]");
			Log.error("[Assertion][FAILED] Cannot extract value with [" + regex + "]");
			exception = e;
			return this;
		}
		return this;
	}

	public APIResponse verifyBodyContain(String expectedValue) throws Exception {
		if (responseBody.contains(expectedValue)) {
			assertionList.add("[PASSED] Response body contains [" + expectedValue + "]");
			Log.info("[Assertion][PASSED] Response body contains [" + expectedValue + "]");
		} else {
			assertionList.add("[FAILED] Response body doesnt contain [" + expectedValue + "]");
			Log.error("[Assertion][FAILED] Response body doesnt contain [" + expectedValue + "]");
		}
		return this;
	}

	public APIResponse verifyBodyEquals(String expectedValue) throws Exception {
		if (responseBody.equals(expectedValue)) {
			assertionList.add("[PASSED] Response body contains [" + expectedValue + "]");
			Log.info("[Assertion][PASSED] Response body contains [" + expectedValue + "]");
		} else {
			assertionList.add("[FAILED] Response body doesnt contain [" + expectedValue + "]");
			Log.error("[Assertion][FAILED] Response body doesnt contain [" + expectedValue + "]");
		}
		return this;
	}

	public APIResponse extractJsonValue(String key) throws Exception {
		if (responseBodyJson != null) {
			JSONObject bodyInJson = (JSONObject) responseBodyJson;
			if (bodyInJson.has(key)) {
				String extractedValue = bodyInJson.get(key).toString();
				PropertiesLoader.getPropertiesLoader().test_variables.setProperty(key, extractedValue);
				extractedVariable.add("[SUCCESS] [" + key + "] = [" + extractedValue + "]");
				Log.info("[Extract][SUCCESS] [" + key + "] = [" + extractedValue + "]");
			} else {
				PropertiesLoader.getPropertiesLoader().test_variables.setProperty(key, "extract-failed");
				extractedVariable.add("[FAILED] key [" + key + "] does not exist. set default to [extract-failed]");
				Log.error("[Extract][FAILED] key [" + key + "] does not exist. set default to [extract-failed]");
			}
		} else {
			PropertiesLoader.getPropertiesLoader().test_variables.setProperty(key, "extract-failed");
			extractedVariable.add(
					"[FAILED] Response body is not JSON for extract [" + key + "]. set default to [extract-failed]");
			Log.error("[Extract][FAILED] Response body is not JSON for extract [" + key
					+ "]. set default to [extract-failed]");

		}
		return this;
	}
	
	/**
	 * This method is used to get the string value from a json object
	 * @param jsonObject
	 * @param keychain
	 * @return
	 * @throws Exception
	 */
	public String getStringValueFromJsonObject(Object jsonObject, String keychain) throws Exception {

		if (jsonObject != null) {

			JSONObject currentNode = (JSONObject) jsonObject;
			String[] keys = keychain.split("\\.");

			for (int i = 0; i < keys.length; i++) {
				String currentExtractKey = keys[i];
				String indexInArray = StringUtils.substringBetween(currentExtractKey, "[", "]");

				if (indexInArray != null) {
					currentExtractKey = currentExtractKey.substring(0, currentExtractKey.indexOf("["));
				}

				if (currentNode.has(currentExtractKey)) {

					Object extractedNode = currentNode.get(currentExtractKey);

					if (extractedNode instanceof JSONArray) {
						if (indexInArray != null) {
							extractedNode = ((JSONArray) extractedNode).get(Integer.parseInt(indexInArray));
						} else {
							extractedNode = ((JSONArray) extractedNode).get(0);
						}
					}

					if (i == keys.length - 1) {

						return extractedNode == null ? "" : extractedNode.toString();

					} else {
						currentNode = (JSONObject) extractedNode;
					}

				} else {
					throw new Exception(String.format("The key [%s] in keychain [%s] does not exist!!!",
							currentExtractKey, keychain));
				}
			}
		}

		throw new Exception("jsonObject is null!!!");

	}

	/**
	 * This method is used to get the string value from the api response
	 * @param keychain
	 * @return
	 * @throws Exception
	 */
	public String getStringValueFromResponseJson(String keychain) throws Exception {

		if (responseBodyJson != null) {

			JSONObject currentNode = (JSONObject) responseBodyJson;
			String[] keys = keychain.split("\\.");

			for (int i = 0; i < keys.length; i++) {
				String currentExtractKey = keys[i];
				String indexInArray = StringUtils.substringBetween(currentExtractKey, "[", "]");

				if (indexInArray != null) {
					currentExtractKey = currentExtractKey.substring(0, currentExtractKey.indexOf("["));
				}

				if (currentNode.has(currentExtractKey)) {

					Object extractedNode = currentNode.get(currentExtractKey);

					if (extractedNode instanceof JSONArray) {
						if (indexInArray != null) {
							extractedNode = ((JSONArray) extractedNode).get(Integer.parseInt(indexInArray));
						} else {
							extractedNode = ((JSONArray) extractedNode).get(0);
						}
					}

					if (i == keys.length - 1) {

						return extractedNode == null ? "" : extractedNode.toString();

					} else {
						currentNode = (JSONObject) extractedNode;
					}

				} else {
					throw new Exception(String.format("The key [%s] in keychain [%s] does not exist!!!",
							currentExtractKey, keychain));
				}
			}
		}

		throw new Exception("Response is null!!! Check the request!!!");

	}

	public APIResponse extractJsonValue(String outputVariable, String regex) throws Exception {
		try {
			if (responseBodyJson != null) {
				JSONObject currentNode = (JSONObject) responseBodyJson;
				String[] keys = regex.split("\\.");
				try {
					for (int i = 0; i < keys.length; i++) {
						String currentExtractKey = keys[i];
						String key = "";
						String indexInArray = StringUtils.substringBetween(currentExtractKey, "[", "]");
						if (indexInArray != null) {
							key = currentExtractKey.substring(0, currentExtractKey.indexOf("["));
						} else {
							key = currentExtractKey;
						}
						if (currentNode.has(key)) {
							Object extractedNode = currentNode.get(key);
							if ((indexInArray != null) && (extractedNode instanceof JSONArray)) {
								extractedNode = ((JSONArray) extractedNode).get(Integer.parseInt(indexInArray));
							} else if ((indexInArray != null) && !(extractedNode instanceof JSONArray)) {
								extractedVariable.add("[FAILED] [" + key + "] in [" + regex + "] is not an json array");
								Log.info("[Extract][FAILED] [" + key + "] in [" + regex + "] is not an json array");
								return this;
							}
							if (i == keys.length - 1) {
								String extractedValue = extractedNode == null ? "" : extractedNode.toString();
								// if(extractedValue == null || extractedValue.equals("null")) {
								// extractedValue = "";
								// }
								PropertiesLoader.getPropertiesLoader().test_variables.setProperty(outputVariable,
										extractedValue);
								extractedVariable.add("[SUCCESS] [" + regex + "] ==> [" + outputVariable + "] = [" + extractedValue + "]");
								Log.info("[Extract][SUCCESS] [" + regex + "] ==> [" + outputVariable + "] = [" + extractedValue + "]");
							} else if (!(extractedNode instanceof JSONObject)
									&& !(extractedNode instanceof JSONArray)) {
								extractedVariable.add("[FAILED] key [" + key + "] in [" + regex + "]does not contain ["
										+ keys[i + 1] + "]");
								Log.error("[Extract][FAILED] key [" + key + "] in [" + regex + "] does not contain ["
										+ keys[i + 1] + "]");
								return this;
							} else {
								currentNode = (JSONObject) extractedNode;
							}
						} else {
							extractedVariable.add("[FAILED] key [" + key + "] in [" + regex + "] does not exist");
							Log.error("[Extract][FAILED] key [" + key + "] in [" + regex + "] does not exist");
							return this;
						}
					}
				} catch (Exception e) {
					extractedVariable.add("[FAILED] Cannot extract value with [" + regex + "]");
					Log.error("[Extract][FAILED] Cannot extract value with [" + regex + "]");
					exception = e;
				}
			} else {
				extractedVariable.add("[FAILED] Response body is not JSON for extract [" + regex + "]");
				Log.error("[Extract][FAILED] Response body is not JSON for extract [" + regex + "]");
			}
		} catch (Exception e) {
			extractedVariable.add("[FAILED] Cannot extract value with [" + regex + "]");
			Log.error("[Extract][FAILED] Cannot extract value with [" + regex + "]");
			exception = e;
			return this;
		}
		return this;
	}

	public void flush() throws Exception {
		if (exception != null || isAssertOrExtractFailed()) {
			HtmlReporter.getTest2().fail(MarkupHelper.createAPIRequestStep(request, response, responseBody,
					assertionList, extractedVariable, exception));
			if (exception != null) {
				Log.error("Test failed due to API Request failed");
				throw exception;
			} else {
				Log.error("Test failed due to API Request failed");
				throw new Exception("Test failed due to API Request failed");
			}
		} else {
			HtmlReporter.getTest2().pass(MarkupHelper.createAPIRequestStep(request, response, responseBody,
					assertionList, extractedVariable));
		}
	}

	private boolean isAssertOrExtractFailed() {
		for (String node : assertionList) {
			if (node.contains("FAILED")) {
				return true;
			}
		}
		for (String node : extractedVariable) {
			if (node.contains("FAILED")) {
				return true;
			}
		}
		return false;
	}

	// public RequestResponse verifyHeaderContains(String key) throws Exception {
	// Header header = this.response.getFirstHeader(key);
	// try {
	// Assert.assertEquals(header != null, true);
	// Log.info("Header contains key '" + key + "'");
	// HtmlReporter.pass("Header contains key '" + key + "'");
	// } catch (Exception e) {
	// Log.error("Header doesn't contains key '" + key + "'");
	// HtmlReporter.fail("Header doesn't contains key '" + key + "'", "screenshot");
	// throw e;
	// }
	// return this;
	// }

}

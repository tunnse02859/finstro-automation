package com.finstro.automation.report.markuphelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.aventstack.extentreports.markuputils.Markup;
import com.finstro.automation.report.Log;
import com.finstro.automation.utility.Common;

public class APIRequestStep implements Markup {

	private HttpUriRequest request;
	private HttpResponse response;
	private Throwable exception;
	private String responseBody;

	private List<String> assertionList;
	private List<String> extractedVariable;

	public APIRequestStep(HttpUriRequest request, HttpResponse response) {
		this.request = request;
		this.response = response;
		assertionList = new ArrayList<String>();
		extractedVariable = new ArrayList<String>();
	}

	public APIRequestStep(HttpUriRequest request, Throwable exception) {
		this.request = request;
		this.exception = exception;
		assertionList = new ArrayList<String>();
		extractedVariable = new ArrayList<String>();
	}

	public APIRequestStep(HttpUriRequest request, HttpResponse response, String responseBody,
			List<String> assertionList, List<String> extractedVariable, Throwable exception) {
		this.request = request;
		this.response = response;
		this.assertionList = assertionList;
		this.extractedVariable = extractedVariable;
		this.exception = exception;
		this.responseBody = responseBody;
	}

	@Override
	public String getMarkup() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='wrapper'>");
		sb.append("<div class='api-title' onclick='toggleDetail(this)'>");
		sb.append("<span class='" + request.getMethod().toLowerCase() + "'>" + request.getMethod() + "</span> ");
		sb.append("<a class='" + getAPIResult() + "'>" + request.getURI() + "</a>");
		sb.append("</div>");
		sb.append("<div class='api-detail' style='display:none;'>");
		sb.append("<div class='tab'>");
		sb.append("<button class='tablinks' onclick='openBlock(event,\"request\")'>Request</button>");
		sb.append("<button class='tablinks' onclick='openBlock(event,\"response\")'>Response</button>");
		sb.append("<button class='tablinks' onclick='openBlock(event,\"assertion\")'>Assertion</button>");
		sb.append("<button class='tablinks' onclick='openBlock(event,\"extract\")'>Extract</button>");
		sb.append("<button class='tablinks' onclick='openBlock(event,\"exception\")'>Exception</button>");
		sb.append("</div>");

		//// tab request
		sb.append("<div class='tabcontent request' style='display:block;'>");
		sb.append("<span>Request Header: </span>");
		sb.append(getHeader());
		sb.append("<span>Request Body: </span>");
		sb.append("<div class='api-detail-block'>");
		sb.append("<span>");
		try {
			sb.append(getRequestBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("</span>");
		sb.append("</div>");
		sb.append("</div>");

		// tab response
		sb.append("<div class='tabcontent response'>");
		sb.append("<span>Response code: </span>");
		sb.append("<div class='api-detail-block'>");
		sb.append("<span>");
		if (response != null) {
			sb.append(response.getStatusLine().getStatusCode());
		}
		sb.append("</span>");
		sb.append("</div>");
		sb.append("<span>Response Body: </span>");
		sb.append("<div class='api-detail-block'>");
		sb.append("<span>");
		try {
			sb.append(getResponseBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("</span>");
		sb.append("</div>");
		sb.append("</div>");

		// tab assertion
		sb.append("<div class='tabcontent assertion'>");
		for (String assertNode : assertionList) {
			sb.append("<div class='api-detail-block'>");
			sb.append("<span> " + assertNode + " </span>");
			sb.append("</div>");
		}
		sb.append("</div>");

		// tab extract
		sb.append("<div class='tabcontent extract'>");
		for (String extractNode : extractedVariable) {
			sb.append("<div class='api-detail-block'>");
			sb.append("<span> " + extractNode + " </span>");
			sb.append("</div>");
		}
		sb.append("</div>");

		// tab exception
		sb.append("<div class='tabcontent exception'>");
		sb.append("<div class='api-detail-block'>");
		if (exception != null) {
			try {
				sb.append(Common.throwableToString(exception));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div>");

		return sb.toString();
	}

	public String getHeader() {
		StringBuilder sb = new StringBuilder();
		Header[] headers = request.getAllHeaders();
		for (Header header : headers) {
			sb.append("<div class='api-detail-block'>");
			sb.append("<span>");
			sb.append(header.getName() + ": " + header.getValue());
			sb.append("</span>");
			sb.append("</div>");
		}
		return sb.toString();
	}

	public String getRequestBody() throws Exception {
		try {
			StringBuilder sb = new StringBuilder();
			if (request.getMethod().equalsIgnoreCase("post") || request.getMethod().equalsIgnoreCase("put")) {
				HttpEntityEnclosingRequestBase baseRequest = (HttpEntityEnclosingRequestBase) request;
				sb.append(EntityUtils.toString(baseRequest.getEntity()));
			}
			return sb.toString();
		} catch (Exception e) {
			Log.error("Cannot get request Entity");
			throw e;
		}
	}

	public String getAPIResult() {
		for (String extractNode : extractedVariable) {
			if (extractNode.contains("FAILED")) {
				return "api-failed";
			}
		}
		for (String assertNode : assertionList) {
			if (assertNode.contains("FAILED")) {
				return "api-failed";
			}
		}
		return "api-passed";
	}

	public String getResponseBody() throws Exception {
		return responseBody;
	}

}

package com.finstro.automation.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.finstro.automation.report.HtmlReporter;
import com.finstro.automation.report.Log;
import com.finstro.automation.report.markuphelper.MarkupHelper;

public class APIRequest {
	private final HttpClient httpClient;

	private String baseUrl;
	private String path;
	private String fullUrl;

	private HttpUriRequest request;
	private HttpEntity bodyEntity;
	private HttpResponse response;

	private Map<String, String> parameters = new HashMap<String, String>();
	private Map<String, String> headers = new HashMap<String, String>();
	private String body;
	private List<NameValuePair> formData;
	private MultipartEntityBuilder multipartBuilder;
	

	public APIRequest() {
		httpClient = HttpClients.createDefault();
	}

	public APIRequest baseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		return this;
	}

	public APIRequest path(String path) {
		this.path = path;
		return this;
	}

	public APIRequest addParam(String key, String value) {
		parameters.put(key, value);
		return this;
	}

	private String generateParameters() {
		if (parameters.isEmpty()) {
			return "";
		}
		StringJoiner paramSJ = new StringJoiner("&");
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			paramSJ.add(String.join("=", entry.getKey(), entry.getValue()));
		}
		return "?" + paramSJ.toString();
	}

	public APIRequest addHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}

	public APIRequest basic(String username, String password) {
		String key = String.join(":", username, password);
		String encode = Base64.getUrlEncoder().encodeToString(key.getBytes());
		headers.put(HttpHeaders.AUTHORIZATION, "Basic " + encode);
		return this;
	}

	public APIRequest oauth2(String accessToken) {
		headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		return this;
	}

	public APIRequest body(String body) {
		this.body = body;
		return this;
	}

	public APIRequest formData(String key, String value) {
		if (formData == null) {
			formData = new ArrayList<NameValuePair>();
		}
		formData.add(new BasicNameValuePair(key, value));
		return this;
	}

	public APIRequest multipartData(String key, String value) {
		if (multipartBuilder == null) {
			multipartBuilder = MultipartEntityBuilder.create();
		}
		multipartBuilder.addTextBody(key, value);
		return this;
	}

	private void generateUrl() {
		fullUrl = baseUrl + path + generateParameters();
	}

	private void createRequestBody() throws UnsupportedEncodingException {
		if (bodyEntity == null && body != null) {
			bodyEntity = new StringEntity(body);
			return;
		}

		if (bodyEntity == null && formData != null) {
			bodyEntity = new UrlEncodedFormEntity(formData);
			return;
		}

		if (bodyEntity == null && multipartBuilder != null) {
			bodyEntity = multipartBuilder.build();
			return;
		}
	}

	public APIRequest get() throws Exception {
		generateUrl();
		request = new HttpGet(fullUrl);
		sendRequest();
		Log.info("Send Request: [GET] [" + fullUrl + "]");
		return this;
	}

	public APIRequest post() throws Exception {
		generateUrl();
		createRequestBody();
		HttpPost post = new HttpPost(fullUrl);
		post.setEntity(bodyEntity);
		request = post;
		sendRequest();
		Log.info("Send Request: [POST] [" + fullUrl + "]");
		return this;
	}

	public APIRequest put() throws Exception {
		generateUrl();
		createRequestBody();
		HttpPut put = new HttpPut(fullUrl);
		put.setEntity(bodyEntity);
		request = put;
		sendRequest();
		Log.info("Send Request: [PUT] [" + fullUrl + "]");
		return this;
	}

	public APIRequest delete() throws Exception {
		generateUrl();
		request = new HttpDelete(fullUrl);
		sendRequest();
		Log.info("Send Request: [DELETE] [" + fullUrl + "]");
		return this;
	}

	private void sendRequest() throws Exception {
		try {
			for (Entry<String, String> header : headers.entrySet()) {
				request.addHeader(header.getKey(), header.getValue());
			}
			response = httpClient.execute(request);
		} catch (Exception e) {
			HtmlReporter.getTest().fail(MarkupHelper.createAPIRequestStep(request, e));
			throw e;
		}
	}
	
	public void flush() {
		HtmlReporter.getTest().pass(MarkupHelper.createAPIRequestStep(request, response));
	}
	
	public RequestResponse then() {
		return new RequestResponse(request,response);
	}

	public HttpResponse getResponse() {
		return response;
	}

}

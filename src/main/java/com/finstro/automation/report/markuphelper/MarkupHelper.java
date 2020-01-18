package com.finstro.automation.report.markuphelper;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import com.aventstack.extentreports.markuputils.Markup;

public class MarkupHelper extends com.aventstack.extentreports.markuputils.MarkupHelper{
	
	public static Markup createAPIRequestStep(HttpUriRequest request, HttpResponse response) {
        return new APIRequestStep(request,response);
    }
	
	public static Markup createAPIRequestStep(HttpUriRequest request, Throwable exception) {
        return new APIRequestStep(request,exception);
    }
	
	public static Markup createAPIRequestStep(HttpUriRequest request, HttpResponse response, String responseBody,List<String> assertionList, List<String> extractedVariable) {
		return new APIRequestStep(request,response,responseBody,assertionList,extractedVariable,null);
    }
	
	public static Markup createAPIRequestStep(HttpUriRequest request, HttpResponse response,String responseBody,List<String> assertionList, List<String> extractedVariable, Throwable exception) {
        return new APIRequestStep(request,response,responseBody,assertionList,extractedVariable,exception);
    }

}

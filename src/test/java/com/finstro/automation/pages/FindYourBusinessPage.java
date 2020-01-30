package com.finstro.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.excelhelper.ExcelHelper;

import static com.finstro.automation.utility.Assertion.*;

import java.util.HashMap;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;


public class FindYourBusinessPage {

	private AppiumBaseDriver driver;
	private static String filePath = "src/main/resources/data/BusinessDetail.xlsx";
	private static String sheetName = "Sheet1";

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_title")
	private WebElement title;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/search_edit_text")
	private WebElement searchbox;

	@AndroidFindBy(id = "au.com.finstro.finstropay:id/address_result_list")
	private WebElement firstItemOnListResult;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/abc_acn_value")
	private WebElement abnObject;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/legal_name_value")
	private WebElement entityNameObject;
	
	@AndroidFindBy(id = "android:id/text1")
	private WebElement businessNameObject;
	
	@AndroidFindBy(id = "au.com.finstro.finstropay:id/btnFindBusiness_text")
	private WebElement findYourBusinessButton;

	public FindYourBusinessPage(AppiumBaseDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
	}

	public boolean isActive() throws Exception {
		return driver.isElementDisplayed(title);
	}

	public void addBusinessDetailSuccessfully(String businessType) throws Exception {
		Object[][] exelData = ExcelHelper.getTableToHashMap(filePath, sheetName);
		String abn = null;
		String entityName = null;
		String businessName = null;
		for(Object[] rowData : exelData ) {
			HashMap<String,String> data = (HashMap<String,String>)rowData[0];
			if (data.get("Business Type").equals(businessType)) {
				abn = data.get("ABN");
				entityName = data.get("Entity name");
				businessName = data.get("Business Name");
				break;
			}
		}
		
		System.out.println(abn);
		driver.inputText(searchbox, abn);
		driver.waitForElementDisplayed(firstItemOnListResult, 10);
		driver.click(firstItemOnListResult);
		driver.waitForElementDisplayed(findYourBusinessButton, 10);
		
		String actualAbn = driver.getText(abnObject);
		assertEquals(actualAbn.replace(" ", ""), abn, "ABN/ACN is not correct for business","ABN/ACN is correct");
		
		String actualEntityName = driver.getText(entityNameObject);
		System.out.println(actualEntityName);
		System.out.println(entityName);
		assertEquals(actualEntityName, entityName, "Entity Name is not correct for business","Entity Name is correct");
		
		String actualBusinessName = driver.getText(businessNameObject);
		System.out.println(actualBusinessName);
		System.out.println(businessName);
		assertEquals(actualBusinessName, businessName, "Business Name is not correct for business","Business Name is correct");
	}


}

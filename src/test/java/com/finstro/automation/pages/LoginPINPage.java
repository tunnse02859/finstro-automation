package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPINPage {
	
	public AppiumBaseDriver driver;
	
	@AndroidFindBy(id="au.com.finstro.finstropay:id/login_email")
    private WebElement loggedEmail;
	
	@AndroidFindBy(uiAutomator="new UiSelector().resourceId(\"au.com.finstro.finstropay:id/pinView\").childSelector(new UiSelector().className(\"android.widget.EditText\").instance(0))")
    private WebElement accessCode;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
    private WebElement submit;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/sign_up_link\"))")
    private WebElement registerPageLink;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/forgot_access_code\"))")
    private WebElement forgotAccessCodePageLink;
	
	
    public LoginPINPage(AppiumBaseDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver.getDriver()), this);
    }
    
    public boolean isActive(){
        return driver.waitForElementDisplayed(loggedEmail, 10);
    }
    
    public String getLoggedEmail() throws Exception {
    	return driver.getText(loggedEmail);
    }

    public void login(String code) throws Exception {
        driver.inputText(accessCode, code);
        driver.click(submit);
    }
    
    public  void toForgotAccessCodePage() throws Exception{
    	driver.clickByPosition(forgotAccessCodePageLink,"right");
    }
    
    public void toRegisterPage() throws Exception {
    	driver.clickByPosition(registerPageLink,"right");
    }
}

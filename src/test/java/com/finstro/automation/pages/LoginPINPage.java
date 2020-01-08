package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPINPage {
	
	public AppiumBaseDriver driver;
    @AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
    private  WebElement errorMessage;

	@AndroidFindBy(id="au.com.finstro.finstropay:id/login_email")
    private WebElement loggedEmail;
	
	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.EditText[1]")
    private WebElement accessCode1;

    @AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.EditText[2]")
    private WebElement accessCode2;

    @AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.EditText[3]")
    private WebElement accessCode3;

    @AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.EditText[4]")

    private WebElement accessCode4;
    @AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.EditText[5]")

    private WebElement accessCode5;
    @AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ScrollView/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.EditText[6]")
    private WebElement accessCode6;

	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
    private WebElement submit;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/sign_up_link\"))")
    private WebElement registerPageLink;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/forgot_access_code\"))")
    private WebElement forgotAccessCodePageLink;

    public String getErrorMessage(){
        return errorMessage.getText();
    }

    public void clickOnLoggedEmail(){
        loggedEmail.click();
    }
	
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
        accessCode1.sendKeys(String.valueOf(code.charAt(0)));
        accessCode1.sendKeys(String.valueOf(code.charAt(1)));
        accessCode3.sendKeys(String.valueOf(code.charAt(2)));
        accessCode4.sendKeys(String.valueOf(code.charAt(3)));
        accessCode5.sendKeys(String.valueOf(code.charAt(4)));
        accessCode6.sendKeys(String.valueOf(code.charAt(5)));
        driver.click(submit);
    }

    public  void clickOnSubmit(){
        submit.click();
    }
    public  void toForgotAccessCodePage() throws Exception{
    	driver.clickByPosition(forgotAccessCodePageLink,"right");
    }
    
    public void toRegisterPage() throws Exception {
    	driver.clickByPosition(registerPageLink,"right");
    }
}

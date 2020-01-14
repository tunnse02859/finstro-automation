package com.finstro.automation.pages;

import com.finstro.automation.appium.driver.AppiumBaseDriver;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.finstro.automation.utility.Assertion.assertEquals;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class LoginPINPage {
	
	public AppiumBaseDriver driver;
    @AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_text")
    @iOSXCUITFindBy(iOSClassChain = "name BEGINSWITH 'ERROR'")
    private  WebElement errorMessage;
    
    @AndroidFindBy(id = "au.com.finstro.finstropay:id/snackbar_action")
	private WebElement errorType;

	@AndroidFindBy(id="au.com.finstro.finstropay:id/login_email")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[2]")
    private WebElement loggedEmail;
	
	@AndroidFindBy(uiAutomator="new UiSelector().className(\"android.widget.EditText\")")
	@iOSXCUITFindBy(className = "XCUIElementTypeTextField")
    private List<WebElement> accessCode;


	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/btnSubmit\"))")
    private WebElement submit;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/sign_up_link\"))")
	@iOSXCUITFindBy(accessibility = "Not a member yet? Sign Up")
	private WebElement registerPageLink;
	
	@AndroidFindBy(uiAutomator="new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\"au.com.finstro.finstropay:id/forgot_access_code\"))")
	@iOSXCUITFindBy(accessibility = "Forgotten your Access Code?")
	private WebElement forgotAccessCodePageLink;

    
    public void verifyErrorMessage(String expectedMessage) throws Exception {
		String actualMessage = "";
		if (driver.isAndroidDriver()) {
			actualMessage = driver.getText(errorType) + ", " + driver.getText(errorMessage);
		} else {
			actualMessage = driver.getText(errorMessage);
		}
		assertEquals(actualMessage, expectedMessage, "Error message isnt correct", "Error message displayed correctly");
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
    	if(code.length() != 6)
    		throw new Exception("Cannot do login in PIN screen with code != 6 charactor. input value was ["+ code +"]");
    	for(int i = 0; i < 6; i++) {
    		driver.click(accessCode.get(i));
    		driver.inputText(accessCode.get(i), "" + code.charAt(i));
    	}
    	if(driver.isAndroidDriver())
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

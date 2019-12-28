package com.finstro.automation.pageObject;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import org.openqa.selenium.By;

public class ForgotAccessCodePage {
    public AppiumBaseDriver driver;

    private By forgotAccessHint = By.id("au.com.finstro.finstropay:id/forgot_access_hint");

    public ForgotAccessCodePage(AppiumBaseDriver driver){
        this.driver=driver;
    }
    public void goToPage(){

    }
    public boolean isActive() throws Exception {
        driver.waitForElementPresent(forgotAccessHint,10);
       String hint =  driver.getText("forgotAccessHint",forgotAccessHint);
        if(hint.equals("Please check your Email above as we will send you a unique access code to login.")){
            return true;
        }
        return false;
    }
}

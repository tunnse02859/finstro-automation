package com.finstro.automation.pageObject;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import com.finstro.automation.common.constants;
import com.finstro.automation.setup.MobileTestSetup;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;

public class LoginPage {
    private By emailAddress = By.id("au.com.finstro.finstropay:id/login_email_edt");
    private By accessCode = By.id("au.com.finstro.finstropay:id/login_access_code_edt");
    private By submit = By.id("au.com.finstro.finstropay:id/submit_text");
    public AppiumBaseDriver driver;
    public LoginPage(AppiumBaseDriver driver){
        this.driver=driver;
    }
    public boolean isActive(){
        return true;
    }

    public void login(String email,String code) throws Exception {
        driver.sendkeys("Comment textfield", emailAddress, constants.loginEmail);
        driver.sendkeys("Comment textfield", accessCode, constants.loginCode);
    }
    public  ForgotAccessCodePage forgotAccessCodePage(){
        return new ForgotAccessCodePage(driver);
    }
}

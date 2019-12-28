package com.finstro.automation.pageObject;

import com.finstro.automation.appium.driver.AppiumBaseDriver;
import org.openqa.selenium.By;

public class RegisterPage {

    private By loginLink = By.id("au.com.finstro.finstropay:id/have_account_link");
    private By agreement = By.id("au.com.finstro.finstropay:id/tvAgreement");
    private AppiumBaseDriver driver;
    public RegisterPage(AppiumBaseDriver driver){
        this.driver = driver;
    }

    public LoginPage goToLoginPage() throws Exception {
        driver.waitForElementPresent(loginLink,10);
        driver.click("loginLink",loginLink);
        return new LoginPage(driver);
    }
    public boolean isActive() throws Exception {
        driver.waitForElementPresent(agreement,10);
        String agreementText = driver.getText("agreement",agreement);
        if(agreementText.equals("Please tick and agree to our Privacy Policy and Terms and Condition so that we can contact you during this process of approval and registration.")){
            return true;
        }
        return false;
    }
}

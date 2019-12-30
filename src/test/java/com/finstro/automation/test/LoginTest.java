package com.finstro.automation.test;

import com.finstro.automation.common.constants;
import com.finstro.automation.pageObject.*;
import com.finstro.automation.setup.MobileTestSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class LoginTest  extends MobileTestSetup {
    LoginPage loginPage;
    RegisterPage registerPage;
    ForgotAccessCodePage forgotAccessCodePage;
    HomePage homePage;

    @Test
    public void login01_VerifyUserLoginSuccessful() throws Exception {
        registerPage = new RegisterPage(driver);
        loginPage = registerPage.goToLoginPage();
        loginPage.login(constants.loginEmail,constants.loginCode);
        homePage = new HomePage();
        assertTrue(homePage.isActive());
    }

    @Test
    public void login07_VerifyUserGoToTheSignUpScreenSuccessfully() throws Exception {
        registerPage = new RegisterPage(driver);
        assertTrue(registerPage.isActive());
    }

    @Test
    public void  login08_VerifyUserGoToTheForgotAccessCodeScreenSuccessfully() throws Exception {
        registerPage = new RegisterPage(driver);
        loginPage = registerPage.goToLoginPage();
        forgotAccessCodePage = loginPage.forgotAccessCodePage();
        assertTrue(forgotAccessCodePage.isActive());
}
    @Test
    public void login09_VerifyUserGoToTheLoginScreenSuccessfully () throws Exception {
        registerPage = new RegisterPage(driver);
        loginPage = registerPage.goToLoginPage();
        assertTrue(loginPage.isActive());
    }
    @Test
    public void  login12_VerifyUserGoToTheSignUpScreenSuccessfully() throws Exception {
        assertTrue(registerPage.isActive());
    }
    @Test
    public void  login14_VerifyTheSignUpScreenIsOpenAfterLogoutAndReopenTheApp() throws Exception {

    }
}

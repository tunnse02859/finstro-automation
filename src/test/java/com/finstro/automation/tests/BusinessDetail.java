package com.finstro.automation.tests;

import com.finstro.automation.pages.*;
import com.finstro.automation.setup.MobileTestSetup;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.finstro.automation.utility.Assertion.assertEquals;


public class BusinessDetail extends MobileTestSetup {
    private BusinessDetailPage businessDetailPage;
    private LoginPage loginPage;
    private BusinessCardPage businessCardPage;
    private static final String LOGIN_EMAIL_ADDRESS = "huongltd2105@gmail.com";
    private static final String LOGIN_ACCESS_CODE = "930947";

    @BeforeMethod
    public void setupPage(Method method) throws Exception {
        loginPage = new LoginPage(driver);
        businessCardPage = new BusinessCardPage(driver);
        businessDetailPage = new BusinessDetailPage(driver);
        loginPage.doSuccessLogin(LOGIN_EMAIL_ADDRESS, LOGIN_ACCESS_CODE);
    }

    @Test
    public void FPC_1314_VerifyUserRedirectToTheBusinessDetailScreenSuccessfulDueToClickingOnTheText500() throws Exception {
        businessCardPage.ClickOnCard("500");
        String pageTitle = businessDetailPage.GetTitle();
        String expectedPageTitle = "Business Details";
        assertEquals(expectedPageTitle, pageTitle, "Incorrect Business Details title", "Correct Business Details title");
    }

    @Test
    public void FPC_1315_VerifyUserRedirectToTheBusinessDetailScreenSuccessfulDueToClickingOnTheText1000() throws Exception {
        businessCardPage.ClickOnCard("1000");
        String pageTitle = businessDetailPage.GetTitle();
        String expectedPageTitle = "Business Details";
        assertEquals(expectedPageTitle, pageTitle, "Incorrect Business Details title", "Correct Business Details title");
    }

    @Test
    public void FPC_1316_VerifyUserRedirectToTheBusinessDetailScreenSuccessfulDueToClickingOnTheText5000() throws Exception {
        businessCardPage.ClickOnCard("5000");
        String pageTitle = businessDetailPage.GetTitle();
        String expectedPageTitle = "Business Details";
        assertEquals(expectedPageTitle, pageTitle, "Incorrect Business Details title", "Correct Business Details title");
    }

    @Test
    public void FPC_1317_VerifyUserRedirectToTheBusinessDetailScreenSuccessfulDueToClickingOnTheText10000() throws Exception {
        businessCardPage.ClickOnCard("10000");
        String pageTitle = businessDetailPage.GetTitle();
        String expectedPageTitle = "Business Details";
        assertEquals(expectedPageTitle, pageTitle, "Incorrect Business Details title", "Correct Business Details title");
    }
    @Test
    public void FPC_1318_VerifyUserRedirectToTheBusinessDetailScreenSuccessfulDueToClickingOnTheText25000() throws Exception {
        businessCardPage.ClickOnCard("25000");
        String pageTitle = businessDetailPage.GetTitle();
        String expectedPageTitle = "Business Details";
        assertEquals(expectedPageTitle, pageTitle, "Incorrect Business Details title", "Correct Business Details title");
    }
    @Test
    public void FPC_1319_VerifyUserRedirectToTheBusinessDetailScreenSuccessfulDueToClickingOnTheText50000() throws Exception {
        businessCardPage.ClickOnCard("50000");
        String pageTitle = businessDetailPage.GetTitle();
        String expectedPageTitle = "Business Details";
        assertEquals(expectedPageTitle, pageTitle, "Incorrect Business Details title", "Correct Business Details title");
    }

    @Test
    public void FPC_1320_VerifyUserRedirectToTheBusinessDetailScreenSuccessfulDueToClickingOnTheText500000() throws Exception {
        businessCardPage.ClickOnCard("500000");
        String pageTitle = businessDetailPage.GetTitle();
        String expectedPageTitle = "Business Details";
        assertEquals(expectedPageTitle, pageTitle, "Incorrect Business Details title", "Correct Business Details title");
    }

}

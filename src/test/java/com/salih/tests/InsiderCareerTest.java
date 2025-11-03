package com.salih.tests;
import com.salih.pages.CareersPage;
import com.salih.pages.HomePage;
import com.salih.pages.OpenPositionsPage;
import com.salih.pages.QAJobsPage;
import org.testng.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class InsiderCareerTest extends BaseTest {

    private HomePage homePage;
    private CareersPage careersPage;
    private QAJobsPage qaJobsPage;
    private OpenPositionsPage openPositionsPage;


    @BeforeClass
    public void pageSetup() {
        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
        qaJobsPage = new QAJobsPage(driver);
        openPositionsPage = new OpenPositionsPage(driver);
    }


    @Test(priority = 1)
    public void step1_verifyHomePageIsOpened() {
        homePage.goToHomePage();

        Assert.assertTrue(homePage.isHomePageOpened(), "Home page title is incorrect.");
        System.out.println("Step 1 PASSED: Home page is opened.");
    }

    @Test(priority = 2, dependsOnMethods = "step1_verifyHomePageIsOpened")
    public void step2_verifyCareersPageNavigation() {
        homePage.goToCareersPage();

        Assert.assertTrue(careersPage.isCareersPageOpened(), "Not navigated to Careers page.");
        System.out.println("Step 2 PASSED: Navigated to Careers page.");
    }

    @Test(priority = 3, dependsOnMethods = "step2_verifyCareersPageNavigation")
    public void step3_verifyCareerBlocksAreVisible() {
        Assert.assertTrue(careersPage.areCareerBlocksVisible(), "One or more blocks (Locations, Teams, Life) are not visible.");
        System.out.println("Step 3 PASSED: Locations, Teams, and Life at Insider blocks are visible.");
    }

    @Test(priority = 4)
    public void step4_navigateToQaPage() {
        qaJobsPage.goToPage();

        openPositionsPage = qaJobsPage.clickSeeAllQaJobs();
        System.out.println("Step 4 PASSED: Navigated to Open Positions page.");
    }

    @Test(priority = 5, dependsOnMethods = "step4_navigateToQaPage")
    public void step5_filterQAJobsInIstanbulAndVerifyList() {
        openPositionsPage.selectIstanbulTurkiye();

        Assert.assertTrue(openPositionsPage.isJobListPresent(),
                "Job list is not present for Location=Istanbul, Turkiye and Department=Quality Assurance.");

        System.out.println("Step 5 PASSED: Job list is present for Location=Istanbul, Turkiye and Department=Quality Assurance.");
    }

    @Test(priority = 6, dependsOnMethods = "step5_filterQAJobsInIstanbulAndVerifyList")
    public void step6_verifyAllCardsTexts() {
        Assert.assertTrue(openPositionsPage.allJobsMatchQAAndIstanbul(),
                "Some jobs do not match Position/Department='Quality Assurance' and Location='Istanbul, Turkey/Turkiye'.");
        System.out.println("Step 6 PASSED: All job cards match expected texts.");
    }

}

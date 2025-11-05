package com.salih.pages;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class QAJobsPage extends BasePage {


    @FindBy(linkText = "See all QA jobs")
    private WebElement seeAllQaJobsButton;

    public QAJobsPage(WebDriver driver) {
        super(driver);
    }


    public void goToPage() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
    }


    public OpenPositionsPage clickSeeAllQaJobs() {
        // button might be out of view, scroll into view first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", seeAllQaJobsButton);
        clickElement(seeAllQaJobsButton);

        return new OpenPositionsPage(driver);
    }
}

package com.salih.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersPage extends BasePage {


    @FindBy(id = "career-our-location")
    private WebElement locationsBlock;

    @FindBy(id = "career-find-our-calling")
    private WebElement teamsBlock; //  "Find your calling"

    @FindBy(xpath = "//h2[normalize-space()='Life at Insider']")
    private WebElement lifeAtInsiderBlock;

    public CareersPage(WebDriver driver) {
        super(driver);
    }


    public boolean isCareersPageOpened() {
        wait.until(driver -> driver.getCurrentUrl().contains("careers"));
        return driver.getCurrentUrl().contains("useinsider.com/careers/");
    }


    public boolean areCareerBlocksVisible() {
        waitForVisibility(locationsBlock);
        waitForVisibility(teamsBlock);
        waitForVisibility(lifeAtInsiderBlock);

        return locationsBlock.isDisplayed() && teamsBlock.isDisplayed() && lifeAtInsiderBlock.isDisplayed();
    }
}

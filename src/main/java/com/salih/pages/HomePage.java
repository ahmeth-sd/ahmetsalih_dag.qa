package com.salih.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(id = "wt-cli-accept-all-btn")
    private WebElement acceptAllCookiesButton;

    @FindBy(xpath = "//a[normalize-space()='Company']")
    private WebElement companyMenuLink;

    @FindBy(xpath = "//a[normalize-space()='Careers']")
    private WebElement careersLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }


    public void goToHomePage() {
        driver.get("https://useinsider.com/");

        // Handle the cookie banner
        try {
            clickElement(acceptAllCookiesButton);
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already accepted.");
        }
    }


    public boolean isHomePageOpened() {
        return driver.getTitle().contains("Insider");
    }

    public CareersPage goToCareersPage() {

        clickElement(companyMenuLink);

        waitForVisibility(careersLink);
        clickElement(careersLink);

        return new CareersPage(driver);
    }
}
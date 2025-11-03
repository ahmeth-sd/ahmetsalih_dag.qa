package com.salih.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import org.testng.annotations.Test;

import java.time.Duration;

public class InsiderCareerTest extends BaseTest {

    @Test
    public void navigateToCareersPageTest() {

        driver.get("https://useinsider.com/");

        try {
            WebElement acceptCookiesButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("wt-cli-accept-all-btn"))
            );
            acceptCookiesButton.click();
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already accepted.");
        }

        WebElement companyMenuLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Company']"))
        );
        companyMenuLink.click();

        WebElement careersLink = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Careers']"))
        );
        careersLink.click();

        wait.until(ExpectedConditions.urlContains("careers"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("useinsider.com/careers/"),
                "Failed to navigate to Careers page. Current URL: " + currentUrl);

        System.out.println("Test Passed: Successfully navigated to Careers page.");
    }
}

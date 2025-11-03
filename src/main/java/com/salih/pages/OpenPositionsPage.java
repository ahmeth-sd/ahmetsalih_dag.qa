package com.salih.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import java.text.Normalizer;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class OpenPositionsPage extends BasePage {

    @FindBy(id = "filter-by-location")
    private WebElement locationFilterDropdown;

    @FindBy(id = "jobs-list")
    private WebElement jobListContainer;

    private final By select2LocationSelection = By.cssSelector("#select2-filter-by-location-container");
    private final By select2LocationResults = By.id("select2-filter-by-location-results");
    private final By jobCards = By.cssSelector("#jobs-list .position-list-item, #jobs-list [data-position-id]");
    private final By titleInCard = By.cssSelector(".position-title");
    private final By departmentInCard = By.cssSelector(".position-department");
    private final By locationInCard = By.cssSelector(".position-location");
    private final By viewRoleInCard = By.xpath(".//a[contains(.,'View Role')]");
    private final Pattern qaPattern = Pattern.compile("(?i)\\b(qa|quality\\s*assurance)\\b");


    private final Duration SHORT = Duration.ofSeconds(5);
    private final Duration MEDIUM = Duration.ofSeconds(12);
    private final Duration LONG = Duration.ofSeconds(20);


    public OpenPositionsPage(WebDriver driver) {
        super(driver);
    }

    public void selectLocationWithRetry(String locationName, int maxAttempts) {
        new WebDriverWait(driver, MEDIUM).until(ExpectedConditions.elementToBeClickable(select2LocationSelection));
        int tries = 0;
        boolean done = false;
        while (!done && tries < maxAttempts) {
            openLocationDropdown();
            done = tryPickLocation(locationName);
            if (!done) {
                closeLocationDropdown();
                sleep(1500);
            }
            tries++;
        }
    }

    public void selectIstanbulTurkiye() {
        selectLocationWithRetry("Istanbul, Turkiye", 6);
    }

    public boolean isJobListPresent() {
        try {
            WebDriverWait w = new WebDriverWait(driver, MEDIUM);
            w.until(ExpectedConditions.visibilityOf(jobListContainer));
            w.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(jobCards),
                    ExpectedConditions.numberOfElementsToBeMoreThan(jobCards, 0)
            ));
            return !driver.findElements(jobCards).isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean allJobsMatchQAAndIstanbul() {
        sleep(1500);

        wait.until(ExpectedConditions.visibilityOfElementLocated(jobCards));

        List<WebElement> cards = driver.findElements(jobCards);
        if (cards.isEmpty()) return false;

        for (WebElement card : cards) {
            String position = textOf(card, titleInCard);
            String department = textOf(card, departmentInCard);
            String location = textOf(card, locationInCard);

            String pos = norm(position);
            String dep = norm(department);
            String loc = norm(location);

            boolean posOk = pos.contains("quality assurance");
            boolean depOk = dep.contains("quality assurance");
            boolean locOk = loc.contains("istanbul") && (loc.contains("turkiye") || loc.contains("turkey"));

            if (!(posOk && depOk && locOk)) return false;
        }
        return true;
    }

    private String textOf(WebElement root, By locator) {
        try { return root.findElement(locator).getText().trim(); }
        catch (Exception e) { return ""; }
    }

    private String norm(String s) {
        try {
            String n = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "");
            return n.toLowerCase().replace('ı','i').replace('İ','I');
        } catch (Exception e) {
            return s == null ? "" : s.toLowerCase();
        }
    }


    private void openLocationDropdown() {
        WebElement selection = new WebDriverWait(driver, MEDIUM)
                .until(ExpectedConditions.elementToBeClickable(select2LocationSelection));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", selection);
        click(selection);
        new WebDriverWait(driver, SHORT).until(ExpectedConditions.visibilityOfElementLocated(select2LocationResults));
    }

    private void closeLocationDropdown() {
        try {
            WebElement selection = driver.findElement(select2LocationSelection);
            click(selection);
            new WebDriverWait(driver, SHORT).until(ExpectedConditions.invisibilityOfElementLocated(select2LocationResults));
        } catch (Exception ignored) {}
    }

    private boolean tryPickLocation(String textToPick) {
        try {
            By optionLi = By.xpath("//ul[@id='select2-filter-by-location-results']/li[contains(normalize-space(), \"" + textToPick + "\")]");
            WebElement li = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(optionLi));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", li);
            click(li);
            new WebDriverWait(driver, SHORT).until(ExpectedConditions.invisibilityOfElementLocated(select2LocationResults));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void click(WebElement el) {
        try {
            el.click();
        } catch (Exception ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    private void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }


}

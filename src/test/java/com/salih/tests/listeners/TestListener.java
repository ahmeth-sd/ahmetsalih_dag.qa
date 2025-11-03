package com.salih.tests.listeners;

import com.salih.tests.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("!!! test is failure: " + result.getName() + " !!!");

        // get WebDriver instance from the test class
        Object testInstance = result.getInstance();
        WebDriver driver = ((BaseTest) testInstance).getDriver();

        if (driver != null) {
            takeScreenshot(driver, result.getName());
        }
    }

    private void takeScreenshot(WebDriver driver, String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";

        try {
            //save screenshot to "screenshots" directory
            Path destinationPath = Paths.get("screenshots", fileName);
            Files.createDirectories(destinationPath.getParent());
            Files.copy(srcFile.toPath(), destinationPath);
            System.out.println("screenshot saved: " + destinationPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("screenshot cannot saved: " + e.getMessage());
        }
    }
}
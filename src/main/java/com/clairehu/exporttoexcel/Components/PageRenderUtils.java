package com.clairehu.exporttoexcel.Components;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@PropertySource("application.properties")
public class PageRenderUtils {

    @Autowired
    private Environment env;

    @Autowired
    private ChromeDriver chromeDriver;

    @Autowired
    private SortBySelector sortBySelector;

    public Document renderPage(String url) {

        try {
            chromeDriver.get(url);
            loginToLeetcode();
            //Sort table by difficulty.
            sortByColumn(SortBy.valueOf(env.getProperty("sortBy.column").toUpperCase()));
            Document doc = Jsoup.parse(chromeDriver.getPageSource());
            return doc;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void loginToLeetcode(){

        waitForPageLoaded();

        WebElement username = chromeDriver.findElement(By.name("login"));
        WebElement password = chromeDriver.findElement(By.name("password"));

        username.sendKeys(env.getProperty("leetcode.username"));
        password.sendKeys(env.getProperty("leetcode.password"));

        WebElement signInBtn = chromeDriver.findElement(By.cssSelector("button.btn__2FMG"));

        signInBtn.click();
    }

    public void sortByColumn(SortBy sortBy){
        waitForPageLoaded();

        WebElement sortByBtn = chromeDriver.findElement(By.cssSelector(sortBySelector.getCssSelector(sortBy)));
        sortByBtn.click();
        chromeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(Long.valueOf(env.getProperty("default.wait.miliseconds")));
            WebDriverWait wait = new WebDriverWait(chromeDriver, Long.valueOf(env.getProperty("default.timeout.seconds")));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println("Timeout waiting for Page Load Request to complete.");
        }
    }


    public void quit() {
        chromeDriver.quit();
    }

}
package com.prophecy.testing.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base Page Object containing common functionality for all pages
 */
public abstract class BasePage extends PageObject {
    
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    
    /**
     * Wait for element to be visible and clickable
     */
    protected void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be visible
     */
    protected void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be present
     */
    protected void waitForElementToBePresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Wait for text to be present in element
     */
    protected void waitForTextToBePresent(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    
    /**
     * Safe click with wait
     */
    protected void safeClick(By locator) {
        waitForElementToBeClickable(locator);
        find(locator).click();
        logger.info("Clicked element: {}", locator);
    }
    
    /**
     * Safe type with clear
     */
    protected void safeType(By locator, String text) {
        waitForElementToBeVisible(locator);
        WebElementFacade element = find(locator);
        element.clear();
        element.type(text);
        logger.info("Typed '{}' into element: {}", text, locator);
    }
    
    /**
     * Get text from element safely
     */
    protected String safeGetText(By locator) {
        waitForElementToBeVisible(locator);
        String text = find(locator).getText();
        logger.info("Retrieved text '{}' from element: {}", text, locator);
        return text;
    }
    
    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is present
     */
    protected boolean isElementPresent(By locator) {
        try {
            return !findAll(locator).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wait for page to load completely
     */
    protected void waitForPageToLoad() {
        wait.until(webDriver -> 
            ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
    
    /**
     * Scroll element into view
     */
    protected void scrollIntoView(By locator) {
        WebElement element = find(locator);
        ((org.openqa.selenium.JavascriptExecutor) getDriver())
            .executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Get all elements matching locator
     */
    protected List<WebElementFacade> getAllElements(By locator) {
        return findAll(locator);
    }
    
    /**
     * Wait for loading spinner to disappear
     */
    protected void waitForLoadingToComplete() {
        // Common loading indicators
        By[] loadingIndicators = {
            By.cssSelector(".loading"),
            By.cssSelector(".spinner"),
            By.cssSelector("[data-testid='loading']"),
            By.xpath("//*[contains(@class, 'loading')]")
        };
        
        for (By indicator : loadingIndicators) {
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(indicator));
            } catch (Exception e) {
                // Ignore if loading indicator is not present
            }
        }
    }
    
    /**
     * Take screenshot for debugging
     */
    protected void takeScreenshot(String description) {
        logger.info("Taking screenshot: {}", description);
        // Serenity automatically handles screenshots
    }
    
    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
}
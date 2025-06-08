package com.prophecy.testing.pages;

import net.serenitybdd.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for Prophecy Login Page
 */
@DefaultUrl("page:webdriver.base.url")
public class LoginPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    
    // Locators for login page elements
    private static final By SSO_LOGIN_BUTTON = By.cssSelector("[data-testid='sso-login-button']");
    private static final By LOGIN_WITH_SSO_LINK = By.linkText("Login with SSO");
    private static final By SSO_PROVIDER_BUTTON = By.cssSelector("[data-testid='adfs-provider']");
    private static final By LOGIN_FORM = By.cssSelector(".login-form");
    private static final By ERROR_MESSAGE = By.cssSelector(".error-message");
    private static final By TIMEOUT_MESSAGE = By.cssSelector(".timeout-message");
    private static final By RETRY_BUTTON = By.cssSelector("[data-testid='retry-button']");
    private static final By LOADING_INDICATOR = By.cssSelector(".login-loading");
    
    // ADFS specific locators (these may vary based on your ADFS configuration)
    private static final By ADFS_USERNAME_FIELD = By.id("userNameInput");
    private static final By ADFS_PASSWORD_FIELD = By.id("passwordInput");
    private static final By ADFS_SUBMIT_BUTTON = By.id("submitButton");
    private static final By ADFS_ERROR_MESSAGE = By.cssSelector(".error");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to login page
     */
    public void navigateToLoginPage() {
        open();
        waitForPageToLoad();
        logger.info("Navigated to Prophecy login page");
    }
    
    /**
     * Check if we're on the login page
     */
    public boolean isOnLoginPage() {
        return isElementDisplayed(LOGIN_FORM) || isElementDisplayed(SSO_LOGIN_BUTTON);
    }
    
    /**
     * Initiate SSO login process
     */
    public void initiateSSO() {
        if (isElementDisplayed(SSO_LOGIN_BUTTON)) {
            safeClick(SSO_LOGIN_BUTTON);
        } else if (isElementDisplayed(LOGIN_WITH_SSO_LINK)) {
            safeClick(LOGIN_WITH_SSO_LINK);
        }
        logger.info("Initiated SSO login process");
    }
    
    /**
     * Select ADFS as SSO provider
     */
    public void selectADFSProvider() {
        waitForElementToBeClickable(SSO_PROVIDER_BUTTON);
        safeClick(SSO_PROVIDER_BUTTON);
        logger.info("Selected ADFS as SSO provider");
    }
    
    /**
     * Authenticate through ADFS with credentials
     * Note: In a real scenario, you might want to handle this differently for security
     */
    public void authenticateWithADFS(String username, String password) {
        // Wait for ADFS page to load
        waitForElementToBeVisible(ADFS_USERNAME_FIELD);
        
        // Enter credentials
        safeType(ADFS_USERNAME_FIELD, username);
        safeType(ADFS_PASSWORD_FIELD, password);
        
        // Submit form
        safeClick(ADFS_SUBMIT_BUTTON);
        
        logger.info("Submitted ADFS authentication form for user: {}", username);
    }
    
    /**
     * Simulate invalid ADFS authentication
     */
    public void authenticateWithInvalidADFS() {
        authenticateWithADFS("invalid_user", "invalid_password");
    }
    
    /**
     * Check if authentication error is displayed
     */
    public boolean isAuthenticationErrorDisplayed() {
        return isElementDisplayed(ERROR_MESSAGE) || isElementDisplayed(ADFS_ERROR_MESSAGE);
    }
    
    /**
     * Get authentication error message
     */
    public String getAuthenticationErrorMessage() {
        if (isElementDisplayed(ERROR_MESSAGE)) {
            return safeGetText(ERROR_MESSAGE);
        } else if (isElementDisplayed(ADFS_ERROR_MESSAGE)) {
            return safeGetText(ADFS_ERROR_MESSAGE);
        }
        return "";
    }
    
    /**
     * Check if timeout error is displayed
     */
    public boolean isTimeoutErrorDisplayed() {
        return isElementDisplayed(TIMEOUT_MESSAGE);
    }
    
    /**
     * Get timeout error message
     */
    public String getTimeoutErrorMessage() {
        return safeGetText(TIMEOUT_MESSAGE);
    }
    
    /**
     * Click retry button after timeout
     */
    public void clickRetryButton() {
        safeClick(RETRY_BUTTON);
        logger.info("Clicked retry button");
    }
    
    /**
     * Wait for SSO authentication to complete
     */
    public void waitForSSOAuthenticationToComplete() {
        // Wait for loading indicator to disappear
        waitForLoadingToComplete();
        
        // Wait for either success (redirect) or error
        wait.until(driver -> 
            !isElementDisplayed(LOADING_INDICATOR) && 
            (getCurrentUrl().contains("dashboard") || isAuthenticationErrorDisplayed())
        );
        
        logger.info("SSO authentication process completed");
    }
    
    /**
     * Simulate SSO timeout scenario
     */
    public void simulateSSOTimeout() {
        // This would typically be handled by waiting for a specific timeout period
        // or by mocking the SSO response
        logger.info("Simulating SSO timeout scenario");
        
        // In a real implementation, you might:
        // 1. Use a test-specific SSO endpoint that times out
        // 2. Use network throttling to simulate slow response
        // 3. Use a mock SSO service that returns timeout
    }
    
    /**
     * Check if currently on ADFS authentication page
     */
    public boolean isOnADFSPage() {
        return isElementDisplayed(ADFS_USERNAME_FIELD) && isElementDisplayed(ADFS_PASSWORD_FIELD);
    }
    
    /**
     * Wait for redirect after successful authentication
     */
    public void waitForSuccessfulRedirect() {
        wait.until(driver -> !getCurrentUrl().contains("login") && !getCurrentUrl().contains("adfs"));
        logger.info("Successfully redirected after authentication");
    }
}
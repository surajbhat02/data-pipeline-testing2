package com.prophecy.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for Prophecy Dashboard Page
 */
public class DashboardPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(DashboardPage.class);
    
    // Locators for dashboard elements
    private static final By DASHBOARD_HEADER = By.cssSelector("[data-testid='dashboard-header']");
    private static final By USER_PROFILE_MENU = By.cssSelector("[data-testid='user-profile-menu']");
    private static final By USER_NAME_DISPLAY = By.cssSelector("[data-testid='user-name']");
    private static final By PIPELINES_SECTION = By.cssSelector("[data-testid='pipelines-section']");
    private static final By PIPELINES_MENU_ITEM = By.cssSelector("[data-testid='pipelines-menu']");
    private static final By WORKSPACE_SELECTOR = By.cssSelector("[data-testid='workspace-selector']");
    private static final By NAVIGATION_MENU = By.cssSelector(".navigation-menu");
    private static final By LOGOUT_BUTTON = By.cssSelector("[data-testid='logout-button']");
    private static final By SESSION_TIMEOUT_MODAL = By.cssSelector("[data-testid='session-timeout-modal']");
    private static final By SESSION_EXTEND_BUTTON = By.cssSelector("[data-testid='extend-session-button']");
    
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Check if we're on the dashboard page
     */
    public boolean isOnDashboard() {
        waitForPageToLoad();
        return isElementDisplayed(DASHBOARD_HEADER) && getCurrentUrl().contains("dashboard");
    }
    
    /**
     * Check if user profile is displayed correctly
     */
    public boolean isUserProfileDisplayed() {
        return isElementDisplayed(USER_PROFILE_MENU) && isElementDisplayed(USER_NAME_DISPLAY);
    }
    
    /**
     * Get displayed user name
     */
    public String getDisplayedUserName() {
        waitForElementToBeVisible(USER_NAME_DISPLAY);
        return safeGetText(USER_NAME_DISPLAY);
    }
    
    /**
     * Navigate to pipelines section
     */
    public void navigateToPipelines() {
        if (isElementDisplayed(PIPELINES_MENU_ITEM)) {
            safeClick(PIPELINES_MENU_ITEM);
        } else {
            safeClick(PIPELINES_SECTION);
        }
        waitForPageToLoad();
        logger.info("Navigated to pipelines section");
    }
    
    /**
     * Check if pipelines section is accessible
     */
    public boolean isPipelinesSectionAccessible() {
        return isElementDisplayed(PIPELINES_SECTION) || isElementDisplayed(PIPELINES_MENU_ITEM);
    }
    
    /**
     * Open user profile menu
     */
    public void openUserProfileMenu() {
        safeClick(USER_PROFILE_MENU);
        logger.info("Opened user profile menu");
    }
    
    /**
     * Logout from the application
     */
    public void logout() {
        openUserProfileMenu();
        waitForElementToBeClickable(LOGOUT_BUTTON);
        safeClick(LOGOUT_BUTTON);
        logger.info("Initiated logout process");
    }
    
    /**
     * Check if session timeout modal is displayed
     */
    public boolean isSessionTimeoutModalDisplayed() {
        return isElementDisplayed(SESSION_TIMEOUT_MODAL);
    }
    
    /**
     * Extend session when timeout modal appears
     */
    public void extendSession() {
        if (isSessionTimeoutModalDisplayed()) {
            safeClick(SESSION_EXTEND_BUTTON);
            logger.info("Extended user session");
        }
    }
    
    /**
     * Wait for session to timeout (simulation)
     */
    public void waitForSessionTimeout() {
        // In a real scenario, this would wait for the actual session timeout
        // For testing purposes, we might simulate this
        logger.info("Waiting for session timeout...");
        
        // Simulate session timeout by waiting for the modal or redirect
        try {
            wait.until(driver -> 
                isSessionTimeoutModalDisplayed() || 
                getCurrentUrl().contains("login")
            );
        } catch (Exception e) {
            logger.warn("Session timeout simulation may not have worked as expected");
        }
    }
    
    /**
     * Check if navigation menu is displayed
     */
    public boolean isNavigationMenuDisplayed() {
        return isElementDisplayed(NAVIGATION_MENU);
    }
    
    /**
     * Select workspace
     */
    public void selectWorkspace(String workspaceName) {
        safeClick(WORKSPACE_SELECTOR);
        By workspaceOption = By.xpath(String.format("//div[contains(@class, 'workspace-option') and text()='%s']", workspaceName));
        waitForElementToBeClickable(workspaceOption);
        safeClick(workspaceOption);
        logger.info("Selected workspace: {}", workspaceName);
    }
    
    /**
     * Get current workspace name
     */
    public String getCurrentWorkspace() {
        return safeGetText(WORKSPACE_SELECTOR);
    }
    
    /**
     * Check if user has access to workspace
     */
    public boolean hasWorkspaceAccess() {
        return isElementDisplayed(WORKSPACE_SELECTOR) && isPipelinesSectionAccessible();
    }
    
    /**
     * Verify dashboard loaded completely
     */
    public void verifyDashboardLoaded() {
        waitForPageToLoad();
        waitForLoadingToComplete();
        
        // Verify key elements are present
        assert isElementDisplayed(DASHBOARD_HEADER) : "Dashboard header not displayed";
        assert isElementDisplayed(USER_PROFILE_MENU) : "User profile menu not displayed";
        assert isNavigationMenuDisplayed() : "Navigation menu not displayed";
        
        logger.info("Dashboard loaded and verified successfully");
    }
}
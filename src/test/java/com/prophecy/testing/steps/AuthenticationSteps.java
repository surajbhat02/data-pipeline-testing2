package com.prophecy.testing.steps;

import com.prophecy.testing.pages.LoginPage;
import com.prophecy.testing.pages.DashboardPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.steps.UIInteractionSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step class for authentication-related business logic
 */
public class AuthenticationSteps extends UIInteractionSteps {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSteps.class);
    
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    
    @Step("Navigate to Prophecy login page")
    public void navigateToLoginPage() {
        loginPage.navigateToLoginPage();
        logger.info("Navigated to Prophecy login page");
    }
    
    @Step("Verify user is on login page")
    public void verifyOnLoginPage() {
        assertThat(loginPage.isOnLoginPage())
            .as("User should be on the login page")
            .isTrue();
        logger.info("Verified user is on login page");
    }
    
    @Step("Initiate SSO login process")
    public void initiateSSOLogin() {
        loginPage.initiateSSO();
        logger.info("Initiated SSO login process");
    }
    
    @Step("Authenticate through ADFS with valid credentials")
    public void authenticateWithValidADFS() {
        // In a real scenario, you might get these from configuration or environment variables
        String username = System.getProperty("test.username", "test.user@company.com");
        String password = System.getProperty("test.password", "TestPassword123!");
        
        loginPage.selectADFSProvider();
        loginPage.authenticateWithADFS(username, password);
        loginPage.waitForSSOAuthenticationToComplete();
        
        logger.info("Authenticated through ADFS with valid credentials");
    }
    
    @Step("Attempt to authenticate through ADFS with invalid credentials")
    public void authenticateWithInvalidADFS() {
        loginPage.selectADFSProvider();
        loginPage.authenticateWithInvalidADFS();
        loginPage.waitForSSOAuthenticationToComplete();
        
        logger.info("Attempted authentication with invalid ADFS credentials");
    }
    
    @Step("Verify successful login to Prophecy")
    public void verifySuccessfulLogin() {
        loginPage.waitForSuccessfulRedirect();
        assertThat(dashboardPage.isOnDashboard())
            .as("User should be successfully logged into Prophecy")
            .isTrue();
        logger.info("Verified successful login to Prophecy");
    }
    
    @Step("Verify main dashboard is displayed")
    public void verifyMainDashboardDisplayed() {
        dashboardPage.verifyDashboardLoaded();
        assertThat(dashboardPage.isOnDashboard())
            .as("Main dashboard should be displayed")
            .isTrue();
        logger.info("Verified main dashboard is displayed");
    }
    
    @Step("Verify user profile is displayed correctly")
    public void verifyUserProfileDisplayed() {
        assertThat(dashboardPage.isUserProfileDisplayed())
            .as("User profile should be displayed correctly")
            .isTrue();
        
        String displayedUserName = dashboardPage.getDisplayedUserName();
        assertThat(displayedUserName)
            .as("User name should be displayed")
            .isNotEmpty();
        
        logger.info("Verified user profile is displayed correctly with name: {}", displayedUserName);
    }
    
    @Step("Verify authentication error message is displayed")
    public void verifyAuthenticationErrorDisplayed() {
        assertThat(loginPage.isAuthenticationErrorDisplayed())
            .as("Authentication error message should be displayed")
            .isTrue();
        
        String errorMessage = loginPage.getAuthenticationErrorMessage();
        assertThat(errorMessage)
            .as("Error message should not be empty")
            .isNotEmpty();
        
        logger.info("Verified authentication error is displayed: {}", errorMessage);
    }
    
    @Step("Verify user remains on login page")
    public void verifyRemainsOnLoginPage() {
        assertThat(loginPage.isOnLoginPage())
            .as("User should remain on the login page")
            .isTrue();
        logger.info("Verified user remains on login page");
    }
    
    @Step("Simulate ADFS authentication timeout")
    public void simulateADFSTimeout() {
        loginPage.selectADFSProvider();
        loginPage.simulateSSOTimeout();
        logger.info("Simulated ADFS authentication timeout");
    }
    
    @Step("Verify timeout error message is displayed")
    public void verifyTimeoutErrorDisplayed() {
        assertThat(loginPage.isTimeoutErrorDisplayed())
            .as("Timeout error message should be displayed")
            .isTrue();
        
        String timeoutMessage = loginPage.getTimeoutErrorMessage();
        assertThat(timeoutMessage)
            .as("Timeout message should not be empty")
            .isNotEmpty();
        
        logger.info("Verified timeout error is displayed: {}", timeoutMessage);
    }
    
    @Step("Retry authentication after timeout")
    public void retryAuthenticationAfterTimeout() {
        loginPage.clickRetryButton();
        logger.info("Retried authentication after timeout");
    }
    
    @Step("Verify user can retry authentication")
    public void verifyCanRetryAuthentication() {
        // After clicking retry, user should be able to initiate SSO again
        assertThat(loginPage.isOnLoginPage())
            .as("User should be able to retry authentication")
            .isTrue();
        logger.info("Verified user can retry authentication");
    }
    
    @Step("Wait for session timeout")
    public void waitForSessionTimeout() {
        dashboardPage.waitForSessionTimeout();
        logger.info("Waited for session timeout");
    }
    
    @Step("Verify session has expired")
    public void verifySessionExpired() {
        boolean isSessionExpired = dashboardPage.isSessionTimeoutModalDisplayed() || 
                                 loginPage.isOnLoginPage();
        
        assertThat(isSessionExpired)
            .as("Session should have expired")
            .isTrue();
        logger.info("Verified session has expired");
    }
    
    @Step("Verify redirected to login page")
    public void verifyRedirectedToLoginPage() {
        assertThat(loginPage.isOnLoginPage())
            .as("User should be redirected to login page")
            .isTrue();
        logger.info("Verified user is redirected to login page");
    }
    
    @Step("Attempt to access protected resource")
    public void attemptToAccessProtectedResource() {
        // Try to navigate to a protected page
        dashboardPage.navigateToPipelines();
        logger.info("Attempted to access protected resource");
    }
    
    @Step("Verify prompted to authenticate again")
    public void verifyPromptedToAuthenticateAgain() {
        // Should be redirected to login or see authentication prompt
        boolean needsAuthentication = loginPage.isOnLoginPage() || 
                                    dashboardPage.isSessionTimeoutModalDisplayed();
        
        assertThat(needsAuthentication)
            .as("User should be prompted to authenticate again")
            .isTrue();
        logger.info("Verified user is prompted to authenticate again");
    }
    
    @Step("Verify Prophecy application is accessible")
    public void verifyProphecyApplicationAccessible() {
        loginPage.navigateToLoginPage();
        
        // Verify the page loads without errors
        assertThat(loginPage.isOnLoginPage())
            .as("Prophecy application should be accessible")
            .isTrue();
        
        logger.info("Verified Prophecy application is accessible");
    }
    
    @Step("Ensure user is successfully logged in")
    public void ensureUserLoggedIn() {
        if (!dashboardPage.isOnDashboard()) {
            navigateToLoginPage();
            initiateSSOLogin();
            authenticateWithValidADFS();
            verifySuccessfulLogin();
        }
        logger.info("Ensured user is successfully logged in");
    }
    
    @Step("Verify workspace access")
    public void verifyWorkspaceAccess() {
        assertThat(dashboardPage.hasWorkspaceAccess())
            .as("User should have access to workspace")
            .isTrue();
        logger.info("Verified user has workspace access");
    }
}
package com.prophecy.testing.stepdefinitions;

import com.prophecy.testing.steps.AuthenticationSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;

/**
 * Step definitions for authentication-related scenarios
 */
public class AuthenticationStepDefinitions {
    
    @Steps
    AuthenticationSteps authenticationSteps;
    
    @Given("the Prophecy application is accessible")
    public void the_prophecy_application_is_accessible() {
        authenticationSteps.verifyProphecyApplicationAccessible();
    }
    
    @Given("I am on the Prophecy login page")
    public void i_am_on_the_prophecy_login_page() {
        authenticationSteps.navigateToLoginPage();
        authenticationSteps.verifyOnLoginPage();
    }
    
    @Given("I am successfully logged into Prophecy")
    public void i_am_successfully_logged_into_prophecy() {
        authenticationSteps.ensureUserLoggedIn();
    }
    
    @When("I initiate SSO login")
    public void i_initiate_sso_login() {
        authenticationSteps.initiateSSOLogin();
    }
    
    @When("I authenticate through ADFS with valid credentials")
    public void i_authenticate_through_adfs_with_valid_credentials() {
        authenticationSteps.authenticateWithValidADFS();
    }
    
    @When("I attempt to authenticate through ADFS with invalid credentials")
    public void i_attempt_to_authenticate_through_adfs_with_invalid_credentials() {
        authenticationSteps.authenticateWithInvalidADFS();
    }
    
    @When("the ADFS authentication times out")
    public void the_adfs_authentication_times_out() {
        authenticationSteps.simulateADFSTimeout();
    }
    
    @When("I remain idle for the session timeout period")
    public void i_remain_idle_for_the_session_timeout_period() {
        authenticationSteps.waitForSessionTimeout();
    }
    
    @When("I attempt to access a protected resource")
    public void i_attempt_to_access_a_protected_resource() {
        authenticationSteps.attemptToAccessProtectedResource();
    }
    
    @Then("I should be successfully logged into Prophecy")
    public void i_should_be_successfully_logged_into_prophecy() {
        authenticationSteps.verifySuccessfulLogin();
    }
    
    @Then("I should see the main dashboard")
    public void i_should_see_the_main_dashboard() {
        authenticationSteps.verifyMainDashboardDisplayed();
    }
    
    @Then("my user profile should be displayed correctly")
    public void my_user_profile_should_be_displayed_correctly() {
        authenticationSteps.verifyUserProfileDisplayed();
    }
    
    @Then("I should see an authentication error message")
    public void i_should_see_an_authentication_error_message() {
        authenticationSteps.verifyAuthenticationErrorDisplayed();
    }
    
    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        authenticationSteps.verifyRemainsOnLoginPage();
    }
    
    @Then("I should see a timeout error message")
    public void i_should_see_a_timeout_error_message() {
        authenticationSteps.verifyTimeoutErrorDisplayed();
    }
    
    @Then("I should be able to retry the authentication")
    public void i_should_be_able_to_retry_the_authentication() {
        authenticationSteps.retryAuthenticationAfterTimeout();
        authenticationSteps.verifyCanRetryAuthentication();
    }
    
    @Then("my session should expire")
    public void my_session_should_expire() {
        authenticationSteps.verifySessionExpired();
    }
    
    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() {
        authenticationSteps.verifyRedirectedToLoginPage();
    }
    
    @Then("I should be prompted to authenticate again")
    public void i_should_be_prompted_to_authenticate_again() {
        authenticationSteps.verifyPromptedToAuthenticateAgain();
    }
    
    @Given("I have access to the pipeline workspace")
    public void i_have_access_to_the_pipeline_workspace() {
        authenticationSteps.verifyWorkspaceAccess();
    }
}
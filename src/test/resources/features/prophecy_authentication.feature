@authentication @smoke
Feature: Prophecy SSO Authentication
  As a data engineer
  I want to authenticate to Prophecy using SSO/ADFS
  So that I can access and manage data pipelines securely

  Background:
    Given the Prophecy application is accessible

  @sso @critical
  Scenario: Successful SSO login with ADFS
    Given I am on the Prophecy login page
    When I initiate SSO login
    And I authenticate through ADFS with valid credentials
    Then I should be successfully logged into Prophecy
    And I should see the main dashboard
    And my user profile should be displayed correctly

  @sso @negative
  Scenario: Failed SSO login with invalid credentials
    Given I am on the Prophecy login page
    When I initiate SSO login
    And I attempt to authenticate through ADFS with invalid credentials
    Then I should see an authentication error message
    And I should remain on the login page

  @sso @timeout
  Scenario: SSO login timeout handling
    Given I am on the Prophecy login page
    When I initiate SSO login
    And the ADFS authentication times out
    Then I should see a timeout error message
    And I should be able to retry the authentication

  @session @security
  Scenario: Session management after successful login
    Given I am successfully logged into Prophecy
    When I remain idle for the session timeout period
    Then my session should expire
    And I should be redirected to the login page
    When I attempt to access a protected resource
    Then I should be prompted to authenticate again
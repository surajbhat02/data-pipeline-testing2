package com.prophecy.testing.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Test runner for authentication-related tests only
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/prophecy_authentication.feature",
        glue = {"com.prophecy.testing.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/authentication",
                "json:target/cucumber-reports/authentication/Cucumber.json",
                "junit:target/cucumber-reports/authentication/Cucumber.xml"
        },
        tags = "@authentication"
)
public class AuthenticationTestRunner {
    // This class remains empty - it's just a runner
}
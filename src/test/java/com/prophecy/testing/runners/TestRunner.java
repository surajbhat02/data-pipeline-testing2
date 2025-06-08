package com.prophecy.testing.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Main test runner for all Cucumber scenarios
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.prophecy.testing.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports",
                "json:target/cucumber-reports/Cucumber.json",
                "junit:target/cucumber-reports/Cucumber.xml"
        },
        tags = "not @ignore"
)
public class TestRunner {
    // This class remains empty - it's just a runner
}
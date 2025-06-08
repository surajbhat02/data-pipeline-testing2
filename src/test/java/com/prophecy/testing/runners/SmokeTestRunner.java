package com.prophecy.testing.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Test runner for smoke tests only
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.prophecy.testing.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/smoke",
                "json:target/cucumber-reports/smoke/Cucumber.json",
                "junit:target/cucumber-reports/smoke/Cucumber.xml"
        },
        tags = "@smoke"
)
public class SmokeTestRunner {
    // This class remains empty - it's just a runner
}
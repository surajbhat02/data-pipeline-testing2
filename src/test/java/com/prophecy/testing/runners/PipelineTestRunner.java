package com.prophecy.testing.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Test runner for pipeline execution tests only
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/pipeline_execution.feature",
        glue = {"com.prophecy.testing.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/pipeline",
                "json:target/cucumber-reports/pipeline/Cucumber.json",
                "junit:target/cucumber-reports/pipeline/Cucumber.xml"
        },
        tags = "@pipeline"
)
public class PipelineTestRunner {
    // This class remains empty - it's just a runner
}
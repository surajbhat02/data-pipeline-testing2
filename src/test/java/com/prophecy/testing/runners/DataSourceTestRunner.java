package com.prophecy.testing.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Test runner for data source configuration tests only
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/custom_data_source.feature",
        glue = {"com.prophecy.testing.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/datasource",
                "json:target/cucumber-reports/datasource/Cucumber.json",
                "junit:target/cucumber-reports/datasource/Cucumber.xml"
        },
        tags = "@datasource"
)
public class DataSourceTestRunner {
    // This class remains empty - it's just a runner
}
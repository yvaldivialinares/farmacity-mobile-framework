package com.farmacity.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Main Cucumber test runner for UI (mobile) scenarios.
 *
 * Run a subset of tests using Maven:
 *   mvn test -Dcucumber.filter.tags="@P0 or @P1"
 *   mvn test -Dcucumber.filter.tags="@happy-path"
 *   mvn test -Dcucumber.filter.tags="@welcome-screen"
 *
 * Allure report:
 *   mvn allure:serve
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.farmacity.automation",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/ui-report.html",
                "json:target/cucumber-reports/ui-report.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = "@P0 or @P1",
        monochrome = true
)
public class TestRunner {}

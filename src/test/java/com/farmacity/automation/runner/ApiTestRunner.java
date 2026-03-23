package com.farmacity.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Cucumber runner for API-only scenarios.
 * API tests do NOT start an Appium session — AppiumHooks is excluded from glue.
 *
 * Run:
 *   mvn test -Dtest=ApiTestRunner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/api-features",
        glue = "com.farmacity.automation.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api-report.html",
                "json:target/cucumber-reports/api-report.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ApiTestRunner {}

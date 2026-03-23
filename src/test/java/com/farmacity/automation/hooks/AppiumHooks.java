package com.farmacity.automation.hooks;

import com.farmacity.automation.config.AppiumConfig;
import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.utils.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Cucumber lifecycle hooks — Appium driver setup and teardown.
 *
 * Execution order per scenario:
 *   @Before setUp()     → scenario steps run → @After tearDown()
 *
 * Screenshot on failure: automatically attached to the Cucumber/Allure report.
 */
public class AppiumHooks {

    @Before(order = 1)
    public void setUp() {
        DriverManager.initDriver(
                AppiumConfig.getCapabilities(),
                AppiumConfig.getServerUrl()
        );
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && DriverManager.hasDriver()) {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png",
                        "failure-" + scenario.getName().replaceAll("[^a-zA-Z0-9]", "-"));
            }
        } finally {
            ScenarioContext.clear();
            DriverManager.quitDriver();
        }
    }
}

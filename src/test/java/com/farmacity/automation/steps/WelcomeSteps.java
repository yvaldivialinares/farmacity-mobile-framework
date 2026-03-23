package com.farmacity.automation.steps;

import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.pages.WelcomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

/**
 * Step definitions for welcome-screen.feature.
 * Methods delegate to WelcomePage — never interact with the driver directly.
 */
public class WelcomeSteps {

    private WelcomePage welcomePage() {
        return new WelcomePage(DriverManager.getDriver());
    }

    @When("the user taps the Login with user button")
    public void theUserTapsTheLoginWithUserButton() {
        welcomePage().tapLoginWithUserButton();
    }

    @When("the user taps the Login with code button")
    public void theUserTapsTheLoginWithCodeButton() {
        welcomePage().tapLoginWithCodeButton();
    }

    @Then("the Greeting text is visible")
    public void theGreetingTextIsVisible() {
        Assert.assertTrue("Greeting text '¡Hola!' should be visible on welcome-screen.",
                welcomePage().isGreetingTextVisible());
    }

    @Then("the Greeting text displays {string}")
    public void theGreetingTextDisplays(String expectedText) {
        String actual = welcomePage().getGreetingText();
        Assert.assertEquals("Greeting text mismatch.", expectedText, actual);
    }
}

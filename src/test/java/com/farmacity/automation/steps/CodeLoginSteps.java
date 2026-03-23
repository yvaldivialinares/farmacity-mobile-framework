package com.farmacity.automation.steps;

import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.pages.CodeLoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;

/**
 * Step definitions unique to code-login-screen.
 *
 * Delegated to SharedSteps (shared with other screens):
 *  - "the user enters valid_email in the Email input"
 *  - "the user enters invalid_email_format in the Email input"
 *  - "the user taps the Back button"
 */
public class CodeLoginSteps {

    private CodeLoginPage page() {
        return new CodeLoginPage(DriverManager.getDriver());
    }

    @And("the user taps the Submit button")
    public void theUserTapsTheSubmitButton() {
        page().tapSubmitButton();
    }

    @Then("the Submit button is disabled")
    public void theSubmitButtonIsDisabled() {
        Assert.assertFalse("Submit button should be disabled when email field is empty.",
                page().isSubmitButtonEnabled());
    }

    @Then("the Submit button is enabled")
    public void theSubmitButtonIsEnabled() {
        Assert.assertTrue("Submit button should be enabled after entering a value in the email field.",
                page().isSubmitButtonEnabled());
    }
}

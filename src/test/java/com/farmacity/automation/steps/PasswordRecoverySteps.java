package com.farmacity.automation.steps;

import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.pages.PasswordRecoveryPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;

/**
 * Step definitions unique to password-recovery-screen.
 *
 * Delegated to SharedSteps (shared with other screens):
 *  - "the user enters valid_email in the Email input"
 *  - "the user enters invalid_email_format in the Email input"
 *  - "the user taps the Back button"
 *
 * Delegated to CommonSteps:
 *  - "a recovery link is sent to valid_email" (confirmation feedback — TBC)
 */
public class PasswordRecoverySteps {

    private PasswordRecoveryPage page() {
        return new PasswordRecoveryPage(DriverManager.getDriver());
    }

    @And("the user taps the Recover password button")
    public void theUserTapsTheRecoverPasswordButton() {
        page().tapRecoverPasswordButton();
    }

    @Then("the Recover password button is disabled")
    public void theRecoverPasswordButtonIsDisabled() {
        Assert.assertFalse("Recover password button should be disabled when email field is empty.",
                page().isRecoverPasswordButtonEnabled());
    }

    @Then("the Recover password button is enabled")
    public void theRecoverPasswordButtonIsEnabled() {
        Assert.assertTrue("Recover password button should be enabled after entering a value in the email field.",
                page().isRecoverPasswordButtonEnabled());
    }
}

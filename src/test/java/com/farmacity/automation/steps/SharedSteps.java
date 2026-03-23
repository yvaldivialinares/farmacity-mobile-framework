package com.farmacity.automation.steps;

import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.pages.*;
import com.farmacity.automation.utils.ScenarioContext;
import com.farmacity.automation.utils.TestData;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;

/**
 * Context-aware shared step definitions.
 *
 * Some Gherkin step texts are IDENTICAL across multiple screens (e.g. "the user enters
 * valid_email in the Email input" appears in user-login, code-login, password-recovery,
 * and registration features). Cucumber requires exactly ONE matching step definition per
 * step text — having duplicate definitions in multiple classes causes an ambiguity error.
 *
 * This class resolves the conflict by reading ScenarioContext.getCurrentScreen() (set by
 * CommonSteps after each screen-verification step) and dispatching to the correct Page Object.
 *
 * Steps owned here (shared across ≥ 2 screens):
 *   - "the user enters valid_email in the Email input"
 *   - "the user enters invalid_email_format in the Email input"
 *   - "the user taps the Back button"
 *   - "the user taps the Continue button"
 *   - "the Continue button is disabled"
 *   - "the Continue button is enabled"
 */
public class SharedSteps {

    private AndroidDriver driver() {
        return DriverManager.getDriver();
    }

    private String currentScreen() {
        String screen = ScenarioContext.getCurrentScreen();
        if (screen == null) {
            throw new IllegalStateException(
                    "ScenarioContext has no current screen. " +
                    "Ensure a 'the user is on the X-screen' step runs before this shared step.");
        }
        return screen;
    }

    // -------------------------------------------------------------------------
    // Email input — used on: user-login, code-login, password-recovery, registration,
    //               code-verification (via code-login Background)
    // -------------------------------------------------------------------------

    @And("the user enters valid_email in the Email input")
    public void theUserEntersValidEmailInTheEmailInput() {
        String email = TestData.getValidEmail();
        switch (currentScreen()) {
            case "user-login":
                new UserLoginPage(driver()).enterEmail(email); break;
            case "code-login":
                new CodeLoginPage(driver()).enterEmail(email); break;
            case "password-recovery":
                new PasswordRecoveryPage(driver()).enterEmail(email); break;
            case "registration":
                new RegistrationPage(driver()).enterEmail(email); break;
            default:
                throw new IllegalStateException(
                        "enterEmail(valid_email) called on unexpected screen: " + currentScreen());
        }
    }

    @And("the user enters invalid_email_format in the Email input")
    public void theUserEntersInvalidEmailFormatInTheEmailInput() {
        String email = TestData.getInvalidEmailFormat();
        switch (currentScreen()) {
            case "code-login":
                new CodeLoginPage(driver()).enterEmail(email); break;
            case "password-recovery":
                new PasswordRecoveryPage(driver()).enterEmail(email); break;
            case "registration":
                new RegistrationPage(driver()).enterEmail(email); break;
            default:
                throw new IllegalStateException(
                        "enterEmail(invalid_email_format) called on unexpected screen: " + currentScreen());
        }
    }

    // -------------------------------------------------------------------------
    // Back button — used on: code-login, code-verification, password-recovery, registration
    // -------------------------------------------------------------------------

    @And("the user taps the Back button")
    public void theUserTapsTheBackButton() {
        switch (currentScreen()) {
            case "code-login":
                new CodeLoginPage(driver()).tapBackButton(); break;
            case "code-verification":
                new CodeVerificationPage(driver()).tapBackButton(); break;
            case "password-recovery":
                new PasswordRecoveryPage(driver()).tapBackButton(); break;
            case "registration":
                // Uses "header-back-text" resource-id (unique to this screen — not "icon-button")
                new RegistrationPage(driver()).tapBackButton(); break;
            default:
                throw new IllegalStateException(
                        "tapBackButton() called on unexpected screen: " + currentScreen());
        }
    }

    // -------------------------------------------------------------------------
    // Continue button actions — used on: code-verification, registration
    // -------------------------------------------------------------------------

    @And("the user taps the Continue button")
    public void theUserTapsTheContinueButton() {
        switch (currentScreen()) {
            case "code-verification":
                new CodeVerificationPage(driver()).tapContinueButton(); break;
            case "registration":
                new RegistrationPage(driver()).tapContinueButton(); break;
            default:
                throw new IllegalStateException(
                        "tapContinueButton() called on unexpected screen: " + currentScreen());
        }
    }

    @Then("the Continue button is disabled")
    public void theContinueButtonIsDisabled() {
        boolean enabled;
        switch (currentScreen()) {
            case "code-verification":
                enabled = new CodeVerificationPage(driver()).isContinueButtonEnabled(); break;
            case "registration":
                enabled = new RegistrationPage(driver()).isContinueButtonEnabled(); break;
            default:
                throw new IllegalStateException(
                        "Continue button assertion called on unexpected screen: " + currentScreen());
        }
        Assert.assertFalse("Continue button should be disabled when required fields are not all filled.", enabled);
    }

    @Then("the Continue button is enabled")
    public void theContinueButtonIsEnabled() {
        boolean enabled;
        switch (currentScreen()) {
            case "code-verification":
                enabled = new CodeVerificationPage(driver()).isContinueButtonEnabled(); break;
            case "registration":
                enabled = new RegistrationPage(driver()).isContinueButtonEnabled(); break;
            default:
                throw new IllegalStateException(
                        "Continue button assertion called on unexpected screen: " + currentScreen());
        }
        Assert.assertTrue("Continue button should be enabled when all required fields are filled.", enabled);
    }
}

package com.farmacity.automation.steps;

import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.pages.*;
import com.farmacity.automation.utils.ScenarioContext;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;

/**
 * Shared steps used across multiple screen feature files.
 *
 * Responsibilities:
 *  - Screen arrival assertions ("the user is on the X-screen")
 *  - Screen retention assertions ("the user remains on the X-screen")
 *  - Cross-cutting assertions without a clear screen owner (e.g. error messages)
 *
 * Pending steps: "the error message is displayed" — no error element locators are mapped
 * in the current domain-rules. Run domain-rules-advisor against an error state to map them.
 */
public class CommonSteps {

    private AndroidDriver driver() {
        return DriverManager.getDriver();
    }

    /**
     * Verifies the app is on the named screen by checking a screen-specific landmark element.
     * Pattern matches: "welcome", "user-login", "code-login", "code-verification",
     * "password-recovery", "registration", "home".
     */
    // In Cucumber 7, all step annotations (@Given, @When, @Then, @And, @But) match any Gherkin keyword.
    // A single @Given annotation will correctly match "Given ...", "And ...", and "Then ..." in feature files.
    @Given("^the user is on the ([\\w-]+)-screen$")
    public void theUserIsOnScreen(String screenName) {
        boolean onScreen = verifyScreen(screenName);
        Assert.assertTrue("Expected to be on " + screenName + "-screen but landmark element was not found.",
                onScreen);
        // Update context so SharedSteps can dispatch to the correct Page Object
        ScenarioContext.setCurrentScreen(screenName);
    }

    @Then("^the user remains on the ([\\w-]+)-screen$")
    public void theUserRemainsOnScreen(String screenName) {
        theUserIsOnScreen(screenName);
    }

    @Then("the error message is displayed")
    public void theErrorMessageIsDisplayed() {
        // No error message locators are currently mapped in domain-rules.
        // To unlock: run domain-rules-advisor against an error state on user-login-screen
        // to identify and map the error element, then implement here.
        throw new PendingException(
                "Error message locator not mapped. Run domain-rules-advisor on an error state " +
                "to identify the element, then implement this step.");
    }

    // --- Next-screen stubs for TBC destinations ---

    @Then("the user is on the next screen")
    public void theUserIsOnTheNextScreen() {
        throw new PendingException(
                "code-verification-screen destination after successful registration is TBC. " +
                "Run domain-rules-advisor on the post-registration screen to map it, " +
                "then replace this step with the mapped screen assertion.");
    }

    @Then("the user is on the next registration step")
    public void theUserIsOnTheNextRegistrationStep() {
        throw new PendingException(
                "registration-screen destination after 'Continuar' is TBC (likely code-verification-screen " +
                "via email code delivery). Run domain-rules-advisor to confirm, then implement.");
    }

    @Then("a recovery link is sent to valid_email")
    public void aRecoveryLinkIsSentToValidEmail() {
        throw new PendingException(
                "password-recovery-confirmation-screen is unmapped. " +
                "Run domain-rules-advisor on the post-recovery screen to map the confirmation element.");
    }

    @Then("a new code is sent to valid_email")
    public void aNewCodeIsSentToValidEmail() {
        new CodeVerificationPage(driver()).isResendConfirmationVisible();
        throw new PendingException(
                "Resend code confirmation element locator is not yet mapped. " +
                "Test with a real backend and map the confirmation UI element.");
    }

    // --- Internal screen verification ---

    private boolean verifyScreen(String screenName) {
        switch (screenName) {
            case "welcome":
                return new WelcomePage(driver()).isOnScreen();
            case "user-login":
                return new UserLoginPage(driver()).isOnScreen();
            case "code-login":
                return new CodeLoginPage(driver()).isOnScreen();
            case "code-verification":
                return new CodeVerificationPage(driver()).isOnScreen();
            case "password-recovery":
                return new PasswordRecoveryPage(driver()).isOnScreen();
            case "registration":
                return new RegistrationPage(driver()).isOnScreen();
            case "home":
                return new HomePage(driver()).isOnScreen();

            // Unmapped downstream screens — add cases here as screens are mapped
            case "categories":
            case "category-detail":
            case "prescriptions":
            case "promotion-detail":
            case "profile-completion":
            case "search-results":
            case "orders":
            case "medications":
            case "profile":
            case "menu":
            case "password-recovery-confirmation":
                throw new PendingException(
                        screenName + "-screen is not yet mapped. " +
                        "Run the domain-rules-pipeline for this screen, then add its isOnScreen() case here.");

            default:
                throw new IllegalArgumentException(
                        "Unknown screen '" + screenName + "'. " +
                        "Add a case for it in CommonSteps#verifyScreen() after running domain-rules-pipeline.");
        }
    }
}

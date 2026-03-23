package com.farmacity.automation.steps;

import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.pages.UserLoginPage;
import com.farmacity.automation.utils.TestData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;

/**
 * Step definitions unique to user-login-screen.
 *
 * Note: "the user enters valid_email in the Email input" is owned by SharedSteps
 * (it also appears in code-login, password-recovery, and registration features).
 * All steps defined here use text that is UNIQUE to this screen.
 */
public class UserLoginSteps {

    private UserLoginPage page() {
        return new UserLoginPage(DriverManager.getDriver());
    }

    @And("the user enters valid_password in the Password input")
    public void theUserEntersValidPasswordInThePasswordInput() {
        page().enterPassword(TestData.getValidPassword());
    }

    @And("the user enters invalid_password in the Password input")
    public void theUserEntersInvalidPasswordInThePasswordInput() {
        page().enterPassword(TestData.getInvalidPassword());
    }

    @And("the user taps the Login button")
    public void theUserTapsTheLoginButton() {
        page().tapLoginButton();
    }

    @And("the user taps the Show password toggle")
    public void theUserTapsTheShowPasswordToggle() {
        page().togglePasswordVisibility();
    }

    @And("the user taps the Password recovery button")
    public void theUserTapsThePasswordRecoveryButton() {
        page().tapPasswordRecoveryButton();
    }

    @And("the user taps the Register button")
    public void theUserTapsTheRegisterButton() {
        page().tapRegisterButton();
    }

    @Then("the Login button is disabled")
    public void theLoginButtonIsDisabled() {
        Assert.assertFalse("Login button should be disabled when required fields are not both filled.",
                page().isLoginButtonEnabled());
    }

    @Then("the password is visible")
    public void thePasswordIsVisible() {
        Assert.assertTrue("Password should be visible (plain text) after tapping Show password.",
                page().isPasswordVisible());
    }

    @Then("the password is masked")
    public void thePasswordIsMasked() {
        Assert.assertTrue("Password should be masked after tapping Show password a second time.",
                page().isPasswordMasked());
    }
}

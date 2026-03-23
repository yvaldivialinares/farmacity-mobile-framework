package com.farmacity.automation.steps;

import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.pages.CodeVerificationPage;
import com.farmacity.automation.utils.TestData;
import io.cucumber.java.en.And;

/**
 * Step definitions unique to code-verification-screen.
 *
 * Delegated to SharedSteps (shared with registration-screen or other screens):
 *  - "the user taps the Back button"
 *  - "the user taps the Continue button"
 *  - "the Continue button is disabled"
 *  - "the Continue button is enabled"
 *
 * Delegated to CommonSteps:
 *  - "a new code is sent to valid_email" (resend confirmation — TBC)
 */
public class CodeVerificationSteps {

    private CodeVerificationPage page() {
        return new CodeVerificationPage(DriverManager.getDriver());
    }

    @And("the user enters valid_code in the Code input")
    public void theUserEntersValidCodeInTheCodeInput() {
        page().enterCode(TestData.getValidCode());
    }

    @And("the user enters first_name in the First name input")
    public void theUserEntersFirstNameInTheFirstNameInput() {
        page().enterFirstName(TestData.getFirstName());
    }

    @And("the user enters last_name in the Last name input")
    public void theUserEntersLastNameInTheLastNameInput() {
        page().enterLastName(TestData.getLastName());
    }

    @And("the user selects gender in the Gender selector")
    public void theUserSelectsGenderInTheGenderSelector() {
        page().selectGender(TestData.getGender());
    }

    @And("the user selects date_of_birth in the Date of birth picker")
    public void theUserSelectsDateOfBirthInTheDateOfBirthPicker() {
        page().selectDateOfBirth(TestData.getDateOfBirth());
    }

    @And("the user taps the Resend code link")
    public void theUserTapsTheResendCodeLink() {
        page().tapResendCodeLink();
    }
}

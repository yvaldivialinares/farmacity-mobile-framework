package com.farmacity.automation.steps;

/**
 * Step definitions for registration-screen.
 *
 * All steps in the registration feature use step text shared with other screens.
 * They are handled by the following classes:
 *
 *  SharedSteps:
 *   - "the user enters valid_email in the Email input"
 *   - "the user enters invalid_email_format in the Email input"
 *   - "the user taps the Back button"         → RegistrationPage.tapBackButton() (header-back-text)
 *   - "the user taps the Continue button"     → RegistrationPage.tapContinueButton()
 *   - "the Continue button is disabled"       → RegistrationPage.isContinueButtonEnabled()
 *   - "the Continue button is enabled"        → RegistrationPage.isContinueButtonEnabled()
 *
 *  WelcomeSteps:
 *   - "the user taps the Login with user button"
 *
 *  UserLoginSteps:
 *   - "the user taps the Register button"
 *
 *  CommonSteps:
 *   - "the user is on the X-screen"
 *   - "the user is on the next registration step" (PendingException — destination TBC)
 *
 * No step definitions are needed in this class. It is kept for structural consistency
 * (one Steps class per mapped screen) and as a reference point for future screen-specific steps.
 */
public class RegistrationSteps {
    // No step definitions — see class javadoc for delegation map.
}

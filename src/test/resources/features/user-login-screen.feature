# NOTE: PENDING — "home-screen" is mapped. All destinations from this screen are fully mapped.
# Generated from domain-rules/FARMACITY/user-login-screen.md

@user-login-screen
Feature: User Login Screen — Credential-Based Authentication

  Background:
    Given the user is on the welcome-screen
    When the user taps the Login with user button
    And the user is on the user-login-screen

  @P1 @happy-path
  Scenario: User logs in successfully with valid credentials
    And the user enters valid_email in the Email input
    And the user enters valid_password in the Password input
    And the user taps the Login button
    Then the user is on the home-screen

  @P1 @negative
  Scenario: Login fails with invalid password
    And the user enters valid_email in the Email input
    And the user enters invalid_password in the Password input
    And the user taps the Login button
    Then the error message is displayed
    And the user remains on the user-login-screen

  @P2 @edge
  Scenario: Login button disabled when only email is filled
    And the user enters valid_email in the Email input
    Then the Login button is disabled

  @P2 @functional
  Scenario: Password visibility toggle works correctly
    And the user enters valid_password in the Password input
    And the user taps the Show password toggle
    Then the password is visible
    When the user taps the Show password toggle
    Then the password is masked

  @P2 @happy-path
  Scenario: Navigation to password recovery works
    And the user taps the Password recovery button
    Then the user is on the password-recovery-screen

  @P2 @happy-path
  Scenario: Navigation to registration works
    And the user taps the Register button
    Then the user is on the registration-screen

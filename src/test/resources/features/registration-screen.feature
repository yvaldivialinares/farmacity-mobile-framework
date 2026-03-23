# NOTE: PENDING — destination after "Continuar" is TBC (likely code-verification-screen).
# "the user is on the next registration step" throws PendingException until confirmed.
# Generated from domain-rules/FARMACITY/registration-screen.md

@registration-screen
Feature: Registration Screen — Step 1 (Email Entry)

  Background:
    Given the user is on the welcome-screen
    When the user taps the Login with user button
    And the user is on the user-login-screen
    And the user taps the Register button
    And the user is on the registration-screen

  @P1 @happy-path
  Scenario: User submits email to begin registration
    And the user enters valid_email in the Email input
    And the user taps the Continue button
    Then the user is on the next registration step

  @P1 @edge
  Scenario: Continue button disabled when email field is empty
    Then the Continue button is disabled

  @P2 @edge
  Scenario: Continue button enabled after entering any value in email field
    And the user enters invalid_email_format in the Email input
    Then the Continue button is enabled

  @P2 @happy-path
  Scenario: Back button returns to user login screen
    And the user taps the Back button
    Then the user is on the user-login-screen

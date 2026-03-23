# NOTE: PENDING — destination after "Continuar" is TBC (home-screen or profile-confirmation-screen).
# "the user is on the next screen" throws PendingException until this is confirmed.
# Generated from domain-rules/FARMACITY/code-verification-screen.md

@code-verification-screen
Feature: Code Verification Screen — Code Entry + Profile Creation

  Background:
    Given the user is on the welcome-screen
    When the user taps the Login with code button
    And the user is on the code-login-screen
    And the user enters valid_email in the Email input
    And the user taps the Submit button
    And the user is on the code-verification-screen

  @P1 @happy-path
  Scenario: User completes code verification and profile form
    And the user enters valid_code in the Code input
    And the user enters first_name in the First name input
    And the user enters last_name in the Last name input
    And the user selects gender in the Gender selector
    And the user selects date_of_birth in the Date of birth picker
    And the user taps the Continue button
    Then the user is on the next screen

  @P1 @edge
  Scenario: Continue button disabled when code field is empty
    Then the Continue button is disabled

  @P2 @edge
  Scenario: Continue button still disabled after entering code but leaving profile fields empty
    And the user enters valid_code in the Code input
    Then the Continue button is disabled

  @P2 @edge
  Scenario: User requests a new code via resend link
    And the user taps the Resend code link
    Then a new code is sent to valid_email

  @P2 @happy-path
  Scenario: Back button returns to code login screen
    And the user taps the Back button
    Then the user is on the code-login-screen

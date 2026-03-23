# NOTE: PENDING — none. code-verification-screen is mapped; welcome-screen is the app entry point.
# Generated from domain-rules/FARMACITY/code-login-screen.md

@code-login-screen
Feature: Code Login Screen — Access-Code-Based Authentication (Step 1)

  Background:
    Given the user is on the welcome-screen
    When the user taps the Login with code button
    And the user is on the code-login-screen

  @P1 @happy-path
  Scenario: User requests code with valid email
    And the user enters valid_email in the Email input
    And the user taps the Submit button
    Then the user is on the code-verification-screen

  @P1 @edge
  Scenario: Submit button disabled when email field is empty
    Then the Submit button is disabled

  @P2 @edge
  Scenario: Submit button enabled after entering any value in email field
    And the user enters invalid_email_format in the Email input
    Then the Submit button is enabled

  @P2 @happy-path
  Scenario: Back button returns to welcome screen
    And the user taps the Back button
    Then the user is on the welcome-screen

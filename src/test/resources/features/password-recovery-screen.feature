# NOTE: PENDING — password-recovery-confirmation-screen is unmapped.
# "a recovery link is sent to valid_email" throws PendingException until mapped.
# Generated from domain-rules/FARMACITY/password-recovery-screen.md

@password-recovery-screen
Feature: Password Recovery Screen

  Background:
    Given the user is on the welcome-screen
    When the user taps the Login with user button
    And the user is on the user-login-screen
    And the user taps the Password recovery button
    And the user is on the password-recovery-screen

  @P1 @happy-path
  Scenario: User submits email to receive password recovery link
    And the user enters valid_email in the Email input
    And the user taps the Recover password button
    Then a recovery link is sent to valid_email

  @P1 @edge
  Scenario: Recover button disabled when email field is empty
    Then the Recover password button is disabled

  @P2 @edge
  Scenario: Recover button enabled after entering any value in email field
    And the user enters invalid_email_format in the Email input
    Then the Recover password button is enabled

  @P2 @happy-path
  Scenario: Back button returns to user login screen
    And the user taps the Back button
    Then the user is on the user-login-screen

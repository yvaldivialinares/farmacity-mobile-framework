# NOTE: PENDING — unmapped downstream screens that this screen leads to: none (all mapped)
# Generated from domain-rules/FARMACITY/welcome-screen.md

@welcome-screen
Feature: Welcome Screen — App Entry Point

  Background:
    Given the user is on the welcome-screen

  @P1 @happy-path
  Scenario: User navigates to user-login-screen
    When the user taps the Login with user button
    Then the user is on the user-login-screen

  @P1 @happy-path
  Scenario: User navigates to code-login-screen
    When the user taps the Login with code button
    Then the user is on the code-login-screen

  @P2 @visual
  Scenario: Welcome greeting is displayed correctly
    Then the Greeting text is visible
    And the Greeting text displays "¡Hola!"

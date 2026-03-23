# NOTE: PENDING — 10 downstream screens are unmapped:
#   categories-screen, category-detail-screen, prescriptions-screen, promotion-detail-screen,
#   profile-completion-screen, search-results-screen, orders-screen, medications-screen,
#   profile-screen, menu-screen.
# Navigation steps to unmapped screens throw PendingException.
# Generated from domain-rules/FARMACITY/home-screen.md

@home-screen
Feature: Home Screen — Authenticated Dashboard

  Background:
    Given the user is on the welcome-screen
    When the user taps the Login with user button
    And the user is on the user-login-screen
    And the user enters valid_email in the Email input
    And the user enters valid_password in the Password input
    And the user taps the Login button
    And the user is on the home-screen

  @P0 @happy-path
  Scenario: Successful login reaches home-screen with greeting
    Then the greeting text is visible

  @P0 @happy-path
  Scenario: Bottom navigation bar is complete on home-screen
    Then the Inicio tab is visible
    And the Pedidos tab is visible
    And the Comprar medicamentos tab is visible
    And the Mi perfil tab is visible
    And the Menu tab is visible

  @P1 @happy-path
  Scenario: Ver categorias navigates to categories screen
    And the user taps the View all categories button
    Then the user is on the categories-screen

  @P1 @happy-path
  Scenario: Ver todas mis recetas navigates to prescriptions screen
    And the user taps the Prescriptions CTA button
    Then the user is on the prescriptions-screen

  @P1 @happy-path
  Scenario: Mi perfil tab navigates to profile screen
    And the user taps the Mi perfil tab
    Then the user is on the profile-screen

  @P2 @happy-path
  Scenario: Search bar is visible on home-screen load
    Then the search bar is visible

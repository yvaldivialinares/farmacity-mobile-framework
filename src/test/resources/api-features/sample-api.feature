# Sample API feature file — replace with real Farmacity API endpoints.
# API tests run independently of the Appium session (no mobile driver required).
# Runner: ApiTestRunner — use `mvn test -Dtest=ApiTestRunner`

@api @sample
Feature: Farmacity API — Sample Health Check

  Background:
    Given the API client is configured

  @P1 @api
  Scenario: API base endpoint responds with 200
    When a GET request is made to "/health"
    Then the response status code is 200

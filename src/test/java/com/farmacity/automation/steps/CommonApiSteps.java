package com.farmacity.automation.steps;

import com.farmacity.automation.api.ApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

/**
 * Generic API step definitions shared across all API feature files.
 * Extend this class or add screen-specific API steps as the API test suite grows.
 *
 * These steps are deliberately kept generic — more specific API steps
 * (e.g. for authentication endpoints) should be added in dedicated step classes
 * named after their domain (e.g. AuthApiSteps, ProductApiSteps).
 */
public class CommonApiSteps {

    private ApiClient client;
    private Response lastResponse;

    @Given("the API client is configured")
    public void theApiClientIsConfigured() {
        client = new ApiClient();
    }

    @When("a GET request is made to {string}")
    public void aGetRequestIsMadeTo(String endpoint) {
        lastResponse = client.get(endpoint);
    }

    @When("a POST request is made to {string} with body:")
    public void aPostRequestIsMadeToWithBody(String endpoint, String body) {
        lastResponse = client.post(endpoint, body);
    }

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int expectedStatus) {
        Assert.assertEquals("Unexpected API response status code.",
                expectedStatus, lastResponse.getStatusCode());
    }

    @Then("the response body contains {string}")
    public void theResponseBodyContains(String expectedText) {
        String body = lastResponse.getBody().asString();
        Assert.assertTrue("Expected response body to contain '" + expectedText + "' but it did not.\nBody: " + body,
                body.contains(expectedText));
    }
}

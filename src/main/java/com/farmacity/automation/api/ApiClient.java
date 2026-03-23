package com.farmacity.automation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * Thin wrapper around RestAssured for Farmacity API calls.
 *
 * Usage patterns:
 *  - As test preconditions: seed data or authenticate before UI tests.
 *  - As API test assertions: verify endpoints independently of the mobile UI.
 *
 * All API steps should use this client — never call RestAssured directly from step definitions.
 */
public class ApiClient {

    private final RequestSpecification spec;

    public ApiClient() {
        spec = RestAssured
                .given()
                .baseUri(ApiConfig.getBaseUrl())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        String token = ApiConfig.getAuthToken();
        if (token != null && !token.isEmpty() && !token.startsWith("<FILL_ME")) {
            spec = spec.header("Authorization", "Bearer " + token);
        }
    }

    // --- Convenience methods ---

    public Response get(String endpoint) {
        return spec.when().get(endpoint);
    }

    public Response get(String endpoint, Map<String, ?> queryParams) {
        return spec.queryParams(queryParams).when().get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return spec.body(body).when().post(endpoint);
    }

    public Response put(String endpoint, Object body) {
        return spec.body(body).when().put(endpoint);
    }

    public Response delete(String endpoint) {
        return spec.when().delete(endpoint);
    }

    // --- Status code assertions ---

    public Response expectStatus(Response response, int expectedStatus) {
        response.then().statusCode(expectedStatus);
        return response;
    }
}

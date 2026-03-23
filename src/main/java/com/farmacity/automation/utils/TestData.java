package com.farmacity.automation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Centralised test data provider.
 * All values are loaded from test-data.properties — never hardcode test data in steps.
 * Keys map 1-to-1 with the variables declared in naming-standards.json.
 */
public class TestData {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = TestData.class.getClassLoader()
                .getResourceAsStream("test-data.properties")) {
            if (is == null) {
                throw new IllegalStateException(
                        "test-data.properties not found on classpath. " +
                        "Copy test-data.properties.example → test-data.properties and fill in your values.");
            }
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load test-data.properties", e);
        }
    }

    private TestData() {}

    // --- Credentials ---

    /** Registered Farmacity account email. */
    public static String getValidEmail() {
        return require("valid_email");
    }

    /** Password for the registered account. */
    public static String getValidPassword() {
        return require("valid_password");
    }

    /** Fixed incorrect password used in negative login tests. */
    public static String getInvalidPassword() {
        return get("invalid_password", "x");
    }

    /** First name shown in the home-screen greeting for the test account. */
    public static String getUserFirstName() {
        return require("user_first_name");
    }

    // --- Validation inputs ---

    /** A string that has a non-empty but invalid email format (e.g. "notanemail@"). */
    public static String getInvalidEmailFormat() {
        return get("invalid_email_format", "notanemail@");
    }

    // --- Registration / Code flow ---

    /** 6-digit access code received by email — must be retrieved fresh before each test run. */
    public static String getValidCode() {
        return require("valid_code");
    }

    /** First name for the registration profile form. */
    public static String getFirstName() {
        return get("first_name", "Juan");
    }

    /** Last name for the registration profile form. */
    public static String getLastName() {
        return get("last_name", "Pérez");
    }

    /** Gender option for the registration gender selector — must be a valid option visible in the dropdown. */
    public static String getGender() {
        return require("gender");
    }

    /** Date of birth for the registration date picker (format: dd/MM/yyyy). */
    public static String getDateOfBirth() {
        return get("date_of_birth", "01/01/1990");
    }

    // --- Helpers ---

    private static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    private static String require(String key) {
        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Required test data key '" + key + "' is missing or empty in test-data.properties. " +
                    "Fill in the value before running tests that depend on it.");
        }
        return value;
    }
}

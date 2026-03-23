package com.farmacity.automation.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads api.properties and provides typed accessors for all API configuration values.
 * Add new configuration keys here as the API testing suite grows.
 */
public class ApiConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = ApiConfig.class.getClassLoader()
                .getResourceAsStream("api.properties")) {
            if (is == null) {
                throw new IllegalStateException(
                        "api.properties not found on classpath. " +
                        "Copy api.properties.example → api.properties and fill in your values.");
            }
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load api.properties", e);
        }
    }

    private ApiConfig() {}

    public static String getBaseUrl() {
        return props.getProperty("api.base.url", "https://api.farmacity.com.ar");
    }

    public static int getTimeoutSeconds() {
        return Integer.parseInt(props.getProperty("api.timeout.seconds", "30"));
    }

    public static String getAuthToken() {
        return props.getProperty("api.auth.token", "");
    }
}

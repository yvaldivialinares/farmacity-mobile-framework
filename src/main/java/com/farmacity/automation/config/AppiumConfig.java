package com.farmacity.automation.config;

import io.appium.java_client.android.options.UiAutomator2Options;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

/**
 * Reads appium.properties and exposes typed capability objects.
 * All Appium session parameters are centralised here — never scatter
 * capabilities across step definitions or hooks.
 */
public class AppiumConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = AppiumConfig.class.getClassLoader()
                .getResourceAsStream("appium.properties")) {
            if (is == null) {
                throw new IllegalStateException(
                        "appium.properties not found on classpath. " +
                        "Copy appium.properties.example → appium.properties and fill in your values.");
            }
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load appium.properties", e);
        }
    }

    public static URL getServerUrl() {
        try {
            return new URL(props.getProperty("appium.server.url", "http://localhost:4723"));
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid appium.server.url in appium.properties", e);
        }
    }

    public static UiAutomator2Options getCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName(props.getProperty("appium.platform.name", "Android"));
        options.setDeviceName(props.getProperty("appium.device.name", "emulator-5554"));
        options.setAutomationName(props.getProperty("appium.automation.name", "UiAutomator2"));
        options.setAppPackage(props.getProperty("appium.app.package"));
        options.setAppActivity(props.getProperty("appium.app.activity"));

        boolean noReset = Boolean.parseBoolean(props.getProperty("appium.no.reset", "true"));
        boolean fullReset = Boolean.parseBoolean(props.getProperty("appium.full.reset", "false"));
        options.setNoReset(noReset);
        options.setFullReset(fullReset);

        int newCommandTimeout = Integer.parseInt(props.getProperty("appium.new.command.timeout", "60"));
        options.setNewCommandTimeout(Duration.ofSeconds(newCommandTimeout));

        // Do NOT set appium:app — avoids restarting the app on session creation
        return options;
    }

    public static int getExplicitWaitSeconds() {
        return Integer.parseInt(props.getProperty("appium.explicit.wait", "15"));
    }
}

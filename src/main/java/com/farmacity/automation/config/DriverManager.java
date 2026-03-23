package com.farmacity.automation.config;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;

/**
 * Thread-local driver registry.
 * Supports parallel execution across multiple devices without static state conflicts.
 * Each test thread owns exactly one AndroidDriver instance.
 */
public class DriverManager {

    private static final ThreadLocal<AndroidDriver> driverThread = new ThreadLocal<>();

    private DriverManager() {}

    /**
     * Creates an AndroidDriver from the given options and registers it for the current thread.
     * Called once per scenario from AppiumHooks#setUp().
     */
    public static void initDriver(UiAutomator2Options options, URL serverUrl) {
        if (driverThread.get() != null) {
            throw new IllegalStateException(
                    "A driver is already registered for this thread. Call quitDriver() first.");
        }
        AndroidDriver driver = new AndroidDriver(serverUrl, options);
        driverThread.set(driver);
    }

    /**
     * Returns the AndroidDriver for the current thread.
     * Throws if no driver has been initialised.
     */
    public static AndroidDriver getDriver() {
        AndroidDriver driver = driverThread.get();
        if (driver == null) {
            throw new IllegalStateException(
                    "No driver found for this thread. Ensure AppiumHooks#setUp() ran before this step.");
        }
        return driver;
    }

    /**
     * Quits the current driver and cleans up the thread-local reference.
     * Called once per scenario from AppiumHooks#tearDown().
     */
    public static void quitDriver() {
        AndroidDriver driver = driverThread.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driverThread.remove();
            }
        }
    }

    public static boolean hasDriver() {
        return driverThread.get() != null;
    }
}

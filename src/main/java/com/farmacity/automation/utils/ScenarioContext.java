package com.farmacity.automation.utils;

/**
 * Thread-local scenario context.
 *
 * Tracks the last verified screen name so that shared step definitions
 * (steps whose Gherkin text is identical across multiple screens) can dispatch
 * to the correct Page Object without ambiguity.
 *
 * Updated by CommonSteps#theUserIsOnScreen() after each successful screen assertion.
 * Read by SharedSteps to determine which Page Object to instantiate.
 */
public class ScenarioContext {

    private static final ThreadLocal<String> currentScreen = new ThreadLocal<>();

    private ScenarioContext() {}

    public static void setCurrentScreen(String screenName) {
        currentScreen.set(screenName);
    }

    /**
     * Returns the last screen name confirmed by a "the user is on the X-screen" step.
     * Returns null if no screen has been confirmed yet in this scenario.
     */
    public static String getCurrentScreen() {
        return currentScreen.get();
    }

    /** Clears the context. Called by AppiumHooks teardown (implicitly via thread cleanup). */
    public static void clear() {
        currentScreen.remove();
    }
}

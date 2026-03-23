package com.farmacity.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Welcome Screen — app entry point.
 * Two authentication paths: credential-based login or access-code login.
 *
 * Locator notes (from domain-rules):
 *  - Greeting text has no resource-id or content-desc; text-based XPath is the only option.
 *  - Login buttons use content-desc (accessibility id) as primary.
 */
public class WelcomePage extends BasePage {

    // --- Greeting ---
    // FRAGILE: text-based XPath — breaks on copy/localization changes
    private final By greetingText     = By.xpath("//android.widget.TextView[@text=\"\u00a1Hola!\"]");
    private final By greetingTextXpath = By.xpath("//android.widget.TextView[@text=\"\u00a1Hola!\"]");

    // --- Authentication navigation ---
    private final By loginWithUserButton      = By.xpath("//*[@content-desc='Ingresar con usuario']");
    private final By loginWithUserButtonXpath  = By.xpath("//android.widget.Button[@content-desc='Ingresar con usuario']");

    private final By loginWithCodeButton      = By.xpath("//*[@content-desc='Ingresar con c\u00f3digo']");
    private final By loginWithCodeButtonXpath  = By.xpath("//android.widget.Button[@content-desc='Ingresar con c\u00f3digo']");

    public WelcomePage(AndroidDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public void tapLoginWithUserButton() {
        tap(loginWithUserButton, loginWithUserButtonXpath);
    }

    public void tapLoginWithCodeButton() {
        tap(loginWithCodeButton, loginWithCodeButtonXpath);
    }

    // --- Queries ---

    public boolean isGreetingTextVisible() {
        return isVisible(greetingText, greetingTextXpath);
    }

    public String getGreetingText() {
        return getText(greetingText, greetingTextXpath);
    }

    /** Screen identity verification — checks the primary CTA button. */
    public boolean isOnScreen() {
        return isVisible(loginWithUserButton, loginWithUserButtonXpath);
    }
}

package com.farmacity.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Password Recovery Screen.
 * Reached from: user-login-screen via "¿Olvidaste tu contraseña?".
 * Leads to: password-recovery-confirmation-screen (TBC), user-login-screen (back).
 *
 * Locator notes:
 *  - Email input uses content-desc as primary, resource-id xpath as fallback.
 *    resource-id "email-input" is distinct from "email-for-code" on code-login-screen.
 *  - Recover password button has NO resource-id — content-desc is the only stable locator.
 *    Both primary and fallback use content-desc xpath.
 *  - Back button uses "icon-button" (consistent app-wide pattern).
 */
public class PasswordRecoveryPage extends BasePage {

    // --- Email input ---
    private final By emailInput      = By.xpath("//*[@content-desc='Correo electr\u00f3nico']");
    private final By emailInputXpath = By.xpath("//android.widget.EditText[@resource-id='email-input']");

    // --- Recover password button (no resource-id — content-desc only) ---
    private final By recoverPasswordButton      = By.xpath("//*[@content-desc='Recuperar contrase\u00f1a']");
    private final By recoverPasswordButtonXpath = By.xpath("//android.widget.Button[@content-desc='Recuperar contrase\u00f1a']");

    // --- Back button ---
    private final By backButton      = By.id("icon-button");
    private final By backButtonXpath = By.xpath("//android.widget.Button[@resource-id='icon-button']");

    // --- Screen title (validation anchor — text-based XPath only) ---
    // FRAGILE: no resource-id or content-desc available
    private final By screenTitle      = By.xpath("//android.widget.TextView[@text='\u00bfOlvidaste tu contrase\u00f1a?']");
    private final By screenTitleXpath = By.xpath("//android.widget.TextView[@text='\u00bfOlvidaste tu contrase\u00f1a?']");

    public PasswordRecoveryPage(AndroidDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public void enterEmail(String email) {
        type(emailInput, emailInputXpath, email);
    }

    public void tapRecoverPasswordButton() {
        tap(recoverPasswordButton, recoverPasswordButtonXpath);
    }

    public void tapBackButton() {
        tap(backButton, backButtonXpath);
    }

    /** Composite: enters email + taps recover button. */
    public void requestPasswordRecovery(String email) {
        enterEmail(email);
        tapRecoverPasswordButton();
    }

    // --- Queries ---

    public boolean isRecoverPasswordButtonEnabled() {
        return isEnabled(recoverPasswordButton, recoverPasswordButtonXpath);
    }

    public boolean isScreenTitleVisible() {
        return isVisible(screenTitle, screenTitleXpath);
    }

    /**
     * Verifies recovery confirmation feedback appeared.
     * Exact locator TBC — requires real backend interaction to observe element.
     */
    public boolean isRecoveryConfirmationVisible() {
        // TODO: map the confirmation feedback element after testing with a real registered email.
        return false;
    }

    /** Screen identity verification — checks for email input. */
    public boolean isOnScreen() {
        return isVisible(emailInput, emailInputXpath);
    }
}

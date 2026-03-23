package com.farmacity.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Registration Screen — Step 1 of the registration flow (email only).
 * Reached from: user-login-screen via "Registrarme".
 * Leads to: code-verification-screen (TBC — via email code delivery), user-login-screen (back).
 *
 * Locator notes:
 *  - Email input resource-id "email-input" is SHARED with password-recovery-screen.
 *    Always use this page object to scope the locator — do NOT share the By reference.
 *  - Continue button shares resource-id "submit-button" and content-desc "Continuar"
 *    with code-verification-screen. Each page object is independently scoped.
 *  - Back button uses resource-id "header-back-text" — UNIQUE to this screen.
 *    All other screens use "icon-button". Do NOT reuse tapBackButton() across pages.
 */
public class RegistrationPage extends BasePage {

    // --- Screen title (shared with code-verification-screen — same registration funnel) ---
    private final By screenTitle      = By.id("crea-tu-cuenta");
    private final By screenTitleXpath = By.xpath("//android.widget.TextView[@resource-id='crea-tu-cuenta']");

    // --- Email input ---
    private final By emailInput      = By.xpath("//*[@content-desc='Correo electr\u00f3nico']");
    private final By emailInputXpath = By.xpath("//android.widget.EditText[@resource-id='email-input']");

    // --- Continue button ---
    private final By continueButton      = By.xpath("//*[@content-desc='Continuar']");
    private final By continueButtonXpath = By.xpath("//android.widget.Button[@resource-id='submit-button']");

    // --- Back button — "header-back-text" is UNIQUE to this screen ---
    private final By backButton      = By.id("header-back-text");
    private final By backButtonXpath = By.xpath("//android.widget.Button[@resource-id='header-back-text']");

    public RegistrationPage(AndroidDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public void enterEmail(String email) {
        type(emailInput, emailInputXpath, email);
    }

    public void tapContinueButton() {
        tap(continueButton, continueButtonXpath);
    }

    /** Uses "header-back-text" — do NOT share this implementation with other page objects. */
    public void tapBackButton() {
        tap(backButton, backButtonXpath);
    }

    /** Composite: enters email + taps continue. Advances to next registration step. */
    public void submitRegistrationEmail(String email) {
        enterEmail(email);
        tapContinueButton();
    }

    // --- Queries ---

    public boolean isContinueButtonEnabled() {
        return isEnabled(continueButton, continueButtonXpath);
    }

    public boolean isScreenTitleVisible() {
        return isVisible(screenTitle, screenTitleXpath);
    }

    /**
     * Screen identity verification.
     * Uses the unique "header-back-text" back button to distinguish this screen
     * from code-verification-screen (which shares the title resource-id).
     */
    public boolean isOnScreen() {
        return isVisible(backButton, backButtonXpath);
    }
}

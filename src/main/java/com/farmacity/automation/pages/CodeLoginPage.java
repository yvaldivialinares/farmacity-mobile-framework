package com.farmacity.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Code Login Screen — access-code-based authentication (step 1: email input).
 * Reached from: welcome-screen via "Ingresar con código".
 * Leads to: code-verification-screen (valid email), welcome-screen (back).
 *
 * Locator notes:
 *  - Email input has three locators (accessibility id, resource-id, xpath); primary is content-desc,
 *    XPath fallback uses the resource-id xpath.
 *  - Submit button similarly has three; primary is content-desc.
 *  - Back button uses resource-id "icon-button" (app-wide consistent pattern).
 */
public class CodeLoginPage extends BasePage {

    // --- Email input ---
    private final By emailInput      = By.xpath("//*[@content-desc='Correo electr\u00f3nico']");
    private final By emailInputXpath = By.xpath("//android.widget.EditText[@resource-id='email-for-code']");

    // --- Submit button ---
    private final By submitButton      = By.xpath("//*[@content-desc='Recibir c\u00f3digo por email']");
    private final By submitButtonXpath = By.xpath("//android.widget.Button[@resource-id='submit-button']");

    // --- Back button (icon-button — app-wide consistent resource-id) ---
    private final By backButton      = By.id("icon-button");
    private final By backButtonXpath = By.xpath("//android.widget.Button[@resource-id='icon-button']");

    public CodeLoginPage(AndroidDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public void enterEmail(String email) {
        type(emailInput, emailInputXpath, email);
    }

    public void tapSubmitButton() {
        tap(submitButton, submitButtonXpath);
    }

    public void tapBackButton() {
        tap(backButton, backButtonXpath);
    }

    /** Composite: enters email + taps submit. Navigates to code-verification-screen. */
    public void requestAccessCode(String email) {
        enterEmail(email);
        tapSubmitButton();
    }

    // --- Queries ---

    public boolean isSubmitButtonEnabled() {
        return isEnabled(submitButton, submitButtonXpath);
    }

    /** Screen identity verification — checks for the email input field. */
    public boolean isOnScreen() {
        return isVisible(emailInput, emailInputXpath);
    }
}

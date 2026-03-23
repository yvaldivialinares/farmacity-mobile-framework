package com.farmacity.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * User Login Screen — credential-based authentication.
 * Reached from: welcome-screen via "Ingresar con usuario".
 * Leads to: home-screen (valid credentials), password-recovery-screen, registration-screen.
 *
 * Locator notes:
 *  - Email and Password inputs have stable resource-ids (primary) with XPath fallbacks.
 *  - Login button and navigation links use content-desc (accessibility id).
 *  - Show password toggle uses content-desc "Show password".
 */
public class UserLoginPage extends BasePage {

    // --- Email input ---
    private final By emailInput      = By.id("correoElectronico-input");
    private final By emailInputXpath = By.xpath("//android.widget.EditText[@resource-id='correoElectronico-input']");

    // --- Password input ---
    private final By passwordInput      = By.id("password-input");
    private final By passwordInputXpath = By.xpath("//android.widget.EditText[@resource-id='password-input']");

    // --- Show password toggle ---
    private final By showPasswordToggle      = By.xpath("//*[@content-desc='Show password']");
    private final By showPasswordToggleXpath = By.xpath("//android.widget.Button[@content-desc='Show password']");

    // --- Login button ---
    private final By loginButton      = By.xpath("//*[@content-desc='Iniciar sesi\u00f3n']");
    private final By loginButtonXpath = By.xpath("//android.widget.Button[@content-desc='Iniciar sesi\u00f3n']");

    // --- Account navigation ---
    private final By passwordRecoveryButton      = By.xpath("//*[@content-desc='\u00bfOlvidaste tu contrase\u00f1a?']");
    private final By passwordRecoveryButtonXpath = By.xpath("//android.widget.Button[@content-desc='\u00bfOlvidaste tu contrase\u00f1a?']");

    private final By registerButton      = By.xpath("//*[@content-desc='Registrarme']");
    private final By registerButtonXpath = By.xpath("//android.widget.Button[@content-desc='Registrarme']");

    public UserLoginPage(AndroidDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public void enterEmail(String email) {
        type(emailInput, emailInputXpath, email);
    }

    public void enterPassword(String password) {
        type(passwordInput, passwordInputXpath, password);
    }

    public void tapLoginButton() {
        tap(loginButton, loginButtonXpath);
    }

    public void togglePasswordVisibility() {
        tap(showPasswordToggle, showPasswordToggleXpath);
    }

    public void tapPasswordRecoveryButton() {
        tap(passwordRecoveryButton, passwordRecoveryButtonXpath);
    }

    public void tapRegisterButton() {
        tap(registerButton, registerButtonXpath);
    }

    /** Composite: fills email + password + taps login. Used in Background navigation flows. */
    public void loginWith(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        tapLoginButton();
    }

    // --- Queries ---

    public boolean isLoginButtonEnabled() {
        return isEnabled(loginButton, loginButtonXpath);
    }

    /**
     * Returns true when the password field is in plain-text (visible) mode.
     * Android exposes the input type via the "password" attribute: "false" = visible.
     */
    public boolean isPasswordVisible() {
        String attr = getAttribute(passwordInput, passwordInputXpath, "password");
        return "false".equals(attr);
    }

    public boolean isPasswordMasked() {
        String attr = getAttribute(passwordInput, passwordInputXpath, "password");
        return "true".equals(attr);
    }

    /** Screen identity verification — checks for email input. */
    public boolean isOnScreen() {
        return isVisible(emailInput, emailInputXpath);
    }
}

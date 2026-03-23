package com.farmacity.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Code Verification + Profile Creation Screen.
 * Reached from: code-login-screen via submit button (valid email entered).
 * Leads to: home-screen (TBC — valid code + profile), code-login-screen (back).
 *
 * Screen complexity: this is a combined flow — code verification AND profile creation
 * are on the same screen. The Continue button is disabled until both the code field
 * AND all mandatory profile fields (nombre, apellido, género, fecha de nacimiento) are filled.
 *
 * Locator notes:
 *  - Code input: primary is content-desc, fallback is resource-id xpath.
 *  - Resend code link: observed clickable=false — may require parent ViewGroup tap.
 *    See tapResendCodeLink() implementation note.
 *  - "apelido-input" resource-id is a typo in the app source — use as-is.
 *  - Continue button: shares "submit-button" resource-id with other screens — scoped by page object.
 */
public class CodeVerificationPage extends BasePage {

    // --- Screen header ---
    private final By screenTitle      = By.id("crea-tu-cuenta");
    private final By screenTitleXpath = By.xpath("//android.widget.TextView[@resource-id='crea-tu-cuenta']");

    // --- Code verification ---
    private final By codeInput      = By.xpath("//*[@content-desc='Ingres\u00e1 el c\u00f3digo de acceso']");
    private final By codeInputXpath = By.xpath("//android.widget.EditText[@resource-id='codigo-input']");

    // FRAGILE: text-based, clickable=false — may need parent ViewGroup tap
    private final By resendCodeLink      = By.xpath("//android.widget.TextView[@text='\u00bfNo recibiste el c\u00f3digo? Solicitar otro c\u00f3digo']");
    private final By resendCodeLinkXpath = By.xpath("//android.widget.TextView[@text='\u00bfNo recibiste el c\u00f3digo? Solicitar otro c\u00f3digo']");

    // --- Profile creation form ---
    private final By firstNameInput      = By.xpath("//*[@content-desc='Nombre']");
    private final By firstNameInputXpath = By.xpath("//android.widget.EditText[@resource-id='nombre-input']");

    // Note: resource-id is "apelido-input" (app-side typo for "apellido") — do not correct
    private final By lastNameInput      = By.xpath("//*[@content-desc='Apellido']");
    private final By lastNameInputXpath = By.xpath("//android.widget.EditText[@resource-id='apelido-input']");

    private final By genderSelector      = By.xpath("//*[@content-desc='G\u00e9nero']");
    private final By genderSelectorXpath = By.xpath("//android.view.View[@resource-id='gender-select-field']");

    private final By dateOfBirthPicker      = By.xpath("//*[@content-desc='Fecha de nacimiento, *']");
    private final By dateOfBirthPickerXpath = By.xpath("//android.view.ViewGroup[@resource-id='dateOfBirth-touchable']");

    // --- Navigation ---
    private final By backButton      = By.id("icon-button");
    private final By backButtonXpath = By.xpath("//android.widget.Button[@resource-id='icon-button']");

    private final By continueButton      = By.xpath("//*[@content-desc='Continuar']");
    private final By continueButtonXpath = By.xpath("//android.widget.Button[@resource-id='submit-button']");

    public CodeVerificationPage(AndroidDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public void enterCode(String code) {
        type(codeInput, codeInputXpath, code);
    }

    public void enterFirstName(String name) {
        type(firstNameInput, firstNameInputXpath, name);
    }

    public void enterLastName(String name) {
        type(lastNameInput, lastNameInputXpath, name);
    }

    public void selectGender(String gender) {
        tap(genderSelector, genderSelectorXpath);
        // After the selector opens, tap the option matching the gender value.
        // Selector opens a native dialog/sheet — locate option by content-desc or text.
        By genderOption = By.xpath("//*[@content-desc='" + gender + "' or @text='" + gender + "']");
        tap(genderOption, genderOption);
    }

    public void selectDateOfBirth(String dob) {
        tap(dateOfBirthPicker, dateOfBirthPickerXpath);
        // Date picker interaction depends on app implementation (native DatePicker or custom wheel).
        // TODO: implement date picker interaction during framework stabilisation phase.
        // For now, the picker opens — engineers must complete this interaction.
    }

    /**
     * Taps the resend code link.
     * Note: the link's clickable attribute is "false" on the TextView itself.
     * First attempt uses the direct XPath; if that fails, the fallback tries
     * the same locator (engineers should replace with parent ViewGroup if needed).
     */
    public void tapResendCodeLink() {
        tap(resendCodeLink, resendCodeLinkXpath);
    }

    public void tapBackButton() {
        tap(backButton, backButtonXpath);
    }

    public void tapContinueButton() {
        tap(continueButton, continueButtonXpath);
    }

    /**
     * Composite: fills code + all required profile fields + taps Continue.
     */
    public void completeRegistration(String code, String firstName, String lastName,
                                     String gender, String dob) {
        enterCode(code);
        enterFirstName(firstName);
        enterLastName(lastName);
        selectGender(gender);
        selectDateOfBirth(dob);
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
     * Verifies that some confirmation feedback appeared after tapping Resend.
     * Exact locator TBC — requires real backend interaction to observe element.
     */
    public boolean isResendConfirmationVisible() {
        // TODO: map the confirmation feedback element after testing with real backend.
        return false;
    }

    /** Screen identity verification — checks for screen title + code input. */
    public boolean isOnScreen() {
        return isVisible(screenTitle, screenTitleXpath) && isVisible(codeInput, codeInputXpath);
    }
}

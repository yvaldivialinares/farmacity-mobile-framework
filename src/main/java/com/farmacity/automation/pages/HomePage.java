package com.farmacity.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Home Screen — main authenticated dashboard.
 * Reached from: user-login-screen via "Iniciar sesión" (valid credentials).
 * Leads to: 10 downstream screens, all currently unmapped.
 *
 * Content notes (from domain-rules):
 *  - Greeting text is dynamic — contains the logged-in user's first name. Use contains() assertions.
 *  - Categories carousel and promotion cards are server-driven — avoid asserting specific content.
 *  - Search bar has no resource-id or content-desc — text placeholder XPath only.
 *  - Bottom navigation bar is factored into BottomNavPage (separate shared component).
 */
public class HomePage extends BasePage {

    // --- Welcome header ---
    // FRAGILE: dynamic name embedded — use contains(@text,'¡Hola,') for stable assertions
    private final By greetingText      = By.xpath("//*[contains(@text,'\u00a1Hola,')]");
    private final By greetingTextXpath = By.xpath("//*[contains(@text,'\u00a1Hola,')]");

    private final By completeProfileButton      = By.xpath("//*[@content-desc='Completar perfil']");
    private final By completeProfileButtonXpath = By.xpath("//android.view.ViewGroup[@content-desc='Completar perfil']");

    // --- Categories ---
    private final By viewAllCategoriesButton      = By.id("button-ver-categorias");
    private final By viewAllCategoriesButtonXpath = By.xpath("//android.view.ViewGroup[@resource-id='button-ver-categorias']");

    private final By categoriesCarousel      = By.id("featured-categories-carousel");
    private final By categoriesCarouselXpath = By.xpath("//android.widget.HorizontalScrollView[@resource-id='featured-categories-carousel']");

    // --- Prescriptions ---
    private final By prescriptionsButton      = By.xpath("//*[@content-desc='Ver todas mis recetas']");
    private final By prescriptionsButtonXpath = By.xpath("//android.widget.Button[@content-desc='Ver todas mis recetas']");

    // --- Search bar ---
    // FRAGILE: placeholder text-based XPath — no resource-id or content-desc available
    private final By searchBar      = By.xpath("//android.widget.EditText[@text='Buscar en Farmacity']");
    private final By searchBarXpath = By.xpath("//android.widget.EditText[@text='Buscar en Farmacity']");

    public HomePage(AndroidDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public void tapViewAllCategoriesButton() {
        tap(viewAllCategoriesButton, viewAllCategoriesButtonXpath);
    }

    public void tapPrescriptionsButton() {
        tap(prescriptionsButton, prescriptionsButtonXpath);
    }

    public void tapCompletarPerfilButton() {
        tap(completeProfileButton, completeProfileButtonXpath);
    }

    /**
     * Taps a category card by its content-desc (category name).
     * Content is server-driven — use known stable category names in tests.
     */
    public void tapCategoryCard(String categoryName) {
        By cardLocator = By.xpath("//*[@content-desc='" + categoryName + "']");
        tap(cardLocator, cardLocator);
    }

    // --- Queries ---

    public boolean isGreetingVisible() {
        return isVisible(greetingText, greetingTextXpath);
    }

    public boolean isSearchBarVisible() {
        return isVisible(searchBar, searchBarXpath);
    }

    public boolean isCategoriesCarouselVisible() {
        return isVisible(categoriesCarousel, categoriesCarouselXpath);
    }

    /** Screen identity verification — checks for greeting text containing "¡Hola,". */
    public boolean isOnScreen() {
        return isVisible(greetingText, greetingTextXpath);
    }
}

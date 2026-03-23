package com.farmacity.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * Bottom Navigation Bar — persistent across all authenticated screens.
 * This is a shared UI component, NOT a full screen. Instantiate it alongside the
 * host screen's Page Object whenever bottom nav interactions or assertions are needed.
 *
 * Tab inventory: Inicio · Pedidos · Comprar medicamentos · Mi perfil · Menú
 *
 * Locator notes:
 *  - All tabs use content-desc as primary (stable if app does not localise).
 *  - "Comprar medicamentos" tab additionally has resource-id "medicamentos-button"
 *    (most robustly identifiable tab).
 *  - All downstream screens reached by bottom nav tabs are currently unmapped —
 *    navigation steps throw PendingException until those screens are mapped.
 */
public class BottomNavPage extends BasePage {

    // --- Inicio tab ---
    private final By inicioTab      = By.xpath("//*[@content-desc='Inicio']");
    private final By inicioTabXpath = By.xpath("//android.widget.Button[@content-desc='Inicio']");

    // --- Pedidos tab ---
    private final By pedidosTab      = By.xpath("//*[@content-desc='Pedidos']");
    private final By pedidosTabXpath = By.xpath("//android.widget.Button[@content-desc='Pedidos']");

    // --- Comprar medicamentos tab (only tab with resource-id) ---
    private final By comprarMedicamentosTab      = By.xpath("//*[@content-desc='Comprar medicamentos']");
    private final By comprarMedicamentosTabXpath = By.id("medicamentos-button");

    // --- Mi perfil tab ---
    private final By miPerfilTab      = By.xpath("//*[@content-desc='Mi perfil']");
    private final By miPerfilTabXpath = By.xpath("//android.widget.Button[@content-desc='Mi perfil']");

    // --- Menú tab ---
    private final By menuTab      = By.xpath("//*[@content-desc='Men\u00fa']");
    private final By menuTabXpath = By.xpath("//android.widget.Button[@content-desc='Men\u00fa']");

    public BottomNavPage(AndroidDriver driver) {
        super(driver);
    }

    // --- Actions ---

    public void tapInicioTab() {
        tap(inicioTab, inicioTabXpath);
    }

    public void tapPedidosTab() {
        tap(pedidosTab, pedidosTabXpath);
    }

    public void tapComprarMedicamentosTab() {
        tap(comprarMedicamentosTab, comprarMedicamentosTabXpath);
    }

    public void tapMiPerfilTab() {
        tap(miPerfilTab, miPerfilTabXpath);
    }

    public void tapMenuTab() {
        tap(menuTab, menuTabXpath);
    }

    // --- Queries ---

    public boolean isInicioTabVisible() {
        return isVisible(inicioTab, inicioTabXpath);
    }

    public boolean isPedidosTabVisible() {
        return isVisible(pedidosTab, pedidosTabXpath);
    }

    public boolean isComprarMedicamentosTabVisible() {
        return isVisible(comprarMedicamentosTab, comprarMedicamentosTabXpath);
    }

    public boolean isMiPerfilTabVisible() {
        return isVisible(miPerfilTab, miPerfilTabXpath);
    }

    public boolean isMenuTabVisible() {
        return isVisible(menuTab, menuTabXpath);
    }

    /** Returns true when all 5 bottom nav tabs are visible. */
    public boolean areAllTabsVisible() {
        return isInicioTabVisible()
                && isPedidosTabVisible()
                && isComprarMedicamentosTabVisible()
                && isMiPerfilTabVisible()
                && isMenuTabVisible();
    }
}

package com.farmacity.automation.steps;

import com.farmacity.automation.config.DriverManager;
import com.farmacity.automation.pages.BottomNavPage;
import com.farmacity.automation.pages.HomePage;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;

/**
 * Step definitions for home-screen.feature.
 *
 * Uses both HomePage (content area) and BottomNavPage (persistent bottom nav component).
 * All 10 downstream destinations are currently unmapped — navigation steps throw PendingException
 * until those screens are processed through the domain-rules-pipeline.
 */
public class HomeSteps {

    private HomePage home() {
        return new HomePage(DriverManager.getDriver());
    }

    private BottomNavPage nav() {
        return new BottomNavPage(DriverManager.getDriver());
    }

    // --- Content area ---

    @Then("the greeting text is visible")
    public void theGreetingTextIsVisible() {
        Assert.assertTrue("Greeting text containing '¡Hola,' should be visible after successful login.",
                home().isGreetingVisible());
    }

    @Then("the search bar is visible")
    public void theSearchBarIsVisible() {
        Assert.assertTrue("Search bar should be visible on home-screen load.",
                home().isSearchBarVisible());
    }

    @And("the user taps the View all categories button")
    public void theUserTapsTheViewAllCategoriesButton() {
        home().tapViewAllCategoriesButton();
    }

    @And("the user taps the Prescriptions CTA button")
    public void theUserTapsThePrescriptionsCTAButton() {
        home().tapPrescriptionsButton();
    }

    // --- Bottom navigation ---

    @Then("the Inicio tab is visible")
    public void theInicioTabIsVisible() {
        Assert.assertTrue("Inicio tab should be visible in the bottom navigation bar.",
                nav().isInicioTabVisible());
    }

    @Then("the Pedidos tab is visible")
    public void thePedidosTabIsVisible() {
        Assert.assertTrue("Pedidos tab should be visible in the bottom navigation bar.",
                nav().isPedidosTabVisible());
    }

    @Then("the Comprar medicamentos tab is visible")
    public void theComprarMedicamentosTabIsVisible() {
        Assert.assertTrue("Comprar medicamentos tab should be visible in the bottom navigation bar.",
                nav().isComprarMedicamentosTabVisible());
    }

    @Then("the Mi perfil tab is visible")
    public void theMiPerfilTabIsVisible() {
        Assert.assertTrue("Mi perfil tab should be visible in the bottom navigation bar.",
                nav().isMiPerfilTabVisible());
    }

    @Then("the Menu tab is visible")
    public void theMenuTabIsVisible() {
        Assert.assertTrue("Menú tab should be visible in the bottom navigation bar.",
                nav().isMenuTabVisible());
    }

    @And("the user taps the Mi perfil tab")
    public void theUserTapsTheMiPerfilTab() {
        nav().tapMiPerfilTab();
    }

    // Unmapped downstream screen assertions (categories, prescriptions, profile, etc.) are
    // handled by CommonSteps#theUserIsOnScreen() via its generic regex pattern.
    // CommonSteps throws PendingException for all unmapped screens — no duplicate definitions needed here.
}

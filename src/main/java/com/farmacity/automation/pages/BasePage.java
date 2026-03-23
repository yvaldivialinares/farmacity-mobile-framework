package com.farmacity.automation.pages;

import com.farmacity.automation.config.AppiumConfig;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all Page Objects.
 *
 * Dual-locator strategy: every interaction method accepts a primary and an XPath fallback.
 * The primary locator (resource-id or content-desc) is attempted first; if it times out,
 * the XPath fallback is used transparently. This makes the suite resilient to minor DOM
 * refactors without requiring immediate locator updates.
 *
 * Rule: single-locator elements (no stable alternative in domain-rules) pass the same By
 * twice. This is intentional — the fallback simply retries the same locator.
 */
public abstract class BasePage {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(AppiumConfig.getExplicitWaitSeconds()));
    }

    /**
     * Finds an element using the primary locator; falls back to the XPath locator on timeout.
     */
    protected WebElement find(By primary, By xpathFallback) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(primary));
        } catch (TimeoutException e) {
            return wait.until(ExpectedConditions.presenceOfElementLocated(xpathFallback));
        }
    }

    /**
     * Finds a visible (displayed + enabled) element using dual-locator strategy.
     */
    protected WebElement findVisible(By primary, By xpathFallback) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(primary));
        } catch (TimeoutException e) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(xpathFallback));
        }
    }

    /**
     * Returns true if the element is present and displayed; false otherwise.
     * Does not throw — safe to use for conditional assertions.
     */
    protected boolean isVisible(By primary, By xpathFallback) {
        try {
            return findVisible(primary, xpathFallback).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Taps an element using dual-locator strategy.
     */
    protected void tap(By primary, By xpathFallback) {
        find(primary, xpathFallback).click();
    }

    /**
     * Clears the field and types the given text. Dual-locator aware.
     */
    protected void type(By primary, By xpathFallback, String text) {
        WebElement el = find(primary, xpathFallback);
        el.clear();
        el.sendKeys(text);
    }

    /**
     * Returns true if the element's 'enabled' attribute is true.
     */
    protected boolean isEnabled(By primary, By xpathFallback) {
        try {
            return find(primary, xpathFallback).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the value of the given attribute on an element.
     */
    protected String getAttribute(By primary, By xpathFallback, String attribute) {
        return find(primary, xpathFallback).getAttribute(attribute);
    }

    /**
     * Returns the visible text of an element.
     */
    protected String getText(By primary, By xpathFallback) {
        return find(primary, xpathFallback).getText();
    }

    /**
     * Takes a screenshot and returns it as a byte array.
     * Used by AppiumHooks on failure.
     */
    public byte[] takeScreenshot() {
        return driver.getScreenshotAs(OutputType.BYTES);
    }
}

# Farmacity Android Automation Framework

End-to-end mobile automation framework for the **Farmacity** Android application.

**Stack:** Java 11 · Appium 8.6.0 (UiAutomator2) · Cucumber 7 (Gherkin BDD) · JUnit 4 · Allure 2 · REST-Assured 5

**Screens covered:** welcome · user-login · code-login · code-verification · password-recovery · registration · home (7 of ~17 total screens)

---

## Prerequisites

| Requirement | Version |
|-------------|---------|
| Java JDK | 11+ |
| Maven | 3.8+ |
| Appium Server | 2.x (`npm install -g appium`) |
| Appium UiAutomator2 driver | `appium driver install uiautomator2` |
| Android SDK | API 30+ |
| Connected device or emulator | Running before test execution |

---

## Setup

### 1. Clone and build
```bash
git clone <repo-url>
cd farmacity-android-framework
mvn compile
```

### 2. Configure Appium
```bash
cp src/test/resources/appium.properties.example src/test/resources/appium.properties
```
Edit `appium.properties` and set your device UDID / emulator name and app package details.

### 3. Configure test data
```bash
cp src/test/resources/test-data.properties.example src/test/resources/test-data.properties
```
Fill in `valid_email`, `valid_password`, `user_first_name`, and `gender`.
Leave `valid_code` empty — it must be retrieved from email before each code-verification test run.

### 4. Configure API (optional)
```bash
cp src/test/resources/api.properties.example src/test/resources/api.properties
```

> **Important:** `*.properties` files (except `*.example`) are gitignored. Never commit credentials.

---

## Running Tests

### Start Appium server first
```bash
appium
```

### Run smoke tests (P0 — login + bottom nav)
```bash
mvn test -Dcucumber.filter.tags="@P0"
```

### Run critical regression (P0 + P1)
```bash
mvn test -Dcucumber.filter.tags="@P0 or @P1"
```

### Run a specific screen
```bash
mvn test -Dcucumber.filter.tags="@welcome-screen"
mvn test -Dcucumber.filter.tags="@user-login-screen"
mvn test -Dcucumber.filter.tags="@home-screen"
```

### Run full suite
```bash
mvn test
```

### Run API tests only (no Appium required)
```bash
mvn test -Dtest=ApiTestRunner
```

### Generate Allure report
```bash
mvn allure:serve
```

---

## Project Structure

See [`FRAMEWORK_MANIFEST.md`](FRAMEWORK_MANIFEST.md) for the full architecture description, locator strategy, data contracts, tag taxonomy, and instructions for adding new screens.

---

## Current Pending Tests

Several scenarios throw `PendingException` because downstream screens are not yet mapped:

| Scenario | Reason |
|----------|--------|
| Error message after invalid login | Error element locator not mapped |
| Navigation after code verification | Destination TBC |
| Navigation after registration step 1 | Destination TBC |
| Password recovery confirmation | confirmation-screen not mapped |
| 3 home-screen navigation tests | categories/prescriptions/profile screens unmapped |

**To unlock these tests:** run `domain-rules-pipeline` for the missing screens, then follow the "How to Add a New Screen" guide in `FRAMEWORK_MANIFEST.md`.

---

## Adding a New Screen

1. Run `domain-rules-pipeline` for the new screen
2. Create `{ScreenName}Page.java` extending `BasePage`
3. Add a `case` in `CommonSteps#verifyScreen()`
4. Add any shared steps to `SharedSteps.java`
5. Create `{ScreenName}Steps.java` for unique steps
6. Create `{screen-name}.feature`

Full guide: `FRAMEWORK_MANIFEST.md` → Section 8.

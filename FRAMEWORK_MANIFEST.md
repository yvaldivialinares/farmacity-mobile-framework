# FRAMEWORK MANIFEST — FARMACITY Android Automation Framework

> **Purpose:** LLM-readable architecture descriptor. Use this file when asking an AI agent to add new screens, step definitions, or Page Objects to the framework. Reference this manifest to understand the project's contracts, conventions, and extension points.
>
> **Stack:** Java 11 · Appium 8.6.0 · Cucumber 7.15.0 · JUnit 4.13.2 · Allure 2.24.0 · REST-Assured 5.4.0
> **App:** FARMACITY (Android)
> **Generated:** 2026-03-23 · Source: `domain-rules/FARMACITY/`

---

## 1. Package Structure

```
farmacity-android-framework/
├── pom.xml
├── README.md
├── FRAMEWORK_MANIFEST.md
├── .gitignore
└── src/
    ├── main/java/com/farmacity/automation/
    │   ├── config/
    │   │   ├── AppiumConfig.java          # Reads appium.properties; exposes UiAutomator2Options + server URL
    │   │   └── DriverManager.java         # ThreadLocal<AndroidDriver> registry
    │   ├── pages/
    │   │   ├── BasePage.java              # Dual-locator find/tap/type helpers; explicit wait
    │   │   ├── WelcomePage.java           # welcome-screen
    │   │   ├── UserLoginPage.java         # user-login-screen
    │   │   ├── CodeLoginPage.java         # code-login-screen
    │   │   ├── CodeVerificationPage.java  # code-verification-screen
    │   │   ├── PasswordRecoveryPage.java  # password-recovery-screen
    │   │   ├── RegistrationPage.java      # registration-screen
    │   │   ├── HomePage.java              # home-screen (content area)
    │   │   └── BottomNavPage.java         # Persistent bottom nav — shared across all auth screens
    │   ├── api/
    │   │   ├── ApiConfig.java             # Reads api.properties
    │   │   └── ApiClient.java             # RestAssured wrapper — GET/POST/PUT/DELETE
    │   └── utils/
    │       ├── TestData.java              # Typed getters from test-data.properties
    │       └── ScenarioContext.java       # ThreadLocal current-screen tracker for SharedSteps dispatch
    └── test/
        ├── java/com/farmacity/automation/
        │   ├── hooks/
        │   │   └── AppiumHooks.java        # @Before setUp, @After tearDown + screenshot on failure
        │   ├── runner/
        │   │   ├── TestRunner.java         # UI tests (@P0 or @P1 by default)
        │   │   └── ApiTestRunner.java      # API tests (api-features/ folder)
        │   └── steps/
        │       ├── CommonSteps.java        # Screen arrival/retention assertions; sets ScenarioContext
        │       ├── SharedSteps.java        # Context-aware dispatch for steps shared across screens
        │       ├── WelcomeSteps.java
        │       ├── UserLoginSteps.java
        │       ├── CodeLoginSteps.java
        │       ├── CodeVerificationSteps.java
        │       ├── PasswordRecoverySteps.java
        │       ├── RegistrationSteps.java  # Empty — all steps delegated to SharedSteps/CommonSteps
        │       ├── HomeSteps.java
        │       └── CommonApiSteps.java
        └── resources/
            ├── appium.properties           # Appium server + device capabilities (gitignored)
            ├── appium.properties.example   # Template committed to repo
            ├── test-data.properties        # Test credentials and params (gitignored)
            ├── test-data.properties.example
            ├── api.properties              # API base URL + auth (gitignored)
            ├── api.properties.example
            ├── features/                   # One .feature per mapped screen
            │   ├── welcome-screen.feature
            │   ├── user-login-screen.feature
            │   ├── code-login-screen.feature
            │   ├── code-verification-screen.feature
            │   ├── password-recovery-screen.feature
            │   ├── registration-screen.feature
            │   └── home-screen.feature
            └── api-features/
                └── sample-api.feature      # Template — replace with real API endpoints
```

---

## 2. Locator Strategy

Every element is represented by **two `By` fields** in its Page Object:

| Field naming convention | Locator type |
|-------------------------|-------------|
| `{camelCaseElement}` | Priority 1 — most stable available (resource-id `By.id()` or content-desc `By.xpath("//*[@content-desc='...']")`) |
| `{camelCaseElement}Xpath` | Priority 2 — explicit XPath with tag and attribute for fallback |

**Priority order (from domain-rules):**
1. `resource-id` → `By.id("the-id")`
2. `content-desc` (accessibility id) → `By.xpath("//*[@content-desc='label']")`
3. XPath with `@resource-id` → `By.xpath("//Tag[@resource-id='the-id']")`
4. XPath with `@content-desc` → `By.xpath("//Tag[@content-desc='label']")`
5. Text-based XPath (**FRAGILE**) → `By.xpath("//Tag[@text='literal text']")` — marked with `# FRAGILE` comments

`BasePage.find(primary, xpathFallback)` tries `primary` first; falls back to `xpathFallback` on `TimeoutException`.
If only one stable locator exists, pass the same `By` for both parameters.

**Known locator constraints:**
- `greeting-text` (welcome-screen): text-based only
- `recover-password-button` (password-recovery-screen): content-desc only (no resource-id)
- `search-bar` (home-screen): placeholder-text-based only
- `resend-code-link` (code-verification-screen): `clickable=false` TextView — may need parent ViewGroup tap

---

## 3. Data Contracts

All test data is stored in `test-data.properties` (gitignored). Getters in `TestData.java`:

| Variable key | TestData getter | Status | Notes |
|---|---|---|---|
| `valid_email` | `getValidEmail()` | `<FILL_ME>` | Registered Farmacity account email |
| `valid_password` | `getValidPassword()` | `<FILL_ME>` | Password for the account above |
| `invalid_password` | `getInvalidPassword()` | `x` (pre-filled) | Fixed short invalid password |
| `user_first_name` | `getUserFirstName()` | `<FILL_ME>` | First name shown in home-screen greeting |
| `invalid_email_format` | `getInvalidEmailFormat()` | `notanemail@` (pre-filled) | Used in button-enabled-state tests |
| `valid_code` | `getValidCode()` | `<FILL_ME>` | 6-digit email code — must be fresh per run |
| `first_name` | `getFirstName()` | `Juan` (pre-filled) | Registration profile form |
| `last_name` | `getLastName()` | `Pérez` (pre-filled) | Registration profile form |
| `gender` | `getGender()` | `<FILL_ME>` | Must match a valid selector option |
| `date_of_birth` | `getDateOfBirth()` | `01/01/1990` (pre-filled) | Registration date picker |

**Rule: never hardcode test data values in step definitions or feature files.**

---

## 4. ScenarioContext & SharedSteps Pattern

Some Gherkin step texts are identical across multiple screens (e.g. `the user enters valid_email in the Email input` appears in 4 feature files). Cucumber requires exactly one matching step definition. The solution:

1. `CommonSteps#theUserIsOnScreen(screenName)` calls `ScenarioContext.setCurrentScreen(screenName)` after verifying the screen.
2. `SharedSteps` reads `ScenarioContext.getCurrentScreen()` and dispatches to the correct Page Object.

Steps owned by SharedSteps:
- `the user enters valid_email in the Email input` → dispatches to UserLoginPage / CodeLoginPage / PasswordRecoveryPage / RegistrationPage
- `the user enters invalid_email_format in the Email input`
- `the user taps the Back button` → dispatches to CodeLoginPage / CodeVerificationPage / PasswordRecoveryPage / RegistrationPage
- `the user taps the Continue button` → dispatches to CodeVerificationPage / RegistrationPage
- `the Continue button is disabled` / `the Continue button is enabled`

---

## 5. Tag Taxonomy

| Tag | Meaning | Usage |
|-----|---------|-------|
| `@welcome-screen` | Feature-level scope tag | Filter by screen |
| `@user-login-screen` | Feature-level scope tag | |
| `@code-login-screen` | Feature-level scope tag | |
| `@code-verification-screen` | Feature-level scope tag | |
| `@password-recovery-screen` | Feature-level scope tag | |
| `@registration-screen` | Feature-level scope tag | |
| `@home-screen` | Feature-level scope tag | |
| `@api` | API test (runs without Appium) | |
| `@P0` | Smoke — run always | `mvn test -Dcucumber.filter.tags="@P0"` |
| `@P1` | Regression critical path | `mvn test -Dcucumber.filter.tags="@P0 or @P1"` |
| `@P2` | Extended regression | `mvn test -Dcucumber.filter.tags="@P0 or @P1 or @P2"` |
| `@happy-path` | Expected success flow | |
| `@negative` | Expected failure flow | |
| `@edge` | Boundary / edge case | |
| `@functional` | Feature behaviour (non-navigation) | |
| `@visual` | UI display assertion | |

---

## 6. Screen Inventory

| Screen ID | Page Object | Steps Class | Feature File | Status |
|-----------|-------------|-------------|--------------|--------|
| `welcome-screen` | WelcomePage | WelcomeSteps | welcome-screen.feature | ✅ Mapped |
| `user-login-screen` | UserLoginPage | UserLoginSteps | user-login-screen.feature | ✅ Mapped |
| `code-login-screen` | CodeLoginPage | CodeLoginSteps | code-login-screen.feature | ✅ Mapped |
| `code-verification-screen` | CodeVerificationPage | CodeVerificationSteps | code-verification-screen.feature | ✅ Mapped |
| `password-recovery-screen` | PasswordRecoveryPage | PasswordRecoverySteps | password-recovery-screen.feature | ✅ Mapped |
| `registration-screen` | RegistrationPage | RegistrationSteps | registration-screen.feature | ✅ Mapped |
| `home-screen` | HomePage + BottomNavPage | HomeSteps | home-screen.feature | ✅ Mapped |
| `categories-screen` | — | — | — | ❌ Unmapped |
| `prescriptions-screen` | — | — | — | ❌ Unmapped |
| `profile-screen` | — | — | — | ❌ Unmapped |
| `orders-screen` | — | — | — | ❌ Unmapped |
| `medications-screen` | — | — | — | ❌ Unmapped |
| `menu-screen` | — | — | — | ❌ Unmapped |
| `search-results-screen` | — | — | — | ❌ Unmapped |
| `profile-completion-screen` | — | — | — | ❌ Unmapped |
| `category-detail-screen` | — | — | — | ❌ Unmapped |
| `promotion-detail-screen` | — | — | — | ❌ Unmapped |
| `password-recovery-confirmation-screen` | — | — | — | ❌ Unmapped |

---

## 7. Pending Scenario Catalogue

Steps throwing `PendingException` and their unlock path:

| Step text | Reason | Unlock path |
|-----------|---------|-------------|
| `the error message is displayed` | No error element locator mapped | Run domain-rules-advisor on user-login-screen with invalid credentials; map error element; implement in CommonSteps |
| `the user is on the next screen` | code-verification-screen destination TBC | Confirm with backend; run domain-rules-advisor on post-registration screen |
| `the user is on the next registration step` | registration-screen destination TBC | Confirm with backend; run domain-rules-advisor on post-registration email-delivery screen |
| `a recovery link is sent to valid_email` | password-recovery-confirmation-screen unmapped | Run domain-rules-advisor on confirmation screen |
| `a new code is sent to valid_email` | Resend confirmation element not mapped | Test with real backend; map confirmation UI element |
| `the user is on the categories-screen` | categories-screen unmapped | Run domain-rules-pipeline for categories-screen |
| `the user is on the prescriptions-screen` | prescriptions-screen unmapped | Run domain-rules-pipeline for prescriptions-screen |
| `the user is on the profile-screen` | profile-screen unmapped | Run domain-rules-pipeline for profile-screen |

---

## 8. How to Add a New Screen

Follow these steps exactly when the domain-rules-pipeline has been run for a new screen:

### Step 1 — Run domain-rules-pipeline
```
@orchestrators/domain-rules-pipeline.md Run the domain-rules pipeline for:
App: FARMACITY
Screen: {screen-name}
Navigation context:
  - Preconditions: ...
  - Previous screen trigger: ...
```

### Step 2 — Create Page Object
1. Create `{ScreenName}Page.java` in `src/main/java/com/farmacity/automation/pages/`
2. Extend `BasePage`
3. For each element in `## Elements & Locators`:
   - Declare `private final By {elementName} = ...;` (Priority 1)
   - Declare `private final By {elementName}Xpath = ...;` (Priority 2 XPath)
4. Add action methods from `## Automation Hints > Step-to-Method Mappings`
5. Add `isOnScreen()` using a unique landmark element

### Step 3 — Add to CommonSteps#verifyScreen()
```java
case "{screen-name}":
    return new {ScreenName}Page(driver()).isOnScreen();
```

### Step 4 — Add to SharedSteps (if shared step text applies)
If the new screen uses step texts also used by other screens (e.g. email input, back button),
add a `case "{screen-name}":` branch in the relevant SharedSteps switch statements.

### Step 5 — Create step class
1. Create `{ScreenName}Steps.java` in `src/test/java/com/farmacity/automation/steps/`
2. Add only steps whose text is **unique** to this screen
3. All shared steps go in SharedSteps.java

### Step 6 — Create feature file
1. Create `{screen-name}.feature` in `src/test/resources/features/`
2. Copy Gherkin verbatim from `## Test Cases` in the domain-rules `.md` file
3. Add Feature-level `@{screen-name}` tag
4. Add `@P0`/`@P1`/`@P2` and type tags per scenario
5. Extract common navigation prefix as `Background:`
6. Add `# NOTE: PENDING` comment listing unmapped destinations

### Step 7 — Resolve PendingExceptions
Remove any `PendingException` stubs in CommonSteps or HomeSteps that referred to the newly mapped screen.

---

## 9. Known Limitations & Technical Debt

| Item | Screen | Severity | Notes |
|------|--------|----------|-------|
| Error message locator not mapped | user-login-screen | High | Negative test cannot assert error — only navigation |
| `resend-code-link` clickable=false | code-verification-screen | Medium | May need parent ViewGroup tap |
| `recover-password-button` no resource-id | password-recovery-screen | Low | content-desc only — fragile if app localizes |
| `header-back-text` vs `icon-button` | registration-screen | Low | Back button resource-id inconsistency with all other screens |
| Date picker interaction not implemented | code-verification-screen | Medium | `selectDateOfBirth()` opens picker but does not select a date |
| Gender selector options unknown | code-verification-screen | Medium | Must inspect dropdown to confirm valid values before filling `gender` in test-data.properties |
| Greeting text uses text-based XPath | welcome-screen, home-screen | Low | Works until app localizes "¡Hola!" |
| Search bar no stable locator | home-screen | Low | Placeholder text XPath only |
| 10 unmapped downstream screens | home-screen | High | All authenticated-area navigation steps are pending |
| `valid_code` must be fresh per run | code-verification-screen | High | Cannot be pre-filled — requires email access before each test run |

---

## 10. Running the Framework

```bash
# Setup
cp src/test/resources/appium.properties.example src/test/resources/appium.properties
cp src/test/resources/test-data.properties.example src/test/resources/test-data.properties
cp src/test/resources/api.properties.example src/test/resources/api.properties
# Fill in the .properties files

# Start Appium server (separate terminal)
appium

# Run smoke tests (P0 only)
mvn test -Dcucumber.filter.tags="@P0"

# Run critical regression (P0 + P1)
mvn test -Dcucumber.filter.tags="@P0 or @P1"

# Run by screen
mvn test -Dcucumber.filter.tags="@welcome-screen"

# Run all UI tests
mvn test

# Run API tests only
mvn test -Dtest=ApiTestRunner

# Generate Allure report
mvn allure:serve
```

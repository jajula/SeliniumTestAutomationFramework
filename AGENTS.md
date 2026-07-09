# AGENTS.md

## Cursor Cloud specific instructions

### What this project is
A Java + Maven **Selenium Page Object Model** test-automation framework (TestNG, Log4j2,
ExtentReports, Apache POI). There is no long-running app/server — the "application" is the
TestNG suite itself, which drives a headless Chrome browser against the live demo site
`https://the-internet.herokuapp.com/login` and asserts on the login flash message.

### Environment (already provided by the update script / base image)
- **Java 21** and **Maven 3.8.7** are available (README asks for 17+/3.9+; 3.8.7 works fine).
- **Google Chrome** is preinstalled; `WebDriverManager` auto-downloads the matching chromedriver
  on first run (needs network egress to Chrome-for-Testing endpoints).
- Running tests requires **network access** to `the-internet.herokuapp.com`.

### How to run (build + run the suite)
```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

- **IMPORTANT gotcha:** always pass `-Dbrowser=chrome` (and prefer `-Dheadless=true`). The
  surefire config maps `<browser>${browser}</browser>`; when the `browser` property is not set on
  the command line it resolves to an empty string that overrides the `config.properties` default,
  causing `IllegalArgumentException: Unsupported browser:`. Plain `mvn test` (as shown in the
  README) fails for this reason — always supply the property explicitly.
- Firefox is not installed; use `chrome` only.
- Headless is required (no display in the cloud VM).

### Outputs (written under `target/`, gitignored)
- ExtentReport HTML: `target/extent/extent-report.html`
- Log file: `target/logs/framework.log`
- Auto-generated test data (created on first run): `target/testdata/LoginTestData.xlsx`

### Notes
- The `WARNING: Unable to find CDP implementation matching <chrome version>` messages are
  harmless version-mismatch warnings from Selenium DevTools and do not affect the tests.
- There is no separate lint step configured; `mvn test` compiles and runs the suite.

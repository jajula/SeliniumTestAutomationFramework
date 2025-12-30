## Playwright Test Automation Framework (POM)

Java Playwright **Page Object Model** test framework with:
- **TestNG**
- **Log4j2**
- **ExtentReports** (Spark HTML)
- **Excel** data source (Apache POI) for data-driven tests

### Prereqs
- Java **17+**
- Maven **3.9+**

### Install Playwright browsers (first time only)

```bash
mvn -q test -Dtestng.dtd.http=true
```

If your environment doesn't auto-download browsers, run:

```bash
mvn -q exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
```

### Run tests

```bash
mvn test
```

Optional overrides:

```bash
# browser: chromium|firefox|webkit
mvn test -Dbrowser=firefox

# headless true|false
mvn test -Dheadless=true

# use a custom Excel file location
mvn test -DexcelPath=/absolute/path/to/LoginTestData.xlsx
```

### Reports & logs
- **Extent report**: `target/extent/extent-report.html`
- **Log file**: `target/logs/framework.log`

### Excel test data
On first run the framework generates a sample Excel workbook at:
- `target/testdata/LoginTestData.xlsx` (sheet name: `Login`)


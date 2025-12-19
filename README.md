"# Selenium Test Automation Framework (POM)"

Java Selenium **Page Object Model** test framework with:
- **TestNG**
- **Log4j2**
- **ExtentReports** (Spark HTML)
- **Excel** data source (Apache POI) for data-driven tests

## How to run

### Prereqs
- Java **17+**
- Maven **3.9+**
- Chrome or Firefox installed (default: **Chrome**)

### Run tests

```bash
mvn test
```

Optional overrides:

```bash
# choose browser: chrome|firefox
mvn test -Dbrowser=firefox

# run headless or headed
mvn test -Dheadless=true

# use a custom Excel file location
mvn test -DexcelPath=/absolute/path/to/LoginTestData.xlsx
```

## Reports & logs
- **Extent report**: `target/extent/extent-report.html`
- **Log file**: `target/logs/framework.log`

## Excel test data
On first run the framework generates a sample Excel workbook at:
- `target/testdata/LoginTestData.xlsx` (sheet name: `Login`)

Columns:
- `testName`, `username`, `password`, `expectedContains`, `run` (Y/N)

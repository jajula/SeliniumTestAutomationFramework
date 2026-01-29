# Python Selenium Test Automation Framework (POM)

Python equivalent of the Java Selenium Page Object Model framework in this repo.

Features:
- **pytest** test runner
- **Selenium WebDriver** with **webdriver-manager**
- **Excel** data source (openpyxl) for data-driven tests
- **Logging** to console + file
- **Screenshots on failure** (saved to target/screenshots)
- Optional **HTML report** with pytest-html

## Prereqs
- Python **3.10+**
- Chrome or Firefox installed (default: **Chrome**)

## Setup

```bash
cd python
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

## Run tests

```bash
cd python
pytest
```

Optional overrides:

```bash
# choose browser: chrome|firefox
pytest --browser=firefox

# run headless or headed
pytest --headless=true

# override base URL
pytest --base-url=https://the-internet.herokuapp.com/login

# use a custom Excel file location
pytest --excel-path=/absolute/path/to/LoginTestData.xlsx

# wait overrides (seconds)
pytest --implicit-wait=2 --explicit-wait=10
```

## Reports & logs
- **Log file**: `target/logs/framework.log`
- **Screenshots**: `target/screenshots/`
- **HTML report** (optional): `pytest --html=target/extent/extent-report.html --self-contained-html`

## Excel test data
On first run the framework generates a sample Excel workbook at:
- `target/testdata/LoginTestData.xlsx` (sheet name: `Login`)

Columns:
- `testName`, `username`, `password`, `expectedContains`, `run` (Y/N)

import os

import pytest

from framework.driver.driver_factory import init_driver, quit_driver
from framework.utils.config_reader import get
from framework.utils.logger import configure_logging
from framework.utils.screenshot_utils import save_screenshot


def pytest_addoption(parser):
    group = parser.getgroup("selenium")
    group.addoption("--browser", action="store", help="Browser: chrome|firefox")
    group.addoption("--headless", action="store", help="Headless: true|false")
    group.addoption("--base-url", action="store", help="Override baseUrl")
    group.addoption("--excel-path", action="store", help="Override excelPath")
    group.addoption("--implicit-wait", action="store", help="Implicit wait seconds")
    group.addoption("--explicit-wait", action="store", help="Explicit wait seconds")


def _apply_cli_overrides(config):
    overrides = {
        "browser": config.getoption("--browser"),
        "headless": config.getoption("--headless"),
        "baseUrl": config.getoption("--base-url"),
        "excelPath": config.getoption("--excel-path"),
        "implicitWaitSeconds": config.getoption("--implicit-wait"),
        "explicitWaitSeconds": config.getoption("--explicit-wait"),
    }

    for key, value in overrides.items():
        if value is not None and str(value).strip() != "":
            os.environ[key] = str(value)


def pytest_configure(config):
    _apply_cli_overrides(config)
    configure_logging()


@pytest.fixture(autouse=True)
def driver_setup():
    driver = init_driver()
    driver.get(get("baseUrl"))
    yield driver
    quit_driver()


@pytest.hookimpl(hookwrapper=True)
def pytest_runtest_makereport(item, call):
    outcome = yield
    report = outcome.get_result()
    if report.when != "call" or not report.failed:
        return

    safe_name = report.nodeid.replace("::", "_").replace("/", "_")
    screenshot_path = save_screenshot(safe_name)

    if screenshot_path is None:
        return

    pytest_html = item.config.pluginmanager.getplugin("html")
    if pytest_html:
        extra = getattr(report, "extra", [])
        extra.append(pytest_html.extras.png(str(screenshot_path)))
        report.extra = extra

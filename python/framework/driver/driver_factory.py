import logging

from selenium import webdriver
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.firefox.service import Service as FirefoxService
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager

from framework.driver.driver_manager import DriverManager
from framework.utils.config_reader import get, get_bool, get_int

log = logging.getLogger(__name__)


def init_driver():
    browser = get("browser").strip().lower()
    headless = get_bool("headless", False)

    if browser == "firefox":
        options = FirefoxOptions()
        if headless:
            options.add_argument("-headless")
        service = FirefoxService(GeckoDriverManager().install())
        driver = webdriver.Firefox(service=service, options=options)
    elif browser == "chrome":
        options = ChromeOptions()
        if headless:
            options.add_argument("--headless=new")
        options.add_argument("--no-sandbox")
        options.add_argument("--disable-dev-shm-usage")
        service = ChromeService(ChromeDriverManager().install())
        driver = webdriver.Chrome(service=service, options=options)
    else:
        raise ValueError(f"Unsupported browser: {browser}")

    implicit_wait = get_int("implicitWaitSeconds", 2)
    driver.implicitly_wait(implicit_wait)

    try:
        driver.maximize_window()
    except Exception:
        driver.set_window_size(1920, 1080)

    DriverManager.set_driver(driver)
    log.info("Initialized WebDriver. browser=%s, headless=%s", browser, headless)
    return driver


def quit_driver():
    try:
        driver = DriverManager.get_driver()
        driver.quit()
    except Exception:
        pass
    finally:
        DriverManager.unload()

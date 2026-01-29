from selenium.webdriver.support import expected_conditions as ec
from selenium.webdriver.support.ui import WebDriverWait

from framework.driver.driver_manager import DriverManager
from framework.utils.config_reader import get_int


class BasePage:
    def __init__(self):
        seconds = get_int("explicitWaitSeconds", 10)
        self.wait = WebDriverWait(DriverManager.get_driver(), seconds)

    def visible(self, locator):
        return self.wait.until(ec.visibility_of_element_located(locator))

    def type(self, locator, value):
        element = self.visible(locator)
        element.clear()
        element.send_keys(value)

    def click(self, locator):
        self.wait.until(ec.element_to_be_clickable(locator)).click()

    def text(self, locator):
        return self.visible(locator).text

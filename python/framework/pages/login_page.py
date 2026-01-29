from selenium.webdriver.common.by import By

from framework.pages.base_page import BasePage


class LoginPage(BasePage):
    def __init__(self):
        super().__init__()
        self.username = (By.ID, "username")
        self.password = (By.ID, "password")
        self.login_btn = (By.CSS_SELECTOR, "button[type='submit']")
        self.flash = (By.ID, "flash")

    def enter_username(self, value):
        self.type(self.username, value)
        return self

    def enter_password(self, value):
        self.type(self.password, value)
        return self

    def click_login(self):
        self.click(self.login_btn)
        return self

    def get_flash_message(self):
        return self.text(self.flash)

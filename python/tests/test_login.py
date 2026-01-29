import logging

import pytest

from framework.pages.login_page import LoginPage
from framework.utils.excel_data_provider import login_excel

log = logging.getLogger(__name__)

DATA = login_excel()


@pytest.mark.parametrize(
    "test_name, username, password, expected_contains, run",
    DATA,
    ids=[row[0] for row in DATA] if DATA else None,
)
def test_login_from_excel(test_name, username, password, expected_contains, run):
    log.info("Running testCase=%s", test_name)

    page = LoginPage()
    page.enter_username(username).enter_password(password).click_login()

    msg = page.get_flash_message()
    assert expected_contains in msg, (
        f"Expected message to contain: {expected_contains} but was: {msg}"
    )

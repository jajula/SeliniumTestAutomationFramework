from pathlib import Path

from framework.utils.config_reader import get_optional
from framework.utils.excel_utils import ensure_sample_login_workbook, read_sheet
from framework.utils.paths import TARGET_DIR


def _excel_path():
    override = get_optional("excelPath")
    if override:
        return Path(override)
    return TARGET_DIR / "testdata" / "LoginTestData.xlsx"


def login_excel():
    file_path = _excel_path()
    ensure_sample_login_workbook(file_path)

    raw = read_sheet(file_path, "Login")
    if not raw:
        return []

    rows = []
    for row in raw:
        if len(row) < 5:
            continue
        run = str(row[4]).strip()
        if run.upper() == "Y":
            rows.append(tuple(row))

    return rows

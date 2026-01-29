from datetime import datetime

from framework.driver.driver_manager import DriverManager
from framework.utils.paths import TARGET_DIR


def screenshot_base64():
    try:
        return DriverManager.get_driver().get_screenshot_as_base64()
    except Exception:
        return None


def save_screenshot(name):
    try:
        safe_name = "".join(c if c.isalnum() or c in "-_." else "_" for c in name)
        stamp = datetime.utcnow().strftime("%Y%m%d_%H%M%S_%f")
        out_dir = TARGET_DIR / "screenshots"
        out_dir.mkdir(parents=True, exist_ok=True)
        path = out_dir / f"{safe_name}_{stamp}.png"
        DriverManager.get_driver().save_screenshot(str(path))
        return path
    except Exception:
        return None

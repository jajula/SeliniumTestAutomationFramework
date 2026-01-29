import logging.config
import sys

from framework.utils.paths import CONFIG_DIR, TARGET_DIR


def configure_logging():
    log_dir = TARGET_DIR / "logs"
    log_dir.mkdir(parents=True, exist_ok=True)

    config_path = CONFIG_DIR / "logging.ini"
    if not config_path.exists():
        logging.basicConfig(level=logging.INFO)
        return

    logging.config.fileConfig(
        config_path,
        defaults={"sys": sys},
        disable_existing_loggers=False,
    )

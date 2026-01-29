from threading import local


class DriverManager:
    _store = local()

    @classmethod
    def get_driver(cls):
        driver = getattr(cls._store, "driver", None)
        if driver is None:
            raise RuntimeError("WebDriver is not initialized for this thread.")
        return driver

    @classmethod
    def set_driver(cls, driver):
        cls._store.driver = driver

    @classmethod
    def unload(cls):
        if hasattr(cls._store, "driver"):
            del cls._store.driver

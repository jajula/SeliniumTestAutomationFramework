import os

from framework.utils.paths import CONFIG_DIR

_PROPS = None


def _load_properties():
    global _PROPS
    if _PROPS is not None:
        return

    path = CONFIG_DIR / "config.properties"
    if not path.exists():
        raise RuntimeError(f"config.properties not found at {path}")

    props = {}
    for line in path.read_text(encoding="utf-8").splitlines():
        line = line.strip()
        if not line or line.startswith("#"):
            continue
        if "=" not in line:
            continue
        key, value = line.split("=", 1)
        props[key.strip()] = value.strip()

    _PROPS = props


def get(key):
    _load_properties()
    env = os.getenv(key)
    if env is not None and str(env).strip() != "":
        return env
    if key in _PROPS:
        return _PROPS[key]
    raise KeyError(f"Missing config key: {key}")


def get_optional(key):
    _load_properties()
    env = os.getenv(key)
    if env is not None and str(env).strip() != "":
        return env
    return _PROPS.get(key)


def get_bool(key, default_value=False):
    try:
        value = get(key)
    except Exception:
        return default_value
    return str(value).strip().lower() == "true"


def get_int(key, default_value=0):
    try:
        value = get(key)
    except Exception:
        return default_value
    try:
        return int(str(value).strip())
    except ValueError:
        return default_value

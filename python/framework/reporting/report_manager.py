from framework.utils.paths import TARGET_DIR


def ensure_extent_dir():
    out_dir = TARGET_DIR / "extent"
    out_dir.mkdir(parents=True, exist_ok=True)
    return out_dir

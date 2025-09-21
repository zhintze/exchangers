package jackyy.exchangers.util;

public enum ExchangerMode {
    PLANE("Plane"),
    HORIZONTAL_COLUMN("Horizontal Column"),
    VERTICAL_COLUMN("Vertical Column");

    private final String displayName;

    ExchangerMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ExchangerMode next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public static ExchangerMode fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            return PLANE;
        }
        return values()[ordinal];
    }
}
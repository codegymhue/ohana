package vn.ohana.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusUtility {
    SHOW("SHOW"), HIDDEN("HIDDEN");
    private final String value;

    StatusUtility(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static StatusUtility parseStatusUtility(String value) {
        StatusUtility[] values = values();
        for (StatusUtility statusUtility : values) {
            if (statusUtility.value.equals(value.toUpperCase())) return statusUtility;
        }
        throw new IllegalArgumentException(value + " invalid!");
    }
}

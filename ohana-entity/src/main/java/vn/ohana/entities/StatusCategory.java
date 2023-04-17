package vn.ohana.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCategory {
    SHOW("SHOW"), HIDDEN("HIDDEN");
    private final String value;

    StatusCategory(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static StatusCategory parseStatusCategory(String value) {
        StatusCategory[] values = values();
        for (StatusCategory statusCategory : values) {
            if (statusCategory.value.equals(value.toUpperCase())) return statusCategory;
        }
        throw new IllegalArgumentException(value + " invalid!");
    }
}

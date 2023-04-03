package vn.ohana.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {
    CONFIRM_EMAIL("CONFIRM_EMAIL"), ACTIVATED("ACTIVATED"), DEACTIVATED("DEACTIVATED");
    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static UserStatus parseUserStatus(String value) {
        UserStatus[] values = values();
        for (UserStatus status : values) {
            if (status.value.equals(value)) return status;
        }
        throw new IllegalArgumentException(value + "invalid");
    }
}

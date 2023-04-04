package vn.ohana.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ADMIN("ADMIN"), USER("USER");
    private String value;

    Role(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Role parseUserStatus(String value) {
        Role[] roles = values();
        for (Role role: roles) {
            if (role.value.equals(value)) return role;
        }
        throw new IllegalArgumentException(value + "invalid");
    }
}

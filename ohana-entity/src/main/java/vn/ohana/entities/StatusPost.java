package vn.ohana.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPost {
    PENDING_REVIEW ("PENDING_REVIEW"),
    PUBLISHED("PUBLISHED"),
    REFUSED("REFUSED"),
    DRAFT("DRAFT"),
    DELETED("DELETED"),
    OVER_ROOM("OVER_ROOM"),
    EMPTY_ROOM("EMPTY_ROOM");

    private final String value;

    StatusPost(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static StatusPost parseStatusPosts(String value) {
        StatusPost[] values = values();
        for (StatusPost statusPost : values) {
            if (statusPost.value.equals(value)) return statusPost;
        }
        throw new IllegalArgumentException(value + "invalid");
    }

}

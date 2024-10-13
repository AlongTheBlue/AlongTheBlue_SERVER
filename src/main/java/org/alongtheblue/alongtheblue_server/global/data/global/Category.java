package org.alongtheblue.alongtheblue_server.global.data.global;

public enum Category {
    RESTAURANT("restaurant"),
    CAFE("cafe"),
    ACCOMMODATION("accommodation"),
    TOURDATA("tourData");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

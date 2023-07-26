package com.springboot.systems.test.models.enums;

import org.apache.commons.lang.StringUtils;

public enum Actions {
    VIEW("View"),
    UPDATE("Update"),
    DELETE("Delete"),
    ADD("Add"),
    YES("Yes"),
    NO("No"),
    AUTHORISE("Authorise"),
    ENABLE("Enable"),
    DISABLE("Disable"),
    FETCH_CLIENTS("Fetch Clients"),
    UPDATE_CLIENTS("Update Clients"),
    BILL("Bill"),
    DONT_BILL("Don't Bill");

    private String status;

    Actions(String status) {
        this.status = status;
    }

    public static final Actions getEnumObject(String value) {
        if (StringUtils.isBlank(value))
            return null;
        for (Actions object : Actions.values()) {
            if (object.getName().equals(value))
                return object;
        }
        return null;
    }

    public String getName() {
        return this.status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}


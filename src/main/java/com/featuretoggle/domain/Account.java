package com.featuretoggle.domain;

import java.util.UUID;

public class Account extends Identifiable {
    private String name;

    public Account(UUID id, String name) {
        super(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("An Account name cannot be null nor empty");
        }
        this.name = name;
    }
}

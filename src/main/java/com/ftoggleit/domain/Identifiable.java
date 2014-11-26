package com.ftoggleit.domain;

import java.util.UUID;

public abstract class Identifiable {
    private UUID id;

    protected Identifiable(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("The ID for a " + this.getClass() + " cannot be null");
        }
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}

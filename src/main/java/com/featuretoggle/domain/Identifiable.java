package com.featuretoggle.domain;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Identifiable that = (Identifiable) o;

        if(id == null) {
            return that.id == null;
        } else {
            return id.equals(that.id);
        }
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

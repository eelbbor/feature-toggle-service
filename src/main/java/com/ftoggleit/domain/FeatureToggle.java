package com.ftoggleit.domain;

import java.util.UUID;

public class FeatureToggle {
    private UUID id;
    private UUID scopeId;
    private String name;
    private boolean enabled;

    public FeatureToggle(UUID id, String name) {
        this(id, name, false);
    }

    public FeatureToggle(UUID id, String name, boolean isEnabled) {
        this(id, name, isEnabled, null);
    }

    public FeatureToggle(UUID id, String name, UUID scopeId) {
        this(id, name, false, scopeId);
    }

    public FeatureToggle(UUID id, String name, boolean isEnabled, UUID scopeId) {
        if(id == null) {
            throw new IllegalArgumentException("The ID for a " + this.getClass() + " cannot be null");
        }
        this.id = id;
        this.name = name;
        this.enabled = isEnabled;
        this.scopeId = scopeId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getScopeId() {
        return scopeId;
    }

    public void setScopeId(UUID scopeId) {
        this.scopeId = scopeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

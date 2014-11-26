package com.ftoggleit.domain;

import java.util.UUID;

public class Toggle extends Identifiable {
    private UUID accountId;
    private UUID scopeId;
    private String name;
    private boolean enabled;

    public Toggle(UUID id, UUID accountId, String name) {
        this(id, accountId, name, false);
    }

    public Toggle(UUID id, UUID accountId, String name, boolean isEnabled) {
        this(id, accountId,name, isEnabled, null);
    }

    public Toggle(UUID id, UUID accountId, String name, UUID scopeId) {
        this(id, accountId,name, false, scopeId);
    }

    public Toggle(UUID id, UUID accountId, String name, boolean isEnabled, UUID scopeId) {
        super(id);
        setAccountId(accountId);
        this.name = name;
        this.enabled = isEnabled;
        this.scopeId = scopeId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        if(accountId == null) {
            throw new IllegalArgumentException("Customer ID cannot be NULL for a Toggle");
        }
        this.accountId = accountId;
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

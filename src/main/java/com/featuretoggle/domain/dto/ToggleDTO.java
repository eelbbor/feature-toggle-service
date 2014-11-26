package com.featuretoggle.domain.dto;

import com.featuretoggle.domain.Toggle;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "")
public class ToggleDTO extends IdentifiableDTO {
    private String accountId;
    private String scopeId;
    private String name;
    private boolean enabled;

    //need the default constructor for response IO
    public ToggleDTO() {
        this(null);
    }

    public ToggleDTO(Toggle toggle) {
        super(toggle);
        if (toggle != null) {
            accountId = toggle.getAccountId().toString();
            scopeId = toggle.getScopeId() == null ? null : toggle.getScopeId().toString();
            name = toggle.getName();
            enabled = toggle.isEnabled();
        }
    }

    @Override
    protected Class getTypeClass() {
        return Toggle.class;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
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

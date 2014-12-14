package com.featuretoggle.domain.dto;

import com.featuretoggle.domain.Toggle;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = ToggleDTO.TYPE)
public class ToggleDTO extends IdentifiableDTO {
    public static final String TYPE = "toggle";
    private String accountId;
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
            name = toggle.getName();
            enabled = toggle.isEnabled();
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
